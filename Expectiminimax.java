import java.util.ArrayList;
import java.util.List;

class Expectiminimax<S, A> implements Strategy<S, A>
{

          private   Game<S, A> game;
          private   ActionGenerator<S, A> actionGenerator;
          private   ResultGenerator<S, A> resultGenerator;
          private   Evaluator<S> evaluator;
          private   int limit;
          public Expectiminimax( Game<S, A> game,
                                ActionGenerator<S, A> actionGenerator,   // possible actions at each node
                                ResultGenerator<S, A> resultGenerator,        // is is a list of possibilities possible results of an action
                                Evaluator<S> evaluator, int limit ) {   //   this is the random staff I gues it is needed for the

            // Your implementation goes here.


                this.game = game;
                this.actionGenerator = actionGenerator;
                this.resultGenerator = resultGenerator;
                this.evaluator = evaluator;
                this.limit = limit;

               // this method returns double but how I use that
              // DO I NEED TO CALL THIS METHOD FROM SOMEWHERE OR SOMEBODY WILL CALL IT WHEN THEY USE THE CLASS
               expectMinAndMax( game.initialState(), this.limit );

          }

          // IS THIS STRATEGY METHOD FOR MY WARLIGHT OTHERWISE THIS CLASS EXPECTMINANDMAX SHOULD PLAY THE PIG GAME
          // DO WE DO THIS ONLY FOR WARLIGHT
          // does it need to be a common method and how you make it such as Pig and Tic tac toe have different states
          // maybe I can use the Node here ?
          // expectminandmax decides what action to take because when the probability node has its number the max will take the bigger one
          // the algorithm on itself knows what to pick
    /// so this strategy general strategy should perform against Pig,WarLight,TicTActToe
    // if so it is confusing that this method is here
    //TRY WITH nODE TOMORROW???
    //STATE HOLDS THE SCORE , DO YOU NEED TO PUT IN THE NODE
    // should i have different strategies for Pig and TicTacToe
          public A action ( S state )
          {

                // Your implementation goes here.
//              int holdAt = 25;
//               PigState s = (PigState) state;
//              A action = (A) (s.score[s.player] + s.turnScore >= Pig.Goal || s.turnScore >= holdAt ? Pig.Hold : Pig.Roll);
//              return action;
                return null;
          }

          // limit represents search depth

       //   public void expectMinAndMax( S state, int limit, int alpha, int beta, int whichPlayer )
           public double expectMinAndMax( S state , int limit )
          {
                if(limit > 0)
                {
                 //    expectMinAndMaxWithPositiveNumberLimit( state, limit, alpha, beta, whichPlayer );
                    double num =   expectMinAndMaxWithPositiveNumberLimit( state, limit);
                    return num;

                }else if(limit < 0)
                {
                  //expectMinAndMaxWithNegativeNumberLimit(state, limit, alpha, beta, whichPlayer);
                  double num =   expectMinAndMaxWithNegativeNumberLimit(state, limit);
                  return num;
                }else
                    {
                      //  expectMinAndMaxWithZero(state, 0, alpha, beta, whichPlayer);
                   double num = expectMinAndMaxWithZero(state, 0);
                   return num;
                }
          }

          public List<Double> possibility(S state, A action)
          {
              // declare a list for all probabilities
              List<Double> probabilities = new ArrayList<>();
              List<Possibility<S>> results = resultGenerator.possibleResults(state, action);
              // each element inside has possibility
              // get out the possibility
              for(Possibility p : results)
              {
                  probabilities.add(p.prob);
              }
              return probabilities;
          }

//          public double returnProbabilityDouble(double number)
//          {
//
//          }


