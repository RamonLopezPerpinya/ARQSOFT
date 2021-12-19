package com.company;

public class CellFactory {

    Helper help;

    public CellFactory(){

        help = new Helper();
    }

    public Cell constructCell(String newContent){

        if(help.isNumeric(newContent)){

            double value = Double.parseDouble(newContent);
            return new CellNum(value);
        }

        else{
            if(newContent.startsWith("=")){
                return new CellFormula(newContent);


            }
            else {
                return new CellString(newContent);


            }

        }

    }

}
