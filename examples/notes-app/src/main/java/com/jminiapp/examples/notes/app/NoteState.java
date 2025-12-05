/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jminiapp.examples.notes.app;

/**
 *
 * @author lexusparra
 */
import java.util.ArrayList;
import java.util.List;

/**
 * State container for the Notes Application.
 * This class wraps the list of notes and is managed by the JMiniApp framework for persistence.
 */
public class NoteState {
    private List<Note> notes;

    public NoteState() {
        this.notes = new ArrayList<>();
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    /**
     * Adds a new note to the list.
     * @param note The note to add.
     */
    public void addNote(Note note) {
        this.notes.add(0, note); // Add to the beginning
    }

    /**
     * Removes a note by its ID.
     * @param id The ID of the note to remove.
     * @return true if the note was found and removed, false otherwise.
     */
    public boolean deleteNote(String id) {
        return this.notes.removeIf(note -> note.getId().equals(id));
    }

    public void displayNotes() {
        if (notes.isEmpty()) {
            System.out.println("\n--- Your Notes (0) ---");
            System.out.println("No notes found. Start by creating a new one!");
        } else {
            System.out.printf("\n--- Your Notes (%d) ---\n", notes.size());
            for (int i = 0; i < notes.size(); i++) {
                System.out.printf("%d. %s\n", i + 1, notes.get(i).toString());
            }
        }
    }
}