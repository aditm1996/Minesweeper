package minesweeper.controllers;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
public class Controller {

    @FXML
    public void handle(ActionEvent event) {

        System.out.println(event.getSource());
        System.out.println("HEET\n");

    }
}
