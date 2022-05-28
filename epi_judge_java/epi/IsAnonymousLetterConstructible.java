package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class IsAnonymousLetterConstructible {
  @EpiTest(testDataFile = "is_anonymous_letter_constructible.tsv")

  // Time complexity: O(n+m)
  // Space complexity (L) where L is the number of distinct characters in the letter and magazine.
  public static boolean isLetterConstructibleFromMagazine(String letterText,
                                                          String magazineText) {
    Map<Character, Integer> letterHashMap = new HashMap<>();
    Map<Character, Integer> magazineHashMap = new HashMap<>();

    for(Character key : letterText.toCharArray())
      letterHashMap.put(key, letterHashMap.containsKey(key) ? letterHashMap.get(key)+1 : 1);

    for(Character key : magazineText.toCharArray())
      magazineHashMap.put(key, magazineHashMap.containsKey(key) ? magazineHashMap.get(key)+1 : 1);

    if(!magazineHashMap.keySet().containsAll(letterHashMap.keySet()))
      return false;

    for(Character key : letterHashMap.keySet()){
        if(letterHashMap.get(key) > magazineHashMap.get(key))
          return false;
      }

    return true;
  }

  // Time complexity: O(n+m)
  // Space complexity: O(L) where L is the number of distinct characters in the letter.
  /*public static boolean isLetterConstructibleFromMagazine(String letterText,
                                                          String magazineText) {

    // O(n)
    Map<Character, Long> charFrequencyForLetter = letterText.chars().mapToObj(c -> (char)c).
            collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    // O(m)
    for(char c : magazineText.toCharArray()){
      if(charFrequencyForLetter.containsKey(c)){
        charFrequencyForLetter.put(c, charFrequencyForLetter.get(c)-1);
        if(charFrequencyForLetter.get(c) == 0L){
          charFrequencyForLetter.remove(c);
          if(charFrequencyForLetter.isEmpty()){
            break;
          }
        }
      }
    }
    return charFrequencyForLetter.isEmpty();
  }*/

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsAnonymousLetterConstructible.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
