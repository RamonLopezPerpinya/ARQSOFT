package com.company;

import netscape.javascript.JSObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;



public class Matrix {

    Hashtable<String,Cell> hashMatrix = new Hashtable<String,Cell>();


    /*public Matrix(int n){
        numRows = n;
        numColumns = n;
        matrix = new ArrayList<>();
        for(int i=0;i<this.numRows;i++){
            matrix.add(new ArrayList<Cell>());
            for(int j = 0; j < this.numColumns; j++){
                matrix.get(i).add(new Cell());
            }
        }
    }*/

    public Matrix(){}

    public Cell GetCell(String cell){
        return hashMatrix.get(cell);
    }

    public void DeleteCell(String cell){
        hashMatrix.remove(cell);
    }

    public void EditCell(String address, String newContent){


        if(this.isNumeric(newContent)){

            double value = Double.parseDouble(newContent);
            CellNum cellNum = new CellNum(value);
            hashMatrix.put(address,cellNum);

        }

        else{
            if(newContent.startsWith("=")){
                CellFormula cellFormula = new CellFormula(newContent);
                cellFormula.content = newContent;
                hashMatrix.put(address,cellFormula);

            }
            else {
                CellString cellString = new CellString(newContent);
                cellString.content = newContent;
                hashMatrix.put(address,cellString);
            }

        }

    }





    /*public void  printMatrix(){

        // Loop through all rows
        for (int i = 0; i < this.numRows; i++) {

            // Loop through all elements of current row
            for (int j = 0; j < numColumns; j++) {
                System.out.print(matrix.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }*/

    /*public void GetContentCell(Cell c){
        int i = 0;
        int j = 0;
        boolean bool = false;
        while(i<this.numRows&&bool == false){
            while(j<this.numColumns&&bool == false){
                if(c == matrix.get(i).get(j)){
                    bool = true;
                }
                else{
                    i++;
                    j++;
                }
            }
        }
        System.out.println(matrix.get(i).get(j));

    }*/

    public boolean isNumeric(String string) {
        int intValue;

        //System.out.println(String.format("Parsing string: \"%s\"", string));

        /*if(string == null || string.equals("")) {
            //System.out.println("String cannot be parsed, it is null or empty.");
            return false;
        }*/

        try {
            intValue = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            //System.out.println("Input String cannot be parsed to Integer.");
        }
        return false;
    }

    /*public void SetContentInACell(int row, int column){
        Scanner scanner = new Scanner(System.in);
        String newContent = scanner.nextLine();
        //String cellType = newContent.getClass().getSimpleName();

        if(this.isNumeric(newContent)== false){
        CellString cellString = new CellString();
        cellString.content = newContent;
        matrix.get(row).set(column,cellString);
        }

        else{
        CellNum cellNum = new CellNum();
        cellNum.content = Double.parseDouble(newContent);
            matrix.get(row).set(column,cellNum);
        }

        System.out.println(matrix);

        }*/

    /*public void GetValueOfACell(Cell c){
        int i = 0;
        int j = 0;
        boolean bool = false;
        while(i<this.numRows&&bool == false){
            while(j<this.numColumns&&bool == false){
                if(c == matrix.get(i).get(j)){
                    bool = true;
                    double content = cellNum.getContent();
                    System.out.println(content);
                }
                else{
                    i++;
                    j++;
                }
            }
        }
        System.out.println(matrix.get(i).get(j));
    }*/

    /*public String GetContentOfACell(Cell c){
        int i = 0;
        int j = 0;
        boolean bool = false;
        while(i<this.numRows&&bool == false){
            while(j<this.numColumns&&bool == false){
                if(c == matrix.get(i).get(j)){
                    bool = true;
                    String content = this.cellString.content;
                    System.out.println(content);
                    return content;
                }
                else{
                    i++;
                    j++;

                }
            }
        }

    }*/


    /*public void SaveMatrix() throws IOException {

        ObjectOutputStream saveMatrix = null;
        saveMatrix = new ObjectOutputStream(new FileOutputStream("Matrix.txt"));
        for (int i = 0; i < this.numRows; i++) {
            for (int j = 0; j < numColumns; j++) {

                saveMatrix.writeObject(matrix.get(i).get(j));
            }
        }
        saveMatrix.close();

    }*/




}
