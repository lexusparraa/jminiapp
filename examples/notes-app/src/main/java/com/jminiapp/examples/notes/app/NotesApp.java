/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.jminiapp.examples.notes.app;

/**
 *
 * @author lexusparra
 */
import com.jminiapp.core.api.JMiniApp;
import com.jminiapp.core.api.JMiniAppConfig;
import com.jminiapp.core.api.JMiniAppContext;
import com.jminiapp.core.engine.JMiniAppRunner;

import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

/**
 * The main application class for the Notes Mini-App.
 * This extends JMiniApp and provides the console-based CRUD functionality.
 */
public class NotesApp extends JMiniApp {

    private NoteState noteState;
    private Scanner scanner;
    private static final String SAVE_FILENAME = "notes_data.json"; // Nombre del archivo de persistencia

    public NotesApp(JMiniAppConfig config) {
        super(config);
        this.scanner = new Scanner(System.in);
    }

    @Override
    protected void initialize() {
        // Initialize state, trying to load any existing data
        List<NoteState> loadedStates = context.getData();
        if (loadedStates != null && !loadedStates.isEmpty()) {
            this.noteState = loadedStates.get(0);
            System.out.println("Notes state loaded successfully from persistence.");
        } else {
            this.noteState = new NoteState();
            System.out.println("Initialized Notes App with a new, empty state.");
        }
    }

    @Override
    protected void run() {
        int option;
        do {
            displayMenu();
            if (scanner.hasNextInt()) {
                option = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                handleOption(option);
            } else {
                System.err.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
                option = -1;
            }
        } while (option != 4);
    }

    @Override
    protected void shutdown() {
        // Save the current state before the app exits
        context.setData(new ArrayList<>(List.of(noteState)));
        try {
            // Exportamos explícitamente usando el nombre del formato definido en el adaptador.
            context.exportData("notes-json"); 
            System.out.printf("\nNotes state saved to %s successfully.\n", SAVE_FILENAME);
        } catch (IOException e) {
            System.err.println("\nSave failed: Could not export notes data.");
        }
        scanner.close();
    }

    private void displayMenu() {
        noteState.displayNotes();
        System.out.println("\n--- Notes Menu ---");
        System.out.println("1. Add New Note");
        System.out.println("2. Delete Note (by ID or index)");
        // El punto 3 será solo informativo, ya que no podemos obtener la ruta.
        System.out.println("3. Show Persistence File Name");
        System.out.println("4. Exit (Saves data automatically)");
        System.out.print("Choose an option: ");
    }

    private void handleOption(int option) {
        switch (option) {
            case 1:
                addNewNote();
                break;
            case 2:
                deleteExistingNote();
                break;
            case 3:
                displayFilePath();
                break;
            case 4:
                System.out.println("Shutting down Notes App...");
                break;
            default:
                System.err.println("Invalid option. Please choose between 1 and 4.");
        }
    }

    private void addNewNote() {
        System.out.print("Enter note title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Enter note content: ");
        String content = scanner.nextLine().trim();

        if (title.isEmpty() || content.isEmpty()) {
            System.out.println("Title and content cannot be empty. Note creation cancelled.");
            return;
        }

        Note newNote = new Note(title, content);
        noteState.addNote(newNote);
        System.out.println("\nNote added successfully!");
    }

    private void deleteExistingNote() {
        if (noteState.getNotes().isEmpty()) {
            System.out.println("No notes available to delete.");
            return;
        }
        noteState.displayNotes();
        System.out.print("\nEnter the index number or the full ID of the note to delete: ");
        String input = scanner.nextLine().trim();

        boolean deleted = false;
        try {
            // Try deleting by index (user inputting 1, 2, 3...)
            int index = Integer.parseInt(input);
            if (index > 0 && index <= noteState.getNotes().size()) {
                noteState.getNotes().remove(index - 1);
                deleted = true;
            }
        } catch (NumberFormatException e) {
            // Try deleting by full ID
            deleted = noteState.deleteNote(input);
        }

        if (deleted) {
            System.out.println("Note deleted successfully.");
        } else {
            System.err.println("Deletion failed. Invalid index or ID provided.");
        }
    }
    
    private void displayFilePath() {
        // CORRECCIÓN FINAL: Ya que getDataFilePath no existe en el contexto, 
        // simplemente mostramos el nombre del archivo de persistencia y la convención.
        System.out.println("\n--- Data File Information ---");
        System.out.printf("The file name is: %s\n", SAVE_FILENAME);
        System.out.println("The framework saves the file automatically in your home directory or app folder.");
    }

    public static void main(String[] args) {
        // Runner configuration:
        JMiniAppRunner.forApp(NotesApp.class)
                // 1. Specify the state class to manage
                .withState(NoteState.class)
                // 2. Register the adapter for JSON persistence
                .withAdapters(new NoteJSONAdapter())
                // 3. Run the application
                .run(args);
    }
}