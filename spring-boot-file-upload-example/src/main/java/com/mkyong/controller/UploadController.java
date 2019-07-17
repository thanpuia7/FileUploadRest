package com.mkyong.controller;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UploadController {

    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "E://Boot//tmp//";

    @GetMapping("/")
    public String index() {
        return "upload";
    }

    @PostMapping("/upload") // //new annotation since 4.3
    public String singleFileUpload(@RequestParam("fileupload") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/";
        }

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

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");
            redirectAttributes.addFlashAttribute("Imgpath","/viewimage?Imgpath="+UPLOADED_FOLDER+"/sam/"+file.getOriginalFilename());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }
    
    @GetMapping("/viewimage")
    public void viewImage(HttpServletRequest request, HttpServletResponse response,@RequestParam("Imgpath") String Imgpath) throws IOException {
    	
    	response.setContentType("text/plain");
    	response.setHeader("Content-disposition", "attachment; filename=sample.jpg");
    	final File initialFile = new File(Imgpath);
        final InputStream targetStream = 
          new DataInputStream(new FileInputStream(initialFile));
        
          OutputStream out = response.getOutputStream();
 
            byte[] buffer = new byte[2048];
         
            int numBytesRead;
            while ((numBytesRead = targetStream.read(buffer)) > 0) {
                out.write(buffer, 0, numBytesRead);
            
        }
		
	}
    

}