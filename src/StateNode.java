import java.lang.reflect.Array;
import java.util.ArrayList;

public class StateNode {

    ArrayList<ArrayList<Vehicle>> state = new ArrayList<>();
    StateNode parent;
    ArrayList<StateNode> children;

    public StateNode(ArrayList<ArrayList<Vehicle>> state) {
        this.state = state;
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
}
