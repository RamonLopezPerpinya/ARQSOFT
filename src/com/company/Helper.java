package com.company;

public class Helper {

    public Helper(){}

    public boolean isNumeric(String string) {
        try {
            Integer.parseInt(string);
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
}
