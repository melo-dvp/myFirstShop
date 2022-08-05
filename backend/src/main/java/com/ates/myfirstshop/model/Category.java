package com.ates.myfirstshop.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="category_name")
    private @NotNull String categoryName;

    @Column(name="description")
    private @NotNull String description;

    @Column(name="imageUrl")
    private @NotNull String imageUrl;

}
