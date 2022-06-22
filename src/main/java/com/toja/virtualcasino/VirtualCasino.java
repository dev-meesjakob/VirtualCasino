package com.toja.virtualcasino;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class VirtualCasino extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(VirtualCasino.class.getResource("virtualcasino-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        scene.getStylesheets().add((getClass().getResource("css/virtualcasino.css")).toExternalForm());
        stage.setResizable(false);
        stage.setTitle("Home");
        stage.setScene(scene);
        stage.getIcons().add(new Image(Objects.requireNonNull(VirtualCasino.class.getResourceAsStream("img" +
                "/VirtualCasino.png"))));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}