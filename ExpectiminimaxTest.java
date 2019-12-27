public class ExpectiminimaxTest {
    public static boolean testTicTacToe() {
        TicTacToe game = new TicTacToe();

        int Games = 500;

        Strategy<TTState, Integer> emm =
            new Expectiminimax<>(game, new TTActionGenerator(), new TTResultGenerator(),
                                 new TTEvaluator(), 0); 

        int[][] wins = Runner.play2(game, emm, new BasicTicTacToeStrategy(), Games);
        return Runner.report(Games, wins, true, 0.5, .06);
    }

    public static boolean testPig() {
        Pig game = new Pig();

        int Games = 5000;
// I THOUGHT WE ARE BUILDING SOMETHING THAT RUNS PIG SO I AM BULDING ONE SIDE OF SOMETHING THAT SOLVES PIG
// OK SO IT DOES IT IT PLAYS
        //expectminandmax implements and always returns a balance result, and I am trying to  implement a method
        // that plays the game but am I made to create a biased min and max
        Strategy<PigState, Boolean> emm =
            new Expectiminimax<>(game, new PigActionGenerator(), new PigResultGenerator(),
                                 new PigEvaluator(), 1); 

        int[][] wins = Runner.play2(game, emm, new BasicPigStrategy(15), Games);

        return Runner.report(Games, wins, false, 0.548, 0.509);
    }

    public static void main(String[] args) {
        testTicTacToe();
       ///   testPig();
    }
}
