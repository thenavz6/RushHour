public class GenerateStartState {

    public static void main(String[] args)
    {
        StateSearch state = new StateSearch();
        StateNode startState = state.generateStartState(11);

        while(!state.solve(startState)){
            startState = state.generateStartState(11);
        }
        state.solve(startState);
        state.printMoves(state.getMovementList());

        System.out.println("Start State: ");
        state.printState(startState);
        System.out.println("End State: ");
        state.printState(state.getMovementList().firstElement());


    }

}
