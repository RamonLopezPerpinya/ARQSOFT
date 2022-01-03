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
}

