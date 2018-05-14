import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.lang.Math;

public class StateSearch {

    ArrayList<ArrayList<Vehicle>> endState = new ArrayList<>();
    private ArrayList<Vehicle> vehicles = new ArrayList<>();
    private ArrayList<StateNode> seen = new ArrayList<>();
    Vehicle emptyVehicle = new Vehicle(VehicleType.EMPTY,Colour.empty,-1,-1,Direction.EMPTY);
    Vehicle primaryCar = new Vehicle(VehicleType.car,Colour.red,2,5,Direction.EAST);
    ArrayList<StateNode> allNodes = new ArrayList<>();
    private Random generator = new Random();
    int numnodes = 1;



    public void solve(StateNode goalState, StateNode startState){
        for(Vehicle item: goalState.getVehicles()){
            System.out.print(item + " ");
        }

        for(Vehicle item: startState.getVehicles()){
            System.out.print(item + " ");
        }
    }

    public StateNode generateStartState(StateNode state){
        for(StateNode item: allNodes){
            if((item.getVehicles().get(0).getFrontColumn() < 3) &&
                    (item.getState().get(2).get(item.getVehicles().get(0).getFrontColumn() + 1).getVehicleType() != VehicleType.EMPTY) /*&&
                    (item.getState().get(2).get(item.getVehicles().get(0).getFrontColumn() + 2).getVehicleType() != VehicleType.EMPTY)*/&&
                    (item.getState().get(2).get(item.getVehicles().get(0).getFrontColumn() + 1).getOrientation().equals("v"))){
                        return item.deepCopy();
            }
        }
        return null;
    }

    public void generateStateSpace(StateNode endState){

        Queue<StateNode> nodes = new ConcurrentLinkedDeque<>();
        int iteration = 1;
        nodes.add(endState);

        while(!nodes.isEmpty() && iteration < 20) {
           //System.out.println("iteration = " + iteration);
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
                        if (vehicle.getDirection() == Direction.NORTH || vehicle.getDirection() == Direction.SOUTH) {
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
                        if (vehicle.getDirection() == Direction.EAST || vehicle.getDirection() == Direction.WEST) {
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
                        if (vehicle.getDirection() == Direction.NORTH || vehicle.getDirection() == Direction.SOUTH) {
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
                        if (vehicle.getDirection() == Direction.EAST || vehicle.getDirection() == Direction.WEST) {
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

        vehicles.add(primaryCar);
        endState.get(primaryCar.getFrontRow()).set(primaryCar.getFrontColumn(),primaryCar);
        endState.get(primaryCar.getBackRow()).set(primaryCar.getBackColumn(),primaryCar);

        for(int i = vehicles.size(); i < numberOfCars ; i++){
            VehicleType type = VehicleType.values()[generator.nextInt(VehicleType.values().length - 1)];
            Colour colour = Colour.values()[generator.nextInt(Colour.values().length - 2)];
            Direction direction = Direction.values()[generator.nextInt(Direction.values().length - 1)];
            int row = generator.nextInt(6);
            int column = generator.nextInt(6);
            while(!checkCoordinates(row,column,direction,type)){
                row = generator.nextInt(6);
                column = generator.nextInt(6);
            }
            Vehicle newVehicle = new Vehicle(type,colour,row,column,direction);
            vehicles.add(newVehicle);
            endState.get(newVehicle.getFrontRow()).set(newVehicle.getFrontColumn(),newVehicle);
            endState.get(newVehicle.getBackRow()).set(newVehicle.getBackColumn(),newVehicle);
            if(type == VehicleType.truck) {
                endState.get(newVehicle.getMidRow()).set(newVehicle.getMidColumn(), newVehicle);
            }
        }

        StateNode goalState = new StateNode(endState,null);
        goalState.addVehicles(vehicles);

        int vertCount = 0;
        int horCount = 0;
        for(Vehicle item: vehicles){
            if(item.getDirection() == Direction.NORTH || item.getDirection() == Direction.SOUTH){
                vertCount++;
            }else{
                horCount++;
            }
        }
        if(Math.abs(horCount-vertCount) > 1){
            generateEndState(numberOfCars);
        }

        return goalState;
    }

    public boolean checkCoordinates(int row, int column, Direction direction,VehicleType type){

        if(endState.get(row).get(column).getVehicleType() != VehicleType.EMPTY){
            return false;
        }
        if(direction == Direction.NORTH){
            int vCount = 0;
            for(int i = 0; i < 6; i++){
                if(endState.get(i).get(column).getDirection() == Direction.NORTH || endState.get(i).get(column).getDirection() == Direction.SOUTH){
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
                if (endState.get(row + 1).get(column).getVehicleType() != VehicleType.EMPTY) {
                    return false;
                }
            }else if(type == VehicleType.truck){
                if(row > 3){
                    return false;
                }
                if((endState.get(row + 1).get(column).getVehicleType() != VehicleType.EMPTY) || (endState.get(row + 2).get(column).getVehicleType() != VehicleType.EMPTY)){
                    return false;
                }
            }
        }
        if(direction == Direction.SOUTH){
            int vCount = 0;
            for(int i = 0; i < 6; i++){
                if(endState.get(i).get(column).getDirection() == Direction.NORTH || endState.get(i).get(column).getDirection() == Direction.SOUTH){
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
                if (endState.get(row - 1).get(column).getVehicleType() != VehicleType.EMPTY) {
                    return false;
                }
            }else if(type == VehicleType.truck){
                if(row < 2){
                    return false;
                }
                if((endState.get(row - 1).get(column).getVehicleType() != VehicleType.EMPTY) || (endState.get(row - 2).get(column).getVehicleType() != VehicleType.EMPTY)){
                    return false;
                }
            }
        }
        if(direction == Direction.EAST){
            int vCount = 0;
            for(int i = 0; i < 6; i++){
                if(endState.get(row).get(i).getDirection() == Direction.EAST || endState.get(row).get(i).getDirection() == Direction.WEST){
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
                if (endState.get(row).get(column - 1).getVehicleType() != VehicleType.EMPTY) {
                    return false;
                }
            }else if(type == VehicleType.truck){
                if(column < 2){
                    return false;
                }
                if((endState.get(row).get(column - 1).getVehicleType() != VehicleType.EMPTY) || (endState.get(row).get(column - 2).getVehicleType() != VehicleType.EMPTY)){
                    return false;
                }
            }
        }
        if(direction == Direction.WEST){
            int vCount = 0;
            for(int i = 0; i < 6; i++){
                if(endState.get(row).get(i).getDirection() == Direction.EAST || endState.get(row).get(i).getDirection() == Direction.WEST){
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
                if (endState.get(row).get(column + 1).getVehicleType() != VehicleType.EMPTY) {
                    return false;
                }
            }else if(type == VehicleType.truck){
                if(column > 3){
                    return false;
                }
                if((endState.get(row).get(column + 1).getVehicleType() != VehicleType.EMPTY) || (endState.get(row).get(column + 2).getVehicleType() != VehicleType.EMPTY)){
                    return false;
                }
            }
        }
        return true;
    }

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
