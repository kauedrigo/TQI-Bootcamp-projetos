import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Scanner;

public class ATM {
  public static void main(String[] args) throws ClassNotFoundException {
     
    // read accounts files
    // the ATM needs the information to give the user access to account operations or create a new account
    HashSet<Account> accounts = new HashSet<Account>();
    loadAccountList(accounts);

    // creates an empty account to make operations possible
    Account currentAccount = new Account();
    
    // auxiliar string that brings more visual clarity
    String lineSeparator = "-------------------------------------------------------------------------";

    // initial message when the user starts interacting with the ATM
    System.out.println(lineSeparator);
    System.out.println("Bem vindo ao caixa eletrônico do Banco Boasvindas.");
    System.out.println("Escolha uma operação para continuar:");
    System.out.println("1- Acessar conta");
    System.out.println("2- Depositar sem acessar uma conta");
    System.out.println("3- Não possui uma conta? Crie sua conta!");
    System.out.println("0- Sair");

    // operations loop
    while (true) {

      // initialize scanner and ask for the first operation
      Scanner scan = new Scanner(System.in);
      int operation = scan.nextInt();

      // make sure that a valid operation is chosen
      while (operation < 0 || operation > 3) {
        System.out.println(lineSeparator);
        System.out.println("Operação inválida! Escolha dentre as operações abaixo:");
        System.out.println("1- Acessar conta");
        System.out.println("2- Depositar sem acessar uma conta");
        System.out.println("3- Não possui uma conta? Crie sua conta!");
        System.out.println("0- Sair");
        operation = scan.nextInt();
      }

      // variables to be used
      String accNumber = "";
      String accPassword = "";

      switch (operation) {

        
        case 0:   // the user doesn't want do do anything so the aplication ends
        break;

        case 1:   // operations that need account access
          // stats asking for account number and password
          System.out.println(lineSeparator);
          System.out.println("Insira o número da conta: ");
          accNumber = scan.next();
          System.out.println("Insira a senha: ");
          accPassword = scan.next();

          // check if accNumber matches accPassword
          Boolean access = false;
          for (Account acc : accounts) {
            if ((acc.getNumber().equals(accNumber)) && (acc.getPassword().equals(accPassword))) {
              access = true;
              currentAccount = acc;
              break;
            }
          }

          // if accNumber doesn't match accPassword the access is denied and the ATM ends
          if (!access) {
            System.out.println("Acesso negado.");
            break;
          }

          // access loop
          while (access) {

            // operations
            System.out.println(lineSeparator);
            System.out.println("Escolha uma operação para realizar: ");
            System.out.println("1- Consulta de saldo");
            System.out.println("2- Deposito");
            System.out.println("3- Saque");
            System.out.println("4- Transferência");
            System.out.println("5- Alterar senha");
            System.out.println("0- Sair");
            operation = scan.nextInt();
  
            // make sure that a valid operation is chosen
            while (operation < 0 || operation > 5) {
              System.out.println("Operação inválida. Escolha entre as opções abaixo: ");
              System.out.println("1- Consulta de saldo");
              System.out.println("2- Deposito");
              System.out.println("3- Saque");
              System.out.println("4- Transferência");
              System.out.println("5- Alterar senha");
              System.out.println("0- Sair");
              operation = scan.nextInt();
            }
  
            switch (operation) {
              case 0:   // exit
                break;

              case 1:   // balance
                System.out.println(lineSeparator);
                System.out.println("O saldo atual é " + currentAccount.getBalance() + ".");
                break;

              case 2:   // deposit
                double depositAmount = checkDeposit(lineSeparator);
                currentAccount.deposit(depositAmount);
                System.out.println("Valor do depósito: " + depositAmount + "\nNovo saldo: " + currentAccount.getBalance());
                break;

              case 3:   // withdraw
                System.out.println(lineSeparator);
                System.out.println("Qual o valor a ser sacado?");
                double withdrawAmount = Math.abs(Double.parseDouble(scan.next()));
                
                if (currentAccount.withdraw(withdrawAmount)) {
                  System.out.println(lineSeparator);
                  System.out.println("Valor sacado: " + withdrawAmount);
                  System.out.println("Saldo remanescente: " + currentAccount.getBalance());
                }

                break;
              
              case 4:   // transfer
                System.out.println(lineSeparator);

                // only checking account is able to transfer
                if (!(currentAccount instanceof CheckingAccount)) {
                  System.out.println("Operação inválida para este tipo de conta.");
                  break;
                }

                CheckingAccount checkingAcc = (CheckingAccount) currentAccount;
                System.out.println("Insira o valor a ser transferido: ");
                double transferAmount = Math.abs(Double.parseDouble(scan.next()));

                System.out.println(lineSeparator);
                System.out.println("Insira a conta destino da transferência:");
                String transferToAcc = scan.next();

                Boolean transferSuccess = false;
                for (Account account : accounts) {
                  if (account.getNumber().equals(transferToAcc)) {
                    System.out.println(lineSeparator);
                    checkingAcc.transfer(transferAmount, account);
                    modifyAccountFile(account);
                    transferSuccess = true;
                    break;
                  }
                }

                if (!transferSuccess) {
                  System.out.println("Conta inválida. Transferência não realizada.");
                }

                break; 
                
              case 5:   // change password
                System.out.println(lineSeparator);
                System.out.println("Insira a nova senha: ");     
                String newPassword = scan.next();
                currentAccount.setPassword(newPassword);         
                break;

              default:
                break;
            }

            // checks if the user is finished or if there is something else to do
            if (operation == 0) {
              modifyAccountFile(currentAccount);
              break;
            } else {
              System.out.println(lineSeparator);
              System.out.println("Deseja realizar outra operação?");
              System.out.println("1- Sim");
              System.out.println("2- Não");
              operation = scan.nextInt();

              while (operation < 1 || operation > 2) {
                System.out.println(lineSeparator);
                System.out.println("Opção inválida.");
                System.out.println("1- Sim");
                System.out.println("2- Não");
                operation = scan.nextInt();
              }

              if (operation == 1) {
                access = true;
              } else if (operation == 2) {
                modifyAccountFile(currentAccount);
                access = false;
              }
            }
          }

        break;

        case 2:   // deposit without login
          
          // get the amount to deposit
          double depositAmount = checkDeposit(lineSeparator);

          // get the account number to deposit
          System.out.println(lineSeparator);
          System.out.println("Insira o número da conta a ser depositada:");
          accNumber = scan.next();

          // check if the account number is valid
          boolean validAccount = false;
          for (Account acc : accounts) {
            if (acc.getNumber().equals(accNumber)) {
              validAccount = true;
              acc.deposit(depositAmount);
              System.out.println("Valor do depósito: " + depositAmount + "\nConta depositada: " + acc.getNumber());
              modifyAccountFile(acc);
              break;
            }
          }

          // if the account number is invalid the user is asked to try again
          while (!validAccount) {
            System.out.println(lineSeparator);
            System.out.println("Número da conta inválido. Tente novamente:");
            accNumber = scan.next();
            
            // check if the account number is valid
            for (Account acc : accounts) {
              if (acc.getNumber().equalsIgnoreCase(accNumber)) {
                  validAccount = true;
                  acc.deposit(depositAmount);
                  modifyAccountFile(acc);
                  break;
              }
            }
          }
        break;

        case 3:   // creates new accounts

          // the user can chosse account type
          System.out.println(lineSeparator);
          System.out.println("Escolha o tipo de conta a ser criada:");
          System.out.println("1- Poupança");
          System.out.println("2- Conta corrente");
          int accountType = scan.nextInt();

          while (accountType < 1 || accountType > 2) {
            System.out.println(lineSeparator);
            System.out.println("Opção inválida. Escolha dentre as contas abaixo:");
            System.out.println("1- Poupança");
            System.out.println("2- Conta corrente");
            accountType = scan.nextInt();
          }

          // then the user sets a password
          System.out.println(lineSeparator);
          System.out.println("Insira uma senha para criar uma nova conta:");
          String password = scan.next();
          System.out.println(lineSeparator);

          // and the account is created
          if (accountType == 1) {
            new SavingAccount(password, accounts);
          } else if (accountType == 2) {
            new CheckingAccount(password, accounts);
          } else {
            System.out.println(lineSeparator);
            System.out.println("Ocorreu um erro na criação da conta. Tente novamente.");
            break;
          }
          
          System.out.println("Para acessar a conta escolha a opção 1- na página inicial.");
        break;

        default:
        break;
      }

      System.out.println(lineSeparator);
      System.out.println("Obrigado por usar nosso caixa eletrônico. O Banco Boasvindas agradece.");
      System.out.println(lineSeparator);
      break;
    }
  }

