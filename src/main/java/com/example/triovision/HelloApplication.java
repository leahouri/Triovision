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
    private TileBoard tileBoard;
    private Player[] players = new Player[2];
    @Override
    public void start(Stage stage) throws IOException {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, UIConstants.APP_WIDTH, UIConstants.APP_HEIGHT);
        stage.setTitle("Triovision !");
        initLayout(root);
        stage.setScene(scene);
        stage.show();
    }

    private void initPlayers(BorderPane root) {
        for (int i = 0; i < 2; i++) {
            Player player = new Player(i + 1, initInventory(root));
            players[i] = player;
        }
    }

    private void initLayout(BorderPane root) {
        initInfoCenter(root);
        initPlayers(root);
        initTileBoard(root);
    }

    private Inventory initInventory(BorderPane root) {
        Inventory inventory = new Inventory(infoCenter);
        root.getChildren().add(inventory.getStackPane());
        return inventory;
    }

    private void initTileBoard(BorderPane root) {
        tileBoard = new TileBoard(infoCenter, players);
        root.getChildren().add(tileBoard.getStackPane());
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
                tileBoard.setVisibility(true);
                players[0].getInventory().setVisibility(true);
            }
        };
    }

    public static void main(String[] args) {
        launch();
    }
}