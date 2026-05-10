package org.redcape.view.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.redcape.controller.GameState;
import org.redcape.controller.KeyController;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;


class UIControllerTest {

    private JFrame frame;
    private KeyController keyController;
    private UIController uiController;

    static class DummyUI extends BaseUI {
        boolean repainted = false;
        public DummyUI() { super(); }
        @Override
        public void repaint() { repainted = true; }

        @Override
        public void build() {}

        @Override
        public void addElements() {}

        @Override
        public void addActionListeners() {}
    }

    @BeforeEach
    void setUp() {
        frame = new JFrame();
        keyController = new KeyController(new GameState(GameState.State.MENU));
        uiController = new UIController(frame, keyController);
    }

    @Test
    void testAddAndSetActiveScreen() {
        DummyUI screen1 = new DummyUI();
        DummyUI screen2 = new DummyUI();

        uiController.addScreen("screen1", screen1);
        uiController.addScreen("screen2", screen2);

        uiController.setActiveScreen("screen1");
        assertTrue(screen1.isVisible());

        uiController.setActiveScreen("screen2");
        assertTrue(screen2.isVisible());
    }

    @Test
    void testSetActiveScreenByObject() {
        DummyUI screen = new DummyUI();
        uiController.setActiveScreen(screen);
        assertTrue(screen.isVisible());
    }

    @Test
    void testRepaint() {
        DummyUI screen = new DummyUI();
        uiController.setActiveScreen(screen);
        screen.repainted = false;
        uiController.repaint();
        assertTrue(screen.repainted);
    }
}