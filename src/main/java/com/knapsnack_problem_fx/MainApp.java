package com.knapsnack_problem_fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class MainApp extends Application {

    private Stage primaryStage;
    private Controller controller;
    private ObservableList<Item> items = FXCollections.observableArrayList();

    public Stage getPrimaryStage() {
        return primaryStage;
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        primaryStage.getIcons().add(new Image("C:\\Users\\Мария\\Desktop\\Java_HSE\\knapsnack_problem_fx\\src\\main\\resources\\images\\icon.png"));
        this.primaryStage.setTitle("Knapsack problem");
        showBaseWindow();

    }


    public void showBaseWindow(){
        try{
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 796, 770);
        controller = fxmlLoader.getController();
        controller.setMainApp(this);
        primaryStage.setScene(scene);
        primaryStage.show();


        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }

    public ObservableList<Item> show_chooser(){
        ObservableList<Item> putItems = FXCollections.observableArrayList();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT", "*.txt"));
        File file = fileChooser.showOpenDialog(primaryStage);
        StringBuilder errMessage = new StringBuilder();
        if (file != null) {
            System.out.println(file);
            System.out.println("File name: " + file.getName());
            try {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {

                    String name = scanner.next();
                    String weight= null, value = null;
                    if (scanner.hasNext()){
                    weight = scanner.next();}
                    if (scanner.hasNext()){
                    value = scanner.next();}
                    if (name == null || name.isEmpty() || weight == null || weight.isEmpty() || value == null || value.isEmpty()) {
                        errMessage.append("invalid data format in file\n");
                    } else {
                        try {
                            Integer.parseInt(weight);
                        } catch (NumberFormatException e) {
                            errMessage.append("Item weight must be an integer\n");
                        }
                        try {
                            Integer.parseInt(value);
                        } catch (NumberFormatException e) {
                            errMessage.append("Item value must be an integer\n");
                        }
                    }

                    if (errMessage.toString().isEmpty()) {
                        putItems.add(new Item(name, Integer.parseInt(weight), Integer.parseInt(value)));
                        ;
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.initOwner(getPrimaryStage());
                        alert.setTitle("Error");
                        alert.setHeaderText("Incorrectly entered data");
                        alert.setContentText(errMessage.toString());
                        alert.showAndWait();
                        putItems.clear();
                        return putItems;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else{
            putItems.clear();
            return putItems;
        }
        return putItems;
    }
    public static void main(String[] args) {
        launch();
    }
}