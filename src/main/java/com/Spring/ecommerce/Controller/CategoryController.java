package com.Spring.ecommerce.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Spring.ecommerce.exceptions.AlreadyExists;
import com.Spring.ecommerce.exceptions.ResourceNotFoundException;
import com.Spring.ecommerce.model.Category;
import com.Spring.ecommerce.responce.ApiResponse;
import com.Spring.ecommerce.service.Category.CategoryService;

@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
	
	private final CategoryService categoryService;
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllCategories(){
		try {
			List<Category> categories = categoryService.getAllCategories();
			return ResponseEntity.ok(new ApiResponse("Found",categories));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error",HttpStatus.INTERNAL_SERVER_ERROR));
		}
	}
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addCategory(@RequestBody Category category){
		try {
			Category c =categoryService.addCategory(category);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("added",c));
		} catch (AlreadyExists e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(),null));
		}
	}
	@GetMapping("/category/{categoryId}/category")
	public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId){
		 
		try {
			Category category = categoryService.getCategoryById(categoryId);
			return ResponseEntity.ok(new ApiResponse("Found",category));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
		}
	}
	@GetMapping("/category/{categoryName}/category")
	public ResponseEntity<ApiResponse> getCategoryById(@PathVariable String categoryName){
		 
		try {
			Category category = categoryService.getCategoryByName(categoryName);
			return ResponseEntity.ok(new ApiResponse("Found",category));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
		}
	}
	@PutMapping("/category/{id}/update")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        try {
            Category updatedCategory = categoryService.updateCategory(category, id);
            return ResponseEntity.ok(new ApiResponse("Update success!", updatedCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
	@DeleteMapping("/category/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id){
        try {
            categoryService.deleteCategoryById(id);
            return  ResponseEntity.ok(new ApiResponse("Found", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
	
}
