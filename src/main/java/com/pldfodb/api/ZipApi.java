package com.pldfodb.api;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import com.pldfodb.model.FileDescription;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface ZipApi {

	@PostMapping("/files")
	public UUID uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("filename") String filename);
	
	@RequestMapping(value = "/files/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public void getFile(@PathVariable("id") UUID fileId, HttpServletResponse response) throws IOException;
	
	@RequestMapping(value = "/files", method = RequestMethod.GET)
	public Page<FileDescription> getFileListing(
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "limit", required = false, defaultValue = "20") int limit);
}
