package com.company;

import java.util.Hashtable;

public interface Spreadsheet {

    public Cell GetCell(String cell);

    public void DeleteCell(String cell);

    public void SetCell(String address, String newContent);

    public boolean exportSpreadsheet(String txt);

    public boolean importSpreadSheet(String name);


}
