package com.company;

public class CellFormula extends Cell{

    String value;
    Tree tree;

    public CellFormula(String value){
        this.value = value;
        this.content = value;
        tree = new Tree();
    }
}
