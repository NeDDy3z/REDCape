package org.redcape.view.ui.elements;

import org.redcape.view.ui.BaseUI;

import javax.swing.*;


/**
 * CustomLabel class for creating a label with default settings.
 * <p>
 * This class extends JLabel and provides methods to set the label's position and size.
 * </p>
 */
public final class CustomLabel extends JLabel {


    /**
     * Constructor for the CustomLabel class
     *
     * @param text of the label
     */
    public CustomLabel(String text) {
        super(text);
        setDefaultSettings();
    }

    /**
     * Set the label's position on the screen.
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
     * Set the label default styling settings.
     */
    private void setDefaultSettings() {
        // Dimensions, pos.
        setBounds(0, 0, 200, 50);

        // Text
        setFont(BaseUI.text);
        setHorizontalAlignment(SwingConstants.LEFT);
        setForeground(BaseUI.fontColor);
    }
}
