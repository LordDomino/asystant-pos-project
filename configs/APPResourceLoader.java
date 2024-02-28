package configs;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public class APPResourceLoader {
    public static void loadFontResources() {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("resources\\inter.ttf")));
        } catch (IOException|FontFormatException e) {
            //Handle exception
        }
    }
}
