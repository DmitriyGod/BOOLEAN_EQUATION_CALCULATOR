package major;

import java.util.HashMap;

public class Algorithm {

    public Algorithm(String path) {
        Parser parser = new Parser(path);
        Prioritizer prioritizer = new Prioritizer(parser.getBrackets(), parser.getBracketsMaxDegree(), parser.getOperations());
        LogicTree tree = new LogicTree(parser.getVariablesEntries(), prioritizer.getSortedOperations());

        int solutions = 0;
        for (int i = 0; i < Math.pow(2, parser.getVariablesNumber()); i++) {
            HashMap<String, Boolean> map = new HashMap<>();
            int temp = i;
            for (int k = 0; k < parser.getVariablesNumber(); k++) {

                if (temp % 2 == 1) {
                    map.put(parser.variables.get(k), true);
                } else {
                    map.put(parser.variables.get(k), false);
                }
                temp /= 2;
            }
            if (tree.calculateSolutions(map)) {
                solutions++;
            }
        }
        System.out.print("Solutions: ");
        System.out.println(solutions);
    }
}
