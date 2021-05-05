package com.ibm.la.mongo.interfaces;

import java.io.InputStream;

public class DdsContentTOREMOVE {
    private InputStream content;
    private String id;
    private String fileName;
    private String contentMimeType;
    private Long size;
    private String uploadDate;

    public InputStream getContent() {
		return content;
	}
	public void setContent(InputStream content) {
		this.content = content;
	}
	public String getFileName() {
        return fileName;
    }
    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }
	public String getContentMimeType() {
		return contentMimeType;
	}
	public void setContentMimeType(final String contentMimeType) {
		this.contentMimeType = contentMimeType;
	}
	public String getId() {
		return id;
	}
	public void setId(final String id) {
		this.id = id;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(final Long size) {
		this.size = size;
	}
	public String getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(final String uploadDate) {
		this.uploadDate = uploadDate;
	}

}
