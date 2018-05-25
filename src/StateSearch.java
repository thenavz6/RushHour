import javax.swing.plaf.nimbus.State;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.lang.Math;

public class StateSearch {

    private ArrayList<ArrayList<Vehicle>> endState = new ArrayList<>();
    private ArrayList<Vehicle> vehicles = new ArrayList<>();
    private Vehicle emptyVehicle = new Vehicle(VehicleType.empty,Colour.empty,-1,-1,Direction.nan,-1);
    private Random generator = new Random();
    private Vehicle primaryCar = new Vehicle(VehicleType.car,Colour.red,2,generator.nextInt(2)+1,Direction.east,1);
    private Stack<StateNode> movements = new Stack<>();

    /**
     * Function used to solve the board state
     * Once the startState is given, go through all the possible
     * combinations, adding each move to the queue and seeing if
     * reaching the end state is possible. Once all possible moves
     * have been done, finish the function
     * @param startState current board state
     * @return false once all moves have been completed
     */
    public boolean solve(StateNode startState){

        movements.clear();
        Queue<StateNode> queue = new ConcurrentLinkedDeque<>();
        ArrayList<StateNode> explored = new ArrayList<>();
        StateNode startCopy = startState.deepCopy();
        startCopy.setParent(null);
        queue.add(startCopy);
        StateNode currentNode = null;
        while (!queue.isEmpty()) {
            currentNode = queue.poll();
            if (explored.contains(currentNode)) continue;
            if (currentNode.getVehicles().get(0).getFrontColumn() == 5) break;
            explored.add(currentNode);

            for (StateNode item: getNeighbours(currentNode)) {
                if (!explored.contains(item))
                    queue.add(item);
                    item.setParent(currentNode);
            }
        }

        if (queue.isEmpty()) return false;

        StateNode prev = currentNode;

        while (prev != null) {
            movements.push(prev);
            prev = prev.getParent();
        }

        return true;
    }

    /**
     * Getter for board movements
     * @return movements
     */
    public Stack<StateNode> getMovementList(){
        return movements;
    }

    /**
     * Print out the amount of moves to complete the board
     * @param movements Possible board state movements
     */
    public void printMoves(Stack<StateNode> movements){
        int i = 1;
        for(StateNode item: movements){
            System.out.println("Move " + i);
            printState(item);
            i++;
        }
    }

    /**
     * Getter for the solution of the puzzle
     * @return first element of movements
     */
    public StateNode getEndState(){
        return movements.firstElement();
    }

    /**
     * Get the neighbouring moves for the current state of the board
     * Copies the neighbour board state and returns it.
     * @param state Current state of the board
     * @return result A arraylist containing the neighbour board state
     */
    public ArrayList<StateNode> getNeighbours(StateNode state){
        ArrayList<StateNode> result = new ArrayList<>();
        StateNode stateCopy = state.deepCopy();

        for(Vehicle item: stateCopy.getVehicles()){
            if(item.getOrientation().equals("v")){
                if(checkMove(stateCopy,item,"up")){
                    move(stateCopy,item, result, "up");
                }if(checkMove(stateCopy,item,"down")){
                    move(stateCopy,item, result, "down");
                }
            }else{
                if(checkMove(stateCopy,item,"left")){
                    move(stateCopy,item, result, "left");
                }if(checkMove(stateCopy,item,"right")){
                    move(stateCopy,item, result, "right");
                }
            }
        }
        return result;
    }

    /**
     * Move the piece in the board while generating solution for it
     * Moves the vehicle specified in the board and stores the
     * new board position in a new child board state.
     * @param childState Previous board state
     * @param vehicle vehicle that needs to be moved
     * @param results arrayList of the neighbour states
     * @param direction which direction to move the vehicle
     */
    public void move(StateNode childState, Vehicle vehicle, ArrayList<StateNode> results, String direction){
        StateNode childStatetoAdd = childState.deepCopy();
        Vehicle vehicleCopy = null;
        for(Vehicle item: childStatetoAdd.getVehicles()){
            if(item.getId() == vehicle.getId()){
                vehicleCopy = item.deepCopy();
            }
        }
        Vehicle memory = vehicleCopy.deepCopy();

        switch(direction) {

            case "right":
                vehicleCopy.moveRightOne(childStatetoAdd);
                updateSate(childStatetoAdd, vehicleCopy, memory);
                results.add(childStatetoAdd);
                break;
            case "left":
                vehicleCopy.moveLeftOne(childStatetoAdd);
                updateSate(childStatetoAdd, vehicleCopy, memory);
                results.add(childStatetoAdd);
                break;
            case "up":
                vehicleCopy.moveUpOne(childStatetoAdd);
                updateSate(childStatetoAdd, vehicleCopy, memory);
                results.add(childStatetoAdd);
                break;
            case "down":
                vehicleCopy.moveDownOne(childStatetoAdd);
                updateSate(childStatetoAdd, vehicleCopy, memory);
                results.add(childStatetoAdd);
                break;
        }
    }

