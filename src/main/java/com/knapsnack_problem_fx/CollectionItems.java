package com.knapsnack_problem_fx;

import com.knapsnack_problem_fx.ArrItems;
import com.knapsnack_problem_fx.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class CollectionItems implements ArrItems{

    private ObservableList<Item> itemList = FXCollections.observableArrayList();
    private Integer capacity;

    {
        capacity = 0;
    }

    public CollectionItems() {
    }

    public CollectionItems(ObservableList<Item> itemList) {
        this.itemList = itemList;
    }

    @Override
    public void Add(Item item){
        itemList.add(item);
    }

    @Override
    public void Set(Integer cap) {
        capacity = cap;
    }

    public ObservableList<Item> getItems() {
        return itemList;
    }

    public int getCapacity(){
        return capacity;
    }

    public void clear(){
        itemList.clear();
    }

    public void setItemList(ObservableList<Item> itemList) {
        this.itemList = itemList;
    }
}
