package com.example.triovision;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class TileBoard {

    private StackPane pane;
    private InfoCenter infoCenter;
    private Tile[][] tiles;
    private Player[] players;
    private int playerTurn;
    private Paint selectedColor;

    public TileBoard(InfoCenter infoCenter, Player[] players) {
        pane = new StackPane();
        this.players = players;
        this.infoCenter = infoCenter;
        pane.setVisible(false);
        pane.setMinSize(UIConstants.APP_WIDTH, UIConstants.TILE_BOARD_HEIGHT);
        pane.setTranslateX(UIConstants.APP_WIDTH / 2);
        pane.setTranslateY(UIConstants.INFO_CENTER_HEIGHT + UIConstants.TILE_BOARD_HEIGHT / 2);
        addTiles();
        playerTurn = 1;
    }

    public StackPane getStackPane() {
        return pane;
    }

    private void addTiles() {
        tiles = new Tile[4][4];
        for (int row = 0; row < 4; row++) {
            for (int col = 0 ; col < 4; col++) {
                Tile tile = new Tile();
                tiles[row][col] = tile;
                tile.getStackPane().setTranslateX(col * 100 - 150);
                tile.getStackPane().setTranslateY(row * 100 - 100);
                setDefaultTileColor(tile, row, col);
                tile.getCircle().setCenterX(col * 100 + 50);
                tile.getCircle().setCenterY(col * 100 - 100 + 50);
                tile.getCircle().setRadius(50);
                tile.getStackPane().getChildren().add(tile.getCircle());
                pane.getChildren().add(tile.getStackPane());
            }
        }
    }

    private void setDefaultTileColor(Tile tile, int row, int col) {
        tile.setCircleColor(Color.TRANSPARENT);
        if (row == 0 && (col == 1 || col == 2)) {
            tile.setCircleColor(Color.GREEN);
        }
        if (row == 3 && (col == 1 || col == 2)) {
            tile.setCircleColor(Color.BLUE);
        }

        if (col == 0 && (row == 1 || row == 2)) {
            tile.setCircleColor(Color.RED);
        }
        if (col == 3 && (row == 1 || row == 2)) {
            tile.setCircleColor(Color.YELLOW);
        }

        tile.getCircle().setFill(tile.getCircleColor());
    }

    public void resetBoard() {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                setDefaultTileColor(tiles[row][col], row, col);
            }
        }
    }

    public Paint handleClick(Paint color){
        Paint returnedColor = null;
        int numberOfTilesSelected = countSelectedTiles();
        if (numberOfTilesSelected == 1) {
            selectedColor = color;
            returnedColor = Color.TRANSPARENT;
            infoCenter.setInstructions("Please select which (different & empty) tile you want to move your color to.");
        } else if (numberOfTilesSelected == 2) {
            returnedColor = selectedColor;
            unselectTiles();
            changePlayerTurn();
        }
        return returnedColor;
    }

    public void unselectTiles() {
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                if (tile.isSelected()){
                    tile.unselectTile();
                }
            }
        }
    }

    public int countSelectedTiles() {
        int selectedTiles = 0;
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                if (tile.isSelected()){
                    selectedTiles += 1;
                }
            }
        }
        return selectedTiles;
    }

    public void changePlayerTurn() {
        players[playerTurn-1].getInventory().setVisibility(false);
        if (playerTurn == 1) {
            playerTurn = 2;
        }
        else {
            playerTurn = 1;
        }

        infoCenter.updateMessage("Player " + playerTurn + "'s Turn");
        infoCenter.setInstructions("Select a Color you Want to Move");
        infoCenter.setPlayerPointsMessage("You have " + players[playerTurn-1].getPoints() + " points.");
        players[playerTurn-1].getInventory().setVisibility(true);
    }

    public void setVisibility(boolean visibility) {
        pane.setVisible(visibility);
    }

    public boolean checkPattern() {
        boolean result = false;
        for (TilePattern tilePattern : players[playerTurn - 1].getInventory().getTilePatterns()) {
            if (!tilePattern.isFound()) {
                for (int row = 0; row < 4; row++) {
                    for (int col = 0; col < 4; col++) {
                        if (tiles[row][col].getCircleColor() == tilePattern.getPatternColors()[2]) {
                            if (tilePattern.isLShaped() == 1) {
                                try { // Checks Top-Left Duo
                                    if (tiles[row - 1][col - 1].getCircleColor() == tilePattern.getPatternColors()[1]) {
                                        try {
                                            if (tiles[row - 2][col - 1].getCircleColor() == tilePattern.getPatternColors()[0]) {
                                                result = true;
                                                tilePattern.setFound();
                                                break;
                                            }
                                        } catch (Exception e) {
                                            ;
                                        }
                                    }
                                } catch (Exception e) {
                                    ;
                                }

                                try { // Checks Top-Horizontal Duo
                                    if (tiles[row - 1][col + 1].getCircleColor() == tilePattern.getPatternColors()[1]) {
                                        try {
                                            if (tiles[row - 1][col + 2].getCircleColor() == tilePattern.getPatternColors()[0]) {
                                                result = true;
                                                tilePattern.setFound();
                                                break;
                                            }
                                        } catch (Exception e) {
                                            ;
                                        }
                                    }
                                } catch (Exception e) {
                                    ;
                                }

                                try { // Checks Bottom-Right Duo
                                    if (tiles[row + 1][col + 1].getCircleColor() == tilePattern.getPatternColors()[1]) {
                                        try {
                                            if (tiles[row + 2][col + 1].getCircleColor() == tilePattern.getPatternColors()[0]) {
                                                result = true;
                                                tilePattern.setFound();
                                                break;
                                            }
                                        } catch (Exception e) {
                                            ;
                                        }
                                    }
                                } catch (Exception e) {
                                    ;
                                }

                                try { // Checks Bottom-Horizontal Duo
                                    if (tiles[row + 1][col - 1].getCircleColor() == tilePattern.getPatternColors()[1]) {
                                        try {
                                            if (tiles[row + 1][col - 2].getCircleColor() == tilePattern.getPatternColors()[0]) {
                                                result = true;
                                                tilePattern.setFound();
                                                break;
                                            }
                                        } catch (Exception e) {
                                            ;
                                        }
                                    }
                                } catch (Exception e) {
                                    ;
                                }

                            } else {
                                try { // Checks Top-Right Duo
                                    if (tiles[row - 1][col + 1].getCircleColor() == tilePattern.getPatternColors()[1]) {
                                        try {
                                            if (tiles[row - 2][col + 1].getCircleColor() == tilePattern.getPatternColors()[0]) {
                                                result = true;
                                                tilePattern.setFound();
                                                break;
                                            }
                                        } catch (Exception e) {
                                            ;
                                        }
                                    }
                                } catch (Exception e) {
                                    ;
                                }

                                try { // Checks Top-Horizontal Duo
                                    if (tiles[row - 1][col - 1].getCircleColor() == tilePattern.getPatternColors()[1]) {
                                        try {
                                            if (tiles[row - 1][col - 2].getCircleColor() == tilePattern.getPatternColors()[0]) {
                                                result = true;
                                                tilePattern.setFound();
                                                break;
                                            }
                                        } catch (Exception e) {
                                            ;
                                        }
                                    }
                                } catch (Exception e) {
                                    ;
                                }

                                try { // Checks Bottom-Left Duo
                                    if (tiles[row + 1][col - 1].getCircleColor() == tilePattern.getPatternColors()[1]) {
                                        try {
                                            if (tiles[row + 2][col - 1].getCircleColor() == tilePattern.getPatternColors()[0]) {
                                                result = true;
                                                tilePattern.setFound();
                                                break;
                                            }
                                        } catch (Exception e) {
                                            ;
                                        }
                                    }
                                } catch (Exception e) {
                                    ;
                                }

                                try { // Checks Bottom-Horizontal Duo
                                    if (tiles[row + 1][col + 1].getCircleColor() == tilePattern.getPatternColors()[1]) {
                                        try {
                                            if (tiles[row + 1][col + 2].getCircleColor() == tilePattern.getPatternColors()[0]) {
                                                result = true;
                                                tilePattern.setFound();
                                                break;
                                            }
                                        } catch (Exception e) {
                                            ;
                                        }
                                    }
                                } catch (Exception e) {
                                    ;
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    public boolean checkWinner() {
        boolean result = true;
        for (TilePattern tilePattern : players[playerTurn - 1].getInventory().getTilePatterns()) {
            if (!tilePattern.isFound()) {
                result = false;
            }
        }
        return result;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public class Tile {
        private StackPane pane;
        private Circle circle;
        private Paint circleColor;
        private boolean selected;
        private Rectangle border;

        private Tile() {
            selected = false;
            pane = new StackPane();
            pane.setMinSize(100, 100);

            border = new Rectangle();
            border.setWidth(100);
            border.setHeight(100);
            border.setFill(Color.TRANSPARENT);
            border.setStroke(Color.BLACK);
            pane.getChildren().add(border);

            circle = new Circle();

            pane.setOnMouseClicked(mouseEvent -> {
                selected = !selected;
                Paint newColor = handleClick(circleColor);
                if (newColor != null) {
                    circleColor = newColor;;
                    circle.setFill(circleColor);
                }
            });
        }

        public boolean isSelected() {
            return selected;
        }

        public void unselectTile() {
            selected = false;
        }

        public StackPane getStackPane() {
            return pane;
        }

        public Circle getCircle() {
            return circle;
        }

        public Paint getCircleColor() {
            return circleColor;
        }

        public void setCircleColor(Paint color) {
            this.circleColor = color;
        }

    }
}
