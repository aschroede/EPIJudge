package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
public class NumberOfScoreCombinations {
  @EpiTest(testDataFile = "number_of_score_combinations.tsv")

  // June 25, 2022 Attempt
//  public static int numCombinationsForFinalScore(int finalScore, List<Integer> individualPlayScores) {
//
//    List<List<Integer>> results = new ArrayList<>();
//
//    for(int i=0; i<individualPlayScores.size(); i++){
//      List<Integer> list = new ArrayList<>(Collections.nCopies(finalScore+1,0));
//      results.add(list);
//    }
//
//    for(int i = 0; i<individualPlayScores.size(); i++){
//      for(int j = 0; j<=finalScore; j++){
//
//        if(i==0){
//          if( j % individualPlayScores.get(i) == 0){
//            results.get(i).set(j, 1);
//          }
//        }
//
//        else{
//          int newVal;
//          if(j-individualPlayScores.get(i) >= 0){
//            newVal = results.get(i-1).get(j) + results.get(i).get(j-individualPlayScores.get(i));
//          }
//          else{
//            newVal = results.get(i-1).get(j);
//          }
//          results.get(i).set(j, newVal);
//        }
//      }
//    }
//    return results.get(individualPlayScores.size()-1).get(finalScore);
//  }

  // EPI Solution
  public static int numCombinationsForFinalScore(int finalScore, List<Integer> individualPlayScores) {

    int [][] numbCombosForScore = new int[individualPlayScores.size()][finalScore+1];

    for(int i = 0; i<individualPlayScores.size(); i++){
      numbCombosForScore[i][0] = 1;

      for(int j = 1; j<=finalScore; j++){

        // Go up
        int withoutThisPlay = i-1 >=0 ? numbCombosForScore[i-1][j] : 0;

        //Go left
        int withThisPlay = j - individualPlayScores.get(i) >=0 ? numbCombosForScore[i][j-individualPlayScores.get(i)] : 0;

        numbCombosForScore[i][j] = withoutThisPlay + withThisPlay;
      }
    }
    return numbCombosForScore[individualPlayScores.size()-1][finalScore];
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "NumberOfScoreCombinations.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
