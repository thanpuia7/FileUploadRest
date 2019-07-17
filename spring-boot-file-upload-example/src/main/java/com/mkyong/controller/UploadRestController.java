package com.mkyong.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadRestController {
	
	private static String UPLOADED_FOLDER = "E://Boot//tmp//";
	
	@PostMapping("/uploadrest")
	public Object uploadrest(@RequestParam("fileupload") MultipartFile file) {


        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            File file1=new File(UPLOADED_FOLDER+"/sam");
            if (!file1.exists()) {
				file1.mkdirs();
			}
            Path path = Paths.get(UPLOADED_FOLDER+"/sam/" + file.getOriginalFilename());
           System.out.println(UPLOADED_FOLDER+"/sam/"+file.getOriginalFilename());
            Files.write(path, bytes);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return "success";
    }
	
	

}
