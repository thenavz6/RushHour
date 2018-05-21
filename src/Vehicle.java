import java.io.*;
import javafx.scene.image.Image;

public class Vehicle implements Serializable{

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
    private Image image;


    /**
     * constructor for Vehicle
     * @param type whether vehicle is a car (2 blocks long) or a truck (3 blocks long)
     * @param colour colour of vehicle - corresponds to image name
     * @param row row of front of vehicle
     * @param column column of front of vehicle
     * @param direction direction vehicle is facing
     */
    public Vehicle(VehicleType type, Colour colour, int row, int column, Direction direction) {
        this.name = colour.toString().charAt(0) + "" + type.toString().charAt(0);
        this.type = type;
        this.colour = colour;
        this.frontRow = row;
        this.frontColumn = column;
        this.direction = direction;
        updateCoordinates();

        // sets up image
    }

    /**
     * getter for vehicle type
     * @return vehicle type (car or truck)
     */
    public VehicleType getVehicleType() {
        return type;
    }

    /**
     * sets vehicle type
     * @param type new type
     */
    public void setType(VehicleType type) {
        this.type = type;
    }

    /**
     * getter for colour
     * @return colour
     */
    public Colour getColour() {
        return colour;
    }

    /**
     * sets colour
     * @param colour new colour
     */
    public void setColour(Colour colour) {
        this.colour = colour;
    }

    /**
     * getter for front row
     * @return front row
     */
    public int getFrontRow() {
        return frontRow;
    }

    /**
     * getter for front column
     * @return front column
     */
    public int getFrontColumn() {
        return frontColumn;
    }

    /**
     * sets row of front
     * @param frontRow row of front
     */
    public void setFrontRow(int frontRow) {
        this.frontRow = frontRow;
        updateCoordinates();
    }

    /**
     * sets column of front
     * @param frontColumn column of front
     */
    public void setFrontColumn(int frontColumn) {
        this.frontColumn = frontColumn;
        updateCoordinates();
    }

    /**
     * getter for row of middle
     * @return middle row
     */
    public int getMidRow() {
        return midRow;
    }

    /**
     * getter for column of middle
     * @return middle column
     */
    public int getMidColumn() {
        return midColumn;
    }

    /**
     * sets row for middle
     * @param midRow new row
     */
    public void setMidRow(int midRow) {
        this.midRow = midRow;
    }

    /**
     * sets column for middle
     * @param midColumn new column
     */
    public void setMidColumn(int midColumn) {
        this.midColumn = midColumn;
    }

    /**
     * getter for back row
     * @return back row
     */
    public int getBackRow() {
        return backRow;
    }

    /**
     * getter for back column
     * @return back column
     */
    public int getBackColumn() {
        return backColumn;
    }

