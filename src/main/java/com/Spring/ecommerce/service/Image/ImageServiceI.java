package com.Spring.ecommerce.service.Image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.Spring.ecommerce.dto.ImageDto;
import com.Spring.ecommerce.model.Image;

public interface ImageServiceI {
			
	
		Image getImageById(Long id);
		
		void deleteImageById(Long Id);
		
		List<ImageDto>  saveImage(List<MultipartFile> file,Long productId);
		
		void updateImage(MultipartFile file,Long imageId);
}
