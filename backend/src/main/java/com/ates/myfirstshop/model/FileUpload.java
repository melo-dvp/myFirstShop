package com.ates.myfirstshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="file")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Long lastModified;
    private Date lastModifiedDate;
    private String name;
    private Long size;
    private String type;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;

}
