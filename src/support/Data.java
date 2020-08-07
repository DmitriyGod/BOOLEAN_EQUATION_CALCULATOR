package support;

public class Data {
    public int leftEnd;
    public int rightEnd;
    public int firstCoordinate;
    public int secondCoordinate;

    public Data() {
    }

    public Data(int left_end, int right_end) {
        this.leftEnd = left_end;
        this.rightEnd = right_end;
    }

    public Data(int left_end, int right_end, int f_coordinate, int s_coordinate) {
        this.leftEnd = left_end;
        this.rightEnd = right_end;
        this.firstCoordinate = f_coordinate;
        this.secondCoordinate = s_coordinate;
    }
}
