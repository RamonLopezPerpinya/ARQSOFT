package com.company;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.POSITIVE_INFINITY;

public class Calculator {

    Spreadsheet spreadsheet = new HashSpreadsheet();
    Helper help = new Helper();


    public void run(Spreadsheet sp, String address){
        this.spreadsheet = sp;
        String content = spreadsheet.GetCell(address).content;
        spreadsheet.SetCellValue(address,  this.Result(content));
    }
    //=SUM(...)+4+45*56-MIN(-...)/777
    //=A3:E3
    public double Result(String formula){
        /*String suma = "";
        if(formula.indexOf("SUM")>=0){

                int index = formula.indexOf("SUM");
                int j = index + 3;
                int openP = 0;
                int closeP = 0;
                while(j<formula.length()){
                    if(formula.charAt(j) == '(')
                        openP++;
                    if(formula.charAt(j) == ')') {
                        closeP++;
                        if (closeP == openP) {
                            suma = formula.substring(index + 4, j );
                            break;
                        }
                    }
                      j++;
                }
        }
        return this.sumArrayList(this.Selection(suma));*/
        Tree newTree = new Tree();
        String separator = this.checkNextSeparator(formula);
        String[] children = this.checkFormulaAndSplit(formula,separator);
        newTree.root = new Node(separator);
        for(int i = 0; i<children.length;i++){
            newTree.root.children.add(new Node(children[i]));
        }
        return 1.1;
    }

    public String checkNextSeparator(String formula){
        int i =0;
        String[] separators = {"\\*","/","\\+","-"};
        String[] divided= null;
        while(divided == null && i<4){
            divided =  this.checkFormulaAndSplit(formula,separators[i]);
            i++;
        }
        if(divided == null){
            return "";
        }
        return separators[i-1];
    }



    public String[] checkFormulaAndSplit(String formula, String separator){
        String aux = separator.replace("\\", "");
        if(formula.contains(aux)) {
            return formula.split(separator);
        }
        else
            return null;
        }

    /*public String[] checkProducts(String[] arrayProducts){
        for(int i=0; i<=arrayProducts.length;i++){
        String [] arrayRatio = arrayProducts[i].split("/");}
        return arrayRatio;
    }*/

    //A1:B2
    public ArrayList<String> Range(String range){
        String [] address = range.split(":");
        ArrayList<String> finalRange = new ArrayList<String>();
        int[] init = help.fromAddressToIndex(address[0]);
        int[] last = help.fromAddressToIndex(address[1]);
        for(int i = init[0]; i<=last[0]; i++){
            for(int j = init[1]; j<=last[1];j++){
                finalRange.add(help.fromIntToString(i) + Integer.toString(j));
            }
        }
        return finalRange;
    }
    //A1;C5;.....
    //C5 = SUM(A1:C34;3+3;A1+3)
    public ArrayList<Double> Selection(String selection){
        ArrayList<String> finalSelection = new ArrayList<String>();
        String [] elements =  selection.split(";");
        for(int i = 0; i<elements.length;i++){
            if(elements[i].contains(":"))
                finalSelection.addAll(this.Range(elements[i]));
            else
                finalSelection.add(elements[i]);
        }
        return this.fromAddressToDouble(finalSelection);
    }

    public ArrayList<Double> fromAddressToDouble(ArrayList<String> selection){
        ArrayList<Double> arrayNums = new ArrayList<Double>();
        for(String element : selection){

            if(help.isNumeric(element)){
                arrayNums.add(Double.parseDouble(element));
            }
            else{
                Cell cell = this.spreadsheet.GetCell(element);
                if(cell == null || cell instanceof CellString)
                    continue;

                if(cell instanceof CellNum)
                    arrayNums.add(((CellNum) cell).value);

                if(cell instanceof  CellFormula)
                    arrayNums.add(((CellFormula) cell).value);
            }
        }
        return arrayNums;
    }

    public double sumArrayList(ArrayList<Double> arrayNums){
        double result = 0;
        for(Double num : arrayNums)
            result = result + num;

        return result;
    }

    public double maxArrayList(ArrayList<Double> arrayNums){
        double result = NEGATIVE_INFINITY;
        for(Double max : arrayNums){
            if(max>result)
                result = max;
        }
        return result;
    }

    public double minArrayList(ArrayList<Double> arrayNums){
        double result = POSITIVE_INFINITY;
        for(Double min : arrayNums){
            if(min<result)
                result = min;
        }
        return result;
    }

    public double promedArrayList(ArrayList<Double> arrayNums){
        return this.sumArrayList(arrayNums)/(arrayNums.size());
    }








}
