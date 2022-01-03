package com.company;

public class CellNum extends Cell{

    double value;


    public CellNum(double value){

        this.value = value;
        this.content = Double.toString(value);

    }

    public String getPrintValue(){
    return Double.toString(this.value);
    }




}
