import javax.swing.plaf.nimbus.State;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.lang.Math;

public class StateSearch {

    ArrayList<ArrayList<Vehicle>> endState = new ArrayList<>();
    private ArrayList<Vehicle> vehicles = new ArrayList<>();
    private ArrayList<StateNode> seen = new ArrayList<>();
    Vehicle emptyVehicle = new Vehicle(VehicleType.empty,Colour.empty,-1,-1,Direction.nan,-1);
    ArrayList<StateNode> allNodes = new ArrayList<>();
    private Random generator = new Random();
    Vehicle primaryCar = new Vehicle(VehicleType.car,Colour.red,2,generator.nextInt(2)+1,Direction.east,1);
    int numnodes = 1;

    public void solve(StateNode goalState, StateNode startState){
        for(Vehicle item: goalState.getVehicles()){
            System.out.print(item + " ");
        }

        for(Vehicle item: startState.getVehicles()){
            System.out.print(item + " ");
        }
    }

    public StateNode generateStartState(StateNode endState){
        for(StateNode item: allNodes){
            if(item.getVehicles().get(0).getFrontColumn() < 4 && item.getState().get(2).get(item.getVehicles().get(0).getFrontColumn() + 1).getVehicleType()!=VehicleType.empty && item.getState().get(2).get(item.getVehicles().get(0).getFrontColumn() + 2).getVehicleType()!=VehicleType.empty){
                return item;
            }else if(item.getVehicles().get(0).getFrontColumn() < 4 && item.getState().get(2).get(item.getVehicles().get(0).getFrontColumn() + 1).getVehicleType()!=VehicleType.empty){
                return item;
            }
        }
        return null;
    }

    public ArrayList<Vehicle> neighbours(Vehicle vehicle, StateNode state){
        int vehicleFrontColumn = vehicle.getFrontColumn();
        int vehicleFrontRow = vehicle.getFrontRow();
        int vehicleBackColumn = vehicle.getBackColumn();
        int vehicleBackRow = vehicle.getBackRow();
        ArrayList<Vehicle> neighbours = new ArrayList<>();

        if(vehicle.getDirection() == Direction.north){
            if(vehicleFrontRow - 1 < 0){
                neighbours.add(state.getState().get(vehicleBackRow + 1).get(vehicleFrontColumn));
            }else if(vehicleBackRow + 1 > 5){
                neighbours.add(state.getState().get(vehicleFrontRow - 1).get(vehicleFrontColumn));
            }
            else {
                neighbours.add(state.getState().get(vehicleFrontRow - 1).get(vehicleFrontColumn));
                neighbours.add(state.getState().get(vehicleBackRow + 1).get(vehicleFrontColumn));
            }
        }else if(vehicle.getDirection() == Direction.south) {
            if (vehicleFrontRow + 1 > 5) {
                neighbours.add(state.getState().get(vehicleBackRow - 1).get(vehicleFrontColumn));
            } else if (vehicleBackRow - 1 > 5){
                neighbours.add(state.getState().get(vehicleFrontRow + 1).get(vehicleFrontColumn));
            } else{
                neighbours.add(state.getState().get(vehicleBackRow - 1).get(vehicleFrontColumn));
                neighbours.add(state.getState().get(vehicleFrontRow + 1).get(vehicleFrontColumn));
            }
        }else if(vehicle.getDirection() == Direction.east){

            if(vehicleFrontColumn + 1 > 5){
                neighbours.add(state.getState().get(vehicleBackRow).get(vehicleBackColumn - 1));
            }else if(vehicleBackColumn - 1 < 0){
                neighbours.add(state.getState().get(vehicleFrontRow).get(vehicleFrontColumn + 1));
            }else{
                neighbours.add(state.getState().get(vehicleFrontRow).get(vehicleFrontColumn + 1));
                neighbours.add(state.getState().get(vehicleBackRow).get(vehicleBackColumn - 1));
            }

        }else if(vehicle.getDirection() == Direction.west){
            if(vehicleFrontColumn - 1 < 0){
                neighbours.add(state.getState().get(vehicleBackRow).get(vehicleBackColumn + 1));
            }else if(vehicleBackColumn + 1 > 5){
                neighbours.add(state.getState().get(vehicleFrontRow).get(vehicleFrontColumn - 1));
            }else {
                neighbours.add(state.getState().get(vehicleFrontRow).get(vehicleFrontColumn - 1));
                neighbours.add(state.getState().get(vehicleBackRow).get(vehicleBackColumn + 1));
            }
        }

        for(int i = 0; i < neighbours.size();i++){
            if(neighbours.get(i).getVehicleType() == VehicleType.empty){
                neighbours.remove(i);
            }
        }

        return neighbours;
    }

