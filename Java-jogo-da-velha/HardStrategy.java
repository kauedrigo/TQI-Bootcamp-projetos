import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * the CPU randoms its choice to any avaiable spot,
 * but first checks if the cpu can make a winning move,
 * and if the player is about to win
 */
public class HardStrategy implements CPUStrategy {

  @Override
  public int choosePlay() {

    // checks if the cpu is 1 move away from victory
    int tryWinningMove = HardStrategy.cpuWins();
    if (tryWinningMove != 0) {
      return tryWinningMove;
    }

    // will check if the player is 1 move away from victory
    // but only if cpu can not make a winning move
    int tryBlockMove = HardStrategy.blockPlayer();    
    if (tryBlockMove != 0) {
      return tryBlockMove;
    } 
       
    // random choice if none of the above works
    int rand = new Random().nextInt(9);
    return rand + 1;
  }

  /**
   * checks if the cpu can make a winning move
   * iterates every possible winning result against cpu's play record
   * @return    a move to make cpu win (if possible)
   */
  public static int cpuWins() {

    ArrayList<Integer> cpuCloseToWinning = new ArrayList<Integer>();

    for (List<Integer> list : Results.getList()) {

      // iterates every choice inside cpu's play record
      for (int choice : PlayRecord.getCpuRecord()) {
        if (list.contains(choice)) {
          cpuCloseToWinning.add(choice);

          // if there are 2 matches the cpu has a winning move to make
          if (cpuCloseToWinning.size() >= 2) {

            // the element that is on list but not on cpuCloseToWinning is the missing move
            for (int winningMove : list) {
              if (!cpuCloseToWinning.contains(winningMove)) {
                
                // the winningMove position needs to be empty
                if (!PlayRecord.getPlayerRecord().contains(winningMove)) {
                  return winningMove;
                } else break;
              }
            }

            // get out of the loop and changes list
            break;
          }
        }
      }

      cpuCloseToWinning.clear();
    }

    return 0;
  }

  /**
   * checks if the player is about to win
   * iterates every possible winning result against player's play record
   * @return    a move to block the player's winning move (if necessary)
   */
  public static int blockPlayer() {

    ArrayList<Integer> playerCloseToWinning = new ArrayList<Integer>();

    for (List<Integer> list : Results.getList()) {

      // iterates every choice inside player's play record
      for (int choice : PlayRecord.getPlayerRecord()) {
        if (list.contains(choice)) {
          playerCloseToWinning.add(choice);

          // if there are 2 matches the player is 1 move away from victory
          if (playerCloseToWinning.size() >= 2) {

            // the element that is on list but not on playerCloseToWinning is the missing move
            for (int blockMove : list) {
              if (!playerCloseToWinning.contains(blockMove)) {
                
                // the blockMove position needs to be empty
                if (!PlayRecord.getCpuRecord().contains(blockMove)) {
                  return blockMove;
                } else break;
              }
            }

            // get out of the loop and changes list
            break;
          }
        }
      }
      
      playerCloseToWinning.clear();
    }

    return 0;
  }
  
}
