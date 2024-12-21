package com.example.domain.model;

public class Meal {
    private String name;
    private String category;
    private String instructions;
    private String imageUrl;

    public Meal(String name, String category, String instructions, String imageUrl) {
        this.name = name;
        this.category = category;
        this.instructions = instructions;
        this.imageUrl = imageUrl;
    }

    // Геттеры и сеттеры
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
