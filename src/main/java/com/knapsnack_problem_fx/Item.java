package com.knapsnack_problem_fx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Item {
    //Имя
    private StringProperty name;
    //Вес
    private IntegerProperty weight;
    //Ценность
    private IntegerProperty value;
    public Item(String name, int weight, int value) {
        this.name = new SimpleStringProperty(name);
        this.weight = new SimpleIntegerProperty(weight);
        this.value = new SimpleIntegerProperty(value);
    }
    public String getName() {
        return name.get();
    }
    public void setName(String name) {
        this.name.set(name);
    }
    public StringProperty nameProperty() {
        return name;
    }
    public int getWeight() {
        return weight.get();
    }
    public void setWeight(int weight) {
        this.weight.set(weight);
    }
    public IntegerProperty weightProperty() {
        return weight;
    }
    public int getValue() {
        return value.get();
    }
    public void setValue(int value) {
        this.value.set(value);
    }
    public IntegerProperty valueProperty() {
        return value;
    }
}
