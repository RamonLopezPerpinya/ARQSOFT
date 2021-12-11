package com.company;
import java.util.Scanner;

public class GUI {

    public GUI(){}

    public void PrintMenu(Matrix matrix){


        String cellAddress;
        Scanner sn = new Scanner(System.in);
        boolean run = true;
        int option;
        while(run){

            System.out.println("0. SeeMatrix");
            System.out.println("1. GetCell");
            System.out.println("2. DeleteCell");
            System.out.println("3. EditCell");
            System.out.println("4. Exit");

            System.out.println("What you wanna do?");
            option = sn.nextInt();
            sn.nextLine();

            switch(option){
                case 0:
                    System.out.println("You selected the option: GetCell");
                    this.PrintMatrix(matrix, sn);
                    break;
                case 1:
                    System.out.println("You selected the option: GetCell");
                    this.PrintGetCellInterface(matrix, sn);
                    break;
                case 2:
                    System.out.println("You selected the option: DeleteCell");
                    this.PrintDeleteCellInterface(matrix, sn);
                    break;
                case 3:
                    System.out.println("You selected the option: EditCell");
                    this.PrintEditCellInterface(matrix, sn);
                    break;
                case 4:
                    run=false;
                    break;
                default:
                    System.out.println("Only number between 1 y 4");
            }
        }
    }

    public void PrintGetCellInterface(Matrix matrix, Scanner sn){
        System.out.println("Which cell?");
        String cellAddress = sn.nextLine();
        Cell returnedCell = matrix.GetCell(cellAddress);
        if(returnedCell != null)
            System.out.println(returnedCell.content);
        else
            System.out.println("This cell has no value");
    }

    public void PrintDeleteCellInterface(Matrix matrix, Scanner sn){
        System.out.println("Which cell?");
        String cellAddress = sn.nextLine();
        matrix.DeleteCell(cellAddress);
    }

    public void PrintEditCellInterface(Matrix matrix, Scanner sn){
        System.out.println("Which cell?");
        String cellAddress = sn.nextLine();
        System.out.println("Which content?");
        String newContent = sn.nextLine();
        matrix.EditCell(cellAddress,newContent);
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
            String temp_header = String.format("%1$"+cell_size+ "s", string);
            char[] chars = temp_header.toCharArray();
            chars[cell_size/2] = (char) (start_col + i);
            header = header + new String(chars) + "|";
        }
        System.out.println(header);
    }

    public void PrintMatrix(Matrix matrix, Scanner sn){
        System.out.println("Will show 10 columns and 10 rows\n at which column you want to start? (char)");
        String column = sn.nextLine();
        System.out.println("At which row you want to start? (num)");
        int row = sn.nextInt();
        System.out.println("How many characters per cell?");
        int cell_size = sn.nextInt();

        char character = column.charAt(0);
        int ascii = (int) character;

        String separator = this.doSeparator(cell_size);

        this.PrintMatrixHeader(ascii, cell_size);

        for(int i = 0; i<10; i++){

            System.out.print(separator + "|" + this.formatCellContent(Integer.toString(i + row), cell_size));

            for(int j = 0; j<10; j++){
                int cur_ascii = ascii + j;
                char cur_char = (char) cur_ascii;
                String address = Character.toString(cur_char) + Integer.toString(i + row);
                Cell currentCell = matrix.GetCell(address);
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
