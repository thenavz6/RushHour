import java.util.*;

public class StateSearch {

    ArrayList<ArrayList<Vehicle>> endState = new ArrayList<>();
    private ArrayList<Vehicle> vehicles = new ArrayList<>();
    private Random generator = new Random();


    public StateNode generateVehicles(){

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

        for(int i = vehicles.size(); i < 4; i++){
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

        StateNode endStateNode = new StateNode(endState);


        return endStateNode;
    }

    public boolean checkCoordinates(int row, int column, Direction direction,VehicleType type){

        System.out.println(type + " " + row);
        System.out.println(type + " " + column);


        if(endState.get(row).get(column).getVehicleType() != VehicleType.EMPTY){
            return false;
        }
        if(direction == Direction.NORTH){
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