    /**
     * Check if moving the vehicle piece is possible and the same as the
     * previous generated board state. Checks the vehicle direction and moves it
     * then checks if the childState and the new State match up.
     * @param childState new board state
     * @param vehicle vehicle that is being moved
     * @param direction direction of the vehicle movement
     * @return true if the board states match
     * @return false if the board state doesn't match
     */
    public boolean checkMove(StateNode childState, Vehicle vehicle, String direction){
        Vehicle memory = vehicle.deepCopy();
        StateNode testState = childState.deepCopy();
        switch(direction) {

            case "right":
                if(vehicle.getDirection() == Direction.east || vehicle.getDirection() == Direction.west) {
                    if (memory.moveRightOne(testState)) {
                        return true;
                    }
                }
                break;
            case "left":
                if(vehicle.getDirection() == Direction.east || vehicle.getDirection() == Direction.west) {
                    if (memory.moveLeftOne(testState)) {
                        return true;
                    }
                }
                break;
            case "up":
                if(vehicle.getDirection() == Direction.north || vehicle.getDirection() == Direction.south) {
                    if (memory.moveUpOne(testState)) {
                        return true;
                    }
                }
                break;
            case "down":
                if(vehicle.getDirection() == Direction.north || vehicle.getDirection() == Direction.south) {
                    if (memory.moveDownOne(testState)) {
                        return true;
                    }
                }
                break;
            default:
                break;
        }
        return false;
    }

    /**
     * Generates a new empty board state to get populated.
     * Once a empty board is present, based on the difficulty the user has selected
     * Put the appropriate amount of cars that needs to be generated on the board.
     * Check if the new board is solvable and the possible moves that the user could do
     * If it isnt, move around vehicle or remove some until a new puzzle that can be solved
     * is generated for the user to play.
     * @param numberOfCars how many cars that needs to be generated
     * @param difficulty difficulty the user has selected
     * @return goalState A board that has been randomly generated for a difficulty setting
     */
    public StateNode generateStartState(int numberOfCars, String difficulty){

        endState.clear();
        vehicles.clear();
        if(endState.size() != 0){
            System.out.println("Endstate is not empty");
        }
        if(vehicles.size() != 0){
            System.out.println("Endstate is not empty");
        }


        for(int i = 0; i < 6; i++) {
            endState.add(i,new ArrayList<>());
        }

        for(ArrayList<Vehicle> row: endState){
            for(int i = 0; i < 6; i++){
                row.add(i,emptyVehicle);
            }

        }


        vehicles.add(primaryCar);
        endState.get(primaryCar.getFrontRow()).set(primaryCar.getFrontColumn(),primaryCar);
        endState.get(primaryCar.getBackRow()).set(primaryCar.getBackColumn(),primaryCar);
        int tooLong = 0;
        for(int i = vehicles.size(); i < numberOfCars ; i++){
            if(tooLong > 100){
                break;
            }
            VehicleType type = VehicleType.values()[generator.nextInt(VehicleType.values().length - 1)];
            Colour colour = Colour.values()[generator.nextInt(Colour.values().length - 2)];
            Direction direction = Direction.values()[generator.nextInt(Direction.values().length - 1)];
            int row = generator.nextInt(6);
            int column = generator.nextInt(6);

            if(checkCoordinates(row,column,direction,type)){
                Vehicle newVehicle = new Vehicle(type, colour, row, column, direction,i+1);
                vehicles.add(newVehicle);
                endState.get(newVehicle.getFrontRow()).set(newVehicle.getFrontColumn(), newVehicle);
                endState.get(newVehicle.getBackRow()).set(newVehicle.getBackColumn(), newVehicle);
                if (type == VehicleType.truck) {
                    endState.get(newVehicle.getMidRow()).set(newVehicle.getMidColumn(), newVehicle);
                }
            }else{
                i--;
            }
            int vehicleCount = 0;
            for(ArrayList<Vehicle> item1: endState){
                for(Vehicle item2: item1){
                    if(item2.getVehicleType() != VehicleType.empty){
                        vehicleCount++;
                    }
                }
            }
            tooLong++;
        }

        StateNode goalState = new StateNode(endState,null);
        goalState.addVehicles(vehicles);

        if(!checkEven(vehicles)){
            goalState = generateStartState(numberOfCars,difficulty);
        }
        if(difficulty.equals("Easy")) {
            if (goalState.getState().get(2).get(goalState.getVehicles().get(0).getFrontColumn() + 1).getVehicleType() == VehicleType.empty || goalState.getState().get(2).get(goalState.getVehicles().get(0).getFrontColumn() + 2).getVehicleType() == VehicleType.empty ) {
                goalState = generateStartState(numberOfCars, difficulty);
            }
        }else if(difficulty.equals("Medium")){
            if (goalState.getState().get(3).get(goalState.getVehicles().get(0).getFrontColumn() + 1).getVehicleType() == VehicleType.empty || goalState.getState().get(2).get(goalState.getVehicles().get(0).getFrontColumn() + 1).getVehicleType() == VehicleType.empty || goalState.getState().get(2).get(goalState.getVehicles().get(0).getFrontColumn() + 2).getVehicleType() == VehicleType.empty ) {
                goalState = generateStartState(numberOfCars, difficulty);
            }
        }else if(difficulty.equals("Hard")){
            if (goalState.getState().get(3).get(goalState.getVehicles().get(0).getFrontColumn() + 1).getVehicleType() == VehicleType.empty || goalState.getState().get(2).get(goalState.getVehicles().get(0).getFrontColumn() + 1).getVehicleType() == VehicleType.empty || goalState.getState().get(2).get(goalState.getVehicles().get(0).getFrontColumn() + 2).getVehicleType() == VehicleType.empty ) {
                goalState = generateStartState(numberOfCars, difficulty);
            }
        }
        return goalState;
    }

