package com.company;
import java.io.*;
import java.util.Hashtable;

public class HashSpreadsheet implements Spreadsheet{

    Hashtable<String,Cell> hashMatrix = new Hashtable<String,Cell>();

    CellFactory cellFactory = new CellFactory();

    Helper help = new Helper();





    public HashSpreadsheet(){}

    public Cell GetCell(String cell){
        return hashMatrix.get(cell);
    }

    public void DeleteCell(String cell){
        hashMatrix.remove(cell);
    }

    public void SetCell(String address, String newContent){
        hashMatrix.put(address, cellFactory.constructCell(newContent));
    }

    public Hashtable GetMatrix(){return hashMatrix;}

    public boolean exportSpreadsheet(String txt) {
        String column = "";
        int row = 0;
        String maxColumn = "A";
        int maxRow = 0;
        Hashtable<String,Cell> matrix = this.hashMatrix;
        for(String key : matrix.keySet()){
            for(int i =0; i<key.length();i++) {
                if (help.isNumeric(Character.toString(key.charAt(i)))){
                    column = key.substring(0,i);
                    row = Integer.parseInt(key.substring(i));
                }

            }
            if(maxRow<row){
                maxRow = row;
            }
            if(maxColumn.compareTo(column) <0){

                maxColumn = column;
            }
        }
        int maxC = help.fromStringToInt(maxColumn);
        String contentFile= "";
        for(int i = 0; i<maxRow;i++){
            String rowContent = "";
            String rowAddress = Integer.toString(i+1);
            for(int j = 0; j< maxC;j++){
                String address = help.fromIntToString(j+1) + rowAddress;
                if(this.GetCell(address)!= null){

                   rowContent = rowContent + this.GetCell(address).content + ";";
                }
                else{

                    rowContent += ";";

                }
            }
            contentFile = contentFile + rowContent.substring(0,rowContent.length()-1) + "\n";
        }

        BufferedWriter file = null;
        try{
        file = new BufferedWriter(new FileWriter(txt));
        file.write(contentFile);

        }
        catch ( IOException e){}

        finally{
                try
                {
                    if ( file != null)
                    file.close( );
                }
                catch ( IOException e)
                {
                }
                }
        return true;



    }

    public boolean importSpreadSheet(String nameFile) {

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(nameFile));
        }
        catch ( FileNotFoundException f){}
        try {
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();

            int numRow = 1;
            String column;
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                String [] content = line.split(";");


                for(int i = 0; i<content.length;i++){
                    column = help.fromIntToString(i+1);
                    String address = column + numRow;
                    this.SetCell(address, content[i]);

                }
                numRow++;
                line = reader.readLine();
            }

        }
        catch( IOException e){}
        finally {
            try
            {
                if (reader != null)
                    reader.close( );
            }
            catch (IOException e)
            {
            }
        }

       return true;

    }

}