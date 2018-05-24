import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.lang.Math;

public class StateSearch {

    private ArrayList<ArrayList<Vehicle>> endState = new ArrayList<>();
    private ArrayList<Vehicle> vehicles = new ArrayList<>();
    private ArrayList<StateNode> seen = new ArrayList<>();
    Vehicle emptyVehicle = new Vehicle(VehicleType.empty,Colour.empty,-1,-1,Direction.empty);
    Vehicle primaryCar = new Vehicle(VehicleType.car,Colour.red,2,5,Direction.east);
    ArrayList<StateNode> allNodes = new ArrayList<>();
    private Random generator = new Random();
    private int numnodes = 1;



    public void solve(StateNode goalState, StateNode startState){
        for(Vehicle item: goalState.getVehicles()){
            System.out.print(item + " ");
        }

        for(Vehicle item: startState.getVehicles()){
            System.out.print(item + " ");
        }
    }

    /**
     * generates a starting state for the game
     * @param state end state //TODO: ???
     * @return startState, or null if unsuccessful
     */
    public StateNode generateStartState(StateNode state){
        for(StateNode item: allNodes){
            if(item.getVehicles().get(0).getFrontColumn() < 4 && item.getState().get(2).get(item.getVehicles().get(0).getFrontColumn() + 1).getVehicleType() != VehicleType.empty){
                return item.deepCopy();
            }
        }
        return null;
    }

    /**
     * Constructor for StateSearch
     * @param endState state of all vehicles for win condition
     */
    public void generateStateSpace(StateNode endState){

        Queue<StateNode> nodes = new ConcurrentLinkedDeque<>();
        int iteration = 1;
        nodes.add(endState);

        while(!nodes.isEmpty() && iteration < 20) {
           // System.out.println("iteration = " + iteration);
            StateNode currentNode = nodes.poll();
            //System.out.println("vehicle = " + item);
            //printState(currentNode);
            for (int o = 0; o < 2; o++) {
                //System.out.println("orientation = " + o);
                StateNode childState = currentNode.deepCopy();
                for (Vehicle vehicle : childState.getVehicles()) {
                    // System.out.println("1 Vehicle");
                    Vehicle memory = vehicle.deepCopy();
                    if (o == 0) {
                        if (vehicle.getDirection() == Direction.north || vehicle.getDirection() == Direction.south) {
                            if (vehicle.moveUpOne(childState)) {
                                updateSate(childState, vehicle, memory);
                                //printState(childState);
                                currentNode.addChild(childState);
                                childState.setParent(currentNode);
                                nodes.add(childState);
                                allNodes.add(childState);
                                numnodes++;

                            }
                        }
                        if (vehicle.getDirection() == Direction.east || vehicle.getDirection() == Direction.west) {
                            if (vehicle.moveRightOne(childState)) {
                                updateSate(childState, vehicle, memory);
                                //printState(childState);
                                currentNode.addChild(childState);
                                childState.setParent(currentNode);
                                nodes.add(childState);
                                allNodes.add(childState);
                                numnodes++;
                            }
                        }
                    }
                    if (o == 1) {
                        if (vehicle.getDirection() == Direction.north || vehicle.getDirection() == Direction.south) {
                            if (vehicle.moveDownOne(childState)) {
                                updateSate(childState, vehicle, memory);
                                //printState(childState);
                                currentNode.addChild(childState);
                                childState.setParent(currentNode);
                                nodes.add(childState);
                                allNodes.add(childState);
                                numnodes++;
                            }
                        }
                        if (vehicle.getDirection() == Direction.east || vehicle.getDirection() == Direction.west) {
                            if (vehicle.moveLeftOne(childState)) {
                                updateSate(childState, vehicle, memory);
                                //printState(childState);
                                currentNode.addChild(childState);
                                childState.setParent(currentNode);
                                nodes.add(childState);
                                allNodes.add(childState);
                                numnodes++;
                            }
                        }
                    }

                }
                //System.out.print("\n");
            }
            iteration++;
            seen.add(currentNode);
        }

    }




