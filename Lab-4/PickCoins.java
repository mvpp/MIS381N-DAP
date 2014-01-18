import static java.lang.Math.*;

public class PickCoins {

  /**
   * Take an array of nonnegative ints representing the initial state of the
   * pick up coins game, and return the maximum amount of coins the first player
   * can pick up.
   *
   */
  public static int maxVictory(int[] C) {
    //TODO: implement this function
	int[][] cache = new int[C.length][C.length];
    return maxVictory(C, 0, C.length - 1, cache);
  }
  public static int maxVictory(int[] C, int leftIndex, int rightIndex, int cache[][]){
	  if (leftIndex > rightIndex) return 0;
	  if(cache[leftIndex][rightIndex] != 0) return cache[leftIndex][rightIndex];
	  int ifPickleft = C[leftIndex] + Math.min(maxVictory(C, leftIndex + 2, rightIndex, cache), maxVictory(C, leftIndex + 1, rightIndex - 1, cache));
	  int ifPickright = C[rightIndex] + Math.min(maxVictory(C, leftIndex + 1, rightIndex - 1, cache), maxVictory(C, leftIndex, rightIndex - 2, cache));
	  return cache[leftIndex][rightIndex] = Math.max(ifPickleft, ifPickright);
  }

}
