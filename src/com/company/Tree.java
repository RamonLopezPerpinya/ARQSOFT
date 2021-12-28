package com.company;

import java.util.ArrayList;

public class Tree {

    Node root;
    Helper helper = new Helper();

    public Tree(String formula){

        String[] values = formula.split(";");
        for(int i = 0; i<values.length;i++){
            root = this.addRecursive(root,values[i]);
        }
    }

    //-;+;3;2;1;
    private Node addRecursive(Node current, String value) {
        if (current == null) {
            return new Node(value);
        }

        if (helper.isNumeric(value)) {
            if((!helper.isNumeric(current.value) || current.right == null)&&!helper.isNumeric(current.right.value)){
                current.right = addRecursive(current.right, value);
            }
            else {
                current.left = addRecursive(current.left, value);
            }
        }
        else{
            current.right = addRecursive(current.right, value);
        }

        return current;
    }
}
