package com.company;

import java.util.ArrayList;

public class Node {

    String content;
    ArrayList<Node> children = new ArrayList<Node>();;
    Double value;
    Helper help = new Helper();


    public Node (String content){
        this.content = content;
    }

    public void setValue (double value){
        this.value = value;
    }

    public ArrayList<String> getAddressRec(){
        ArrayList<String> addressList = new ArrayList<String>();
        if(help.isAddress( content)){
            addressList.add(content);
        }
        for(Node c : children){
            addressList.addAll(c.getAddressRec());
        }
        return addressList;
    }



}
