package com.company;

import java.util.ArrayList;

public class Node {

    String content;
    ArrayList<Node> children;
    Double value;


    public Node (String content){

        this.content = content;
        this.children = new ArrayList<Node>();

    }

    public void setValue (double value){
        this.value = value;
    }




}
