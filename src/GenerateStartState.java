public class GenerateStartState {

    public static void main(String[] args)
    {
        StateSearch state = new StateSearch();
        //System.out.println("Step1");
        StateNode endState = state.generateEndState(11);
        //System.out.println("Step2");
        StateNode startState;
        //state.generateStateSpace(endState);
        //System.out.println("Step3");
        //startState = state.generateStartState(endState);
        //System.out.println("Step4");

        //while(startState == null){
        //    System.out.println("NULL");
        //    endState = state.generateEndState(9);
       //     state.generateStateSpace(endState);
       //     startState = state.generateStartState(endState);
       // }

        //state.solve(endState,startState);

       // System.out.println("Start State: \n");
       // state.printState(startState);
       // System.out.println("\n");
        System.out.println("End State: ");
        state.printState(endState);

    }

}
