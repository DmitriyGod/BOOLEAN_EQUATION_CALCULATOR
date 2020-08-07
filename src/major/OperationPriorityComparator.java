package major;

import support.OperationPair;

import java.util.Comparator;

public class OperationPriorityComparator implements Comparator<OperationPair> {
    private char zeroPriorityOperation = '!';
    private char firstPriorityOperation = '*';
    private char[] secondPriorityOperation = {'+', '$'};
    private char thirdPriorityOperation = '>';
    private char fourthPriorityOperation = '~';

    @Override
    public int compare(OperationPair operation1, OperationPair operation2) {
        int k = 0;
        int j = 0;
        for (int i = 0; i < 5; i++) {
            if (operation1.first == zeroPriorityOperation) {
                k = 4;
            }
            if (operation1.first == firstPriorityOperation) {
                k = 3;
            }
            if (operation1.first == secondPriorityOperation[0] || operation1.first == secondPriorityOperation[1]) {
                k = 2;
            }
            if (operation1.first == thirdPriorityOperation) {
                k = 1;
            }
            if (operation1.first == fourthPriorityOperation) {
                k = 0;
            }
            if (operation2.first == zeroPriorityOperation) {
                j = 4;
            }
            if (operation2.first == firstPriorityOperation) {
                j = 3;
            }
            if (operation2.first == secondPriorityOperation[0] || operation2.first == secondPriorityOperation[1]) {
                j = 2;
            }
            if (operation2.first == thirdPriorityOperation) {
                j = 1;
            }
            if (operation2.first == fourthPriorityOperation) {
                j = 0;
            }
        }
        if (k < j) {
            return 1;
        }
        if (j < k) {
            return -1;
        }
        if (j == k) {
            if (operation1.second > operation2.second) {
                return 1;
            }
            if (operation1.second < operation2.second) {
                return -1;
            }
        }
        return 0;
    }
}