    public void generateStateSpace(StateNode endState){

        Queue<StateNode> nodes = new ConcurrentLinkedDeque<>();
        allNodes.clear();
        seen.clear();
        int iteration = 1;
        nodes.add(endState);
        Directions directionOld = null;
        Vehicle vehicleOld = null;
        while(!nodes.isEmpty() && iteration < 750) {
            System.out.println("iteration = " + iteration);
            StateNode currentNode = nodes.poll();
            printState(currentNode);
            int moved = 0;

            int random = generator.nextInt(currentNode.getVehicles().size());
            StateNode childState = currentNode.deepCopy();
            Directions direction = Directions.left;
            Vehicle vehicle = childState.getVehicles().get(0);
            ArrayList<Vehicle> neighbours = new ArrayList<>();

            if (checkMove(childState, vehicle, "left")) {
                continue;
            }else{
                neighbours = neighbours(vehicle, childState);
                vehicle = neighbours.get(generator.nextInt(neighbours.size()));
            }

            direction = randomDirection(directionOld,vehicle);
            if(vehicle.getId() == 1){
                direction = Directions.left;
            }

            vehicleOld = vehicle;
            directionOld = direction;


            while (moved < 1) {
                System.out.println("YOU ARE THE CHOSEN VEHICLE " + vehicle.getName());
                System.out.println("YOU ARE THE CHOSEN DIRECTION " + direction.toString());
                System.out.println("YOU ARE THE CHOSEN STATE ");
                printState(childState);
                if(direction == Directions.up) {
                    if (checkMove(childState, vehicle, "up")) {
                        System.out.println("Can Do");
                        move(childState, currentNode, nodes, vehicle, "up");
                        moved++;
                    } else {
                        System.out.println("Cant Move " + vehicle.getName() + "  up 1");
                        neighbours = neighbours(vehicle,childState);
                        vehicle = neighbours.get(generator.nextInt(neighbours.size()));
                        direction = randomDirection(directionOld,vehicle);
                        if(vehicle.getId() == 1){
                            direction = Directions.left;
                        }
                        directionOld = direction;
                        vehicleOld = vehicle;
                    }
                }else if(direction == Directions.right) {
                    if (checkMove(childState, vehicle, "right")) {
                        System.out.println("Can Do");
                        move(childState, currentNode, nodes, vehicle, "right");
                        moved++;
                    } else {
                        System.out.println("Cant Move " + vehicle.getName() + " Right 1");
                        neighbours = neighbours(vehicle,childState);
                        vehicle = neighbours.get(generator.nextInt(neighbours.size()));
                        direction = randomDirection(directionOld,vehicle);
                        if(vehicle.getId() == 1){
                            direction = Directions.left;
                        }
                        directionOld = direction;
                        vehicleOld = vehicle;
                    }
                }else if(direction == Directions.down) {
                    if (checkMove(childState, vehicle, "down")) {
                        System.out.println("Can Do");
                        move(childState, currentNode, nodes, vehicle, "down");
                        moved++;
                    } else {
                        System.out.println("Cant Move " + vehicle.getName() + " Down 1");
                        neighbours = neighbours(vehicle,childState);
                        vehicle = neighbours.get(generator.nextInt(neighbours.size()));
                        direction = randomDirection(directionOld,vehicle);
                        if(vehicle.getId() == 1){
                            direction = Directions.left;
                        }
                        directionOld = direction;
                        vehicleOld = vehicle;
                    }
                }else if(direction == Directions.left) {
                    if (checkMove(childState, vehicle, "left")) {
                        System.out.println("Can Do");
                        move(childState, currentNode, nodes, vehicle, "left");
                        moved++;
                    } else {
                        System.out.println("Cant Move " + vehicle.getName() + " Left 1");
                        neighbours = neighbours(vehicle,childState);
                        vehicle = neighbours.get(generator.nextInt(neighbours.size()));
                        direction = randomDirection(directionOld,vehicle);
                        if(vehicle.getId() == 1){
                            direction = Directions.left;
                        }
                        directionOld = direction;
                        vehicleOld = vehicle;
                    }
                }
            }
            iteration++;
            seen.add(currentNode);
        }
        System.out.println("StateSpace Generated");
    }

