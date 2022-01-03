package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Hashtable;
import java.util.Set;

public interface Spreadsheet {

    public Cell GetCell(String cell);

    public Set<String> GetFormulas();

    public void DeleteCell(String cell);

    public void SetCell(String address, String newContent);

    public void SetTreeCellFormula(String address, Tree t);

    public String exportSpreadsheet();

    public boolean importSpreadSheet(BufferedReader reader) throws IOException;


}
