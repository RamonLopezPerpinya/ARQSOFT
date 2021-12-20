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
        while(run) {
            option = gui.PrintMenu(sn);

            switch (option) {
                case 0:
                    System.out.println("You selected the option: SeeMatrix");
                    gui.PrintMatrix(spreadsheet, sn);
                    break;
                case 1:
                    System.out.println("You selected the option: GetCell");
                    gui.PrintGetCellInterface(spreadsheet, sn);
                    break;
                case 2:
                    System.out.println("You selected the option: DeleteCell");
                    gui.PrintDeleteCellInterface(spreadsheet, sn);
                    break;
                case 3:
                    System.out.println("You selected the option: EditCell");
                    gui.PrintEditCellInterface(spreadsheet, sn);
                    break;
                case 4:
                    System.out.println("You selected the option: ExportSpreadsheet");
                    filename = gui.PrintExportSpreadSheetInterface(sn);
                    this.exportSpreadsheet(filename);
                    break;
                case 5:
                    System.out.println("You selected the option: ImportSpreadsheet");
                    filename = gui.PrintImportSpreadSheetInterface(sn);
                    this.importSpreadsheet(filename);
                    break;
                case 6:
                    run = false;
                    break;
                default:
                    System.out.println("Only number between 1 y 4");
            }
        }
    }
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
