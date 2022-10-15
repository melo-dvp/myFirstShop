package com.ates.myfirstshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "products_new")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private @NotNull String name;
    private @NotNull double price;
    private @NotNull String description;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String imageUrl;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "image_id")
    FileUpload fileUpload;

    // Many to one relationship
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "category_id")
    Category category;


}
