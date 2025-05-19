package com.Spring.ecommerce.service.Product;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.Spring.ecommerce.dto.ImageDto;
import com.Spring.ecommerce.dto.ProductDto;
import com.Spring.ecommerce.exceptions.ProductNotFoundException;
import com.Spring.ecommerce.exceptions.ResourceNotFoundException;
import com.Spring.ecommerce.model.Category;
import com.Spring.ecommerce.model.Image;
import com.Spring.ecommerce.model.Product;
import com.Spring.ecommerce.repository.CategoryRepository;
import com.Spring.ecommerce.repository.ImageRepository;
import com.Spring.ecommerce.repository.ProductRepository;
import com.Spring.ecommerce.requests.AddProductRequest;
import com.Spring.ecommerce.requests.UpdateProductRequest;
@Service
public class ProductService implements ProductServiceI{
	
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;
    // Single constructor for both dependencies
    
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository,
			ModelMapper modelMapper, ImageRepository imageRepository) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
		this.modelMapper = modelMapper;
		this.imageRepository = imageRepository;
	}
    
    
    
	@Override
	public Product addProduct(AddProductRequest request) {

		Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
				.orElseGet(()-> {
					Category newCategory = new Category(request.getCategory().getName());
					return categoryRepository.save(newCategory);
				});
		request.setCategory(category);
		return productRepository.save(createProduct(request,category));
//		Category category = categoryRepository.findByName(request.getCategory().getName());
//
//		if (category == null) {
//		    category = new Category(request.getCategory().getName());
//		    category = categoryRepository.save(category);
//		}
//
//		request.setCategory(category);
//
//		return productRepository.save(createProduct(request, category));

	}
	
	public Product createProduct(AddProductRequest request, Category category) {
		return new Product(
				request.getName(),
				request.getBrand(),
				request.getPrice(),
				request.getInventory(),
				request.getDescription(),
				category
			);
	}
	@Override
	public Product getProductById(Long id) {
		
		return productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product Not Found with "+id));
	}

	@Override
	public void deleteProductById(Long id) {
		
		productRepository.findById(id).ifPresent(productRepository::delete);
		//repository.findById(id).ifPresent(product -> repository.delete(product)
	}

	@Override
	public Product updateProduct(UpdateProductRequest request, Long id) {
//		Product updatedProduct = updateExistingProduct(request,productRepository.findById(id).get());
//		productRepository.save(updatedProduct);
		return productRepository.findById(id)
				.map(existingProduct->updateExistingProduct(request,existingProduct))
				.map(productRepository::save)
				.orElseThrow(()-> new ProductNotFoundException("Product Not Found with "+id));
	}
	public Product updateExistingProduct(UpdateProductRequest request,Product existProduct) {
		existProduct.setName(request.getName());
		existProduct.setBrand(request.getBrand());
		//existProduct.setCategory(request.getCategory());
		existProduct.setDescription(request.getDescription());
		existProduct.setInventory(request.getInventory());
		existProduct.setPrice(request.getPrice());
		Category category = categoryRepository.findByName(request.getCategory().getName());
		existProduct.setCategory(category);
		return existProduct;
	}
	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public List<Product> getProductsByCategory(String category) {
		return productRepository.findAllByCategoryName(category);
	}

	@Override
	public List<Product> getProductsByBrand(String Brand) {
		return productRepository.findAllByBrand(Brand);
	}

	@Override
	public List<Product> getProductsByName(String name) {
		return productRepository.findAllByName(name);
	}

	@Override
	public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
		return productRepository.findAllByCategoryNameAndBrand(category,brand);
	}

	@Override
	public List<Product> getProductsByCategoryAndName(String category, String name) {
		return productRepository.findAllByCategoryNameAndName(category,name);
	}

	@Override
	public List<Product> getProductsByNameAndBrand(String name, String brand) {
		return productRepository.findAllByNameAndBrand(name,brand);
	}

	@Override
	public Long countProductsByBrandAndName(String brand, String name) {
		return productRepository.countByBrandAndName(brand,name);
	}
	public List<ProductDto> getConvertedProducts(List<Product> products){
		return products.stream().map(this::convertToDto).toList();
	}
	public ProductDto convertToDto(Product product) {
		ProductDto productDto = modelMapper.map(product,ProductDto.class);
		List<Image> images = imageRepository.findByProductId(product.getId());
		List<ImageDto> imageDtos = images.stream()
									.map(image->modelMapper.map(image,ImageDto.class)).toList();
		productDto.setImages(imageDtos);
		return productDto;
	}
	
}