    /**
     * getter for direction
     * @return direction - north, south, east or west
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * sets direction
     * @param direction new direction
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * sets name
     * @param name new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Moves piece one square up if facing north or south
     * @param state current state
     * @return boolean - true if piece was successfully moved, false otherwise
     */
    public boolean moveUpOne(StateNode state){
        if(direction == Direction.NORTH || direction == Direction.SOUTH) {
            if (this.frontRow - 1 >= 0 && this.backRow - 1 >= 0) {
                if(state.getState().get(this.frontRow - 1).get(this.frontColumn).getVehicleType() == VehicleType.EMPTY || state.getState().get(this.backRow - 1).get(this.backColumn).getVehicleType() == VehicleType.EMPTY) {
                    if (type == VehicleType.car) {
                        this.frontRow--;
                        this.backRow--;
                    } else if(state.getState().get(this.midRow - 1).get(this.midColumn).getVehicleType() == VehicleType.EMPTY) {
                        this.frontRow--;
                        this.midRow--;
                        this.backRow--;
                    }
                    updateCoordinates();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Moves piece one square down if facing north or south
     * @param state current state
     * @return boolean - true if piece was successfully moved, false otherwise
     */
    public boolean moveDownOne(StateNode state){
        if(direction == Direction.NORTH || direction == Direction.SOUTH) {
            if (this.frontRow + 1 <= 5 && this.backRow + 1 <= 5) {
                if(state.getState().get(this.frontRow + 1).get(this.frontColumn).getVehicleType() == VehicleType.EMPTY || state.getState().get(this.backRow + 1).get(this.backColumn).getVehicleType() == VehicleType.EMPTY) {
                    if (type == VehicleType.car) {
                        this.frontRow++;
                        this.backRow++;
                    } else if(state.getState().get(this.midRow + 1).get(this.midColumn).getVehicleType() == VehicleType.EMPTY){
                        this.frontRow++;
                        this.midRow++;
                        this.backRow++;
                    }
                    updateCoordinates();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Moves piece one square left if facing east or west
     * @param state current state
     * @return boolean - true if piece was successfully moved, false otherwise
     */
    public boolean moveLeftOne(StateNode state){
        if(direction == Direction.EAST || direction == Direction.WEST) {
            if (this.frontColumn - 1 >= 0 && this.backColumn - 1 >= 0) {
                if(state.getState().get(this.frontRow).get(this.frontColumn - 1).getVehicleType() == VehicleType.EMPTY || state.getState().get(this.backRow).get(this.backColumn - 1).getVehicleType() == VehicleType.EMPTY) {
                    if (type == VehicleType.car) {
                        this.frontColumn--;
                        this.backColumn--;
                    } else if(state.getState().get(this.midRow).get(this.midColumn - 1).getVehicleType() == VehicleType.EMPTY){
                        this.frontColumn--;
                        this.midColumn--;
                        this.backColumn--;
                    }
                    updateCoordinates();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Moves piece one square right if facing east or west
     * @param state current state
     * @return boolean - true if piece was successfully moved, false otherwise
     */
    public boolean moveRightOne(StateNode state){
        if(direction == Direction.EAST || direction == Direction.WEST) {
            if (this.frontColumn + 1 <= 5 && this.backColumn + 1 <= 5) {
                if(state.getState().get(this.frontRow).get(this.frontColumn + 1).getVehicleType() == VehicleType.EMPTY || state.getState().get(this.backRow).get(this.backColumn + 1).getVehicleType() == VehicleType.EMPTY) {
                    if (type == VehicleType.car) {
                        this.frontColumn++;
                        this.backColumn++;
                    } else if(state.getState().get(this.midRow).get(this.midColumn + 1).getVehicleType() == VehicleType.EMPTY){
                        this.frontColumn++;
                        this.midColumn++;
                        this.backColumn++;
                    }
                    updateCoordinates();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * TODO: ?????
     */
    public void updateCoordinates(){
        if(this.direction == Direction.NORTH){
            this.backColumn = this.frontColumn;
            if(this.type == VehicleType.car) {
                this.backRow = this.frontRow + 1;
            }else{
                this.backRow = this.frontRow + 2;
                this.midRow = this.frontRow + 1;
                this.midColumn = this.frontColumn;
            }
        }else if(this.direction == Direction.SOUTH){
            this.backColumn = this.frontColumn;
            if(this.type == VehicleType.car) {
                this.backRow = this.frontRow - 1;
            }else{
                this.backRow = this.frontRow - 2;
                this.midRow = this.frontRow - 1;
                this.midColumn = this.frontColumn;
            }
        }else if(this.direction == Direction.EAST){
            this.backRow = this.frontRow;
            if(this.type == VehicleType.car) {
                this.backColumn = this.frontColumn - 1;
            }else{
                this.backColumn = this.frontColumn - 2;
                this.midColumn = this.frontColumn - 1;
                this.midRow = this.frontRow;
            }
        }else if(this.direction == Direction.WEST){
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

    /**
     * creates a proper copy of the current vehicle
     * @return copy of vehicle
     */
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

    /**
     * getter for direction
     * @return direction (North, South, East, West)
     */
    public String getOrientation(){
        if(this.direction == Direction.NORTH || this.direction == Direction.SOUTH){
            return "v";
        }else{
            return "h";
        }
    }

    /**
     * overrides toString for formatting purposes
     * @return name followed by a space
     */
    @Override
    public String toString() {
        return name + " ";

    }
}