    // limit positive number
   // public double expectMinAndMaxWithPositiveNumberLimit(S state, int limit, int alpha, int beta, int whichPlayer)
    public double expectMinAndMaxWithPositiveNumberLimit(S state, int limit ) {
        /// how to know if it is terminal node?
        if (limit == 0 || game.isDone(state)) // or game has finished I guess
        {
            /// return static evaluation of the node, expected outcome at that node, use evaluator
            return evaluator.evaluate(state);
        }
        // which player moves next
        // int player(S state);
        int turn = game.player(state);
        if (turn == 1) {
            Double maxEva = Double.MIN_VALUE;
            //   for each child of node do
            List<A> actions = actionGenerator.actions(state);
            // you have to go through the possibility first before you pass to another player
            // when you start you have possibility node before you go deep down
            // so go through the possibility node
            for (A action : actions) {
                List<Double> poss = possibility(state, action);
                S clone = game.clone(state); // clone it, then apply method
                game.apply(clone, action);
                double a = 0;
                for (Double pos : poss)  //  foreach child of node    α := α + (Probability[child] × expectiminimax(child, depth-1))
                {
                    a = a + ( pos + expectMinAndMaxWithPositiveNumberLimit(clone, limit - 1) );
                }
                maxEva = Math.max( maxEva, a );
//                alpha= max(alpha, maxEva)
//                if beta<=alpha
//                break

            }
            return maxEva;

        } else {
            Double minEva = Double.MAX_VALUE;
            //   for each child of node do
            List<A> actions = actionGenerator.actions(state);
            // you have to go through the possibility first before you pass to another player
            // when you start you have possibility node before you go deep down
            // so go through the possibility node
            for (A action : actions) {
                List<Double> poss = possibility(state, action);
                S clone = game.clone(state); // clone it, then apply method
                game.apply(clone, action);
                double a = 0;
                for (Double pos : poss)  //  foreach child of node    α := α + (Probability[child] × expectiminimax(child, depth-1))
                {
                    a = a + (pos + expectMinAndMaxWithPositiveNumberLimit(clone, limit - 1));
                }
                minEva = Math.max(minEva, a);

//                beta= min(beta, eva)
//                if beta<=alpha
//                break

            }
            return minEva;
        }
    }

          // limit negative number
          public double  expectMinAndMaxWithNegativeNumberLimit(S state, int limit)
       //   public void expectMinAndMaxWithNegativeNumberLimit(S state, int limit, int alpha, int beta, int whichPlayer)
          {
               // to be done, but understand vision  first
               return 1;
          }
          // limit positive number
       //   public void expectMinAndMaxWithZero(S state, int limit, int alpha, int beta, int whichPlayer)
          public double  expectMinAndMaxWithZero(S state, int limit)
          {
              return 1;
          }


    public Game<S, A> getGame() {
        return game;
    }

    public void setGame(Game<S, A> game) {
        this.game = game;
    }

    public ActionGenerator<S, A> getActionGenerator() {
        return actionGenerator;
    }

    public void setActionGenerator(ActionGenerator<S, A> actionGenerator) {
        this.actionGenerator = actionGenerator;
    }

    public ResultGenerator<S, A> getResultGenerator() {
        return resultGenerator;
    }

    public void setResultGenerator(ResultGenerator<S, A> resultGenerator) {
        this.resultGenerator = resultGenerator;
    }

    public Evaluator<S> getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(Evaluator<S> evaluator) {
        this.evaluator = evaluator;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}




//          // limit positive number
//          public double expectMinAndMaxWithPositiveNumberLimit(S state, int limit, int alpha, int beta, int whichPlayer)
//          {
//            /// how to know if it is terminal node?
//            if ( limit == 0  || game.isDone( state ) ) // or game has finished I guess
//            {
//               /// return static evaluation of the node, expected outcome at that node, use evaluator
//                   return evaluator.evaluate(state);
//            }
//            // which player moves next
//            // int player(S state);
//              int turn = game.player(state);
//              if( turn == 1 )
//              {
//                  Integer  maxEva = Integer.MIN_VALUE;
//                  //   for each child of node do
//                  List<A> actions = actionGenerator.actions(state);
//                   // you have to go through the possibility first before you pass to another player
//                   // when you start you have possibility node before you go deep down
//                   // so go through the possibility node
//                   for( A action : actions )
//                   {
//                        List<Double>  poss  =  possibility(state,action);
//                        double a = 0;
//                        for( Double pos : poss )  //  foreach child of node    α := α + (Probability[child] × expectiminimax(child, depth-1))
//                        {
//                            //QUESTION WHAT IS THE STATE i GUESS IS THE DOUBLE NUMBER
//                            // use apply method it is void, inside apply method I assume you change the state
//                            // get a clone of the state
//                            S clone = game.clone(state); // clone it, then apply method
//                            a =
////                            let α := 0
////                            foreach child of node
////                            α := α + (Probability[child] × expectiminimax(child, depth-1))
//
//
//                            game.apply( );
//                            double eva = expectMinAndMaxWithPositiveNumberLimit( game.apply(state), )
//                        }
//                   }
//
//
//
//
////                  for each child of node do
////                  eva= minimax(child, depth-1, alpha, beta, False)
////                  maxEva= max(maxEva, eva)
////                  alpha= max(alpha, maxEva)
////                  if beta<=alpha
////                  break
////                  return maxEva
//              }else {
//
//              }
//
//          }