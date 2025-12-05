/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jminiapp.examples.notes.app;

/**
 *
 * @author lexusparra
 */
import java.time.LocalDateTime;

/**
 * Data model for a single note item.
 * This class holds the content, title, and creation timestamp.
 */
public class Note {
    private String id;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    // Default constructor needed for JSON deserialization (Gson/JMiniApp)
    public Note() {
    }

    public Note(String title, String content) {
        this.id = String.valueOf(System.currentTimeMillis());
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (Created: %s)\nContent: %s",
                this.id.substring(this.id.length() - 4),
                this.title,
                this.createdAt.toLocalDate(),
                this.content.substring(0, Math.min(this.content.length(), 50)) + "..."
        );
    }
}