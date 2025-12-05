/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jminiapp.examples.notes.app;

/**
 *
 * @author lexusparra
 */
import com.jminiapp.core.adapters.JSONAdapter;

/**
 * JSON Adapter for the NoteState.
 * This registers the NoteState class with the JMiniApp framework for
 * automatic JSON serialization and deserialization.
 */
public class NoteJSONAdapter implements JSONAdapter<NoteState> {

    @Override
    public Class<NoteState> getstateClass() {
        // CORRECCIÓN: El nombre del método se ajusta exactamente a lo que la interfaz requiere: getstateClass() (minúscula).
        return NoteState.class;
    }

    @Override
    public String getFormatName() {
        // Name used for saving/loading data (e.g., context.exportData("notes-json"))
        return "notes-json";
    }
}