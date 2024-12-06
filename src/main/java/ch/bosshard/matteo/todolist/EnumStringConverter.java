package ch.bosshard.matteo.todolist;

import javafx.util.StringConverter;

public class EnumStringConverter<T extends Enum<T>> extends StringConverter<T> {
    @Override
    public String toString(T object) {
        if (object == null) return "";
        try {
            // Assume all enums have a `toFormattedString` method
            return (String) object.getClass()
                    .getMethod("toFormattedString")
                    .invoke(object);
        } catch (Exception e) {
            throw new RuntimeException("Enum must have a toFormattedString method", e);
        }
    }

    @Override
    public T fromString(String string) {
        return null;
    }
}