    /**
     * Check if the current board state has the appropriate amount of cars
     * Used for the puzzle generation to check if it is solvable.
     * @param vehicles Vehicle array
     * @return true if even amount of vehicles
     * @return false if odd amount of vehicles
     */
    public boolean checkEven(ArrayList<Vehicle> vehicles){
        int hCount = 0;
        int vCount = 0;
        for(Vehicle item: vehicles){
            if(item.getOrientation().equals("v")){
                vCount++;
            }else{
                hCount++;
            }
        }
        if(Math.abs(vCount - hCount) > 1){
            return false;
        }else{
            return true;
        }
    }

    /**
     * Update the current board state with new vehicles and old vehicles
     * this is used to test if the current state can be solved and moves around
     * the pieces while generating the board
     * @param state Current State of the board
     * @param newVehicle new Vehicle to be added to the board
     * @param oldVehicle old Vehicle to be removed from the board
     */
    public void updateSate(StateNode state, Vehicle newVehicle, Vehicle oldVehicle){

        state.getState().get(oldVehicle.getFrontRow()).set(oldVehicle.getFrontColumn(),emptyVehicle);
        state.getState().get(oldVehicle.getBackRow()).set(oldVehicle.getBackColumn(),emptyVehicle);
        state.getState().get(newVehicle.getFrontRow()).set(newVehicle.getFrontColumn(),newVehicle);
        state.getState().get(newVehicle.getBackRow()).set(newVehicle.getBackColumn(),newVehicle);
        state.getVehicles().get(state.getVehicles().indexOf(newVehicle)).setFrontRow(newVehicle.getFrontRow());
        state.getVehicles().get(state.getVehicles().indexOf(newVehicle)).setFrontColumn(newVehicle.getFrontColumn());
        if(newVehicle.getVehicleType() == VehicleType.truck) {
            state.getState().get(oldVehicle.getMidRow()).set(oldVehicle.getMidColumn(),emptyVehicle);
            state.getState().get(newVehicle.getMidRow()).set(newVehicle.getMidColumn(), newVehicle);
            state.getVehicles().get(state.getVehicles().indexOf(newVehicle)).setMidRow(newVehicle.getMidRow());
            state.getVehicles().get(state.getVehicles().indexOf(newVehicle)).setMidColumn(newVehicle.getMidColumn());
        }

    }

