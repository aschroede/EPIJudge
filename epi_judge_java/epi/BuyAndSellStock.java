package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.Collections;
import java.util.List;
public class BuyAndSellStock {
  @EpiTest(testDataFile = "buy_and_sell_stock.tsv")

  // Example: Stock prices <310, 315, 275, 295, 260, 270, 290, 230, 255, 250>
  // Maximum profit is 30 (290-260)
  // Need to find P1 and P2 where P2 occurs after P1 and where P2-P1 is greatest

  // Divide and Conquer. Time complexity: O(nlgn)
  /*public static double computeMaxProfit(List<Double> prices) {

    // No profit possible if only one data point
    if(prices.size() == 1)
      return 0;

    // Calculate max profit
    double profit = divideAndConquer(prices);

    // If max profit is negative, don't make a trade and return 0 profit
    if (profit < 0)
      return 0;

    // Else return profit
    return profit;

  }*/

  /*public static double divideAndConquer(List<Double> prices){

    double maxProfit;

    // Base case
    if (prices.size() == 1)
      return prices.get(0);

    // Divide
    int q = (int)Math.floor((prices.size()-1)/2);
    List<Double> Left = prices.subList(0, q+1);
    List<Double> Right = prices.subList(q+1, prices.size());

    // Conquer
    double maxLeftProfit = divideAndConquer(Left);
    double maxRightProfit = divideAndConquer(Right);

    // Combine
    double LeftMin = Collections.min(Left);
    double RightMax = Collections.max(Right);
    double crossSubarrayProfit = RightMax - LeftMin;

    // If there is only one item in the left and right subarrays, the only profit is the crossSubArrayProfit
    if(Left.size()==1 && Right.size()==1)
      return crossSubarrayProfit;

    // Else if the left array is 2 and the right array is 1, the max profit is either the crossSubArrayProfit, or the maxLeftProfit
    else if(Left.size() == 2 && Right.size() == 1)
      return Math.max(crossSubarrayProfit, maxLeftProfit);

    // Inverse of the above case
    else if(Right.size() == 2 && Left.size() == 1)
      return Math.max(crossSubarrayProfit, maxRightProfit);

    return Math.max(crossSubarrayProfit, Math.max(maxLeftProfit, maxRightProfit));
  }*/

  // Brute force method O(n^2) time
  /*public static double computeMaxProfit(List<Double> prices) {
    double largest = 0;
    for (int i = 0; i < prices.size(); i++) {
      for (int j = i+1; j < prices.size() ; j++) {
        if(prices.get(j) - prices.get(i) > largest)
          largest = prices.get(j) - prices.get(i);
      }
    }
    return largest;
  }*/

  // Big-brain solution. Time complexity O(n), Space Complexity O(1)
  // The maximum profit that can be made on a specific day is determined by the minimum stock price of the previous days.
  // For solving difficult array problems, usually a good solution can be found by the clever usage of a couple array indexes
  // or additional variables that keep track of minimum or maximum values.
  public static double computeMaxProfit(List<Double> prices) {

    double maximumProfitSoFar = Double.MIN_VALUE;
    double minimumPrice = Double.MAX_VALUE;

    for(int i=0; i< prices.size(); i++){
      if(prices.get(i) < minimumPrice)
        minimumPrice = prices.get(i);
      if(prices.get(i)-minimumPrice > maximumProfitSoFar)
        maximumProfitSoFar = prices.get(i)-minimumPrice;
    }
    return maximumProfitSoFar;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "BuyAndSellStock.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
