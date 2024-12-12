package ch.bosshard.matteo.todolist;

/**
 * EnumStringConverter.java
 * @author Matteo Bosshard
 * @version 12.12.2024
 **/

import javafx.util.StringConverter;

public class EnumStringConverter<T extends Enum<T>> extends StringConverter<T> {

    private boolean useShortenedString = false;

    public void setUseShortened(boolean useShortened) {
        this.useShortenedString = useShortened;
    }

    @Override
    public String toString(T object) {
        if (object == null) return "";
        try {
            String methodName = useShortenedString ? "toShortenedString" : "toFormattedString";
            // Assume all enums have a `toFormattedString` method
            return (String) object.getClass()
                    .getMethod(methodName)
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
