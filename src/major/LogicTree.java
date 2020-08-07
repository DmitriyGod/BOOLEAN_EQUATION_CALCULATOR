package major;


import support.*;

import java.util.ArrayList;
import java.util.HashMap;

public class LogicTree {
    ArrayList<VariableCoordinate> variablesEntries;
    ArrayList<OperationUnit> operationUnits = new ArrayList<>();
    Operation invert = (boolean... a) -> !a[0];
    Operation disp = (boolean... a) -> a[0];
    Operation con = (boolean... a) -> a[0] && a[1];
    Operation dis = (boolean... a) -> a[0] || a[1];
    Operation XoR = (boolean... a) -> a[0] && !a[1] || !a[0] && a[1];
    Operation imp = (boolean... a) -> !a[0] || a[1];
    Operation eq = (boolean... a) -> (!a[0] || a[1]) && (a[0] || !a[1]);

    public void addVariables(ArrayList<VariableCoordinate> variablesEntries) {

        for (int i = 0; i < variablesEntries.size(); i++) {
            operationUnits.add(new OperationUnit(disp, new Data(variablesEntries.get(i).second, variablesEntries.get(i).second)));
        }
        this.variablesEntries = variablesEntries;
    }

    public Data findCoord(int edge, boolean uno) {
        Data data = new Data();

        if (uno) {
            int rightSide = edge + 1;
            for (int i = operationUnits.size() - 1; i >= 0; i--) {
                var op = operationUnits.get(i);
                if (rightSide == op.data.leftEnd) {
                    data.leftEnd = edge;
                    data.rightEnd = op.data.rightEnd;
                    data.secondCoordinate = i;
                    data.firstCoordinate = i;
                    break;
                }
            }
            return data;
        }

        int leftSide = edge - 1;
        boolean leftSideFinded = false;
        int rightSide = edge + 1;
        boolean rightSideFinded = false;

        for (int i = operationUnits.size() - 1; i >= 0; i--) {
            var op = operationUnits.get(i);
            if (!leftSideFinded) {
                if (leftSide == op.data.rightEnd) {
                    leftSideFinded = true;
                    data.leftEnd = op.data.leftEnd;
                    data.firstCoordinate = i;
                }

            }
            if (!rightSideFinded) {
                if (rightSide == op.data.leftEnd) {
                    rightSideFinded = true;
                    data.rightEnd = op.data.rightEnd;
                    data.secondCoordinate = i;
                }
            }
        }
        return data;
    }

    public void addOperations(ArrayList<OperationPair> queue) {
        for (int i = 0; i < queue.size(); i++) {
            var elem = queue.get(i);

            if (elem.first == '!') {
                operationUnits.add(
                        new OperationUnit(
                                invert,
                                findCoord(elem.second, true)
                        )
                );
            }

            if (elem.first == '*') {
                operationUnits.add(
                        new OperationUnit(
                                con,
                                findCoord(elem.second, false)
                        )
                );
            }
            if (elem.first == '$' || elem.first == '+') {
                if (elem.first == '+') {
                    operationUnits.add(
                            new OperationUnit(
                                    dis,
                                    findCoord(elem.second, false)
                            )
                    );
                } else {
                    operationUnits.add(
                            new OperationUnit(
                                    XoR,
                                    findCoord(elem.second, false)
                            )
                    );
                }
            }
            if (elem.first == '>') {
                operationUnits.add(
                        new OperationUnit(
                                imp,
                                findCoord(elem.second, false)
                        )
                );
            }
            if (elem.first == '~') {
                operationUnits.add(
                        new OperationUnit(
                                eq,
                                findCoord(elem.second, false)
                        )
                );
            }
        }
    }

    public boolean calculateSolutions(HashMap<String, Boolean> vector) {

        for (int i = 0; i < variablesEntries.size(); i++) {
            operationUnits.get(i).value = operationUnits.get(i).op.operation(vector.get(variablesEntries.get(i).first), false);
        }

        for (int i = variablesEntries.size(); i < operationUnits.size(); i++) {
            operationUnits.get(i).value = operationUnits.get(i).op.operation(operationUnits.get(operationUnits.get(i).data.firstCoordinate).value, operationUnits.get(operationUnits.get(i).data.secondCoordinate).value);
        }
        return operationUnits.get(operationUnits.size() - 1).value;
    }

    LogicTree(ArrayList<VariableCoordinate> variables, ArrayList<OperationPair> queue) {
        addVariables(variables);
        addOperations(queue);
    }
}
