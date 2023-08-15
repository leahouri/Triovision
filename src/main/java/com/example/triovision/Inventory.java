package com.example.triovision;


import javafx.scene.layout.StackPane;

public class Inventory {
    private StackPane pane;
    private TilePattern[] tilePatterns = new TilePattern[4];

    public Inventory(InfoCenter infoCenter) {
        pane = new StackPane();
        pane.setVisible(false);
        pane.setMinSize(UIConstants.APP_WIDTH, UIConstants.INVENTORY_HEIGHT);
        pane.setTranslateX(UIConstants.APP_WIDTH / 2);
        pane.setTranslateY(UIConstants.INFO_CENTER_HEIGHT + UIConstants.TILE_BOARD_HEIGHT + UIConstants.INVENTORY_HEIGHT / 2);
        for (int i = 0; i < 4; i++) {
            TilePattern tilePattern = new TilePattern(i);
            tilePatterns[i] = tilePattern;
            pane.getChildren().add(tilePattern.getStackPane());
        }
    }

    public TilePattern[] getTilePatterns() {
        return tilePatterns;
    }

    public StackPane getStackPane() {
        return pane;
    }

    public void setVisibility(boolean visibility) {
        pane.setVisible(visibility);
    }
}
