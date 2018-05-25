import java.io.Serializable;

public class Coordinates implements Serializable {
    int row;
    int col;

    public Coordinates(int row, int col){
        this.row = row;
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates coordinate = (Coordinates) o;
        boolean eq = false;
        if(coordinate.getRow() == this.row && coordinate.getCol() == this.col){
            eq = true;
        }
        return eq;
    }
}
