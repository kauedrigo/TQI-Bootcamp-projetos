import java.util.Random;

/**
 * the CPU randoms its choice to any avaiable spot,
 * but first checks if the cpu can make a winning move
 */
public class NormalStrategy implements CPUStrategy {

  @Override
  public int choosePlay() {
    
    // checks if the cpu is 1 move away from victory
    int tryWinningMove = HardStrategy.cpuWins();
    if (tryWinningMove != 0) {
      return tryWinningMove;
    }
    
    // random choice if cpu can't win right away
    int rand = new Random().nextInt(9);
    return rand + 1;
  }

}