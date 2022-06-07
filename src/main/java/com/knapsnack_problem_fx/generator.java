package com.knapsnack_problem_fx;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class generator {

    private static ArrayList<String> list = new ArrayList<>(Arrays.asList("pen", "phone", "chalk", "clock", "eraser", "glue", "marker", "pencil",
            "pencil case", "sharpener", "ruler", "globe", "map", "scissors", "tape", "textbook", "notebook", "paper", "book", "pins", "clip",
            "pencil sharpener", "stapler", "calculator", "ballpoint", "highlighter", "paint", "palette", "paint brush", "protractor",
            "setsquare", "beaker", "flask", "test tube", "funnel", "laptop", "paper", "keyboard", "mouse", "badge", "notepad", "tablets",
            "scrunchy", "wet wipes", "pomade", "patch", "disinfector", "mask", "deodorant", "headphones", "power bank", "muesli", "paper handkerchiefs",
            "nail file", "comb", "mirror", "glasses", "keys", "powder", "mascara", "hand cream", "phone charger", "umbrella", "purse", "documents",
            "perfume", "corkscrew", "apple", "chocolate bar", "bottle of water", "launch box", "sandwich", "juice"));
    //Collection <String> list = {"pen", }

    public static Item GenerateItem(int max_weight, int max_value){
        int i = (int)(Math.random()*(list.size()));
        int w = (int)(1 + Math.random()*(max_weight));
        int v = (int)(Math.random()*(max_value));
        Item gen_item = new Item(list.get(i), w, v);
        return gen_item;
    }
}
