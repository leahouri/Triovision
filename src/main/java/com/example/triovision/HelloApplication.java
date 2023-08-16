package com.example.triovision;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HelloApplication extends Application {
    private Stage stage;

    private InfoCenter infoCenter;
    private TileBoard tileBoard;
    private Player[] players = new Player[2];
    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, UIConstants.APP_WIDTH, UIConstants.APP_HEIGHT + 50);
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
        infoCenter.setCheckPatternButtonAction(checkPattern());
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
                infoCenter.setStartGameButtonVisibility(false);
                infoCenter.updateMessage("Player 1's Turn");
                infoCenter.getInstructions().setVisible(true);
                infoCenter.getPlayerPointsLabel().setVisible(true);
                infoCenter.setPlayerPointsMessage("You have " + players[0].getPoints() + " points.");
                infoCenter.setCheckPatternButtonVisibility(true);
                tileBoard.setVisibility(true);
                players[0].getInventory().setVisibility(true);
            }
        };
    }

    private EventHandler<ActionEvent> checkPattern() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (tileBoard.checkPattern()) {
                    players[tileBoard.getPlayerTurn() - 1].addPoints(1);
                    System.out.println("Correct ! +1 Point");
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (Exception e) {
                        ;
                    }
                    if (tileBoard.checkWinner()) {
                        endGame();
                    } else {
                        tileBoard.changePlayerTurn();
                        tileBoard.resetBoard();
                    }
                } else {
                    System.out.println("Wrong !");
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (Exception e) {
                        ;
                    }
                    tileBoard.changePlayerTurn();
                }
            }
        };
    }

    private void endGame(){
        System.out.println("PLAYER " + tileBoard.getPlayerTurn() + " WINS !");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e) {
            ;
        }
        Platform.exit();
        System.exit(0);

    }

    public static void main(String[] args) {
        launch();
    }
}