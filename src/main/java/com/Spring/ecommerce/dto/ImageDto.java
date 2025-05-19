package com.Spring.ecommerce.dto;

public class ImageDto {
	private Long id;
	private String fileName;
	private String downloadUrl;
	public ImageDto() {

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

	

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}


	public ImageDto(Long id, String fileName, String downloadUrl) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.downloadUrl = downloadUrl;
	}


	@Override
	public String toString() {
		return "ImageDto [id=" + id + ", fileName=" + fileName + ", downloadUrl=" + downloadUrl + "]";
	}
	

}

