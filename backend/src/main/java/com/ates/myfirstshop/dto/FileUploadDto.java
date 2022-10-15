package com.ates.myfirstshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadDto {

    private Integer id;
    private Long lastModified;
    private Date lastModifiedDate;
    private String name;
    private Long size;
    private String type;
}
