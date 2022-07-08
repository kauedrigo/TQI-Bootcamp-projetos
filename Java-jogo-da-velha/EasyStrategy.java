import java.util.Random;

/**
 * the CPU randoms its choice to any avaiable spot
 */
public class EasyStrategy implements CPUStrategy {

  @Override
  public int choosePlay() {
    int rand = new Random().nextInt(9);
    return rand + 1;
  }
  
}
