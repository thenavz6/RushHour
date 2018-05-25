import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Objects;


public class StateNode implements Serializable{

    ArrayList<ArrayList<Vehicle>> state;
    ArrayList<Vehicle> vehicles = new ArrayList<>();
    StateNode parent;
    ArrayList<StateNode> children = new ArrayList<>();

    public StateNode(ArrayList<ArrayList<Vehicle>> state, StateNode parent) {
        this.state = state;
        this.parent = parent;
    }

    public void addChild(StateNode childState){
        this.children.add(childState);
    }

    public void addVehicles(ArrayList<Vehicle> vehicles){
        this.vehicles.addAll(vehicles);
    }

    public ArrayList<Vehicle> getVehicles(){
        return this.vehicles;
    }

    public ArrayList<ArrayList<Vehicle>> getState() {
        return state;
    }

    public void setState(ArrayList<ArrayList<Vehicle>> state) {
        this.state = state;
    }

    public StateNode getParent() {
        return parent;
    }

    public void setParent(StateNode parent) {
        this.parent = parent;
    }

    public ArrayList<StateNode> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<StateNode> children) {
        this.children = children;
    }

    public StateNode deepCopy(){

        StateNode copy = null;
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
            copy = (StateNode) in.readObject();
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
        StateNode stateNode = (StateNode) o;
        boolean eq = true;
        for(int i = 0; i < stateNode.getState().size();i++){
            for(int j = 0; j < stateNode.getState().get(i).size();j++){
                if(stateNode.getState().get(i).get(j).getName().equals(this.getState().get(i).get(j).getName())){
                    eq = true;
                }else {
                    eq = false;
                    return eq;
                }
            }
        }
        return eq;
    }

    @Override
    public int hashCode() {

        return Objects.hash(state);
    }

    public void printNode(){
        if(this.children == null) {
            printState(this);
            System.out.print("\n");
        }else {
            printState(this);
            System.out.print("\n");
            for (StateNode item : this.children) {
                System.out.println("    ");
                item.printNode();
            }
        }
    }

    public void printState(StateNode state){

        ArrayList<Vehicle> printed = new ArrayList<>();

        for(ArrayList<Vehicle> row: state.getState()){
            for(Vehicle column: row) {
                    printed.add(column);
                    System.out.print(column);
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }
}
