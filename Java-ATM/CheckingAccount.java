import java.util.HashSet;

public class CheckingAccount extends Account {

  /**
   * creates a new checking account
   * @param password    the password to access the account
   * @param accounts    the list of existing accounts
   */
  public CheckingAccount(String password, HashSet<Account> accounts) {
    super(password, accounts);
  }

  /**
   * creates an empty saving account
   */
  public CheckingAccount() {

  }

  /**
   * transfers money from one account to another
   * @param amount    the amount to be transfered
   * @param account   the account to receive the amount
   */
  public void transfer(double amount, Account account) {
    if (amount > this.balance) {
      System.out.println("Saldo insuficiente.");
    } else {
      this.withdraw(amount);
      account.deposit(amount);
      System.out.println("Valor transferido: " + amount);
      System.out.println("Conta origem: " + this.getNumber());
      System.out.println("Conta destino: " + account.getNumber());
      System.out.println("Saldo remanescente: " + this.getBalance());
    }
  }
}