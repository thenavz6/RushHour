import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

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
        Random generator = new Random();
        int numberOfCars = generator.nextInt(6)+6;
        StateNode startState = state.generateStartState(numberOfCars,Menu.difficulty);

        while(!state.solve(startState)){
            startState = state.generateStartState(numberOfCars,Menu.difficulty);
        }
        state.solve(startState);
        state.printMoves(state.getMovementList());

        /*System.out.println("Start State: ");
        state.printState(startState);
        System.out.println("End State: ");
        state.printState(state.getEndState());*/

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