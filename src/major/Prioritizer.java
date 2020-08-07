package major;

import support.BracketPair;
import support.OperationPair;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Prioritizer {
    ArrayList<OperationPair> sortedOperations = new ArrayList<>();

    public ArrayList<OperationPair> getSortedOperations() {
        return sortedOperations;
    }

    Prioritizer(ArrayList<BracketPair> brackets, int maxDegree, ArrayList<OperationPair> operations) {
        while (!brackets.isEmpty()) {
            int iter = 0;
            var iter_to_delite = new ArrayList<BracketPair>();
            for (int i = 0; i < brackets.size(); i++) {
                if (brackets.get(i).nesting_degree == maxDegree) {
                    iter = i;
                    iter_to_delite.add(brackets.get(i));
                    iter_to_delite.add(brackets.get(i + 1));
                    i += 1;

                    var queue = new PriorityQueue<OperationPair>(new OperationPriorityComparator());
                    var indexes = new ArrayList<OperationPair>();
                    for (int j = 0; j < operations.size(); j++) {
                        if (operations.get(j).second >= brackets.get(iter).coord && operations.get(j).second < brackets.get(iter + 1).coord) {
                            indexes.add(operations.get(j));
                            queue.add(new OperationPair(operations.get(j).first, operations.get(j).second));
                        }
                    }
                    for (var index : indexes) {
                        operations.remove(index);
                    }
                    while (!queue.isEmpty()) {
                        sortedOperations.add(queue.poll());
                    }
                }
            }
            for (var it : iter_to_delite) {
                brackets.remove(it);
            }
            maxDegree--;
        }
    }
}
