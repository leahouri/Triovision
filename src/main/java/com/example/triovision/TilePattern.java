package com.example.triovision;

import javafx.scene.layout.StackPane;

public class TilePattern {
    private StackPane pane;
    private TileBoard.Tile[][] tiles;

    public TilePattern(int inventorySlot) {
        this.pane = new StackPane();
        pane.setMinSize(UIConstants.APP_WIDTH / 4, UIConstants.INVENTORY_HEIGHT / 4);
        if (inventorySlot == 0) {
            pane.setTranslateX(12.5);
        } else {
            pane.setTranslateX(UIConstants.APP_WIDTH / 4 / inventorySlot + 12.5);
        }
        pane.setTranslateY(UIConstants.INFO_CENTER_HEIGHT + UIConstants.TILE_BOARD_HEIGHT + UIConstants.INVENTORY_HEIGHT / 2);
    }


    public StackPane getStackPane() {
        return pane;
    }
}
