package com.ates.myfirstshop.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="category_new")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String categoryName;

    private String description;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String imageUrl;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "image_id")
    FileUpload fileUpload;
}
