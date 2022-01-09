package com.company;

import java.util.ArrayList;

public class CellFormula extends Cell{

    Double value;
    Tree tree;
    Calculator calculator = new Calculator();
    Helper help = new Helper();
    ArrayList<String> addresses;

    public CellFormula(String content){
        this.content = content;
        content = content.substring(1);
        content = content.replace(" ", "");
        String errorMessage = fourmulaIsIllegal(content);

        tree = new Tree(content, errorMessage);
        addresses = tree.getAddressess();

    }

    public String fourmulaIsIllegal(String formula) {

        int j = 0, openP = 0, closeP = 0;
        if(formula.contains("#")){
            return "Error, this '#' symbol is not allowed";
        }
        while (j < formula.length()) {
            if (formula.charAt(j) == '(') {
                if (formula.charAt(j + 1) == ')') {
                    return "Error, there is no content in some parethesis";
                }
                openP++;
            }
            else if (formula.charAt(j) == ')'){
                closeP++;
            }
            j++;

        }

        if (openP != closeP)
            return "Error the number of parethesis is wrong";
        else
            return "";
    }

    public void setTreeValue(Tree t){
        this.tree = t;
        this.value = t.root.value;
    }

    public String getPrintValue(){
        if(this.tree.root.content.contains("Error")){
            return this.tree.root.content;
        }
        else{
        return Double.toString(this.value);}
    }



}
