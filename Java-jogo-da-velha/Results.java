import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Results {
  
  public static ArrayList<List<Integer>> getList() {
    // possible winning results
    List<Integer> line1 = Arrays.asList(1, 2, 3);
    List<Integer> line2 = Arrays.asList(4, 5, 6);
    List<Integer> line3 = Arrays.asList(7, 8, 9);
    List<Integer> row1 = Arrays.asList(1, 4, 7);
    List<Integer> row2 = Arrays.asList(2, 5, 8);
    List<Integer> row3 = Arrays.asList(3, 6, 9);
    List<Integer> cross1 = Arrays.asList(1, 5, 9);
    List<Integer> cross2 = Arrays.asList(3, 5, 7);

    // compile those to a list
    ArrayList<List<Integer>> winningResults = new ArrayList<List<Integer>>();
    Collections.addAll(winningResults, line1, line2, line3, row1, row2, row3, cross1, cross2);
    
    return winningResults;
  }

}
