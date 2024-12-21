package com.example.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_recipes") // Убедитесь, что здесь указано правильное имя таблицы
public class FavoriteRecipeEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String category;
    private String instructions;
    private String imageUrl;

    public FavoriteRecipeEntity(String name, String category, String instructions, String imageUrl) {
        this.name = name;
        this.category = category;
        this.instructions = instructions;
        this.imageUrl = imageUrl;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
