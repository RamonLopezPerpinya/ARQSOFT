package com.company;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.POSITIVE_INFINITY;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class Calculator {

    Spreadsheet spreadsheet = new HashSpreadsheet();
    Helper help = new Helper();

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


    public void run(Spreadsheet sp, String address){
        spreadsheet = sp;
        String content = spreadsheet.GetCell(address).content;
        // clean formula: remove = at begin and remove white spaces
        content = content.substring(1);
        content = content.replace(" ", "");

        Tree t;
        String errorMessage = fourmulaIsIllegal(content);
        if(errorMessage != "" ){
            t = new Tree(content, new Node(errorMessage));
        }
        else {
            // get resultant tree from formula
            t = Result(content);
        }
        spreadsheet.SetTreeCellFormula(address, t);
    }

    //=SUM(...)+4+45*56-MIN(-...)/777
    //=A3:E3

    public Tree Result(String formula){

        Tree tree;
        if(buildNode(formula,null) == null){
            tree = new Tree(formula, new Node("Error, invalid content"));
        }
        else {
            tree = new Tree(formula, buildNode(formula, null));
        }

        this.computeTree(tree);
        return tree;
    }

    public Tree computeTree(Tree tree){
        tree.root = this.computeNode(tree.root);
        tree.value = tree.root.value;
        return tree;
    }

    public Node computeNode(Node node){
        if(help.isNumeric(node.content))
            return  node;
        else
            return returnOperation(node);
    }

    public Node returnOperation(Node node){
        Node number;
        ArrayList<Double> valuesToOperate = new ArrayList<Double>();
        for(Node child : node.children){
            number = this.computeNode(child);
            valuesToOperate.add(number.value);
        }
        switch (node.content){
            case "+":
                node.value = this.sumArrayList(valuesToOperate);
                break;
            case "-":
                node.value = this.substractArrayList(valuesToOperate);
                break;
            case "*":
                node.value = this.productArrayList(valuesToOperate);
                break;
            case "/":
                node.value = this.divisionArrayList(valuesToOperate);
                break;

        }
        return node;
    }

    public Node buildNode(String formula, ArrayList<Node> parenthesisNodes){

        Node node;


        String initialSelectionOperation = this.checkSelectionOperation(formula.substring(0,min(4,formula.length())));

        if(initialSelectionOperation != "" && Objects.equals(formula.substring(formula.length()-1), ")")){

            node = new Node(initialSelectionOperation);
            node.children.addAll(this.Selection(formula.substring(initialSelectionOperation.length()+1, formula.length() -1 )));
            return node;
        }


        // check parenthesis to create sub tree recursively and substitute its presence in formula by hashtag
        // if parenthesis is preceeded by
        // store the subtree in NodesParenthesis which is not reliable at all maybe we should do a hash tabl

        int idxOpen = 0, idxClose = 0, j = 0, openP = 0, closeP = 0;
        int nSeparated_Parethesis = 0;
        ArrayList<Node> NodesParenthesis = new ArrayList<Node>();

        if(parenthesisNodes!=null)
            NodesParenthesis.addAll(parenthesisNodes);

        while(j<formula.length()){
            if(formula.charAt(j) == '(') {
                if(openP == 0)
                    idxOpen = j;
                openP++;
            }
            else if(formula.charAt(j) == ')')
                closeP++;
            if(openP == closeP && openP != 0) {
                nSeparated_Parethesis++;
                openP = 0;
                closeP = 0;
                idxClose = j;

                // build node with interior parenthesis and add to list of nodes
                String selectionOperation = this.checkSelectionOperation(formula.substring(max(idxOpen-4,0),idxOpen));

                if(selectionOperation == "")
                    if (buildNode(formula.substring(idxOpen + 1, idxClose), null) == null)
                        return null;
                    else {
                        NodesParenthesis.add(buildNode(formula.substring(idxOpen + 1, idxClose), null));
                        formula = formula.substring(0, idxOpen) + "#" + formula.substring(idxClose + 1);
                    }

                else{
                    NodesParenthesis.add(buildNode(selectionOperation + formula.substring(idxOpen, idxClose + 1), null));
                    formula = formula.substring(0, idxOpen-selectionOperation.length()) + "#" + formula.substring(idxClose + 1);
                }



                // reset index to avoid skiping characters
                j = j - idxClose + idxOpen;

            }
            j++;
        }

        String separator = help.checkNextSeparator(formula);

        String[] children = help.checkFormulaAndSplit(formula,separator);
        if(children.length == 0){
            return null;
        }
        int ParenthesisStored = 0;

        if(separator == ""){
            if(help.isNumeric(children[0])){
                node = new Node(formula);
                node.setValue(Double.parseDouble(formula));
            }
            else if(children[0].contains("#")){
                node = NodesParenthesis.get(0);
            }
            else{
                return null;
            }
        }
        else{
            separator = separator.replace("\\", "");
            node = new Node(separator);
            for(int i = 0; i<children.length;i++){
                if(Objects.equals(children[i], "#")){
                    node.children.add(NodesParenthesis.get(ParenthesisStored));
                    ParenthesisStored++;
                }else{
                    if(children[i].contains("#")){
                        if(this.buildNode(children[i],NodesParenthesis) == null){
                            return null;
                        }
                        else {
                            node.children.add(this.buildNode(children[i], NodesParenthesis));
                            ParenthesisStored++;
                        }
                    }
                    else{
                        if(this.buildNode(children[i],null) == null){
                            return null;
                        }
                        else {
                            node.children.add(this.buildNode(children[i], null));
                        }
                    }
                }
            }
        }
        return node;
    }

    public String checkSelectionOperation(String formula){
        if(formula.contains("SUM"))
            return "SUM";
        else if (formula.contains("MIN"))
            return "MIN";
        else if (formula.contains("MAX"))
            return "MAX";
        else if (formula.contains("MEAN"))
            return "MEAN";
        else
            return "";

    }

    //A1:B2
    public ArrayList<String> Range(String range){
        String [] address = range.split(":");
        ArrayList<String> finalRange = new ArrayList<String>();
        int[] init = help.fromAddressToIndex(address[0]);
        int[] last = help.fromAddressToIndex(address[1]);
        for(int i = init[0]; i<=last[0]; i++){
            for(int j = init[1]; j<=last[1];j++){
                finalRange.add(help.fromIntToString(i) + Integer.toString(j));
            }
        }
        return finalRange;
    }
    //A1;C5;.....
    //C5 = SUM(A1:C34;3+3;A1+3)
    public ArrayList<String> ParseSelectionContent(String content){
        int parenthesis = 0;
        ArrayList<Integer> idxList = new ArrayList<Integer>();
        ArrayList<String> SelectionList = new ArrayList<String>();

        for(int i = 0; i < content.length(); i++){
            char c = content.charAt(i);
            if(c == '(')
                parenthesis++;
            else if(c == ')')
                parenthesis--;

            if(parenthesis != 0)
                continue;
            else if(c == ';')
                idxList.add(i);
        }

        int idxLeft = 0;
        for(int idxRight : idxList){
            SelectionList.add(content.substring(idxLeft,idxRight));
            idxLeft = idxRight + 1;
        }
        SelectionList.add(content.substring(idxLeft));
        return SelectionList;
    }

    public ArrayList<Node> Selection(String selection){
        ArrayList<Node> finalSelection = new ArrayList<Node>();
        ArrayList<String> elements =  ParseSelectionContent(selection);
        for(String element : elements){
            /*
            if(element.contains(":"))
                finalSelection.addAll(this.Range(element));
            else*/
            finalSelection.add(buildNode(element, null));
        }
        return finalSelection;
    }

    public ArrayList<Double> fromAddressToDouble(ArrayList<String> selection){
        ArrayList<Double> arrayNums = new ArrayList<Double>();
        for(String element : selection){

            if(help.isNumeric(element)){
                arrayNums.add(Double.parseDouble(element));
            }
            else{
                Cell cell = this.spreadsheet.GetCell(element);
                if(cell == null || cell instanceof CellString)
                    continue;

                if(cell instanceof CellNum)
                    arrayNums.add(((CellNum) cell).value);

                if(cell instanceof  CellFormula)
                    arrayNums.add(((CellFormula) cell).value);
            }
        }
        return arrayNums;
    }

    public double sumArrayList(ArrayList<Double> arrayNums){
        double result = 0;
        for(Double num : arrayNums)
            result = result + num;

        return result;
    }

    public double substractArrayList(ArrayList<Double> arrayNums){
        double result = arrayNums.get(0);
        for(int i = 1; i<arrayNums.size(); i++)
            result = result-arrayNums.get(i);

        return result;
    }
    public double productArrayList(ArrayList<Double> arrayNums){
        double result = arrayNums.get(0);
        for(int i = 1; i<arrayNums.size(); i++)
            result = result*arrayNums.get(i);

        return result;
    }
    public double divisionArrayList(ArrayList<Double> arrayNums){
        double result = arrayNums.get(0);
        for(int i = 1; i<arrayNums.size(); i++)
            result = result/arrayNums.get(i);

        return result;
    }

    public double maxArrayList(ArrayList<Double> arrayNums){
        double result = NEGATIVE_INFINITY;
        for(Double max : arrayNums){
            if(max>result)
                result = max;
        }
        return result;
    }

    public double minArrayList(ArrayList<Double> arrayNums){
        double result = POSITIVE_INFINITY;
        for(Double min : arrayNums){
            if(min<result)
                result = min;
        }
        return result;
    }

    public double promedArrayList(ArrayList<Double> arrayNums){
        return this.sumArrayList(arrayNums)/(arrayNums.size());
    }








}
