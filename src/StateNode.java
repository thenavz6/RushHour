import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;


public class StateNode implements Serializable{

    ArrayList<ArrayList<Vehicle>> state;
    StateNode parent;
    ArrayList<StateNode> children = new ArrayList<>();

    public StateNode(ArrayList<ArrayList<Vehicle>> state, StateNode parent) {
        this.state = state;
        this.parent = parent;
    }

    public void addChild(StateNode childState){
        this.children.add(childState);
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
}
