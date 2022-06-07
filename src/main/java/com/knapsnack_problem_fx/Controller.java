package com.knapsnack_problem_fx;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.util.Duration;

public class Controller {

    @FXML
    private TextField Value_Field;

    @FXML
    private TextField Weight_Field;

    @FXML
    private Label textCapacity;

    @FXML
    private Button button_clear;

    @FXML
    private Button Button_exit;

    @FXML
    private Button Button_fill;

    @FXML
    private Button Set_button;

    @FXML
    private Button Add_button;

    @FXML
    private Button generate_items_bottom;

    @FXML
    private Button download;

    @FXML
    private TextField max_weight;

    @FXML
    private TextField max_value;

    @FXML
    private TextField quantity;

    @FXML
    private TextField Capacity_Field;

    @FXML
    private TextField Name_Field;

    @FXML
    private TableView<Item> itemsView_all;
    @FXML
    private TableColumn<Item, Integer> weightColumn;
    @FXML
    private TableColumn <Item, String> nameColumn;
    @FXML
    private TableColumn<Item, Integer> valueColumn;

    @FXML
    private Pane visual_panel;

    @FXML
    private Circle circle_item2;
    @FXML
    private Circle circle_item1;

    @FXML
    private Label item_name2;

    @FXML
    private Label item_name1;

    @FXML
    private TableView<Item> itemsView_in;
    @FXML
    private TableColumn<Item, Integer> weightColumn2;
    @FXML
    private TableColumn<Item, String> nameColumn2;
    @FXML
    private TableColumn<Item, Integer> valueColumn2;



