import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JogoDaVelha {
  public static void main(String[] args) {

    // the player can choose the CPU difficulty
    Scanner scan = new Scanner(System.in);
    CPUStrategy cpuStrategy = chooseDificulty();

    // declare and print an empty game board to start the game
    char[][] gameBoard = {{' ', '|', ' ', '|', ' '},
                          {'-', '+', '-', '+', '-'},
                          {' ', '|', ' ', '|', ' '},
                          {'-', '+', '-', '+', '-'},
                          {' ', '|', ' ', '|', ' '}};
                          
    printBoard(gameBoard);

    // gameplay loop
    while (true) {

      // player's turn
      int playerChoice = 0;

      do {
        System.out.println("Escolha uma posição para jogar (1-9):");
        playerChoice = scan.nextInt();
      } while (PlayRecord.getPlayerRecord().contains(playerChoice) 
              || PlayRecord.getCpuRecord().contains(playerChoice)
              || playerChoice < 1 
              || playerChoice > 9);
      
      PlayRecord.addPlayerRecord(playerChoice);
      changeBoard(gameBoard, playerChoice, "player");

      // did the player win or tie?
      if (checkWinner(PlayRecord.getPlayerRecord())) {
        printBoard(gameBoard);
        System.out.println("Parabéns! Você ganhou!");
        break;
      } else if (PlayRecord.getPlayerRecord().size() + PlayRecord.getCpuRecord().size() >= 9) {
        printBoard(gameBoard);
        System.out.println("Empate!");
        break;
      }
      
      // cpu's turn
      int cpuChoice = 0;

      do {
        cpuChoice = cpuStrategy.choosePlay();
      } while (PlayRecord.getPlayerRecord().contains(cpuChoice) || PlayRecord.getCpuRecord().contains(cpuChoice));
      
      changeBoard(gameBoard, cpuChoice, "cpu");
      PlayRecord.addCpuRecord(cpuChoice);
      printBoard(gameBoard);

      // did the machine beat the player?
      if (checkWinner(PlayRecord.getCpuRecord())) {
        System.out.println("F! A máquina ganhou =/");
        break;
      }
    }
  }

  // prints the board
  public static void printBoard(char[][] board) {
    System.out.println("-------------------------------------");

    for (char[] line : board) {
      for (char elem : line) {
        System.out.print(elem);
      }
        System.out.println();
    }

    System.out.println("-------------------------------------");
  }

  // place the corresponding symbol on the board
  public static void changeBoard(char[][] board, int choice, String whose) {
    
    char symbol = ' ';
    if (whose == "player") {
      symbol = 'X';
    } else if (whose == "cpu") {
      symbol = 'O';
    }

    switch (choice) {
      case 1:
        board[0][0] = symbol;
        break;
      case 2:
        board[0][2] = symbol;
        break;
      case 3:
        board[0][4] = symbol;
        break;
      case 4:
        board[2][0] = symbol;
        break;
      case 5:
        board[2][2] = symbol;
        break;
      case 6:
        board[2][4] = symbol;
        break;
      case 7:
        board[4][0] = symbol;
        break;
      case 8:
        board[4][2] = symbol;
        break;
      case 9:
        board[4][4] = symbol;
        break;  
      default:
        break;
    }
  }

  // check if there is a winner
  public static boolean checkWinner(ArrayList<Integer> choiceHistory) {

    for (List<Integer> list : Results.getList()) {
      if (choiceHistory.containsAll(list)) {
        return true;
      }
    }

    return false;
  }

  public static CPUStrategy chooseDificulty() {

    // auxiliar string
    String lineBreak = "-------------------------------------";

    Scanner scan = new Scanner(System.in);

    System.out.println(lineBreak);
    System.out.println("Escolha a dificuldade do adversário (1-3):");
    System.out.println("1- Fácil");
    System.out.println("2- Média");
    System.out.println("3- Difícil");

    switch (scan.nextInt()) {
      case 1:   // easy
        System.out.println(lineBreak);
        System.out.println("Dificuldade FÁCIL escolhida.");
        return new EasyStrategy();

      case 2:   // normal
        System.out.println(lineBreak);
        System.out.println("Dificuldade MÉDIA escolhida.");
        return new NormalStrategy();

      case 3:   // hard
        System.out.println(lineBreak);
        System.out.println("Dificuldade DIFÍCIL escolhida.");
        return new HardStrategy();
    
      default:
        System.out.println("Erro ao definir dificuldade.");
        System.out.println("O jogo será configurado para a dificuldade padrão.");
        return new NormalStrategy();
    }
  }

}