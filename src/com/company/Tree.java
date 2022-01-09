package com.company;

import java.util.ArrayList;
import java.util.Objects;

public class Tree {

    String formula;
    Node root;
    NodeFactory NF = new NodeFactory();

    public Tree(String formula, String errorMessage){
        this.formula = formula;


        if(!Objects.equals(errorMessage, "")){
            root = new Node(errorMessage);
        }

        else{
            Node node = NF.buildNode(formula,null);
            if(node == null)
                root = new Node("Error, invalid content");
            else
                root = node;
        }

        ArrayList<String> addressList = new ArrayList<String>();
        addressList.addAll(getAddressess());

    }

    public ArrayList<String> getAddressess(){
        return root.getAddressRec();
    }



}
