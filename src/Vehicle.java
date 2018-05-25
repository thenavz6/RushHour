import java.io.*;


public class Vehicle implements Serializable{

    private String name;
    private int id;
    private VehicleType type;
    private Colour colour;
    private Coordinates coordinates;
    private int frontRow;
    private int frontColumn;
    private int midRow;
    private int midColumn;
    private int backRow;
    private int backColumn;
    private Direction direction;

    public Vehicle(VehicleType type, Colour colour, int row, int column, Direction direction, int id) {
        this.name = colour.toString().charAt(0) + ""+type.toString().charAt(0)+""+direction.toString().charAt(0);
        this.type = type;
        this.colour = colour;
        this.frontRow = row;
        this.frontColumn = column;
        this.direction = direction;
        this.coordinates = new Coordinates(row,column);
        this.id = id;
        updateCoordinates();
    }

    public VehicleType getVehicleType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public void setId(int i){
        this.id = i;

    }

    public Coordinates getCoordinates() {
        return coordinates;
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

    public void setMidRow(int midRow) {
        this.midRow = midRow;
    }

    public void setMidColumn(int midColumn) {
        this.midColumn = midColumn;
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

    public boolean moveUpOne(StateNode state){
        if(direction == Direction.north || direction == Direction.south) {
            if (this.frontRow - 1 >= 0 && this.backRow - 1 >= 0) {
                if(state.getState().get(this.frontRow - 1).get(this.frontColumn).getVehicleType() == VehicleType.empty || state.getState().get(this.backRow - 1).get(this.backColumn).getVehicleType() == VehicleType.empty) {
                    if (type == VehicleType.car) {
                        this.frontRow--;
                        this.backRow--;
                    } else if(state.getState().get(this.midRow - 1).get(this.midColumn).getVehicleType() == VehicleType.empty) {
                        this.frontRow--;
                        this.midRow--;
                        this.backRow--;
                    }
                   // System.out.println("Moved " + this.name + " up 1");
                    updateCoordinates();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean moveDownOne(StateNode state){
        if(direction == Direction.north || direction == Direction.south) {
            if (this.frontRow + 1 <= 5 && this.backRow + 1 <= 5) {
                if(state.getState().get(this.frontRow + 1).get(this.frontColumn).getVehicleType() == VehicleType.empty || state.getState().get(this.backRow + 1).get(this.backColumn).getVehicleType() == VehicleType.empty) {
                    if (type == VehicleType.car) {
                        this.frontRow++;
                        this.backRow++;
                    } else if(state.getState().get(this.midRow + 1).get(this.midColumn).getVehicleType() == VehicleType.empty){
                        this.frontRow++;
                        this.midRow++;
                        this.backRow++;
                    }
                    //System.out.println("Moved " + this.name + "down 1");
                    updateCoordinates();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean moveLeftOne(StateNode state){
        if(direction == Direction.east || direction == Direction.west) {
            if (this.frontColumn - 1 >= 0 && this.backColumn - 1 >= 0) {
                if(state.getState().get(this.frontRow).get(this.frontColumn - 1).getVehicleType() == VehicleType.empty || state.getState().get(this.backRow).get(this.backColumn - 1).getVehicleType() == VehicleType.empty) {
                    if (type == VehicleType.car) {
                        this.frontColumn--;
                        this.backColumn--;
                    } else if(state.getState().get(this.midRow).get(this.midColumn - 1).getVehicleType() == VehicleType.empty){
                        this.frontColumn--;
                        this.midColumn--;
                        this.backColumn--;
                    }
                    //System.out.println("Moved " + this.name + "left 1");
                    updateCoordinates();
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    public boolean moveRightOne(StateNode state){
        if(direction == Direction.east || direction == Direction.west) {
            if (this.frontColumn + 1 <= 5 && this.backColumn + 1 <= 5) {
                if(state.getState().get(this.frontRow).get(this.frontColumn + 1).getVehicleType() == VehicleType.empty || state.getState().get(this.backRow).get(this.backColumn + 1).getVehicleType() == VehicleType.empty) {
                    if (type == VehicleType.car) {
                        this.frontColumn++;
                        this.backColumn++;
                    } else if(state.getState().get(this.midRow).get(this.midColumn + 1).getVehicleType() == VehicleType.empty){
                        this.frontColumn++;
                        this.midColumn++;
                        this.backColumn++;
                    }
                    //System.out.println("Moved " + this.name + "right 1");
                    updateCoordinates();
                    return true;
                }
            }
        }
        return false;
    }

    public void updateCoordinates(){
        if(this.direction == Direction.north){
            this.backColumn = this.frontColumn;
            if(this.type == VehicleType.car) {
                this.backRow = this.frontRow + 1;
            }else{
                this.backRow = this.frontRow + 2;
                this.midRow = this.frontRow + 1;
                this.midColumn = this.frontColumn;
            }
        }else if(this.direction == Direction.south){
            this.backColumn = this.frontColumn;
            if(this.type == VehicleType.car) {
                this.backRow = this.frontRow - 1;
            }else{
                this.backRow = this.frontRow - 2;
                this.midRow = this.frontRow - 1;
                this.midColumn = this.frontColumn;
            }
        }else if(this.direction == Direction.east){
            this.backRow = this.frontRow;
            if(this.type == VehicleType.car) {
                this.backColumn = this.frontColumn - 1;
            }else{
                this.backColumn = this.frontColumn - 2;
                this.midColumn = this.frontColumn - 1;
                this.midRow = this.frontRow;
            }
        }else if(this.direction == Direction.west){
            this.backRow = this.frontRow;
            if(this.type == VehicleType.car) {
                this.backColumn = this.frontColumn + 1;
            }else{
                this.backColumn = this.frontColumn + 2;
                this.midColumn = this.frontColumn + 1;
                this.midRow = this.frontRow;
            }
        }
    }

    public Vehicle deepCopy(){

        Vehicle copy = null;
        try {
            // Write the object out to a byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(this);
            out.flush();
            out.close();

            // Make an input stream from the byte array and read
            // a copy of the object back in.
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
            copy = (Vehicle) in.readObject();
        }
        catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if(copy == null){
            System.out.println("copy is null");
        }
        return copy;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        boolean eq;
        if(this.getId()== vehicle.getId() && this.getId() == vehicle.getId() ){
            eq = true;
        }else {
            eq = false;
            return eq;
        }
        return eq;
    }

    public String getOrientation(){
        if(this.direction == Direction.north || this.direction == Direction.south){
            return "v";
        }else{
            return "h";
        }
    }

    public String getName(){
        return this.name;
    }

    @Override
    public String toString() {
        return name + " ";

    }
}

