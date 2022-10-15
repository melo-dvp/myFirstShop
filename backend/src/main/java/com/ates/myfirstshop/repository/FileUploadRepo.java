package com.ates.myfirstshop.repository;

import com.ates.myfirstshop.model.FileUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileUploadRepo extends JpaRepository<FileUpload, Integer> {

}
