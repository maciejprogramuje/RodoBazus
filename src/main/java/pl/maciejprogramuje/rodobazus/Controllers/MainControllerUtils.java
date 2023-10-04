package pl.maciejprogramuje.rodobazus.Controllers;

import javafx.application.Platform;
import javafx.beans.property.StringProperty;

import java.text.MessageFormat;

public class MainControllerUtils {
    public static void showOnMessageLabel(final String message, final StringProperty stringProperty) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                stringProperty.setValue(message);
            }
        });
    }

    public static void showOnMessageLabelPattern(String string, StringProperty stringProperty, Object... arguments) {
        String pattern = string;
        String message = MessageFormat.format(pattern, arguments);
        showOnMessageLabel(message, stringProperty);
    }
}
