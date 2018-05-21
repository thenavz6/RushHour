import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Objects;


public class StateNode implements Serializable{

    private ArrayList<ArrayList<Vehicle>> state;
    private ArrayList<Vehicle> vehicles = new ArrayList<>();
    private StateNode parent;
    private ArrayList<StateNode> children = new ArrayList<>();

    /**
     * Constructor for StateNode
     * @param state Current location of all pieces on board
     * @param parent parent node, i.e. the move before this one
     */
    public StateNode(ArrayList<ArrayList<Vehicle>> state, StateNode parent) {
        this.state = state;
        this.parent = parent;
    }

    /**
     * adds an existing node as a child node to the current node
     * @param childState child node
     */
    public void addChild(StateNode childState){
        this.children.add(childState);
    }

    /**
     * adds a vehicle to the list of existing vehicles
     * @param vehicles vehicle to be added
     */
    public void addVehicles(ArrayList<Vehicle> vehicles){
        this.vehicles.addAll(vehicles);
    }

    /**
     * getter for list of existing vehicles
     * @return list of existing vehicles
     */
    public ArrayList<Vehicle> getVehicles(){
        return this.vehicles;
    }

    /**
     * getter for state
     * @return state
     */
    public ArrayList<ArrayList<Vehicle>> getState() {
        return state;
    }

    /**
     * changes state to new state
     * @param state new state to be changed to
     */
    public void setState(ArrayList<ArrayList<Vehicle>> state) {
        this.state = state;
    }

    /**
     * getter for parent node
     * @return parent node
     */
    public StateNode getParent() {
        return parent;
    }

    /**
     * sets a parent node for the current node
     * @param parent parent node
     */
    public void setParent(StateNode parent) {
        this.parent = parent;
    }

    /**
     *  getter for child nodes
     * @return list of child nodes
     */
    public ArrayList<StateNode> getChildren() {
        return children;
    }

    /**
     * sets child nodes
     * @param children list of child nodes
     */
    public void setChildren(ArrayList<StateNode> children) {
        this.children = children;
    }

    /**
     * creates a full copy of current node
     * @return copy of node
     */
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

    /**
     * compares two StateNodes and returns whether they're equal
     * @param o object being compared to
     * @return boolean true or false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StateNode stateNode = (StateNode) o;
        return Objects.equals(state, stateNode.state);
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
