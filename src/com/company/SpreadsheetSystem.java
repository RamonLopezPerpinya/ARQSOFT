package com.company;

import java.io.*;
import java.util.Hashtable;
import java.util.Scanner;

public class SpreadsheetSystem {

    Spreadsheet spreadsheet;
    GUI gui;
    Helper help;
    Scanner sn;

    public SpreadsheetSystem(){

        spreadsheet = new HashSpreadsheet();
        gui = new GUI();
        help = new Helper();
        sn = new Scanner(System.in);

    }

    public void run() throws IOException {
        /*
        spreadsheet.SetCell("E1", "hola");
        spreadsheet.SetCell("E2", "adeu");
        spreadsheet.SetCell("A23", "socors");
        spreadsheet.SetCell("ZZ3", "maldicion");
        */
        int option;
        boolean run = true;
        String filename = "";
        String addres = "";
        while(run) {
            option = gui.PrintMenu(sn);

            switch (option) {
                case 0:
                    System.out.println("You selected the option: SeeMatrix");
                    gui.PrintMatrix(spreadsheet, sn);
                    break;
                case 1: // get cell
                    addres = gui.getCellAddresFromUser(sn, "You selected the option: PrintCell");
                    this.printCell(addres);
                    break;
                case 2: // delete cell
                    addres = gui.getCellAddresFromUser(sn, "You selected the option: DeleteCell");
                    this.deleteCell(addres);
                    break;
                case 3: // edit cell
                    addres = gui.getCellAddresFromUser(sn,"You selected the option: EditCell");
                    this.editCell(addres);
                    break;
                case 4: // export spreadsheet
                    filename = gui.exportInterface(sn);
                    this.exportSpreadsheet(filename);
                    break;
                case 5: // import spreadsheet
                    filename = gui.importInterface(sn);
                    this.importSpreadsheet(filename);
                    break;
                case 6:
                    run = false;
                    break;
                default:
                    System.out.println("Only number from 0 to 6 !!  !!! ");
            }
        }
    }


    // print cell
    public void printCell(String addres){
        Cell returnedCell = this.spreadsheet.GetCell(addres);
        if(returnedCell != null) {
            System.out.println("Addres: " + addres);
            System.out.println("Content: " + returnedCell.content);
        }
        else
            System.out.println("This cell has no value");
    }

    // delete cell
    public void deleteCell(String addres){
        this.spreadsheet.DeleteCell(addres);
    }

    // edit cell
    public void editCell(String addres){
        String content = gui.getUserInput("Introduce the content:", sn);
        spreadsheet.SetCell(addres, content);
    }

    // export spreadsheet
    public void exportSpreadsheet(String filename){

        String userDirectory = System.getProperty("user.dir");
        File file_ = new File(userDirectory+"/output");
        file_.mkdir();

        String fileContent = spreadsheet.exportSpreadsheet();
        BufferedWriter file = null;
        try{
            file = new BufferedWriter(new FileWriter(filename));
            file.write(fileContent);
        }
        catch ( IOException e){
            e.printStackTrace();
        }
        finally{
            try {
                if ( file != null)
                    file.close( );
            }
            catch ( IOException e) {
                e.printStackTrace();
            }
        }
    }

    // import spreadsheet
    public void importSpreadsheet(String nameFile){
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(nameFile));
        }
        catch ( FileNotFoundException f){
            f.printStackTrace();
        }
        try {
            spreadsheet.importSpreadSheet(reader);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {}
        }
    }
}