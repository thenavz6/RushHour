import java.util.*;

public class StateSearch {

    private ArrayList<Vehicle> vehicles = new ArrayList<>();
    private Random generator = new Random();


    public ArrayList<Vehicle> generateVehicles(){

        Vehicle primaryCar = new Vehicle(VehicleType.CAR,Colour.RED,2,5,Direction.EAST);
        System.out.println("Type = " + primaryCar.getVehicleType().toString());
        System.out.println("Direction = " + primaryCar.getDirection().toString());
        System.out.println("FrontRow = " + primaryCar.getFrontRow());
        System.out.println("FrontColumn = " + primaryCar.getFrontColumn());
        System.out.println("BackRow = " + primaryCar.getBackRow());
        System.out.println("BackColumn = " + primaryCar.getBackColumn());

        vehicles.add(primaryCar);

        for(int i = 0; i < 4; i++){
            VehicleType type = VehicleType.values()[generator.nextInt(VehicleType.values().length)];
            Colour colour = Colour.values()[generator.nextInt(Colour.values().length - 2)];
            System.out.println(colour.toString());
            Direction direction = Direction.values()[generator.nextInt(Direction.values().length)];
            int row = generator.nextInt(6);
            int column = generator.nextInt(6);
            column = checkCoordinates(row, column);
            Vehicle newVehicle = new Vehicle(type,colour,row,column,direction);
            vehicles.add(newVehicle);
        }
        return vehicles;
    }

    public StateNode generateRandomEndState(){
        generateVehicles();
        return new StateNode(populateBoard());
    }

    public ArrayList<ArrayList<Vehicle>> populateBoard(){

        ArrayList<ArrayList<Vehicle>> endState = new ArrayList<>();

        for(int i = 0; i < 6; i++) {
            endState.add(i,new ArrayList<>());
        }

        for(ArrayList<Vehicle> row: endState){
            for(int i = 0; i < 6; i++){
                row.add(i,new Vehicle(VehicleType.EMPTY,Colour.EMPTY,-1,-1,Direction.EMPTY));
            }

        }

        for(Vehicle item: vehicles){
            endState.get(item.getFrontRow()).set(item.getFrontColumn(),item);
            endState.get(item.getBackRow()).set(item.getBackColumn(),item);
        }

        for(ArrayList<Vehicle> row: endState){
            for(Vehicle column: row){
                if(column.getVehicleType() == VehicleType.EMPTY){
                    column.setName("**");
                }
            }
        }
        return endState;
    }

    public int checkCoordinates(int row, int column){
        int newColumn;
        for(Vehicle item: vehicles){
            if((item.getFrontRow() == row && item.getFrontColumn() == column) || (item.getBackRow() == row && item.getBackColumn() == column) || row > 6 || column > 6){
                newColumn = generator.nextInt(6);
                checkCoordinates(row,newColumn);
                return newColumn;
            }
        }
        return column;
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
