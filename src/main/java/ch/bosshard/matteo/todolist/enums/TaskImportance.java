package ch.bosshard.matteo.todolist.enums;

/**
 * TaskImportance.java
 * @author Matteo Bosshard
 * @version 12.12.2024
 **/

public enum TaskImportance {
    CRITICAL,
    HIGH,
    MEDIUM,
    LOW,
    ESSENTIAL,
    OPTIONAL;

    public String toFormattedString() {
         String name = this.name().toLowerCase().replace("_", " ");
         String[] words = name.split(" ");
         StringBuilder formattedString = new StringBuilder();
         for (String word : words) {
             formattedString.append(word.substring(0, 1).toUpperCase())
                     .append(word.substring(1))
                     .append(" ");
         }
         return formattedString.toString().trim();
    }
}
