package com.company;

import java.util.ArrayList;

public class Tree {

    Double value = 0.0;
    String formula;
    Node root;
    Helper helper = new Helper();

    public Tree(String formula, Node root){
        this.formula = formula;
        this.root = root;
    }

}
