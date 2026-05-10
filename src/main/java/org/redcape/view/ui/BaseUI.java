package org.redcape.view.ui;

import org.redcape.view.GameWindow;

import javax.swing.*;
import java.awt.*;


/**
 * BaseUI class for the game UI.
 * <p>
 * This class provides the basic settings for the UI, such as fonts and colors.
 * It is extended by other UI classes to create a consistent look and feel.
 * </p>
 */
public abstract class BaseUI extends JPanel implements UI {

    /**
     * Constructor for the BaseUI class
     */
    public BaseUI() {
    }

    // Dimensions
    public static final int WIDTH = GameWindow.WIDTH;
    public static final int HEIGHT = GameWindow.HEIGHT;

    // Colors
    public static final Color fontColor = new Color(0xBA, 0xB6, 0xA0);
    public static final Color healthColor = new Color(250, 33, 102);
    public static final Color healthBackgroundColor = new Color(119, 31, 31);
    public static final Color potionColor = new Color(47, 103, 187);
    public static final Color potionackgroundColor = new Color(31, 46, 119);

    /**
     * Project wide fontStyle
     */
    public static final String fontStyle = "Times New Roman";

    /**
     * Project wide title Font
     */
    public static final Font title = new Font(fontStyle, Font.BOLD, 60);

    /**
     * Project wide text Font
     */
    public static final Font text = new Font(fontStyle, Font.PLAIN, 30);

    /**
     * Project wide description Font
     */
    public static final Font description = new Font(fontStyle, Font.ITALIC, 15);

}
