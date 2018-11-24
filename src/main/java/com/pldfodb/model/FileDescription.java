package com.pldfodb.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class FileDescription {

	private UUID id;
	private String filename;
	private Instant uploadTime;
	private long size;
	
	public FileDescription(UUID id, String filename, Instant uploadTime, long size) {
		this.id = Objects.requireNonNull(id);
		this.filename = Objects.requireNonNull(filename);
		this.uploadTime = Objects.requireNonNull(uploadTime);
		this.size = size;
	}
	
	public UUID getId() {
		return id;
	}
	
	public String getName() {
		return filename;
	}
	
	public Instant getUploadTime() {
		return uploadTime;
	}

	public long getSize() {
		return size;
	}
}
