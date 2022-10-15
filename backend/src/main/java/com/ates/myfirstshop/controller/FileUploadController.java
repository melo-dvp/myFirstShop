package com.ates.myfirstshop.controller;

import com.ates.myfirstshop.model.FileUpload;
import com.ates.myfirstshop.repository.CategoryRepo;
import com.ates.myfirstshop.repository.FileUploadRepo;
import com.ates.myfirstshop.service.FileUploadService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/file")
public class FileUploadController {


    @Autowired
    private FileUploadRepo fileUploadRepo;
    @Autowired
    private FileUploadService fileUploadService;


    @RolesAllowed("admin")
    @PostMapping("/upload")
    public String fileupload(
            @RequestParam("file")MultipartFile multipartFile,
            @RequestParam("name")String name,
            @RequestParam("desc")String desc
    ){
        return String.format("File %s uploaded successfully ", multipartFile.getOriginalFilename());
    }

    @GetMapping("/getImage")
    public List<FileUpload> getImage(){
        List<FileUpload> files = fileUploadRepo.findAll();
        return files;
    }
}
