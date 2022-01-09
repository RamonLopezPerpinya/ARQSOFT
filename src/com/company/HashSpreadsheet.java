package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import java.util.Hashtable;

public class HashSpreadsheet implements Spreadsheet{

    Hashtable<String,Cell> hashMatrix = new Hashtable<String,Cell>();

    Set<String> setOfFormulas = new TreeSet<String>();

    CellFactory cellFactory = new CellFactory();

    Helper help = new Helper();




    public HashSpreadsheet(){}

    public Cell GetCell(String cell){
        return hashMatrix.get(cell);
    }

    public Set<String> GetFormulas() { return this.setOfFormulas; }


    public void DeleteCell(String cell){
        setOfFormulas.remove(cell);
        hashMatrix.remove(cell);
    }

    public void ClearSpreadsheet(){
        hashMatrix = new Hashtable<String,Cell>();
        setOfFormulas = new TreeSet<String>();
    }
    public void SetCell(String address, String newContent){
        Cell cell = cellFactory.constructCell(newContent);
        if( cell instanceof CellFormula){
            setOfFormulas.add(address);
        }
        else {
            setOfFormulas.remove(address);
        }
        hashMatrix.put(address, cell);
    }

    public void SetResult(String address, Tree result){
        CellFormula c = (CellFormula) hashMatrix.get(address);
        c.setTreeValue(result);
    }


    public Hashtable GetMatrix(){return hashMatrix;}

    public String exportSpreadsheet() {
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
                    break;
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
        String fileContent= "";
        for(int i = 0; i<maxRow;i++){
            String rowContent = "";
            String rowAddress = Integer.toString(i+1);
            for(int j = 0; j< maxC;j++){
                String address = help.fromIntToString(j+1) + rowAddress;
                if(this.GetCell(address)!= null){
                   rowContent = rowContent + this.GetCell(address).content.replace(";", ",") + ";";
                }
                else{
                    rowContent += ";";
                }
            }
            fileContent = fileContent + rowContent.substring(0,rowContent.length()-1) + "\n";
        }

        return fileContent;

    }

    public boolean importSpreadSheet(BufferedReader reader) throws IOException {

            ClearSpreadsheet();

            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();

            int numRow = 1;
            String column;
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                String [] content = line.split(";");


                for(int i = 0; i<content.length;i++){
                    if(content[i] != "") {
                        column = help.fromIntToString(i + 1);
                        String address = column + numRow;
                        this.SetCell(address, content[i].replace(",", ";"));
                    }
                }
                numRow++;
                line = reader.readLine();
            }



       return true;

    }

}
