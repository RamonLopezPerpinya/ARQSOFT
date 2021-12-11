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

    public void PrintMatrix(Matrix matrix, Scanner sn){
        System.out.println("Will show 10 columns and 10 rows\n at which column you want to start? (char)");
        String column = sn.nextLine();
        System.out.println("At which row you want to start? (num)");
        String row = sn.nextLine();

        for(int i = 0; i<10; i++){
            System.out.println("---------------------------------------------------------------------");
            for(int j = 0; j<10; j++){
                System.out.print("Hola"+ Integer.toString(j));
            }
        }

    }
}
