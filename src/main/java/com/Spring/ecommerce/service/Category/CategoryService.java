package com.Spring.ecommerce.service.Category;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Spring.ecommerce.exceptions.AlreadyExists;
import com.Spring.ecommerce.exceptions.ResourceNotFoundException;
import com.Spring.ecommerce.model.Category;
import com.Spring.ecommerce.repository.CategoryRepository;

@Service
public class CategoryService implements CategoryServiceI{

	private CategoryRepository categoryRepository;
	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	@Override
	public Category getCategoryById(Long id) {
		
		return categoryRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Category Not Found"));
	}

	@Override
	public Category getCategoryByName(String name) {
		return categoryRepository.findByName(name);
	}

	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAll() ;
	}

	
	@Override
	public void deleteCategoryById(Long id) {
		//categoryRepository.deleteById(id);
		categoryRepository.findById(id).ifPresentOrElse(categoryRepository::delete,
				()-> {throw new ResourceNotFoundException("Category Not Found");
				});
	}
	
//	@Override
//	public Category addCategory(Category category) {
//		if(!categoryRepository.existsByName(category.getName())) {
//			return categoryRepository.save(category);
//		}
//		throw new AlreadyExists(category.getName()+" already Exists");
//	}
		@Override
	    public Category addCategory(Category category) {
	        return  Optional.of(category).filter(c -> !categoryRepository.existsByName(c.getName()))
	                .map(categoryRepository :: save)
	                .orElseThrow(() -> new AlreadyExists(category.getName()+" already exists"));
	    }
////	@Override
//	public Category addCategory(Category category) {
//	    // Check if a category with the same name already exists
//	    if (categoryRepository.existsByName(category.getName())) {
//	        throw new AlreadyExists(category.getName() + " already exists");
//	    }
//
//	    // Ensure the ID is null for a new item
//	    if (category.getId() != null) {
//	        throw new IllegalStateException("New category cannot have an existing ID");
//	    }
//
//	    // Save the new category to the database
//	    return categoryRepository.save(category);
//	}



	@Override
	public Category updateCategory(Category category,Long id) {
	
		return Optional.ofNullable(getCategoryById(id)).map(oldCategory->{
					oldCategory.setName(category.getName());
					return categoryRepository.save(oldCategory);
				}).orElseThrow(()->new ResourceNotFoundException("Resource Not Found"));
//		Category oldCategory = getCategoryById(id);
//		if (oldCategory != null) {
//		    oldCategory.setName(category.getName());
//		    return categoryRepository.save(oldCategory);
//		} else {
//		    throw new ResourceNotFoundException("Resource Not Found");
//		}

	}


}
