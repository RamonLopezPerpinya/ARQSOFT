package com.company;

import java.util.ArrayList;

public class Node {

    String value;
    ArrayList<Node> children;


    public Node (String value){

        this.value = value;
        this.children = new ArrayList<Node>();

    }



}
