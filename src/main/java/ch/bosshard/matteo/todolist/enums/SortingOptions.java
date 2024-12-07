package ch.bosshard.matteo.todolist.enums;

public enum SortingOptions {
    UNCATEGORIZED,
    CATEGORIZED,
    IMPORTANCE_HIGH_TO_LOW,
    IMPORTANCE_LOW_TO_HIGH,
    STATUS_HIGH_TO_LOW,
    STATUS_LOW_TO_HIGH;

    public String toShortenedString() {
        switch (this) {
            case IMPORTANCE_HIGH_TO_LOW:
                return "Imp.: H → L";
            case IMPORTANCE_LOW_TO_HIGH:
                return "Imp.: L → H";
            case STATUS_HIGH_TO_LOW:
                return "Stat.: H → L";
            case STATUS_LOW_TO_HIGH:
                return "Stat.: L → H";
            case UNCATEGORIZED:
                return "Uncat.";
            case CATEGORIZED:
                return "Cat.";
            default:
                return "";
        }
    }

    public String toFormattedString() {
        String name = this.name().toLowerCase().replace("_", " ");
        String[] words = name.split(" ");
        StringBuilder formattedString = new StringBuilder();

        // Add special handling for keywords
        if (name.startsWith("importance")) {
            formattedString.append("Importance: ");
        } else if (name.startsWith("status")) {
            formattedString.append("Status: ");
        }

        // Format the rest of the words
        for (int i = 1; i < words.length; i++) { // Skip the first word if already handled
            formattedString.append(words[i].substring(0, 1).toUpperCase())
                    .append(words[i].substring(1))
                    .append(" ");
        }

        // Handle cases like UNSORTED and BY_CATEGORY (no special prefix)
        if (formattedString.length() == 0) {
            for (String word : words) {
                formattedString.append(word.substring(0, 1).toUpperCase())
                        .append(word.substring(1))
                        .append(" ");
            }
        }

        return formattedString.toString().trim();
    }

}