    /**
     * TODO: ??????
     * @param state
     * @param newVehicle
     * @param oldVehicle
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
     * generates end positions for all vehicles
     * @param numberOfCars total number of cars on board
     * @return node for end state
     */
    public StateNode generateEndState(int numberOfCars){

        endState = new ArrayList<>();
        vehicles = new ArrayList<>();

        for(int i = 0; i < 6; i++) {
            endState.add(i,new ArrayList<>());
        }

        for(ArrayList<Vehicle> row: endState){
            for(int i = 0; i < 6; i++){
                row.add(i,emptyVehicle);
            }

        }

        addVehicle(primaryCar);

        for(int i = vehicles.size(); i < numberOfCars ; i++){
            Vehicle newVehicle = generateVehicle();
            addVehicle(newVehicle);
        }

        StateNode goalState = new StateNode(endState,null);
        goalState.addVehicles(vehicles);

        int vertCount = 0;
        int horCount = 0;
        for(Vehicle item: vehicles){
            if(item.getDirection() == Direction.north || item.getDirection() == Direction.south){
                vertCount++;
            }else{
                horCount++;
            }
        }
        if(Math.abs(horCount-vertCount) > 1){
            goalState = generateEndState(numberOfCars);
        }

        return goalState;
    }

    /**
     * TODO: ?????
     * @param row
     * @param column
     * @param direction
     * @param type
     * @return
     */
    public boolean checkCoordinates(int row, int column, Direction direction,VehicleType type){

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
            if(vCount > 2){
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
            if(vCount > 2){
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
                if(row < 2){
                    return false;
                }
                if((endState.get(row - 1).get(column).getVehicleType() != VehicleType.empty) || (endState.get(row - 2).get(column).getVehicleType() != VehicleType.empty)){
                    return false;
                }
            }
        }
        if(direction == Direction.east){
            int vCount = 0;
            for(int i = 0; i < 6; i++){
                if(endState.get(row).get(i).getDirection() == Direction.east || endState.get(row).get(i).getDirection() == Direction.west){
                    vCount++;
                }
            }
            if(vCount > 2){
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
                if(column < 2){
                    return false;
                }
                if((endState.get(row).get(column - 1).getVehicleType() != VehicleType.empty) || (endState.get(row).get(column - 2).getVehicleType() != VehicleType.empty)){
                    return false;
                }
            }
        }
        if(direction == Direction.west){
            int vCount = 0;
            for(int i = 0; i < 6; i++){
                if(endState.get(row).get(i).getDirection() == Direction.east || endState.get(row).get(i).getDirection() == Direction.west){
                    vCount++;
                }
            }
            if(vCount > 2){
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
     * prints state to standard output
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

    /**
     * adds a vehicle to node
     * @param newVehicle vehicle to be added
     */
    private void addVehicle(Vehicle newVehicle){
        vehicles.add(newVehicle);
        int frontRow = newVehicle.getFrontRow();
        int frontCol = newVehicle.getFrontColumn();
        int backRow = newVehicle.getBackRow();
        int backCol = newVehicle.getBackColumn();
        if(newVehicle.getVehicleType() == VehicleType.truck) {
            int midRow = newVehicle.getMidRow();
            int midCol = newVehicle.getMidColumn();
            endState.get(midRow).set(midCol, newVehicle);
        }
        endState.get(frontRow).set(frontCol,newVehicle);
        endState.get(backRow).set(backCol,newVehicle);
    }

    /**
     * generates a semi-random vehicle in a valid position
     * @return new vehicle
     */
    private Vehicle generateVehicle(){
        VehicleType type = VehicleType.values()[generator.nextInt(VehicleType.values().length - 1)];
        Colour colour = Colour.values()[generator.nextInt(Colour.values().length - 2)];
        Direction direction = Direction.values()[generator.nextInt(Direction.values().length - 1)];
        int row = generator.nextInt(6);
        int column = generator.nextInt(6);
        while(!checkCoordinates(row,column,direction,type)){
            row = generator.nextInt(6);
            column = generator.nextInt(6);
        }
        return new Vehicle(type,colour,row,column,direction);
    }

}