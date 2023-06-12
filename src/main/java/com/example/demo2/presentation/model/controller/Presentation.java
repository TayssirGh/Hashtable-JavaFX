package com.example.demo2.presentation.model.controller;

import com.example.demo2.model.Table;
import com.example.demo2.presentation.view.HashtableDrawComponent;
import com.example.demo2.service.Service;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Optional;

public class Presentation {
    private int size;
    public void showPresentation(Stage stage){

        Pane pane = new Pane();


        BorderPane borderPane = new BorderPane();
        borderPane.setPrefSize(700,400);

        Scene scene = new Scene(borderPane,700,400);
        MenuBar menubar = new MenuBar();
        Menu FileMenu = new Menu("File");
        MenuItem sizeClick = new MenuItem("size");
        MenuItem addClick =new MenuItem("add");
        MenuItem clearClick =new MenuItem("clear");
        Menu help=new Menu("help");
        MenuItem about=new MenuItem("about");
        help.getItems().addAll(about);
        Service service = new Service(new Table(10));
        HashtableDrawComponent drawComponent = new HashtableDrawComponent();
//        drawComponent.setModel(service.getTable());
        clearClick.setOnAction(actionEvent -> {
            pane.getChildren().clear();
            borderPane.setCenter(pane);
        });
        about.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("About");
            alert.setHeaderText(null);
            alert.setContentText("Tayssir");

            ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(buttonTypeOk);

            alert.showAndWait();
        });
        sizeClick.setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Size Input");
            dialog.setHeaderText(null);
            dialog.setContentText("Enter the size:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(sizeString -> {
                try {
                    this.size = Integer.parseInt(sizeString);
                    Table table = new Table(size);
                    pane.getChildren().clear();
                    service.setTable(table);
                    drawComponent.setModel(table);
                    System.out.println("Entered size: " + size);
                    pane.getChildren().add(drawComponent.paintComponent());
                    borderPane.setCenter(pane);


                }
                catch (Exception e){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Size Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Yehdik ! ");
                    alert.showAndWait();
                }
            });
        });

        addClick.setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("name Input");
            dialog.setHeaderText(null);
            dialog.setContentText("Enter the name:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(name -> {
                service.add(name);
                pane.getChildren().clear();
                drawComponent.setModel(drawComponent.getModel());
                pane.getChildren().add(drawComponent.paintComponent());
                borderPane.setCenter(pane);

            });
        });

        scene.setOnMouseClicked(event -> {
            if (drawComponent.getDelTest()) {
                service.remove(drawComponent.getDelName());
                pane.getChildren().clear();
                drawComponent.setModel(drawComponent.getModel());
                pane.getChildren().add(drawComponent.paintComponent());
                System.out.println("salut : "+drawComponent.getDelName());
            }
        });
        FileMenu.getItems().addAll(sizeClick,addClick, clearClick);
        menubar.getMenus().addAll(FileMenu,help);
        borderPane.setTop(menubar);

        stage.setScene(scene);
        stage.show();


    }

}
