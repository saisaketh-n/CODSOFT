import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class BankAccount {
    private String accountNumber;
    private double balance;

    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited " + amount + " to account " + accountNumber + ". New balance: " + balance);
        } else {
            System.out.println("Invalid amount. Please enter a positive value.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0) {
            if (balance >= amount) {
                balance -= amount;
                System.out.println("Withdrew " + amount + " from account " + accountNumber + ". New balance: " + balance);
            } else {
                System.out.println("Insufficient funds.");
            }
        }
        else {
            System.out.println("Invalid amount. Please enter a positive value.");
        }
    }

    public boolean hasSufficientBalance(double amount) {
        return balance >= amount;
    }
}
class ATM {


        private BankAccount bankAccount;

        public ATM(BankAccount bankAccount) {
            this.bankAccount = bankAccount;
        }

        public double checkBalance() {
            return bankAccount.getBalance();
        }

        public String deposit(double amount) {
            if (amount > 0) {
                bankAccount.deposit(amount);
                return "Deposited " + amount + ". New balance: " + bankAccount.getBalance();
            } else {
                return "Invalid amount. Please enter a positive value.";
            }
        }

        public String withdraw(double amount) {
            if (amount > 0) {
                if (bankAccount.hasSufficientBalance(amount)) {
                    bankAccount.withdraw(amount);
                    return "Withdrew " + amount + ". New balance: " + bankAccount.getBalance();
                } else {
                    return "Insufficient funds.";
                }
            } else {
                return "Invalid amount. Please enter a positive value.";
            }
        }
    }


class LoginGUI {
    private ATMGUI atmGUI;
    private JFrame frame;
    private JTextField accountNumberField;
    private JButton loginButton;

    public LoginGUI(ATMGUI atmGUI) {
        this.atmGUI = atmGUI;
        initializeLoginGUI();
    }
    private void initializeLoginGUI() {
        frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        panel.add(new JLabel("Account Number:"));
        accountNumberField = new JTextField(10);
        panel.add(accountNumberField);

        loginButton = new JButton("Login");
        panel.add(new JLabel());
        panel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String accountNumber = accountNumberField.getText();
                if (accountNumber.equals("12345")) { // Replace with actual account verification logic
                    atmGUI.showATMGUI();
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid account number. Please try again.");
                }
            }
        });

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
class ATMGUI {
    public void showATMGUI() {
        frame.setVisible(true);
    }
    private ATM atm;
    private JFrame frame;
    private JTextField depositField;
    private JTextField withdrawField;
    private JTextArea outputArea;

    public ATMGUI(ATM atm) {
        this.atm = atm;
        initializeGUI();
    }

    private void initializeGUI() {
        frame = new JFrame("ATM Machine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        panel.add(new JLabel("Balance:"));
        JTextField balanceField = new JTextField("0.00");
        balanceField.setEditable(false);
        panel.add(balanceField);

        panel.add(new JLabel("Deposit: "));
        depositField = new JTextField(10);
        panel.add(depositField);

        panel.add(new JLabel("Withdraw: "));
        withdrawField = new JTextField(10);
        panel.add(withdrawField);

        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton exitButton = new JButton("Exit");

        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double amount = Double.parseDouble(depositField.getText());
                outputArea.append(atm.deposit(amount) + "\n");
                balanceField.setText("" + atm.checkBalance());
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double amount = Double.parseDouble(withdrawField.getText());
                outputArea.append(atm.withdraw(amount) + "\n");
                balanceField.setText("" + atm.checkBalance());
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(depositButton);
        buttonPanel.add(withdrawButton);
        buttonPanel.add(exitButton);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    public void createAndShowGUI() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initializeGUI();
                frame.setVisible(true);
            }
        });
    }
}
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                BankAccount bankAccount = new BankAccount("12345", 0.0);
                ATM atm = new ATM(bankAccount);

                ATMGUI atmGUI = new ATMGUI(atm);
                LoginGUI loginGUI = new LoginGUI(atmGUI);
            }
        });
    }
}
