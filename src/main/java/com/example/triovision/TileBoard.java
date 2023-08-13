package com.example.triovision;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class TileBoard {

    private StackPane pane;
    private InfoCenter infoCenter;

    public TileBoard(InfoCenter infoCenter) {
        this.infoCenter = infoCenter;
        pane = new StackPane();
        pane.setMinSize(UIConstants.APP_WIDTH, UIConstants.TILE_BOARD_HEIGHT);
        pane.setTranslateX(UIConstants.APP_WIDTH / 2);
        pane.setTranslateY(UIConstants.INFO_CENTER_HEIGHT + UIConstants.TILE_BOARD_HEIGHT / 2);
    }

    public StackPane getStackPane() {
        return pane;
    }

    private class Tile {
        private StackPane pane;
        private Label label;
        private Paint circleColor;

        public Tile() {
            pane = new StackPane();
            pane.setMinSize(100, 100);

            Rectangle border = new Rectangle();
            border.setWidth(100);
            border.setHeight(100);
            border.setFill(Color.TRANSPARENT);
            border.setStroke(circleColor);
            pane.getChildren().add(border);

            Label label = new Label("");
            label.setAlignment(Pos.CENTER);
            label.setFont(Font.font(24));
            pane.getChildren().add(label);

            pane.setOnMouseClicked(); //TO DO
        }

        public StackPane getStackPane() {
            return pane;
        }

        public String getValue(){
            return label.getText();
        }

        public void setValue(String value) {
            label.setText(value);
        }

        public void setCircleColor(Paint color) {
            this.circleColor = color;
        }

    }
}
