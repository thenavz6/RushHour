import java.util.*;

public class StateSearch {

    ArrayList<ArrayList<Vehicle>> endState = new ArrayList<>();
    private ArrayList<Vehicle> vehicles = new ArrayList<>();
    private Random generator = new Random();



    public void generateStateSpace(StateNode endState){

        Queue<StateNode> nodes = new PriorityQueue<>();
        int iteration = 0;
        nodes.add(endState);

        while(!nodes.isEmpty() || iteration < 10){
            for(Vehicle item: vehicles){
                if(item.getDirection() == Direction.NORTH || item.getDirection() == Direction.SOUTH){
                    for
                }
            }
        }




    }



    public StateNode generateEndState(int numberOfCars){

        for(int i = 0; i < 6; i++) {
            endState.add(i,new ArrayList<>());
        }

        for(ArrayList<Vehicle> row: endState){
            for(int i = 0; i < 6; i++){
                row.add(i,new Vehicle(VehicleType.EMPTY,Colour.EMPTY,-1,-1,Direction.EMPTY));
            }

        }

        Vehicle primaryCar = new Vehicle(VehicleType.CAR,Colour.RED,2,5,Direction.EAST);
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
            if(type == VehicleType.TRUCK) {
                endState.get(newVehicle.getMidRow()).set(newVehicle.getMidColumn(), newVehicle);
            }
        }


        return new StateNode(endState,null);
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
            if(vCount > 3){
                return false;
            }
            if(type == VehicleType.CAR) {
                if(row > 4){
                    return false;
                }
                if (endState.get(row + 1).get(column).getVehicleType() != VehicleType.EMPTY) {
                    return false;
                }
            }else if(type == VehicleType.TRUCK){
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
            if(vCount > 3){
                return false;
            }
            if(type == VehicleType.CAR) {
                if(row < 1){
                    return false;
                }
                if (endState.get(row - 1).get(column).getVehicleType() != VehicleType.EMPTY) {
                    return false;
                }
            }else if(type == VehicleType.TRUCK){
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
            if(vCount > 3){
                return false;
            }
            if(type == VehicleType.CAR) {
                if(column < 1){
                    return false;
                }
                if (endState.get(row).get(column - 1).getVehicleType() != VehicleType.EMPTY) {
                    return false;
                }
            }else if(type == VehicleType.TRUCK){
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
            if(vCount > 3){
                return false;
            }
            if(type == VehicleType.CAR) {
                if(column > 4){
                    return false;
                }
                if (endState.get(row).get(column + 1).getVehicleType() != VehicleType.EMPTY) {
                    return false;
                }
            }else if(type == VehicleType.TRUCK){
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
    }


}
