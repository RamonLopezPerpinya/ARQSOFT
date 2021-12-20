package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Hashtable;

public interface Spreadsheet {

    public Cell GetCell(String cell);

    public void DeleteCell(String cell);

    public void SetCell(String address, String newContent);

    public String exportSpreadsheet();

    public boolean importSpreadSheet(BufferedReader reader) throws IOException;


}
