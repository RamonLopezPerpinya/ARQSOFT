package com.company;

public class CellString extends Cell{

    String value;

    public CellString(String value){

        this.value = value;
        this.content =value;
    }

    public String getPrintValue(){
        return this.value;
    }


}
