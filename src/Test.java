public class Test {

    public static void main(String[] args)
    {
        StateSearch state = new StateSearch();
        StateNode endState = state.generateEndState(6);
        state.printState(endState);
        state.generateStateSpace(endState);
    }

}
