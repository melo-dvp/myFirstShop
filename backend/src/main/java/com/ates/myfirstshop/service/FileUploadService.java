package com.ates.myfirstshop.service;

import com.ates.myfirstshop.dto.FileUploadDto;
import com.ates.myfirstshop.model.Category;
import com.ates.myfirstshop.model.FileUpload;
import com.ates.myfirstshop.repository.CategoryRepo;
import com.ates.myfirstshop.repository.FileUploadRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;

@Service
public class FileUploadService {

    @Autowired
    private FileUploadRepo fileUploadRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    public FileUploadDto saveImage(MultipartFile file){
        FileUpload fileUpload = new FileUpload();

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if(fileName.contains("..")){
            System.out.println("not a valid file");
        }

        try {
            fileUpload.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FileUploadDto fileUploadDto = new FileUploadDto();

        fileUpload.setName(file.getOriginalFilename());
        fileUpload.setSize(file.getSize());
        fileUpload.setType(file.getContentType());
        fileUpload.setLastModifiedDate(new Date());
        fileUpload.setLastModified(fileUpload.getLastModifiedDate().getTime());

        fileUploadDto.setName(file.getOriginalFilename());
        fileUploadDto.setSize(file.getSize());
        fileUploadDto.setType(file.getContentType());
        fileUploadDto.setLastModifiedDate(new Date());
        fileUploadDto.setLastModified(fileUpload.getLastModifiedDate().getTime());

        fileUploadRepo.save(fileUpload);
        fileUploadDto.setId(fileUpload.getId());
        return fileUploadDto;
    }

    public FileUploadDto editFileUpload(int id, MultipartFile file){
        FileUpload fileUpload = fileUploadRepo.getReferenceById(id);

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if(fileName.contains("..")){
            System.out.println("not a valid file");
        }

        try {
            fileUpload.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FileUploadDto fileUploadDto = new FileUploadDto();

        fileUpload.setName(file.getOriginalFilename());
        fileUpload.setSize(file.getSize());
        fileUpload.setType(file.getContentType());
        fileUpload.setLastModifiedDate(new Date());
        fileUpload.setLastModified(fileUpload.getLastModifiedDate().getTime());

        fileUploadDto.setName(file.getOriginalFilename());
        fileUploadDto.setSize(file.getSize());
        fileUploadDto.setType(file.getContentType());
        fileUploadDto.setLastModifiedDate(new Date());
        fileUploadDto.setLastModified(fileUpload.getLastModifiedDate().getTime());

        fileUploadRepo.save(fileUpload);
        fileUploadDto.setId(fileUpload.getId());
        return fileUploadDto;


    }



}
