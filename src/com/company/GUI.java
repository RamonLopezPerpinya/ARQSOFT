package com.company;
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GUI {

    Helper help = new Helper();

    public int PrintMenu(Scanner sn) throws IOException {

        System.out.println("0. PrintMatrix");
        System.out.println("1. PrintCell");
        System.out.println("2. DeleteCell");
        System.out.println("3. EditCell");
        System.out.println("4. ExportSpreadsheet");
        System.out.println("5. ImportSpreadsheet");
        System.out.println("6. Exit");

        return this.GetNumberFromUser("Option must be a numerical","What you wanna do?",sn);
    }

    public String getUserInput(String message, Scanner sn){
        System.out.println(message);
        String input = sn.nextLine();
        return input;
    }

    public int GetNumberFromUser(String message, String question, Scanner sn){
        boolean notNumber = true;
        int num = 0;
        while(notNumber) {
            System.out.println(question);
            try {
                num = sn.nextInt();
                notNumber = false;
            } catch (InputMismatchException e) {
                System.out.println(message);
            }
            sn.nextLine();
        }
        return num;
    }

    public String getColumnFromUser(String message, String question, Scanner sn){
        boolean notString = true;
        String column = "";
        while (notString) {
            System.out.println(question);
            column = sn.nextLine();
            for (int i = 0; i < column.length(); i++) {
                if (help.isNumeric(Character.toString(column.charAt(i)))) {
                    System.out.println(message);
                    notString = true;
                }
                else{
                    notString = false;
                }
            }
        }
        return column;
    }

    public String getCellAddresFromUser(Scanner sn, String intro){
        System.out.println(intro);
        String column = this.getColumnFromUser("The column must be only letters","Which column? (String)",sn);
        int row = this.GetNumberFromUser("The rows must be numerical","Which row? (num)", sn);
        return column.toUpperCase() + Integer.toString(row);
    }

    public String importInterface(Scanner sn) throws IOException {
        System.out.println("You selected the option: ExportSpreadsheet");
        String userDirectory = System.getProperty("user.dir");
        File file = new File(userDirectory+"/output");
        String[] fileNames = file.list();

        for(int i = 0 ; i < fileNames.length ; i++ ){
            System.out.println(Integer.toString(i) + ". " + fileNames[i]);
        }

        System.out.println();
        int input = this.GetNumberFromUser("Which spreadsheet do you want to load from the avaliable above? (enter number)",
                "Please, only number", sn );
        return "output/" + fileNames[input];
    }

    public String exportInterface(Scanner sn) throws IOException {
        System.out.println("You selected the option: ImportSpreadsheet");
        System.out.println("Which name do you want to save the spreadsheet");
        String name = sn.nextLine();
        return "output/" + name;
    }

    public String formatCellContent(String content, int length){
        if(content.length() > length)
            content = content.substring(0,length);

        else{
            while(content.length() < length){
                content = content + " ";
            }
        }
        content = content + "|";
        return content;
    }

    public String doSeparator(int cell_size){
        String separator = "+";

        for(int i = 0; i < 11; i++){
            for(int j = 0; j < cell_size; j++){
                separator = separator + "-";
            }
            separator = separator + "+";
        }
        return separator+"\n";
    }

    public void PrintMatrixHeader(int start_col, int cell_size){
        String string = "";
        String header = String.format("%1$"+(1+cell_size)+ "s", string) + "|";

        for(int i = 0; i < 10; i++){
            String temp_header = String.format("%1$"+cell_size/2+ "s", string);
            temp_header += help.fromIntToString(start_col + i);
            int curr_size = temp_header.length();
            temp_header += String.format("%1$"+(cell_size - curr_size) + "s", string);
            header = header + temp_header + "|";
        }
        System.out.println(header);
    }

    public void PrintMatrix(Spreadsheet spreadsheet, Scanner sn){

        String column = this.getColumnFromUser("The column must be only letters",
                "Will show 10 columns and 10 rows\n at which column you want to start? (char)", sn);
        int row = this.GetNumberFromUser("The rows must be numerical","At which row you want to start? (num)", sn);
        int cell_size = this.GetNumberFromUser("The characters must be numerical","How many characters per cell?", sn);
        column = column.toUpperCase();

        int columnIdx = help.fromStringToInt(column);

        String separator = this.doSeparator(cell_size);

        this.PrintMatrixHeader(columnIdx, cell_size);

        for(int i = 0; i<10; i++){

            System.out.print(separator + "|" + this.formatCellContent(Integer.toString(i + row), cell_size));

            for(int j = 0; j<10; j++){
                int cur_column = columnIdx + j;
                String address = help.fromIntToString(cur_column)+ Integer.toString(i + row);
                Cell currentCell = spreadsheet.GetCell(address);
                if(currentCell == null){
                    System.out.print(this.formatCellContent("", cell_size));
                }
                else{
                    System.out.print(this.formatCellContent(currentCell.content, cell_size));
                }
            }
            System.out.print("\n");
        }
        System.out.print(separator);
    }
}