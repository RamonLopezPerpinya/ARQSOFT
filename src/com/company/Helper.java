package com.company;

import java.util.ArrayList;

public class Helper {


    public Helper(){}

    public int[] fromAddressToIndex(String address){
        int [] index = new int[2];
        for(int i =0; i<address.length();i++) {
            if (this.isNumeric(Character.toString(address.charAt(i)))){
                index[0] = this.fromStringToInt(address.substring(0,i));
                index[1] = Integer.parseInt(address.substring(i));
                break;
            }
        }
        return index;


    }
    public boolean isNumeric(String string) {
        try {
            Double.parseDouble(string);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
    //project structure, add dependecnies,
    public int fromStringToInt(String string){
        // A= 65(1) I Z=90(26) (AA =27, ZZ=
        int sum = 0;
        int ascii;
        for(int i = string.length() ; i > 0 ; i--){
            ascii = (int) string.charAt(i - 1);
            ascii -= 64;
            sum += ascii * Math.pow(26, string.length()-i );
        }
        return sum;
    }

    public String fromIntToString(int number) {

        String columnName = "";

        while (number > 0) {
            // Find remainder
            int rem = number % 26;

            // If remainder is 0, then a
            // 'Z' must be there in output
            if (rem == 0) {
                columnName = "Z" + columnName;
                number = (number / 26) - 1;
            } else // If remainder is non-zero
            {
                columnName = (char) ((rem - 1) + 'A') + columnName;
                number = number / 26;
            }

        }
        return columnName;
    }

    public String checkNextSeparator(String formula){
        int i =0;
        String[] separators = {"\\+","-","\\*","/"};
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

    public String checkSelectionOperation(String formula){
        if(formula.contains("SUM"))
            return "SUM";
        else if (formula.contains("MIN"))
            return "MIN";
        else if (formula.contains("MAX"))
            return "MAX";
        else if (formula.contains("MEAN"))
            return "MEAN";
        else
            return "";

    }

    public boolean isAddress(String address){
        int cont = 0;
        if(Character.isAlphabetic(address.charAt(0))== false){
            return false;
        }
        for(int i =0; i<address.length();i++){
            if(Character.isAlphabetic(address.charAt(i))){
                cont++;
            }
            else{
                break;
            }
        }
        String digits = address.substring(cont);

        return this.isNumeric(digits);

    }

    public ArrayList<String> ParseSelectionContent(String content){
        int parenthesis = 0;
        ArrayList<Integer> idxList = new ArrayList<Integer>();
        ArrayList<String> SelectionList = new ArrayList<String>();

        for(int i = 0; i < content.length(); i++){
            char c = content.charAt(i);
            if(c == '(')
                parenthesis++;
            else if(c == ')')
                parenthesis--;

            if(parenthesis != 0)
                continue;
            else if(c == ';')
                idxList.add(i);
        }

        int idxLeft = 0;
        for(int idxRight : idxList){
            SelectionList.add(content.substring(idxLeft,idxRight));
            idxLeft = idxRight + 1;
        }
        SelectionList.add(content.substring(idxLeft));
        return SelectionList;
    }
}

