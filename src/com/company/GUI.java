package com.company;
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GUI {

    //SpreadsheetSystem spreadsheetSystem = new SpreadsheetSystem();
    Helper help;
    public GUI(){
        this.help = new Helper();
    }

    public void PrintMenu(Spreadsheet spreadsheet) throws IOException {


        String cellAddress;
        Scanner sn = new Scanner(System.in);
        boolean run = true;
        int option;
        while(run){

            System.out.println("0. SeeMatrix");
            System.out.println("1. GetCell");
            System.out.println("2. DeleteCell");
            System.out.println("3. EditCell");
            System.out.println("4. ExportSpreadsheet");
            System.out.println("5. ImportSpreadsheet");
            System.out.println("6. Exit");


            option = this.GetNumberFromUser("Option must be a numerical","What you wanna do?",sn);


            switch(option){
                case 0:
                    System.out.println("You selected the option: SeeMatrix");
                    this.PrintMatrix(spreadsheet, sn);
                    break;
                case 1:
                    System.out.println("You selected the option: GetCell");
                    this.PrintGetCellInterface(spreadsheet, sn);
                    break;
                case 2:
                    System.out.println("You selected the option: DeleteCell");
                    this.PrintDeleteCellInterface(spreadsheet, sn);
                    break;
                case 3:
                    System.out.println("You selected the option: EditCell");
                    this.PrintEditCellInterface(spreadsheet, sn);
                    break;
                case 4:
                    System.out.println("You selected the option: ExportSpreadsheet");
                    this.PrintExportSpreadSheetInterface(spreadsheet, sn);
                    break;
                case 5:
                    System.out.println("You selected the option: ImportSpreadsheet");
                    this.PrintImportSpreadSheetInterface(spreadsheet, sn);
                    break;
                case 6:
                    run=false;
                    break;
                default:
                    System.out.println("Only number between 1 y 4");
            }
        }
    }

    public boolean isNumeric(String string) {
        try {
            Integer.parseInt(string);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
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

    public String GetOnlyStrings(String message, String question, Scanner sn){
        boolean notString = true;
        String column = "";
        while (notString) {
            System.out.println(question);
            column = sn.nextLine();
            for (int i = 0; i < column.length(); i++) {
                if (this.isNumeric(Character.toString(column.charAt(i)))) {
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

    public void PrintImportSpreadSheetInterface(Spreadsheet spreadsheet, Scanner sn) throws IOException {
        String userDirectory = System.getProperty("user.dir");
        File file = new File(userDirectory+"/output");
        String[] fileNames = file.list();

        for(int i = 0 ; i < fileNames.length ; i++ ){
            System.out.println(Integer.toString(i) + ". " + fileNames[i]);
        }

        System.out.println();
        int input = this.GetNumberFromUser("Which spreadsheet do you want to load from the avaliable above? (enter number)",
                                            "Please, only number", sn );
        spreadsheet.importSpreadSheet("output/" + fileNames[input]);

    }

    public void PrintExportSpreadSheetInterface(Spreadsheet spreadsheet, Scanner sn) throws IOException {
        String userDirectory = System.getProperty("user.dir");
        File file = new File(userDirectory+"/output");
        file.mkdir();

        System.out.println("Which name do you want to save the spreadsheet");
        String name = sn.nextLine();
        spreadsheet.exportSpreadsheet("output/" + name);

    }

    public void PrintGetCellInterface(Spreadsheet spreadsheet, Scanner sn){
        String column = this.GetOnlyStrings("The column must be only letters","Which column? (String)",sn);
        int row = this.GetNumberFromUser("The rows must be numerical","Which row? (num)", sn);
        String cellAddress = column.toUpperCase() + Integer.toString(row);
        Cell returnedCell = spreadsheet.GetCell(cellAddress);
        if(returnedCell != null)
            System.out.println(returnedCell.content);
        else
            System.out.println("This cell has no value");
    }

    public void PrintDeleteCellInterface(Spreadsheet spreadsheet, Scanner sn){
        String column = this.GetOnlyStrings("The column must be only letters","Which column? (String)",sn);
        int row = this.GetNumberFromUser("The rows must be numerical","Which row? (num)", sn);
        String cellAddress = column.toUpperCase() + Integer.toString(row);
        spreadsheet.DeleteCell(cellAddress);
    }

    public void PrintEditCellInterface(Spreadsheet spreadsheet, Scanner sn){

        String column = this.GetOnlyStrings("The column must be only letters","Which column? (String)",sn);
        int row = this.GetNumberFromUser("The rows must be numerical","Which row? (num)", sn);
        String cellAddress = column.toUpperCase() + Integer.toString(row);
        System.out.println("Which content?");
        String newContent = sn.nextLine();
        spreadsheet.SetCell(cellAddress, newContent);

    }

    public String formatCellContent(String content, int length){
        if(content.length() > length){
            content = content.substring(0,length);
        }
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

        String column = this.GetOnlyStrings("The column must be only letters",
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
