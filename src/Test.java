public class Test {

    public static void main(String[] args)
    {
        StateSearch state = new StateSearch();
        StateNode endState = state.generateEndState(6);
        StateNode startState;
        state.generateStateSpace(endState);
        startState = state.generateStartState(endState);

        while(startState == null){
            endState = state.generateEndState(6);
            state.generateStateSpace(endState);
            startState = state.generateStartState(endState);
        }

        //state.solve(endState,startState);

        System.out.println("Start State: \n");
        state.printState(startState);
        System.out.println("\n");
        System.out.println("End State: ");
        state.printState(endState);

    }



}