    //Ccылка на главный класс-контроллер
    private MainApp mainApp;
    private ObservableList<Item> Items = FXCollections.observableArrayList(); //коллекция всех предметов, претендующих на попадание в рюкзак
    private ObservableList<Item> putItems = FXCollections.observableArrayList(); //коллекция всех предметов, попавших в рюкзак, в результате работы алгоритма
    int capacity = 0;
    private int indx = 0;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    // управление таблицами на форме
    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("weight"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("value"));
        itemsView_all.setItems(Items);
        nameColumn2.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        weightColumn2.setCellValueFactory(new PropertyValueFactory<Item, Integer>("weight"));
        valueColumn2.setCellValueFactory(new PropertyValueFactory<Item, Integer>("value"));
        itemsView_in.setItems(putItems);

    }
    // действия при нажатии на кнопку добавления предмета
    @FXML
    private void AddItem() {
        if (isInputValidForAdd()) //проверка на валидность ввода
        {
            String name = Name_Field.getText();
            int weight = Integer.parseInt(Weight_Field.getText());
            int value = Integer.parseInt(Value_Field.getText());
            Items.add(new Item(name, weight, value));
        }
    }
    @FXML
    private  void SetCapacity()
    {
        if (isInputValidForFillInventory()){
        capacity = Integer.parseInt(Capacity_Field.getText());
        textCapacity.setText(Integer.toString(capacity));
        }
    }

    @FXML
    private void GenerateItem() {
        if (isInputValidForGenerate()) {
            int count = Integer.parseInt(quantity.getText());
            int m_weight = Integer.parseInt(max_weight.getText());
            int m_value = Integer.parseInt(max_value.getText());
            for (int i=0; i<count; i++){
            Items.add(generator.GenerateItem(m_weight, m_value));}
        }
    }

    @FXML
    private void Clear() {
        Items.clear();
        putItems.clear();
    }
    @FXML
    private  void Fill(){
        if (capacity==0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Error");
            alert.setHeaderText("Enter backpack capacity!");
            alert.showAndWait();
        }
        else{
        putItems = problem_solver.getBestItems(Items, capacity);
        itemsView_in.setItems(putItems);
        if (putItems.size()>=1){
            indx = 0;
            circle_item2.setLayoutX(572);
            circle_item2.setLayoutY(105);
            item_name2.setLayoutX(545);
            item_name2.setLayoutY(96);
            circle_item1.setLayoutX(200);
            circle_item1.setLayoutY(105);
            item_name1.setLayoutX(175);
            item_name1.setLayoutY(96);
            Button_fill.setDisable(true);
            animation(putItems.size(), putItems);}}
    }
    @FXML
    private void download (){
        MainApp choose = new MainApp();
        Items = choose.show_chooser();
        itemsView_all.setItems(Items);
        //itemsView_in.setItems(putItems.getItems());
    }
    private void animation(Integer k, ObservableList<Item> putItems){
        //Iterator<Item> Iter = putItems.iterator();
        Item[] ArrItems = putItems.toArray(Item[]::new);
        String that = ArrItems[indx++].getName();
        if (indx<ArrItems.length){
        String that2 = ArrItems[indx++].getName();
        item_name2.setText(that2);
        if (that2.length()>7){item_name2.setFont(Font.font("Eras Demi ITC", FontPosture.REGULAR ,12));}
        else {item_name2.setFont(Font.font("Eras Demi ITC", FontPosture.REGULAR ,15));}
        item_name2.setVisible(true);
        circle_item2.setVisible(true);}
        item_name1.setText(that);
        if (that.length()>7){item_name1.setFont(Font.font("Eras Demi ITC", FontPosture.REGULAR ,12));}
        else {item_name1.setFont(Font.font("Eras Demi ITC", FontPosture.REGULAR ,15));}
        circle_item1.setVisible(true);
        item_name1.setVisible(true);
        KeyValue xValue = new KeyValue(circle_item1.translateXProperty(), 173);
        KeyValue yValue = new KeyValue(circle_item1.translateYProperty(), 80);
        KeyValue xValueL = new KeyValue(item_name1.translateXProperty(), 173);
        KeyValue yValueL = new KeyValue(item_name1.translateYProperty(), 80);

        KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
            boolean b = true;
            String that;
            @Override
            public void handle(ActionEvent actionEvent) {
                if(b){

                    KeyFrame in = new KeyFrame(Duration.millis(500), xValue, yValue);
                    KeyFrame inL = new KeyFrame(Duration.millis(500), xValueL, yValueL);
                    Timeline timeline_in = new Timeline();
                    timeline_in.getKeyFrames().addAll(in, inL);
                    timeline_in.play();
                    b = !b;

                }
                else {
                    if (indx < ArrItems.length){
                        that = ArrItems[indx++].getName();
                        item_name1.setText(that);
                        if (that.length()>7){item_name1.setFont(Font.font("Eras Demi ITC", FontPosture.REGULAR ,12));}
                        else {item_name1.setFont(Font.font("Eras Demi ITC", FontPosture.REGULAR ,15));}}
                    else {circle_item1.setVisible(false);
                        item_name1.setVisible(false);
                        Button_fill.setDisable(false);}
                    circle_item1.setTranslateX(circle_item1.getTranslateX() - 173);
                    circle_item1.setTranslateY(circle_item1.getTranslateY() - 80);
                    b = !b;
                    item_name1.setTranslateX(item_name1.getTranslateX() - 173);
                    item_name1.setTranslateY(item_name1.getTranslateY() - 80);
                }
            }
        });


        //////////////////////

        KeyValue xValue2 = new KeyValue(circle_item2.translateXProperty(), -173);
        KeyValue yValue2 = new KeyValue(circle_item2.translateYProperty(), 80);
        KeyValue xValueL2 = new KeyValue(item_name2.translateXProperty(), -173);
        KeyValue yValueL2 = new KeyValue(item_name2.translateYProperty(), 80);
        KeyFrame keyFrame2 = new KeyFrame(Duration.millis(2000), new EventHandler<ActionEvent>() {
            boolean b2 = true;
            String that2;
            @Override
            public void handle(ActionEvent actionEvent) {
                if(b2){
                    KeyFrame in2 = new KeyFrame(Duration.millis(500), xValue2, yValue2);
                    KeyFrame in2L = new KeyFrame(Duration.millis(500), xValueL2, yValueL2);
                    Timeline timeline_in = new Timeline();
                    timeline_in.getKeyFrames().addAll(in2, in2L);
                    timeline_in.play();
                    b2 = !b2;

                }
                else {
                    if (indx<ArrItems.length ){
                    that2 = ArrItems[indx++].getName();
                    item_name2.setText(that2);
                        if (that2.length()>7){item_name2.setFont(Font.font("Eras Demi ITC", FontPosture.REGULAR ,12));}
                        else {item_name2.setFont(Font.font("Eras Demi ITC", FontPosture.REGULAR ,15));}}
                    else {circle_item2.setVisible(false);
                        item_name2.setVisible(false);
                        Button_fill.setDisable(false);}
                    circle_item2.setTranslateX(circle_item2.getTranslateX() + 173);
                    circle_item2.setTranslateY(circle_item2.getTranslateY() - 80);
                    item_name2.setTranslateX(item_name2.getTranslateX() + 173);
                    item_name2.setTranslateY(item_name2.getTranslateY() - 80);
                    b2 = !b2;
                }
            }
        });
        ///////////////
        Timeline timeline = new Timeline();
        if (k%2==0){
        timeline.setCycleCount(k);}
        else {timeline.setCycleCount(k+1);}
        timeline.getKeyFrames().addAll(keyFrame, keyFrame2);
        timeline.play();

    }


    private boolean isInputValidForFillInventory() {
        StringBuilder errMessage = new StringBuilder();
        if (Capacity_Field.getText() == null || Capacity_Field.getText().isEmpty()) {
            errMessage.append("Backpack capacity cannot be empty\n");
        }
        else {
            try {
                Integer.parseInt(Capacity_Field.getText());
            }
            catch (NumberFormatException e) {
                errMessage.append("Backpack capacity must be an integer\n");
            }
        }
        if (errMessage.toString().isEmpty()) {
            return true;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Error");
            alert.setHeaderText("Incorrectly entered data");
            alert.setContentText(errMessage.toString());
            alert.showAndWait();
            return false;
        }
    }

    private boolean isInputValidForAdd() {
        StringBuilder errMessage = new StringBuilder();
        if (Name_Field.getText() == null || Name_Field.getText().isEmpty()) {
            errMessage.append("Item name cannot be empty\n");
        }
        if (Weight_Field.getText() == null || Weight_Field.getText().isEmpty()) {
            errMessage.append("Item weight cannot be empty\n");
        }
        else {
            try {
                Integer.parseInt(Weight_Field.getText());
            }
            catch (NumberFormatException e) {
                errMessage.append("Item weight must be an integer\n");
            }
        }
        if (Value_Field.getText() == null || Value_Field.getText().isEmpty()) {
            errMessage.append("Item value cannot be empty\n");
        }
        else {
            try {
                Integer.parseInt(Value_Field.getText());
            }
            catch (NumberFormatException e) {
                errMessage.append("Item value must be an integer\n");
            }
        }
        if (errMessage.toString().isEmpty()) {
            return true;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Error");
            alert.setHeaderText("Incorrectly entered data");
            alert.setContentText(errMessage.toString());
            alert.showAndWait();
            return false;
        }
    }

    private boolean isInputValidForGenerate() {
        StringBuilder errMessage = new StringBuilder();
        if (quantity.getText() == null || quantity.getText().isEmpty()) {
            errMessage.append("Quantity cannot be empty\n");
        }
        else {
            try {
                Integer.parseInt(quantity.getText());
            }
            catch (NumberFormatException e) {
                errMessage.append("Quantity must be an integer\n");
            }
        }
        if (max_weight.getText() == null || max_weight.getText().isEmpty()) {
            errMessage.append("Item max weight cannot be empty\n");
        }
        else {
            try {
                Integer.parseInt(max_weight.getText());
            }
            catch (NumberFormatException e) {
                errMessage.append("Item max weight must be an integer\n");
            }
        }
        if (max_value.getText() == null || max_value.getText().isEmpty()) {
            errMessage.append("Item max value cannot be empty\n");
        }
        else {
            try {
                Integer.parseInt(max_value.getText());
            }
            catch (NumberFormatException e) {
                errMessage.append("Item max value must be an integer\n");
            }
        }
        if (errMessage.toString().isEmpty()) {
            return true;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Error");
            alert.setHeaderText("Incorrectly entered data");
            alert.setContentText(errMessage.toString());
            alert.showAndWait();
            return false;
        }
    }
}
