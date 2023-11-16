package com.example.server.Controllers;

import javafx.animation.TranslateTransition;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class NewReportCont {

    public Button button1;
    public Button button2;
    public Button button3;
    public Button toggleMenuButton;
    @FXML
    private VBox burgerMenu;

    private boolean menuVisible = false;

    @FXML
    private void initialize() {
    }
    @FXML
    private void toggleMenu() {
        TranslateTransition menuAnimation = new TranslateTransition(Duration.millis(300), burgerMenu);

        if (!menuVisible) {
            menuAnimation.setToX(0);
            menuVisible = true;
            burgerMenu.setVisible(true);
            toggleMenuButton.setText("✖");
        } else {
            menuAnimation.setToX(-burgerMenu.getWidth());
            menuVisible = false;
            burgerMenu.setVisible(false);
            toggleMenuButton.setText("☰");
        }
        menuAnimation.play();
    }
}
