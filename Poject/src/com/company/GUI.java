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

            System.out.println("1. GetCell");
            System.out.println("2. DeleteCell");
            System.out.println("3. EditCell");
            System.out.println("4. Exit");

            System.out.println("What you wanna do?");
            option = sn.nextInt();
            sn.nextLine();

            switch(option){
                case 1:
                    System.out.println("You selected the option 1");
                    System.out.println("Which cell?");
                    cellAddress = sn.nextLine();
                    Cell returnedCell = matrix.GetCell(cellAddress);
                    System.out.println(returnedCell.content);
                    break;
                case 2:
                    System.out.println("You selected the option 2");
                    System.out.println("Which cell?");
                    cellAddress = sn.nextLine();
                    matrix.DeleteCell(cellAddress);
                    break;
                case 3:
                    System.out.println("You selected the option 3");
                    System.out.println("Which cell?");
                    cellAddress = sn.nextLine();
                    System.out.println("Which content?");
                    String newContent = sn.nextLine();
                    matrix.EditCell(cellAddress,newContent);
                    break;
                case 4:
                    run=false;
                    break;
                default:
                    System.out.println("Only number between 1 y 4");
            }





        }


    }
}
