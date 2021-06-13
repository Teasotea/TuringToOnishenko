import com.shpp.cs.a.console.TextProgram;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class turToOnish extends TextProgram {
    public void run() {
        countLinesInFile();
        parseTxtFile();
        deleteComments();
        deleteSpaces();
        divideColumns();
        changeHaltToExclamationMark();
        changeStarToS();
        changeStarIn2nd3rdColumns();
        addQTo1stAnd5thColumns();
        deleteUnderlineSymbol();
        addColonTo2ndColumn();
        addCommaTo1st3rd5thColumns();
        swap3rdAnd5thColumns();
        swap4thAnd5thColumns();
        for (ArrayList<String> strings : turingMachine) {
            for (int j = 0; j < 5; j++)
                if (strings.get(j) != null)
                    print(strings.get(j));//+ " "
                else
                    print("");
            println();
        }
    }

    private void changeStarIn2nd3rdColumns() {
        ArrayList<String> symbolsInTur = createArrayOfSymbols();
        //Copy new rows, where instead of * * there are symbols of ArrayList<String> symbolsInTur
        for (int i = 0; i < turingMachine.size(); i++) {
            //Check if there are two stars "*" together in second and 3rd column
            if (checkIfTwoStarsIn2nd3rdColumns(i)) {
                //Find 2nd and 3rd symbols of turingMachine[i][0] == turingMachine[our row][0]
                //Locally delete them from ArrayList<String> symbolsInTur
                ArrayList<String> symbolsInCurrRow = findSymbolsOfSameNamedRows(i, symbolsInTur);
                addNewList(symbolsInCurrRow, i);
                fillNewListWithElements(symbolsInCurrRow,i);
            }
            else if(checkIfStarIn2ndColumn(i)){
                ArrayList<String> symbolsInCurrRow = findSymbolsOfSameNamedRows(i, symbolsInTur);
                addNewList(symbolsInCurrRow, i);
                fillNewListWithElements2ndColumn(symbolsInCurrRow,i);
            }
        }
    }

    /**
     * This method adds new rows with elements of symbolsInCurrRow array,
     * when we have star only in 2nd column
     * @param symbolsInCurrRow  amount of different symbols in rows,
     *                          excluding symbols that were used in rows with same first element
     * @param i numb of row
     */
    private void fillNewListWithElements2ndColumn(ArrayList<String> symbolsInCurrRow, int i) {
        turingMachine.get(i).set(1, "1");
        for (int j = 1; j < symbolsInCurrRow.size(); j++) {
            turingMachine.get(i+j).set(0, turingMachine.get(i).get(0));
            turingMachine.get(i+j).set(2, turingMachine.get(i).get(2));
            turingMachine.get(i+j).set(3, turingMachine.get(i).get(3));
            turingMachine.get(i+j).set(4, turingMachine.get(i).get(4));
        }
        //println(symbolsInCurrRow);
        for (int j = 0; j < symbolsInCurrRow.size(); j++) {
            turingMachine.get(i+j).set(1, symbolsInCurrRow.get(j));
        }
    }


    /**
     * This method adds new rows with elements of symbolsInCurrRow array,
     * when we have 2 stars together in 2nd and 3rd columns
     * @param symbolsInCurrRow  amount of different symbols in rows,
     *                          excluding symbols that were used in rows with same first element
     * @param i numb of row
     */
    private void fillNewListWithElements(ArrayList<String> symbolsInCurrRow, int i) {
        turingMachine.get(i).set(1, "1");
        turingMachine.get(i).set(2, "1");
        for (int j = 1; j < symbolsInCurrRow.size(); j++) {
            turingMachine.get(i+j).set(0, turingMachine.get(i).get(0));
            turingMachine.get(i+j).set(3, turingMachine.get(i).get(3));
            turingMachine.get(i+j).set(4, turingMachine.get(i).get(4));
        }
        //println(symbolsInCurrRow);
        for (int j = 0; j < symbolsInCurrRow.size(); j++) {
            turingMachine.get(i+j).set(1, symbolsInCurrRow.get(j));
            turingMachine.get(i+j).set(2, symbolsInCurrRow.get(j));
        }
    }

    /**
     * This method adds new elements to ArrayList turingMachine, in places where there are stars *
     * @param symbolsInCurrRow  amount of different symbols in rows,
     *                          excluding symbols that were used in rows with same first element
     * @param i numb of row
     */
    private void addNewList(ArrayList<String> symbolsInCurrRow, int i) {
        ArrayList<String> symbols= new ArrayList<>(symbolsInCurrRow);
        for (int k = 0; k < symbols.size()-1; k++) {
            turingMachine.add(i+1, new ArrayList<>());
            for (int d = 0; d < 5; d++) {
                turingMachine.get(i + 1).add(d, "1");
            }
        }
    }


    /**
     * This method creates array of each symbol that weren`t used in rows with the same name
     *
     * @param i            number of row
     * @param symbolsInTur array that contains all symbols used in program
     * @return array of all symbols, that were not used in rows with the same name
     */
    private ArrayList<String> findSymbolsOfSameNamedRows(int i, ArrayList<String> symbolsInTur) {
        //Find 2nd symbol of turingMachine[i][0] == turingMachine[our row][0]
        //Locally delete them from ArrayList<String> symbolsInTur
        ArrayList<String> symbolsOfCurrName = new ArrayList<>();
        ArrayList<String> symbolsInTurTemp = new ArrayList<>(symbolsInTur);

        for (int j = 0; j < turingMachine.size(); j++) {
            if (Objects.equals(turingMachine.get(j).get(0), turingMachine.get(i).get(0)) && j != i) {
                //We have i - numb of curr row and j - numb of raw with the same name
                //Output array with elements of j rows
                symbolsOfCurrName.add("*");
                if (isNotInArray(symbolsOfCurrName, turingMachine.get(j).get(1))) {
                    symbolsOfCurrName.add(turingMachine.get(j).get(1));
                    symbolsOfCurrName.remove("*");
                }
            }
        }

        for (String s : symbolsOfCurrName) {
            symbolsInTurTemp.remove(s);
        }
        symbolsInTurTemp.remove("*");
        //println(symbolsInTurTemp);
        return symbolsInTurTemp;
    }

    /**
     * This method checks if current row has stars in 2nd and 3rd columns
     *
     * @param i number of row
     * @return true if current row has stars in 2nd and 3rd columns
     */
    private boolean checkIfTwoStarsIn2nd3rdColumns(int i) {
        return Objects.equals(turingMachine.get(i).get(1), "*") &&
                Objects.equals(turingMachine.get(i).get(2), "*");
    }

    /**
     * This method checks if current row has stars in 2nd column
     *
     * @param i number of row
     * @return true if current row has stars in 2nd column
     */
    private boolean checkIfStarIn2ndColumn(int i) {
        return Objects.equals(turingMachine.get(i).get(1), "*");
    }

    /**
     * This method crate array of symbols inn turing machine
     *
     * @return array which contain each symbol of turing machine, but only once
     */
    private ArrayList<String> createArrayOfSymbols() {
        ArrayList<String> symbolsInTur = new ArrayList<>();
        symbolsInTur.add("0");
        for (ArrayList<String> strings : turingMachine) {
            if (strings.get(1) != null && !strings.get(1).equals(symbolsInTur.get(0))) {
                if (isNotInArray(symbolsInTur, strings.get(1))) {
                    //println("In " + symbolsInTur + " There aren`t any " + strings.get(1));
                    symbolsInTur.add(strings.get(1));
                }
            }
        }
        for (String s : symbolsInTur) {
            System.out.print(s + " ");
        }
        return symbolsInTur;
    }

    /**
     * This method checks if the are needed element in array
     *
     * @param s  array
     * @param s1 string
     * @return true if there are such element
     */
    private boolean isNotInArray(ArrayList<String> s, String s1) {
        for (String value : s) {
            if (value.equals(s1))
                return false;
        }
        return true;
    }

    /**
     * This method deletes "_" from 2nd and 3rd columns
     */
    private void deleteUnderlineSymbol() {

        for (ArrayList<String> strings : turingMachine) {
            if (Objects.equals(strings.get(1), "_"))
                strings.set(1, "");

            if (Objects.equals(strings.get(2), "_"))
                strings.set(2, "");
        }
    }

    /**
     * This method swaps column 4 and column 5
     */
    private void swap4thAnd5thColumns() {
        for (ArrayList<String> strings : turingMachine) {
            String temp = strings.get(3);
            strings.set(3, strings.get(4));
            strings.set(4, temp);
        }
    }

    /**
     * This method swaps column 3 and column 5
     */
    private void swap3rdAnd5thColumns() {
        for (ArrayList<String> strings : turingMachine) {
            String temp = strings.get(2);
            strings.set(2, strings.get(4));
            strings.set(4, temp);
        }
    }


    /**
     * This method adds "," to each element of 1st, 3rd and 5th columns
     */
    private void addCommaTo1st3rd5thColumns() {
        for (ArrayList<String> strings : turingMachine) {
            if (strings.get(0) != null)
                strings.set(0, strings.get(0) + ",");
            if (strings.get(2) != null)
                strings.set(2, strings.get(2) + ",");
            if (strings.get(4) != null)
                strings.set(4, strings.get(4) + ",");
        }
    }

    /**
     * This method adds ":" to each element of 2nd column
     */
    private void addColonTo2ndColumn() {
        for (ArrayList<String> strings : turingMachine) {
            //println(turingMachine[i][1]);
            if (strings.get(1) != null && !Objects.equals(strings.get(1), ""))
                strings.set(1, strings.get(1) + ":");
            else if (Objects.equals(strings.get(1), ""))
                strings.set(1, ":");
        }
    }

    /**
     * This method adds "q" to each element of 1st and 5th columns
     */
    private void addQTo1stAnd5thColumns() {
        for (ArrayList<String> strings : turingMachine) {
            if (strings.get(0) != null)
                strings.set(0, "q" + strings.get(0));
            if (strings.get(4) != null) {
                if (!Objects.equals(strings.get(4), "!"))
                    strings.set(4, "q" + strings.get(4));
            }
        }
    }

    /**
     * This method changes each "*" in 4th column to "s"
     */
    private void changeStarToS() {
        for (int i = 0; i < tur.size(); i++) {
            String star = "*";
            if (Objects.equals(turingMachine.get(i).get(3), star)) {
                turingMachine.get(i).set(3, "s");
            }
        }
    }

    /**
     * This method changes each word "halt" in 5th column to "!"
     */
    private void changeHaltToExclamationMark() {
        for (int i = 0; i < tur.size(); i++) {
            String halt = "halt";
            if (Objects.equals(turingMachine.get(i).get(4), halt)) {
                turingMachine.get(i).set(4, "!");
            }
        }
    }

    /**
     * This method creates 2 dimensional array,
     * where each element has gotten from elements
     * of tur array, split by space
     */
    private void divideColumns() {
        turingMachine = new ArrayList<>(tur.size());
        for (int i = 0; i < tur.size(); i++) {
            //Create array of spaces of index
            int[] indexOfSpace = new int[4];
            indexOfSpace[0] = tur.get(i).indexOf(" ");
            for (int j = 1; j < 4; j++) {
                indexOfSpace[j] = tur.get(i).indexOf(" ", indexOfSpace[j - 1] + 1);
            }
            turingMachine.add(new ArrayList<>());
            //Initialize array turingMachine[][] and trim substrings from it
            if (!tur.get(i).equals("") && tur.get(i).contains(" ")) {
                turingMachine.get(i).add(0, tur.get(i).substring(0, indexOfSpace[0]).trim());
                turingMachine.get(i).add(1, tur.get(i).substring(indexOfSpace[0], indexOfSpace[1]).trim());
                turingMachine.get(i).add(2, tur.get(i).substring(indexOfSpace[1], indexOfSpace[2]).trim());
                turingMachine.get(i).add(3, tur.get(i).substring(indexOfSpace[2], indexOfSpace[3]).trim());
                turingMachine.get(i).add(4, tur.get(i).substring(indexOfSpace[3]).trim());
            } else {
                for (int j = 0; j < 5; j++) {
                    turingMachine.get(i).add(null);
                }
            }
        }
    }

    /**
     * This method deletes spaces if there are more than 1 line with it
     */
    private void deleteSpaces() {
        for (int i = 0; i < tur.size(); i++) {
            tur.set(i, tur.get(i).trim());
            if (tur.get(i).equals("") && i + 1 != tur.size()) {
                if (tur.get(i + 1).equals("")) {
                    tur.remove(i);
                    i--;
                }
            }
        }
    }

    /**
     * This method deletes everything after ';' - sign of comment
     */
    private void deleteComments() {
        for (int i = 0; i < tur.size(); i++) {
            int indexOfComment = tur.get(i).indexOf(';');
            if (indexOfComment != -1) {
                tur.set(i, tur.get(i).substring(0, indexOfComment));
            }
        }
    }

    /**
     * This method parses the file and puts each word on array that we will use later
     */
    private void parseTxtFile() {
        try {
            tur = new ArrayList<>();
            BufferedReader file = new BufferedReader(new FileReader(fileName));
            for (int i = 0; i < amountOfLines; i++) {//file.readLine() != null
                tur.add(i, file.readLine());
            }
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method parses the file and counts amount of lines there
     * in order to initialise array with them later
     */
    private void countLinesInFile() {

        BufferedReader file;
        try {
            file = new BufferedReader(new FileReader(fileName));
            //count lines
            while (file.readLine() != null) {
                amountOfLines++;
            }
            //close file
            file.close();
            //exceptions
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String fileName = "assets/subtraction.txt";//
    private ArrayList<String> tur;
    private int amountOfLines;
    private ArrayList<ArrayList<String>> turingMachine;
}