  /**
   * loads all the account files into the account list
   * allows creation of unique account numbers
   * allows access to accounts via number and password
   */
  private static void loadAccountList(HashSet<Account> accounts) {

    // get file path
    File folder = new File("./accounts");
    File[] fileList = folder.listFiles();

    // use file path to add accounts to the list
    for (File file : fileList) {
      try {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        accounts.add((Account) ois.readObject());
        ois.close();
      } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
      }
    }

    for (Account acc : accounts) {
      System.out.println(acc.getNumber());
    }
  }

  /**
   * rewrite account file
   * @param account   the account to modify
   */
  private static void modifyAccountFile(Account account) {
    // store the new account data in a file
    try {

      // stores the file path for code clarity
      String filePath = "./accounts/" + account.getNumber() + ".ser";

      // create a new file for new accounts
      File accFile = new File(filePath);
      accFile.delete();
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

  /**
   * get the amount to deposit and check if the value is valid
   * @param lineSeparator   string for formatting purpose
   * @return                the amount to deposit
   */
  private static double checkDeposit(String lineSeparator) {
    Scanner scan = new Scanner(System.in);
    
    // get the amount to deposit
    System.out.println(lineSeparator);
    System.out.println("Qual o valor a ser depositado?");
    double depositAmount = Double.parseDouble(scan.next());

    // check if the amount is valid
    while (depositAmount < 0) {
      System.out.println(lineSeparator);
      System.out.println("Valor inválido. Insira um número positivo:");
      depositAmount = Double.parseDouble(scan.next());
    }

    return depositAmount;
  }
}