# Notes App Example

A simple console application demonstrating the JMiniApp framework's capabilities for structured data persistence (lists of objects) and console-based CRUD (Create, Read, Update, Delete) operations.

## Overview

This example shows how to create a basic mini-app using JMiniApp core that manages a collection of user notes. The application is run from the console and uses file persistence (JSON) to save the notes between sessions.

## Features

- **Add New Note**: Allows the user to input a title and content to create a new note.

- **Delete Note**: Removes a note by its index number or full ID.

- **Display Notes**: Shows a list of all current notes in the console.

- **Persistent State**: The list of notes is maintained across application restarts using JSON persistence.

## Project Structure

```
notes-app/
├── pom.xml
├── README.md
└── src/main/java/com/jminiapp/examples/notesapp/
    ├── Note.java              # Data model for a single note (Title, Content, Date)
    ├── NoteState.java         # State model holding the List<Note>
    ├── NoteJSONAdapter.java   # JSON Adapter for NoteState persistence
    └── NotesApp.java          # Main application class with console menu and logic
```


## Key Components

### NoteState

This class represents the entire application state by holding a `List<Note>`. Key methods include:

- `addNote(Note note)`: Adds a new note to the list.

- `deleteNote(String id)`: Removes a note from the list based on its unique ID.

- `displayNotes()`: Prints the current list of notes to the console.

### NoteJSONAdapter

A format adapter that enables JSON import/export for `NoteState`:

- Implements `JsonAdapter<NoteState>` from the framework.

- Registers the NoteState class for automatic serialization/deserialization.

- Defines the format name used for saving/loading ("notes-json").

### NotesApp

The main application class that extends `JMiniApp` and implements:

- `initialize()`: Sets up the initial state, loading existing notes using context.getData().

- `run()`: The main application loop that displays the menu and processes user input (Add, Delete, Exit).

- `shutdown()`: Saves the final state back to the persistence file using context.exportData("notes-json") before the application closes.

### NotesAppRunner

The application is launched via the main method within `NotesApp.java` (following the With State and Adapters template):

- Registers the `NoteState.class` as the primary application state.

- Registers the `NoteJSONAdapter` via `.withAdapters(new NoteJSONAdapter())`.

- Launches the console interface.

## Building and Running

### Prerequisites

- Java 17 or higher

- Maven 3.6 or higher

### Build the project

From the **project root** (jminiapp directory):

```
mvn clean install
```

This command validates the structure, compiles all four modules (Core, Counter, and Notes), and creates the runnable JAR.

### Run the application

Option 1: Using Maven exec plugin (recommended for debugging and quick launches)

From the examples/notes-app directory:
```
cd examples/notes-app
mvn exec:java
```

Option 2: Using the packaged JAR (from the examples/notes-app directory)
```
cd examples/notes-app
java -jar target/notes-app.jar
```

## Usage Example

### Basic Operations

```
=== Notes App ===
Initialized Notes App with a new, empty state.

--- Your Notes (0) ---
No notes found. Start by creating a new one!

--- Notes Menu ---
1. Add New Note
2. Delete Note (by ID or index)
3. Show Persistence File Name
4. Exit (Saves data automatically)
Choose an option: 1
Enter note title: My First Note
Enter note content: This is content for my first note.

Note added successfully!

--- Your Notes (1) ---
1. [3456] My First Note (Created: 2025-12-05)
Content: This is content for my first note....

--- Notes Menu ---
1. Add New Note
2. Delete Note (by ID or index)
3. Show Persistence File Name
4. Exit (Saves data automatically)
Choose an option: 4
Shutting down Notes App...
Notes state saved to notes_data.json successfully.
```


### Deleting a Note

If the note list contains multiple items:

```
--- Your Notes (2) ---
1. [1234] Task List (Created: 2025-12-05)
2. [5678] Ideas for Project (Created: 2025-12-05)

--- Notes Menu ---
...
Choose an option: 2

Enter the index number or the full ID of the note to delete: 1
Note deleted successfully.
```

Author: Lexus Parra

Project: JMiniApp Notes App Example