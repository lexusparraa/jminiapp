# Notes App Tutorial

This guide provides a step-by-step process for implementing and integrating the Notes Example into the JMiniApp framework. This example demonstrates how to manage structured data (a list of objects) and ensure persistence using the framework's JSON adapter.

**Source Code:** [examples/notes-app](https://github.com/lexusparraa/jminiapp/tree/main/examples/notes-app)

## 1. Project Setup and Structure

The Notes App is implemented as a standard Maven module, similar to the Counter Example.

### Prerequisites

- A local fork of the jminiapp repository.

- Java 17 or higher.

- Maven 3.6 or higher.

### Directory Creation

```
Create the module folder structure and the required package path:

# Navigate to the examples directory
cd examples

# Create the module directory
mkdir notes-app

# Create the package path based on the Java package name
mkdir -p notes-app/src/main/java/com/jminiapp/examples/notesapp
```
### 2. Java Source Code Implementation

The application relies on four primary Java classes in the `com.jminiapp.examples.notes.app` package.

**2.1. Data Model** (`Note.java`)

This is the POJO (Plain Old Java Object) representing a single note's data fields.

**2.2. State Model** (`NoteState.java`)

This class wraps the application's entire state, containing the `List<Note>`. It includes basic CRUD methods and the `displayNotes()` logic.

**2.3. Persistence Adapter** (`NoteJSONAdapter.java`)

This adapter registers the `NoteState` class for JSON file operations, implementing the required persistence contract.
```
public class NoteJSONAdapter implements JSONAdapter<NoteState> {
    @Override
    public Class<NoteState> getstateClass() {
        return NoteState.class;
    }
    // ... other methods
}
```

**2.4. Application Logic** (`NotesApp.java`)

This is the main class that manages the console menu, user input, and the application lifecycle (`initialize`, `run`, `shutdown`).
```
public class NotesApp extends JMiniApp {
    private NoteState noteState;
    // ... logic methods (addNewNote, deleteExistingNote, displayMenu)
    
    @Override
    protected void shutdown() {
        // Saves the current list of notes using the registered adapter
        context.setData(List.of(noteState));
        try {
            context.exportData("notes-json"); 
        } catch (IOException e) {
            // handle error
        }
    }
    
    public static void main(String[] args) {
        JMiniAppRunner
            .forApp(NotesApp.class)
            .withState(NoteState.class)
            .withAdapters(new NoteJSONAdapter())
            .run(args);
    }
}
```

### 3. Maven Configuration (`pom.xml`)

Create the `pom.xml` file inside the `examples/notes-app` directory to define dependencies (on `jminiapp-core`) and configure the executable JAR (`maven-shade-plugin`) and quick execution (`exec-maven-plugin`).

(The full `pom.xml` content is omitted here for brevity, but it should include the parent module, the `jminiapp-core` dependency, and the `shade` and `exec` plugins pointing to `com.jminiapp.examples.notes.app.NotesApp` as the main class.)

### 4. Integration and Execution

**4.1. Integrate into Parent POM**

To ensure `Notes Example` is built automatically with the framework, edit the root `pom.xml` and add the module reference:
```
<modules>
    <module>modules/core</module>
    <module>examples/counter</module>
    <module>examples/notes-app</module>  <!-- ADD THIS LINE -->
</modules>
```

**4.2. Build the Project**

From the project root:

```
mvn clean install
```


This builds all modules and installs `jminiapp-core` and `notes-example` into your local Maven repository.

**4.3. Run the Notes App**

From the `examples/notes-app` directory:
```
mvn exec:java
```

### Usage and Persistence

The application starts by attempting to load `notes_data.json`. The user can interact via the console menu:

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
Choose an option:
```
The application demonstrates State Persistence as notes added in one session will automatically load when the application is run again.