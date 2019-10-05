/**
 * 
 */
package com.tcs.demo.recipe.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * File uploading utility.
 * @author Dhiraj
 *
 */
@Component
public class FileUtil {
	private static final  Logger LOGGER = LogManager.getLogger(FileUtil.class);

	@Value("${user.home}")
	private String FOLDER_TO_UPLOAD ;

	public String uploadFile(MultipartFile file) throws IOException{
		if(file.isEmpty()) {
			throw new IllegalArgumentException("File to upload is empty");
		}
		byte[] bytes  = file.getBytes();
		Path path = Paths.get(FOLDER_TO_UPLOAD ,"recipeImages", file.getOriginalFilename());

		LOGGER.info("Attempting to save file {} to {} ",file.getOriginalFilename(),path.getParent());

		if (!path.getParent().toFile().exists()) //create directory if it does not exist. Mostly during first time upload
			Files.createDirectories(path.getParent());
		Files.write(path, bytes,StandardOpenOption.CREATE);
		return path.toString(); 
	}
}
