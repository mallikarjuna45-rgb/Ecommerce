package com.Spring.ecommerce.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Spring.ecommerce.dto.ProductDto;
import com.Spring.ecommerce.exceptions.ResourceNotFoundException;
import com.Spring.ecommerce.model.Product;
import com.Spring.ecommerce.requests.AddProductRequest;
import com.Spring.ecommerce.responce.ApiResponse;
import com.Spring.ecommerce.service.Product.ProductService;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
		
		
		private final ProductService productService;
		public ProductController(ProductService productService) {
			this.productService = productService;
		}
		@GetMapping("/all")
		public ResponseEntity<ApiResponse> getAllProducts(){
			List<Product> products = productService.getAllProducts();
			List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
			return ResponseEntity.ok(new ApiResponse("success",convertedProducts));
			
		}
		

		@GetMapping("/product/{productId}/product")
		public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId){
			try {
				Product product = productService.getProductById(productId);
				ProductDto convertedProducts = productService.convertToDto(product);
				return ResponseEntity.ok(new ApiResponse("success",convertedProducts));
			} catch (ResourceNotFoundException e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
			}
		}
		@GetMapping("/products/{productName}/products")
		public ResponseEntity<ApiResponse> getProductById(@PathVariable String productName){
			try {
				List<Product> products = productService.getProductsByName(productName);
				
				if (products.isEmpty()) {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body((new ApiResponse("No Products Found",null)));
				}
				return ResponseEntity.ok(new ApiResponse("sucess",products));
			} catch (ResourceNotFoundException e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
			}
		}
		 @GetMapping("/products/{category}/all/products")
		    public ResponseEntity<ApiResponse> findProductByCategory(@PathVariable String category) {
		        try {
		            List<Product> products = productService.getProductsByCategory(category);
		            if (products.isEmpty()) {
		                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No products found ", null));
		            }
		            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
		            return  ResponseEntity.ok(new ApiResponse("success", convertedProducts));
		        } catch (Exception e) {
		            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
		        }
		    }
		@GetMapping("/product/by-brand")
		public ResponseEntity<ApiResponse> findProductByBrand(@RequestParam String brand){
			try {
				List<Product> products = productService.getProductsByBrand(brand);
				List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
				if (products.isEmpty()) {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body((new ApiResponse("No Products Found",null)));
				}
				return ResponseEntity.ok(new ApiResponse("sucess",convertedProducts));
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
			}
		}
		
		@GetMapping("/products/by/category-and-name")
		public ResponseEntity<ApiResponse> getProductByCategoryAndName(@RequestParam String CategoryName, @RequestParam String name){
			try {
				List<Product> products = productService.getProductsByCategoryAndName(CategoryName,name);
				List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
				if(products.isEmpty())
					return ResponseEntity.ok(new ApiResponse("No Products Found",null));
				return ResponseEntity.ok(new ApiResponse("success",convertedProducts));
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
			}
		}
		@GetMapping("/products/by/category-and-brand")
		public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String CategoryName, @RequestParam String brandName){
			try {
				List<Product> products = productService.getProductsByCategoryAndBrand(CategoryName,brandName);
				List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
				if(products.isEmpty())
					return ResponseEntity.ok(new ApiResponse("No Products Found",null));
				return ResponseEntity.ok(new ApiResponse("success",convertedProducts));
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
			}
		}
		@GetMapping("/products/by/brand-and-name")
		public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName, @RequestParam String name){
			try {
				List<Product> products = productService.getProductsByNameAndBrand(name, brandName);
				List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
				if(products.isEmpty())
					return ResponseEntity.ok(new ApiResponse("No Products Found",null));
				return ResponseEntity.ok(new ApiResponse("success",convertedProducts));
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
			}
		}
		@GetMapping("/product/count/by-brand/and-name")
		public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String name) {
	        try {
	            var productCount = productService.countProductsByBrandAndName(brand, name);
	            return ResponseEntity.ok(new ApiResponse("Product count!", productCount));
	        } catch (Exception e) {
	            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
	        }
	    }
		
		@PostMapping("/add")
		public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product){
			try {
				Product addedProduct =productService.addProduct(product);
				ProductDto convertedProducts = productService.convertToDto(addedProduct);
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("sucess",convertedProducts));
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
			}
		}
		
}
