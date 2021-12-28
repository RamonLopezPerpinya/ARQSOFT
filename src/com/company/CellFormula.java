package com.company;

public class CellFormula extends Cell{

    Double value;
    Tree tree;
    Calculator calculator = new Calculator();

    public CellFormula(String content){
        this.content = content;
        //tree = new Tree(content);
        this.value = calculator.Result(content);
    }



}
