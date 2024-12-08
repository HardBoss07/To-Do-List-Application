package ch.bosshard.matteo.todolist;

import org.json.JSONArray;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SaveSystem {

    private String filePath;

    public SaveSystem(String filePath) {
        this.filePath = filePath;
    }

    // Save data to the file
    public void saveData(List<ToDoList> list) {
        // Create a JSONArray directly from the list
        JSONArray jsonArray = new JSONArray();

        // Convert each ToDoList object to JSON and add it to the JSONArray
        for (ToDoList toDoList : list) {
            jsonArray.put(toDoList.toJSON());
        }

        try (FileWriter file = new FileWriter(filePath)) {
            // This will overwrite the file
            file.write(jsonArray.toString(2)); // Pretty print with an indent factor of 2
            System.out.println("Data saved to " + filePath);
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    // Load data from the file
    public List<ToDoList> loadData() {
        List<ToDoList> data = new ArrayList<>();

        try (FileReader fileReader = new FileReader(filePath)) {
            StringBuilder sb = new StringBuilder();
            int content;
            while ((content = fileReader.read()) != -1) {
                sb.append((char) content); // Build the file content as a string
            }
            // Convert the string content to a JSONArray
            JSONArray jsonArray = new JSONArray(sb.toString());
            // Convert each JSON object to a ToDoList object and add it to the list
            for (int i = 0; i < jsonArray.length(); i++) {
                data.add(ToDoList.fromJSON(jsonArray.getJSONObject(i)));
            }
        } catch (IOException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
        return data;
    }
}
