package major;

import support.BracketPair;
import support.EquationValuePair;
import support.OperationPair;
import support.VariableCoordinate;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {
    String equationsSystem = "";
    String parsedEquationsSystem = "";

    ArrayList<BracketPair> brackets = new ArrayList<>();
    int maxDegree = 1;

    ArrayList<String> variables = new ArrayList<>();
    ArrayList<VariableCoordinate> variablesEntries = new ArrayList<>();

    ArrayList<OperationPair> operations = new ArrayList<>();

    public void showEquationsSystem() {

        System.out.println("Equations system: ");
        System.out.println(equationsSystem);
    }

    public int getVariablesNumber() {
        return variables.size();
    }

    public ArrayList<VariableCoordinate> getVariablesEntries() {
        return variablesEntries;
    }

    public ArrayList<OperationPair> getOperations() {
        return operations;
    }

    public ArrayList<BracketPair> getBrackets() {
        return brackets;
    }

    public int getBracketsMaxDegree() {
        return maxDegree;
    }


    private void parseEquationsSystem() {
        int valuables = 0;
        int nestingDegree = 1;

        for (int i = 0; i < parsedEquationsSystem.length(); i++) {
            char c = parsedEquationsSystem.charAt(i);
            if (c == ' ') {
                continue;
            }

            if (c == '(' || c == ')') {
                if (c == '(') {
                    brackets.add(new BracketPair(valuables, nestingDegree));
                    if (nestingDegree > maxDegree) {
                        maxDegree = nestingDegree;
                    }
                    nestingDegree++;
                }
                if (c == ')') {
                    nestingDegree--;
                    brackets.add(new BracketPair(valuables - 1, nestingDegree));
                }
                continue;
            }

            if (c == 'x') {
                String tempvariable = "x";
                int k = 1;
                while ((parsedEquationsSystem.charAt(i + k)) <= '9' && (parsedEquationsSystem.charAt(i + k)) >= '0') {
                    tempvariable += parsedEquationsSystem.charAt(i + k);
                    k++;
                }
                i += k - 1;
                if (!variables.contains(tempvariable)) {
                    variables.add(tempvariable);
                }
                variablesEntries.add(new VariableCoordinate(tempvariable, valuables));
                valuables++;
            } else {
                operations.add(new OperationPair(parsedEquationsSystem.charAt(i), valuables));
                valuables++;
            }
        }
    }

    public Parser(String path) {
        try (FileReader reader = new FileReader(path)) {
            int c;
            while ((c = reader.read()) != -1) {
                equationsSystem += (char) c;
            }

            reader.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        ArrayList<EquationValuePair> equationsArray = new ArrayList<>();
        int start = 0;

        for (int i = 0; i < equationsSystem.length(); i++) {
            String parsedEquation = "";
            if (equationsSystem.charAt(i) == '\n') {

                for (int k = start; k < i - 2; k++) {
                    parsedEquation += equationsSystem.charAt(k);
                }
                start = i + 1;
                equationsArray.add(new EquationValuePair(parsedEquation, equationsSystem.charAt(i - 1) - '0'));
            }
        }

        parsedEquationsSystem += "(";
        for (int i = 0; i < equationsArray.size(); i++) {
            if (equationsArray.get(i).value == 0) {
                parsedEquationsSystem += "!";
            }
            parsedEquationsSystem += "(";
            parsedEquationsSystem += equationsArray.get(i).equation;
            parsedEquationsSystem += ")";
            if (i != equationsArray.size() - 1) {
                parsedEquationsSystem += "*";
            }
        }
        parsedEquationsSystem += ")";

        parseEquationsSystem();
        showEquationsSystem();
    }
}
