/**
 * 
 */
package com.tcs.demo.recipe.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.tcs.demo.recipe.bean.Recipe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
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

	/**
	 * Upload file to User home directory
	 * @param file
	 * @return
	 * @throws IOException
	 */
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

	/**
	 * Create the bytearrayoutputstream for downloading pdf
	 * @param recipe
	 * @return
	 * @throws Exception
	 */
	public ByteArrayOutputStream createRecipePdfInputSteam(Recipe recipe) throws Exception{
		Document  document = new Document();
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PdfWriter writer = PdfWriter.getInstance(document, byteArrayOutputStream);
		document.open();
		document.add(new Paragraph("Recipe : "+recipe.getRcpName()));
		try {
			Image image = Image.getInstance("D:\\IntelliJ Workspace\\recipe\\src\\main\\resources\\static\\"+recipe.getRcpImagePath());
			image.scaleAbsolute(150f, 150f);
			document.add(image);
		}catch(Exception e){
			LOGGER.error("Image file not found {}",e.getMessage());
		}
		List orderedList = getRecipeOrderedList(recipe);
		document.add(orderedList);
		document.close();
		return byteArrayOutputStream;
	}

	private List getRecipeOrderedList(Recipe recipe){
		List orderedList = new List(List.ORDERED);
		orderedList.add(new ListItem("Ingredients : "+recipe.getRcpIngredientDescription()));
		orderedList.add(new ListItem("Cooking Instructions : "+recipe.getRcpCookingInstruction()));
		orderedList.add(new ListItem("Is Vegetarian : "+recipe.getRcpIsVegetarian()));
		orderedList.add(new ListItem("Servers : "+recipe.getRcpSuitableFor().getGroupDescription()));
		orderedList.add(new ListItem("Recipe Created By : "+recipe.getRcpCreatedBy()));
		orderedList.add(new ListItem("Recipe Created At : "+recipe.getRcpCreatedAt()));
		orderedList.add(new ListItem("Recipe Updated By : "+recipe.getRcpUpdatedBy()));
		orderedList.add(new ListItem("Recipe Updated At : "+recipe.getRcpUpdatedAt()));
		return orderedList;
	}
}
