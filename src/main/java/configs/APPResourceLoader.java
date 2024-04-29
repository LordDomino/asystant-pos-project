package main.java.configs;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

/**
 * Class containing loader methods to retrieve external resources and
 * integrate them to the Swing library which handles the creation and
 * display of all the UI elements and components of the application.
 */
public class APPResourceLoader {

    /**
     * Loads third-party fonts located at {@code src\main\resources} and
     * registers them to the graphics environment of the application.
     */
    public static void loadFonts() {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src\\main\\resources\\inter.ttf")));
        } catch (IOException | FontFormatException exception) {
            exception.printStackTrace();
        }
    }
}
