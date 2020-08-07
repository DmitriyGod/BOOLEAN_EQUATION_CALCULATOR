package support;

public class OperationUnit {
    public Operation op;
    public Data data;
    public boolean value = false;

    public OperationUnit(Operation op, Data data) {
        this.op = op;
        this.data = data;
    }
}
