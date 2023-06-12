package com.example.demo2.presentation.view;

import com.example.demo2.model.Node;
import com.example.demo2.model.Table;
import javafx.animation.FillTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Optional;

public class HashtableDrawComponent  {
    String delName;
    Rectangle delNode;
    Boolean delTest = false;
    private Table model = new Table(10) ;

    public Boolean getDelTest() {
        return delTest;
    }

    public String getDelName() {
        return delName;
    }

    public void setModel(Table model){
        this.model = model;
//        Platform.runLater(this::paintComponent);
    }
    public Table getModel() {

        return model;
    }


    public HashtableDrawComponent() {

    }





    public void drawNode(Pane pane, Node n, int nodeX  , int nodeY , int caseSize){
        Rectangle nodeRect = new Rectangle(nodeX, nodeY, caseSize - 20, caseSize - 40);
        nodeRect.setFill(Color.WHITE);
        nodeRect.setStroke(Color.BLACK);
        nodeRect.setStrokeWidth(1);
        pane.getChildren().add(nodeRect);

        Text nodeText = new Text(n.getValue());
        nodeText.setX(nodeX + 15);
        nodeText.setY(nodeY + 30);
        pane.getChildren().add(nodeText);

        Line line = new Line(nodeX, nodeY + 25, nodeX - 40, nodeY + 25);
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(1);
        pane.getChildren().add(line);

        Line connectionLine = new Line(nodeX + caseSize - 17, nodeY + 25, nodeX + caseSize + 20, nodeY + 25);
        connectionLine.setStroke(Color.BLACK);
        connectionLine.setStrokeWidth(1);
        pane.getChildren().add(connectionLine);
        nodeRect.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Remove Node");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to remove " + n.getValue() + " ?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    delTest = true;
                    delName = n.getValue();
                    System.out.println(delName);
                    delNode = nodeRect;

                }
            }
        });

    }
    public void drawMsalha(Pane pane, int nodeX, int lineY){
        Line line1 = new Line(nodeX, lineY + 20, nodeX, lineY - 20);
        line1.setStroke(Color.BLACK);
        line1.setStrokeWidth(1);
        pane.getChildren().add(line1);

        Line line2 = new Line(nodeX, lineY+10 , nodeX + 12, lineY-2 );
        line2.setStroke(Color.BLACK);
        line2.setStrokeWidth(1);
        pane.getChildren().add(line2);

        Line line3 = new Line(nodeX, lineY , nodeX + 12, lineY - 12);
        line3.setStroke(Color.BLACK);
        line3.setStrokeWidth(1);
        pane.getChildren().add(line3);
        Line line4 = new Line(nodeX, lineY - 10, nodeX + 12, lineY - 22);
        line4.setStroke(Color.BLACK);
        line4.setStrokeWidth(1);
        pane.getChildren().add(line4);

    }
    public Pane paintComponent(){
        Pane pane = new Pane();

        int tableSize = model.getNodes().length;
        int caseSize = 100;
        int tableHeight = caseSize * tableSize;

        int startX = (int) (pane.getWidth() - caseSize * 2 - 100) / 2 +500;
        int startY = (int) (pane.getHeight() - tableHeight) / 2 + 300;
        SequentialTransition sequentialTransition = new SequentialTransition();

        for (int i = 0; i < tableSize; i++) {
            System.out.println("case : " +i);
            int y = startY + i * caseSize;
            Rectangle cell = new Rectangle(startX, y, caseSize, caseSize);
            cell.setFill(Color.WHITE);
//            FillTransition fill = new FillTransition();
//            fill.setCycleCount(50);
//            fill.setDuration(Duration.millis(1000));
//            fill.setFromValue(Color.WHITE);
//            fill.setToValue(Color.GREEN);
            FillTransition fill = new FillTransition(Duration.millis(500), cell);
            fill.setFromValue(Color.WHITE);
            fill.setToValue(Color.GREEN);

            int finalI = i;
            fill.setOnFinished(event -> {
                // Add the index label to the cell
                Label indexLabel = new Label(String.valueOf(finalI));
                indexLabel.setLayoutX(startX + 5);
                indexLabel.setLayoutY(y + 5);
                pane.getChildren().add(indexLabel);
            });

        sequentialTransition.play();

            cell.setStroke(Color.BLACK);
            cell.setStrokeWidth(3);
            pane.getChildren().add(cell);

            Node n = model.getNodes()[i];
            boolean test = false;
            int nodeX = startX + caseSize + 40;

            while (n != null) {
                test = true;
                int nodeY = y + caseSize - 75;
                drawNode(pane , n, nodeX, nodeY, caseSize);
                nodeX += caseSize + 20;
                n = n.getNext();
            }

            if (test) {
                int lineY = y + caseSize / 2;
                drawMsalha( pane, nodeX, lineY);
            }
        }
    return pane;
    }

}
