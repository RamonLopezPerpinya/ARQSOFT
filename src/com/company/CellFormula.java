package com.company;

public class CellFormula extends Cell{

    Double value;
    Tree tree;
    Calculator calculator = new Calculator();

    public CellFormula(String content){
        this.content = content;
    }

    public void setValueAndTree(Tree tree){
        this.tree = tree;
        this.value = tree.value;
    }

    public String getPrintValue(){
        return Double.toString(this.value);
    }



}
