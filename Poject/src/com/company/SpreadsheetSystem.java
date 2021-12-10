package com.company;

public class SpreadsheetSystem {
    Matrix matrix;
    GUI g;

    public SpreadsheetSystem(){

        matrix = new Matrix();
        g = new GUI();

    }

    public void run(){
        g.PrintMenu(matrix);
    }


}
