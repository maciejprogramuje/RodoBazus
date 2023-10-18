package pl.maciejprogramuje.rodobazus.controllers;

import javafx.application.Platform;
import javafx.beans.property.StringProperty;

import java.text.MessageFormat;

public class MainControllerUtils {
    public static void showOnMessageLabel(final String message, final StringProperty stringProperty) {
        Platform.runLater(() -> stringProperty.setValue(message));
    }

    public static void showOnMessageLabelPattern(String string, StringProperty stringProperty, Object... arguments) {
        String message = MessageFormat.format(string, arguments);
        showOnMessageLabel(message, stringProperty);
    }
}
