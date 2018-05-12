public class Vehicle {

    private String name;
    private VehicleType type;
    private Colour colour;
    private int frontRow;
    private int frontColumn;
    private int midRow;
    private int midColumn;
    private int backRow;
    private int backColumn;
    private Direction direction;

    public Vehicle(VehicleType type, Colour colour, int row, int column, Direction direction) {
        this.name = colour.toString().charAt(0) + ""+type.toString().charAt(0);
        this.type = type;
        this.colour = colour;
        this.frontRow = row;
        this.frontColumn = column;
        this.direction = direction;
        updateCoordinates();
    }

    public VehicleType getVehicleType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public int getFrontRow() {
        return frontRow;
    }

    public int getFrontColumn() {
        return frontColumn;
    }

    public int getBackRow() {
        return backRow;
    }

    public int getBackColumn() {
        return backColumn;
    }

    public int getMidRow() {
        return midRow;
    }

    public int getMidColumn() {
        return midColumn;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFrontRow(int frontRow) {
        this.frontRow = frontRow;
        updateCoordinates();
    }

    public void setFrontColumn(int frontColumn) {
        this.frontColumn = frontColumn;
        updateCoordinates();
    }

    public void moveUpOne(){
        if(type == VehicleType.CAR) {
            this.frontRow += 1;
            this.backRow += 1;
        }else{
            this.frontRow += 1;
            this.midRow += 1;
            this.backRow += 1;
        }
        updateCoordinates();
    }

    public void moveDownOne(){
        if(type == VehicleType.CAR) {
            this.frontRow -= 1;
            this.backRow -= 1;
        }else{
            this.frontRow -= 1;
            this.midRow -= 1;
            this.backRow -= 1;
        }
        updateCoordinates();
    }

    public void moveLeftOne(){
        if(type == VehicleType.CAR) {
            this.frontColumn--;
            this.backColumn--;
        }else{
            this.frontColumn--;
            this.midColumn--;
            this.backColumn--;
        }
        updateCoordinates();
    }

    public void moveRightOne(){
        if(type == VehicleType.CAR) {
            this.frontColumn += 1;
            this.backColumn += 1;
        }else{
            this.frontColumn += 1;
            this.midColumn += 1;
            this.backColumn += 1;
        }
        updateCoordinates();
    }

    public void updateCoordinates(){
        if(this.direction == Direction.NORTH){
            this.backColumn = this.frontColumn;
            if(this.type == VehicleType.CAR) {
                this.backRow = this.frontRow + 1;
            }else{
                this.backRow = this.frontRow + 2;
                this.midRow = this.frontRow + 1;
                this.midColumn = this.frontColumn;
            }
        }else if(this.direction == Direction.SOUTH){
            this.backColumn = this.frontColumn;
            if(this.type == VehicleType.CAR) {
                this.backRow = this.frontRow - 1;
            }else{
                this.backRow = this.frontRow - 2;
                this.midRow = this.frontRow - 1;
                this.midColumn = this.frontColumn;
            }
        }else if(this.direction == Direction.EAST){
            this.backRow = this.frontRow;
            if(this.type == VehicleType.CAR) {
                this.backColumn = this.frontColumn - 1;
            }else{
                this.backColumn = this.frontColumn - 2;
                this.midColumn = this.frontColumn - 1;
                this.midRow = this.frontRow;
            }
        }else if(this.direction == Direction.WEST){
            this.backRow = this.frontRow;
            if(this.type == VehicleType.CAR) {
                this.backColumn = this.frontColumn + 1;
            }else{
                this.backColumn = this.frontColumn + 2;
                this.midColumn = this.frontColumn + 1;
                this.midRow = this.frontRow;
            }
        }
    }

    @Override
    public String toString() {
        return name + " ";

    }
}
