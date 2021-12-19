package com.company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

public class SpreadsheetSystem {

    Spreadsheet spreadsheet;
    GUI g;
    Helper help;

    public SpreadsheetSystem(){

        spreadsheet = new HashSpreadsheet();
        g = new GUI();
        help = new Helper();

    }

    public void run() throws IOException {
        spreadsheet.SetCell("E1", "hola");
        spreadsheet.SetCell("E2", "adeu");
        spreadsheet.SetCell("E3", "hola");
        spreadsheet.SetCell("ZZ3", "maldicion");
        g.PrintMenu(spreadsheet);

    }





}