    /**
     * Checks the board state and the given variables to see if the
     * vehicle piece is at the current location.
     * Used to see if pieces are in the correct location
     * @param row Board row position
     * @param column Board column position
     * @param direction direction the piece is facing
     * @param type vehicle type of the piece
     * @return true if all the coordinates are correct
     * @return false if it doesn't match up with the given coordinates
     */
    public boolean checkCoordinates(int row, int column, Direction direction,VehicleType type){
        if(row > 5 || column > 5 || row < 0 || column < 0){

            return false;
        }
        if(endState.get(row).get(column).getVehicleType() != VehicleType.empty){

            return false;
        }

        if(direction == Direction.north){
            int vCount = 0;
            for(int i = 0; i < 6; i++){
                if(endState.get(i).get(column).getDirection() == Direction.north || endState.get(i).get(column).getDirection() == Direction.south){
                    vCount++;
                }
            }
            if(vCount > 4){

                return false;
            }
            if(type == VehicleType.car) {
                if(row > 4){

                    return false;
                }
                if (endState.get(row + 1).get(column).getVehicleType() != VehicleType.empty) {

                    return false;
                }
            }else if(type == VehicleType.truck){
                if(row > 3){

                    return false;
                }
                if((endState.get(row + 1).get(column).getVehicleType() != VehicleType.empty) || (endState.get(row + 2).get(column).getVehicleType() != VehicleType.empty)){

                    return false;
                }
            }
        }
        if(direction == Direction.south){
            int vCount = 0;
            for(int i = 0; i < 6; i++){
                if(endState.get(i).get(column).getDirection() == Direction.north || endState.get(i).get(column).getDirection() == Direction.south){
                    vCount++;
                }
            }
            if(vCount > 4){

                return false;
            }
            if(type == VehicleType.car) {
                if(row < 1){

                    return false;
                }
                if (endState.get(row - 1).get(column).getVehicleType() != VehicleType.empty) {

                    return false;
                }
            }else if(type == VehicleType.truck){
                if(row < 3){

                    return false;
                }
                if((endState.get(row - 1).get(column).getVehicleType() != VehicleType.empty) || (endState.get(row - 2).get(column).getVehicleType() != VehicleType.empty)){

                    return false;
                }
            }
        }
        if(direction == Direction.east){

            if(row == 2){
                return false;
            }

            int hCount = 0;
            for(int i = 0; i < 6; i++){
                if(endState.get(row).get(i).getDirection() == Direction.east || endState.get(row).get(i).getDirection() == Direction.west){
                    hCount++;
                }
            }

            if(hCount > 4){

                return false;
            }
            if(type == VehicleType.car) {
                if(column < 1){

                    return false;
                }
                if (endState.get(row).get(column - 1).getVehicleType() != VehicleType.empty) {

                    return false;
                }
            }else if(type == VehicleType.truck){
                if(column < 3){
                    return false;
                }
                if((endState.get(row).get(column - 1).getVehicleType() != VehicleType.empty) || (endState.get(row).get(column - 2).getVehicleType() != VehicleType.empty)){

                    return false;
                }
            }
        }
        if(direction == Direction.west){

            if(row == 2){
                return false;
            }

            int hCount = 0;
            for(int i = 0; i < 6; i++){
                if(endState.get(row).get(i).getDirection() == Direction.east || endState.get(row).get(i).getDirection() == Direction.west){
                    hCount++;
                }
            }
            if(hCount > 4){

                return false;
            }
            if(type == VehicleType.car) {
                if(column > 4){

                    return false;
                }
                if (endState.get(row).get(column + 1).getVehicleType() != VehicleType.empty) {

                    return false;
                }
            }else if(type == VehicleType.truck){
                if(column > 3){

                    return false;
                }
                if((endState.get(row).get(column + 1).getVehicleType() != VehicleType.empty) || (endState.get(row).get(column + 2).getVehicleType() != VehicleType.empty)){

                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Print the board state in the output.
     * The array is represented as a 3 char string
     * that is used to represent which colour and vehicle
     * it is or if its a empty space.
     * @param state
     */
    public void printState(StateNode state){

        for(ArrayList<Vehicle> row: state.getState()){
            System.out.print("\n");
            for(Vehicle column: row){
                System.out.print(column);
            }
        }
        System.out.print("\n");
    }


}