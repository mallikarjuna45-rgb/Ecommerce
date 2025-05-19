package com.Spring.ecommerce.Controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Spring.ecommerce.dto.ImageDto;
import com.Spring.ecommerce.exceptions.ResourceNotFoundException;
import com.Spring.ecommerce.model.Image;
import com.Spring.ecommerce.responce.ApiResponse;
import com.Spring.ecommerce.service.Image.ImageService;

@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
		

		private ImageService imageService;
		public ImageController(ImageService imageService) {
			this.imageService = imageService;
		}
		
		@PostMapping("/upload")
		public ResponseEntity<ApiResponse> saveImage(@RequestParam List<MultipartFile> files,@RequestParam Long productId){
			try {
				List<ImageDto> imageDtos = imageService.saveImage(files, productId);
				return ResponseEntity.status(HttpStatus.ACCEPTED).body((new ApiResponse("upload success",imageDtos)));
			}catch(Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("upload failed",e.getMessage()));
			}
		}
		//learn more about it
		@GetMapping("/image/download/{imageId}")
	    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
	        Image image = imageService.getImageById(imageId);
	        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));
	        return  ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +image.getFileName() + "\"")
	                .body(resource);
	    }
		@PutMapping("/image/{imageId}/update")
		public ResponseEntity<ApiResponse> updateImage(@RequestParam MultipartFile newfile, @PathVariable Long imageId){
			try {
				Image image = imageService.getImageById(imageId);
				if(image != null) {
					imageService.updateImage(newfile, imageId);
					return ResponseEntity.ok(new ApiResponse("update success",null));
				}
			}catch(ResourceNotFoundException e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("update failed",null));
		}
		
		@DeleteMapping("/image/{imageId}/delete")
		public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId) {
		    try {
		        imageService.deleteImageById(imageId);
		        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse("delete success", null));
		    } catch (ResourceNotFoundException e) {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		    } catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("delete failed", null));
		    }
		}

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
}