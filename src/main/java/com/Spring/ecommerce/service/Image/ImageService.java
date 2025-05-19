package com.Spring.ecommerce.service.Image;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Spring.ecommerce.dto.ImageDto;
import com.Spring.ecommerce.exceptions.ResourceNotFoundException;
import com.Spring.ecommerce.model.Image;
import com.Spring.ecommerce.model.Product;
import com.Spring.ecommerce.repository.ImageRepository;
import com.Spring.ecommerce.service.Product.ProductService;

@Service
public class ImageService implements ImageServiceI {

    private final ImageRepository imageRepository;
    private final ProductService productService;
    
    // Use a single constructor to inject all dependencies
    public ImageService(ImageRepository imageRepository, ProductService productService) {
        this.imageRepository = imageRepository;
        this.productService = productService;
    }
    

	@Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image Not Found with id " + id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(
                imageRepository::delete,
                () -> {
                    throw new ResourceNotFoundException("Image Not Found");
                }
        );
    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDto> savedImages = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);
                String buildDownloadUrl = "/api/v1/images/image/download/";
                image.setDownloadUrl(buildDownloadUrl);
                Image savedImage = imageRepository.save(image);
                savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());
                imageRepository.save(savedImage); // Again saving after updating downloadUrl
                ImageDto savedImageDto = new ImageDto();
                savedImageDto.setId(savedImage.getId());
                savedImageDto.setFileName(savedImage.getFileName());
                savedImageDto.setDownloadUrl(savedImage.getDownloadUrl());
                savedImages.add(savedImageDto);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImages;
    }

    @Override
    public void updateImage(MultipartFile newFile, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(newFile.getOriginalFilename());
            image.setImage(new SerialBlob(newFile.getBytes()));
            image.setFileType(newFile.getContentType());
            imageRepository.save(image);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
