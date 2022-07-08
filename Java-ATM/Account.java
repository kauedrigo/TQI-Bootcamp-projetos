import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Random;

public class Account implements Serializable {
  
  protected String number;
  protected String password;
  protected double balance;
  // public HashSet<Account> accounts = new HashSet<Account>();

  /**
   * creates a new Account
   * @param password    the password to access the account
   */
  public Account(String password, HashSet<Account> accounts) {
    
    // the account number is generated ramdomly
    String tryNumber = "";
    Random rand = new Random();
    boolean generateNewNumber = false;

    do {
      // generate new account number
      for (int i = 0; i < 6; i++) {
        tryNumber = tryNumber.concat(String.valueOf(rand.nextInt(10)));
      }

      // try the new number againts accounts already in place
      for (Account account : accounts) {
        if (account.number == tryNumber) {
          generateNewNumber = true;
          break;
        }
        generateNewNumber = false;
      }
    } while (generateNewNumber);

    // set the account number after random generation
    this.number = tryNumber;

    // password chosen by user
    this.password = password;

    // after creating the account, it is added to accounts list
    accounts.add(this);

    // store the new account data in a file
    writeAccountFile(this);

    // print information so the user can know the account was created and its number
    System.out.println("Conta criada com sucesso.");
    System.out.println("Número da conta: " + this.number);
    System.out.println("Senha: " + this.password);
  }

  /**
   * empty account
   */
  public Account() {

  }

  /**
   * balance getter
   * @return    the current balance
   */
  public double getBalance() {
    return this.balance;
  }

  /**
   * balance setter
   * @param amount    the amount to add to the balance
   * @return          the current balance
   */
  public void deposit(double amount) {
    this.balance = this.balance + amount;
  }

  /**
   * balance setter
   * @param amount    the amount to remove from the balance
   * @return          the current balance
   */
  public boolean withdraw(double amount) {
    if (amount > balance) {
      System.out.println("Saldo insuficiente.");
      return false;
    } else {
      this.balance = this.balance - amount;
      return true;
    }
  }

  /**
   * permits access to the account number from outside methods
   * @return    the account number
   */
  public String getNumber() {
    return this.number;
  }

  /**
   * password getter to grant access
   * @return    the account password
   */
  public String getPassword() {
    return this.password;
  }

  /**
   * password setter
   * @param password    the new password
   */
  public void setPassword(String password) {
    this.password = password;
    System.out.println("A nova senha é " + this.password);
  }

  /**
   * create or modify account files
   * @param account   the account wich file should be created or modified
   */
  protected static void writeAccountFile(Account account) {
    
    // store the new account data in a file
    try {

      // stores the file path for code clarity
      String filePath = "./accounts/" + account.getNumber() + ".ser";

      // create a new file for new accounts
      File accFile = new File(filePath);
      accFile.createNewFile();

      // writes the currentAccount object into the file
      FileOutputStream fout = new FileOutputStream(filePath);
      ObjectOutputStream oos = new ObjectOutputStream(fout);
      oos.writeObject(account);
      fout.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
