import java.util.PriorityQueue;
import java.util.Queue;

public class Levels implements Runnable{

    private Queue<StateNode> q;
    private Thread t;

    Levels (PriorityQueue<StateNode> q){
        this.q = q;
    }
    @Override
    public void run(){
        // generation the puzzles section
        StateSearch state = new StateSearch();
        StateNode endState = state.generateEndState(6);
        StateNode startState;
        state.generateStateSpace(endState);
        startState = state.generateStartState(endState);

        while (startState == null) {
            endState = state.generateEndState(6);
            state.generateStateSpace(endState);
            startState = state.generateStartState(endState);
        }
        // adds it onto the Priority Queue
        // for some reason it does not add onto the queue
        q.add(startState);

    }
    public void start(){
        if (t == null){
            t = new Thread (this);
            t.start();
        }
    }


}
