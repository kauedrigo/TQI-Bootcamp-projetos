import java.util.HashSet;

public class SavingAccount extends Account {
  
  /**
   * creates a new saving account
   * @param password    the password to access the account
   * @param accounts    the list of existing accounts
   */
  public SavingAccount(String password, HashSet<Account> accounts) {
    super(password, accounts);
  }

  /**
   * creates an empty saving account
   */
  public SavingAccount() {

  }
}
