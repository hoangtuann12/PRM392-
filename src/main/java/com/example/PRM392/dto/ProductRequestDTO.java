package com.example.PRM392.dto;

import com.example.PRM392.entity.Category;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductRequestDTO {
    private String name;
    private Integer categoryId;
    private String image;
    private Double price;
    private String status;
    private LocalDateTime createAt;

    public ProductRequestDTO(String name, Integer categoryId, String image, Double price, String status, LocalDateTime createAt) {
        this.name = name;
        this.categoryId = categoryId;
        this.image = image;
        this.price = price;
        this.status = status;
        this.createAt = createAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }
}
