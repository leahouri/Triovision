package com.example.triovision;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private InfoCenter infoCenter;
    @Override
    public void start(Stage stage) throws IOException {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, UIConstants.APP_WIDTH, UIConstants.APP_HEIGHT);
        stage.setTitle("Triovision !");
        initLayout(root);
        stage.setScene(scene);
        stage.show();
    }

    private void initLayout(BorderPane root) {
        initInfoCenter(root);
        initTileBoard(root);
        initInventory(root);
    }

    private void initInventory(BorderPane root) {
    }

    private void initTileBoard(BorderPane root) {
        
    }

    private void initInfoCenter(BorderPane root) {
        infoCenter = new InfoCenter();
        infoCenter.setStartButtonOnAction(startNewGame());
        root.getChildren().add(infoCenter.getStackPane());
    }

    private EventHandler<ActionEvent> startNewGame() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                infoCenter.hideStartButton();
                infoCenter.updateMessage("Player 1's Turn");
            }
        };
    }

    public static void main(String[] args) {
        launch();
    }
}