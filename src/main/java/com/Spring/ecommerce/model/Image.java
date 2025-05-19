package com.Spring.ecommerce.model;

import java.sql.Blob;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class Image {
	
		@Id
		@GeneratedValue (strategy = GenerationType.IDENTITY)
		private Long id;
		private String fileName;
		private String fileType;
		@Lob
		private Blob image;
		private String downloadUrl;
		
		@ManyToOne(cascade = CascadeType.ALL)
		@JoinColumn(
				name="product_id",
				referencedColumnName="id"
				)
		private Product product;

		public Image() {

		}

		public Image(Long id, String fileName, String fileType, Blob image, String downloadUrl, Product product) {
			this.id = id;
			this.fileName = fileName;
			this.fileType = fileType;
			this.image = image;
			this.downloadUrl = downloadUrl;
			this.product = product;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public String getFileType() {
			return fileType;
		}

		public void setFileType(String fileType) {
			this.fileType = fileType;
		}

		public Blob getImage() {
			return image;
		}

		public void setImage(Blob image) {
			this.image = image;
		}

		public String getDownloadUrl() {
			return downloadUrl;
		}

		public void setDownloadUrl(String downloadUrl) {
			this.downloadUrl = downloadUrl;
		}

		public Product getProduct() {
			return product;
		}

		public void setProduct(Product product) {
			this.product = product;
		}

		@Override
		public String toString() {
			return "Image [id=" + id + ", fileName=" + fileName + ", fileType=" + fileType + ", image=" + image
					+ ", downloadUrl=" + downloadUrl + ", product=" + product + "]";
		}
		
		
}
