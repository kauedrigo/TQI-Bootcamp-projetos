import java.util.ArrayList;

/**
 * records player's and cpu's plays
 */
public class PlayRecord {
  
  // choice Record
  static ArrayList<Integer> playerRecord = new ArrayList<Integer>();
  static ArrayList<Integer> cpuRecord = new ArrayList<Integer>();
  
  public static ArrayList<Integer> getPlayerRecord() {
    return playerRecord;
  }

  public static void addPlayerRecord(int playerRec) {
    playerRecord.add(playerRec);
  }

  public static ArrayList<Integer> getCpuRecord() {
    return cpuRecord;
  }

  public static void addCpuRecord(int cpuRec) {
    cpuRecord.add(cpuRec);
  }
  
}