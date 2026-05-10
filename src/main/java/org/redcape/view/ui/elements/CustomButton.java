package org.redcape.view.ui.elements;

import org.redcape.view.ui.BaseUI;
import javax.swing.*;


/**
 * CustomButton class for creating a custom button with specific styling.
 * <p>
 * This class extends JButton and provides methods to set the button's position, size, and default styling.
 * </p>
 */
public final class CustomButton extends JButton {

    /**
     * Constructor for the CustomButton class
     *
     * @param text of the button
     */
    public CustomButton(String text) {
        super(text);
        setDefaultSettings();
    }

    /**
     * Set the button's position on the screen.
     *
     * @param x the new x coordinate in px
     * @param y the new y coordinate in px
     */
    public void setPosition(int x, int y) {
        setBounds(x, y, getWidth(), getHeight());
    }


    /**
     * @param width  the new width in px
     * @param height the new height in px
     */
    public void setSize(int width, int height) {
        setBounds(getX(), getY(), width, height);
    }

    /**
     * Set the button default styling settings.
     */
    private void setDefaultSettings() {
        // Dimensions, pos.
        setBounds(0, 0, 200, 40);

        // Background/Foreground
        setBorderPainted(false);
        setFocusPainted(false);
        //setBorder(BorderFactory.createLineBorder(BaseUI.fontColor)); // Debugging

        // Text
        setText(getText().toUpperCase());
        setFont(BaseUI.text);
        setHorizontalAlignment(SwingConstants.LEFT);
        setForeground(BaseUI.fontColor);

        // Set text fitting
        setSize(getText().length() * 40, getHeight());
    }
}