    public Directions randomDirection(Directions oldDirection, Vehicle vehicle){
        Directions direction;
        if(vehicle.getOrientation().equals("v")){
            direction = Directions.values()[generator.nextInt(Directions.values().length - 2)];
            while(direction == oldDirection) {
                direction = Directions.values()[generator.nextInt(Directions.values().length - 2)];
            }
        }else{
            direction = Directions.values()[generator.nextInt(Directions.values().length - 2) + 2];
            while(direction == oldDirection) {
                direction = Directions.values()[generator.nextInt(Directions.values().length - 2) + 2];
            }
        }
        return direction;
    }

    public Vehicle randomVehicle(StateNode childState, Vehicle vehicleOld){
        Vehicle vehicle = childState.getVehicles().get(generator.nextInt(childState.getVehicles().size()-1));
        while(vehicle.getColour() == Colour.red && vehicle.equals(vehicleOld)){
            vehicle = childState.getVehicles().get(generator.nextInt(childState.getVehicles().size()-1));
        }
        return vehicle;
    }

    public void move(StateNode childState, StateNode currentNode, Queue<StateNode> nodes, Vehicle vehicle, String direction){
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
                System.out.println("VEHICLE ROW BEFORE" + vehicleCopy.getFrontRow());
                System.out.println("VEHICLE COL BEFORE" + vehicleCopy.getFrontColumn());
                vehicleCopy.moveRightOne(childStatetoAdd);
                updateSate(childStatetoAdd, vehicleCopy, memory);
                currentNode.addChild(childStatetoAdd);
                childStatetoAdd.setParent(currentNode);
                nodes.add(childStatetoAdd);
                allNodes.add(childStatetoAdd);
                seen.add(childStatetoAdd);
                System.out.println("YOU ARE THE RESULT OF THIS RIGHT MOVE ");
                System.out.println("VEHICLE ROW AFTER" + vehicleCopy.getFrontRow());
                System.out.println("VEHICLE COL AFTER" + vehicleCopy.getFrontColumn());
                printState(childStatetoAdd);
                numnodes++;
                break;
            case "left":
                System.out.println("VEHICLE ROW BEFORE" + vehicleCopy.getFrontRow());
                System.out.println("VEHICLE COL BEFORE" + vehicleCopy.getFrontColumn());
                vehicleCopy.moveLeftOne(childStatetoAdd);
                updateSate(childStatetoAdd, vehicleCopy, memory);
                currentNode.addChild(childStatetoAdd);
                childStatetoAdd.setParent(currentNode);
                nodes.add(childStatetoAdd);
                allNodes.add(childStatetoAdd);
                seen.add(childStatetoAdd);
                System.out.println("YOU ARE THE RESULT OF LEFT THIS MOVE ");
                System.out.println("VEHICLE ROW AFTER " + vehicleCopy.getFrontRow());
                System.out.println("VEHICLE COL AFTER " + vehicleCopy.getFrontColumn());
                printState(childStatetoAdd);
                numnodes++;
                break;
            case "up":
                System.out.println("VEHICLE ROW BEFORE" + vehicleCopy.getFrontRow());
                System.out.println("VEHICLE COL BEFORE" + vehicleCopy.getFrontColumn());
                vehicleCopy.moveUpOne(childStatetoAdd);
                updateSate(childStatetoAdd, vehicleCopy, memory);
                currentNode.addChild(childStatetoAdd);
                childStatetoAdd.setParent(currentNode);
                nodes.add(childStatetoAdd);
                allNodes.add(childStatetoAdd);
                seen.add(childStatetoAdd);
                System.out.println("YOU ARE THE RESULT OF THIS UP MOVE ");
                System.out.println("VEHICLE ROW AFTER " + vehicleCopy.getFrontRow());
                System.out.println("VEHICLE COL AFTER " + vehicleCopy.getFrontColumn());
                printState(childStatetoAdd);
                numnodes++;
                break;
            case "down":
                System.out.println("VEHICLE ROW BEFORE" + vehicleCopy.getFrontRow());
                System.out.println("VEHICLE COL BEFORE" + vehicleCopy.getFrontColumn());
                vehicleCopy.moveDownOne(childStatetoAdd);
                updateSate(childStatetoAdd, vehicleCopy, memory);
                currentNode.addChild(childStatetoAdd);
                childStatetoAdd.setParent(currentNode);
                nodes.add(childStatetoAdd);
                allNodes.add(childStatetoAdd);
                seen.add(childStatetoAdd);
                System.out.println("YOU ARE THE RESULT OF THIS DOWN MOVE ");
                System.out.println("VEHICLE ROW AFTER " + vehicleCopy.getFrontRow());
                System.out.println("VEHICLE COL AFTER " + vehicleCopy.getFrontColumn());
                printState(childStatetoAdd);
                numnodes++;
                break;
        }
    }

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

    public StateNode generateEndState(int numberOfCars){

        System.out.println("trying to make state here");
        endState.clear();
        vehicles.clear();
        ArrayList<Coordinates> usedCoordinates = new ArrayList<>();
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
        System.out.println("trying to make vehicles here");
        for(int i = vehicles.size(); i < numberOfCars ; i++){
            System.out.println("i is" + i);
            if(i < 6) {
                VehicleType type = VehicleType.values()[generator.nextInt(VehicleType.values().length - 1)];
                Colour colour = Colour.values()[generator.nextInt(Colour.values().length - 2)];
                Direction direction = Direction.values()[generator.nextInt(Direction.values().length - 1)];
                int row = generator.nextInt(6);
                int column = generator.nextInt(6);

                int threshold = 0;
                outterwhile:
                while (!checkCoordinates(row, column, direction, type)) {
                    if(threshold > 100){
                        for(int j = 0; j < 5; j++){
                            for(int k = 0; k < 5; k++){
                                if(endState.get(j).get(k).getVehicleType() == VehicleType.empty && endState.get(j).get(k+1).getVehicleType() == VehicleType.empty){
                                    type = VehicleType.car;
                                    direction = Direction.west;
                                    row = j;
                                    column = k;
                                    break outterwhile;
                                }
                            }
                        }
                    }
                    System.out.println("checking coordinates");
                    if(type == VehicleType.car){
                        if(direction == Direction.north){
                            row = generator.nextInt(5);
                            column = getEmptyColumn(row);
                        }else if(direction == Direction.south){
                            row = generator.nextInt(5) + 1;
                            column = getEmptyColumn(row);
                        }else if(direction == Direction.east){
                            column = generator.nextInt(5) + 1;
                            row = getEmptyRow(column);
                        }
                        else if(direction == Direction.west){
                            column = generator.nextInt(5);
                            row = getEmptyRow(column);
                        }
                    }else{
                        if(direction == Direction.north){
                            row = generator.nextInt(4);
                            column = getEmptyColumn(row);
                        }else if(direction == Direction.south){
                            row = generator.nextInt(4) + 2;
                            column = getEmptyColumn(row);
                        }else if(direction == Direction.east){
                            column = generator.nextInt(4) + 2;
                            row = getEmptyRow(column);
                        }
                        else if(direction == Direction.west){
                            column = generator.nextInt(4);
                            row = getEmptyRow(column);
                        }
                    }
                    threshold++;
                }
                Vehicle newVehicle = new Vehicle(type, colour, row, column, direction,i+1);
                vehicles.add(newVehicle);
                endState.get(newVehicle.getFrontRow()).set(newVehicle.getFrontColumn(), newVehicle);
                endState.get(newVehicle.getBackRow()).set(newVehicle.getBackColumn(), newVehicle);
                if (type == VehicleType.truck) {
                    endState.get(newVehicle.getMidRow()).set(newVehicle.getMidColumn(), newVehicle);
                }
            }else{
                Vehicle newVehicle = backUpGenerator();
                while(newVehicle == null){
                    newVehicle = backUpGenerator();
                }
                newVehicle.setId(i+1);
                vehicles.add(newVehicle);
                 endState.get(newVehicle.getFrontRow()).set(newVehicle.getFrontColumn(), newVehicle);
                endState.get(newVehicle.getBackRow()).set(newVehicle.getBackColumn(), newVehicle);
            }
            System.out.println("Size of vehicle list " + vehicles.size());
        }

        System.out.println("Vehicles Made");
        for(Vehicle item: vehicles){
            System.out.println(item.getId());
        }
        StateNode goalState = new StateNode(endState,null);
        goalState.addVehicles(vehicles);

        if(!checkEven(vehicles)){
            goalState = generateEndState(numberOfCars);
        }
        System.out.println("New Goal state Generated");
        return goalState;
    }

    public Vehicle backUpGenerator(){

        int tryAgain = 1;
        while(tryAgain == 1) {
            tryAgain = 0;
            int j = generator.nextInt(5);
            for (int k = 0; k < endState.get(j).size() - 1; k++) {
                if (endState.get(j).get(k).getVehicleType() == VehicleType.empty && endState.get(j).get(k + 1).getVehicleType() == VehicleType.empty) {
                    VehicleType type = VehicleType.car;
                    Colour colour = Colour.values()[generator.nextInt(Colour.values().length - 2)];
                    Direction direction = Direction.west;
                    int row = j;
                    int column = k;
                    if (!checkCoordinates(row, column, direction, type)) {
                        tryAgain = 1;
                    }else{
                        return new Vehicle(type, colour, row, column, direction, -1);
                    }
                } else if (endState.get(j).get(k).getVehicleType() == VehicleType.empty && endState.get(j + 1).get(k).getVehicleType() == VehicleType.empty) {
                    VehicleType type = VehicleType.car;
                    Colour colour = Colour.values()[generator.nextInt(Colour.values().length - 2)];
                    Direction direction = Direction.north;
                    int row = j;
                    int column = k;
                    if (!checkCoordinates(row, column, direction, type)) {
                        tryAgain = 1;
                    }else{
                        return new Vehicle(type, colour, row, column, direction, -1);
                    }
                }

            }
        }
        return null;
    }

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

    public int getEmptyRow(int column){
        for(int i = 0; i < 6; i++){
                if(endState.get(i).get(column).getVehicleType() == VehicleType.empty){
                    return i;
                }
        }
        return -1;
    }

    public int getEmptyColumn(int row){
        for(int j = 0; j<6; j++){
            if(endState.get(row).get(j).getVehicleType() == VehicleType.empty){
                return j;
            }
        }
        return -1;
    }

    public boolean checkCoordinates(int row, int column, Direction direction,VehicleType type){
        if(row > 5 || column > 5 || row < 0 || column < 0){
            System.out.println("Too big or small");
            return false;
        }
        if(endState.get(row).get(column).getVehicleType() != VehicleType.empty){
            System.out.println("Something here");
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
                System.out.println("Too many verticals");
                return false;
            }
            if(type == VehicleType.car) {
                if(row > 4){
                    System.out.println("Out of bounds");
                    return false;
                }
                if (endState.get(row + 1).get(column).getVehicleType() != VehicleType.empty) {
                    System.out.println("Overlapping");
                    return false;
                }
            }else if(type == VehicleType.truck){
                if(row > 3){
                    System.out.println("Out of bounds");
                    return false;
                }
                if((endState.get(row + 1).get(column).getVehicleType() != VehicleType.empty) || (endState.get(row + 2).get(column).getVehicleType() != VehicleType.empty)){
                    System.out.println("Overlapping");
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
            if(vCount > 3){
                System.out.println("Too many verticals");
                return false;
            }
            if(type == VehicleType.car) {
                if(row < 1){
                    System.out.println("Out of bounds");
                    return false;
                }
                if (endState.get(row - 1).get(column).getVehicleType() != VehicleType.empty) {
                    System.out.println("overlapping");
                    return false;
                }
            }else if(type == VehicleType.truck){
                if(row < 3){
                    System.out.println("Out of bounds");
                    return false;
                }
                if((endState.get(row - 1).get(column).getVehicleType() != VehicleType.empty) || (endState.get(row - 2).get(column).getVehicleType() != VehicleType.empty)){
                    System.out.println("overlapping");
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

            if(hCount > 3){
                System.out.println("Too many horizontals");
                return false;
            }
            if(type == VehicleType.car) {
                if(column < 1){
                    System.out.println("Out of bounds");
                    return false;
                }
                if (endState.get(row).get(column - 1).getVehicleType() != VehicleType.empty) {
                    System.out.println("Overlapping");
                    return false;
                }
            }else if(type == VehicleType.truck){
                if(column < 3){
                    System.out.println("Out of bounds");
                    return false;
                }
                if((endState.get(row).get(column - 1).getVehicleType() != VehicleType.empty) || (endState.get(row).get(column - 2).getVehicleType() != VehicleType.empty)){
                    System.out.println("Overlapping");
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
            if(hCount > 3){
                System.out.println("Too many horizontals");
                return false;
            }
            if(type == VehicleType.car) {
                if(column > 4){
                    System.out.println("Out of bounds");
                    return false;
                }
                if (endState.get(row).get(column + 1).getVehicleType() != VehicleType.empty) {
                    System.out.println("Overlapping");
                    return false;
                }
            }else if(type == VehicleType.truck){
                if(column > 3){
                    System.out.println("Out of bounds");
                    return false;
                }
                if((endState.get(row).get(column + 1).getVehicleType() != VehicleType.empty) || (endState.get(row).get(column + 2).getVehicleType() != VehicleType.empty)){
                    System.out.println("Overlapping");
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