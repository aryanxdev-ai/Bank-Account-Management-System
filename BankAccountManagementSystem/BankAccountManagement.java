import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

// ================= ACCOUNT CLASS =================

class Account {

    private String accountNumber;
    private String accountHolder;
    protected double balance;

    // Constructor
    Account(String accountNumber, String accountHolder, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    // Encapsulation - Getter methods
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    // Deposit
    public void deposit(double amount) {
        if (amount > 0) {
            balance = balance + amount;
        }
    }

    // Withdraw
    public boolean withdraw(double amount) {

        if (amount > 0 && amount <= balance) {
            balance = balance - amount;
            return true;
        }

        return false;
    }

    // Method to be overridden
    public String getAccountType() {
        return "General Account";
    }
}


// ================= SAVINGS ACCOUNT =================

class SavingsAccount extends Account {

    // Constructor
    SavingsAccount(String accountNumber, String accountHolder, double balance) {
        super(accountNumber, accountHolder, balance);
    }

    // Method Overriding
    @Override
    public String getAccountType() {
        return "Savings Account";
    }
}


// ================= CURRENT ACCOUNT =================

class CurrentAccount extends Account {

    // Constructor
    CurrentAccount(String accountNumber, String accountHolder, double balance) {
        super(accountNumber, accountHolder, balance);
    }

    // Method Overriding
    @Override
    public String getAccountType() {
        return "Current Account";
    }
}


// ================= MAIN APPLICATION =================

public class BankAccountManagement extends JFrame {

    // Array to store accounts
    Account[] accounts = new Account[100];

    // Number of accounts
    int accountCount = 0;

    // Input fields
    JTextField accountNumberField;
    JTextField accountHolderField;
    JTextField balanceField;
    JTextField searchField;

    JComboBox<String> accountTypeBox;

    // Table
    DefaultTableModel tableModel;
    JTable accountTable;


    // ================= CONSTRUCTOR =================

    public BankAccountManagement() {

        setTitle("Bank Account Management System");
        setSize(850, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );


        // ================= TITLE =================

        JLabel title = new JLabel(
                "BANK ACCOUNT MANAGEMENT SYSTEM",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 25));

        mainPanel.add(title, BorderLayout.NORTH);


        // ================= INPUT PANEL =================

        JPanel inputPanel = new JPanel(
                new GridLayout(4, 2, 10, 10)
        );

        inputPanel.setBorder(
                BorderFactory.createTitledBorder("Create New Account")
        );


        inputPanel.add(new JLabel("Account Number:"));
        accountNumberField = new JTextField();
        inputPanel.add(accountNumberField);


        inputPanel.add(new JLabel("Account Holder:"));
        accountHolderField = new JTextField();
        inputPanel.add(accountHolderField);


        inputPanel.add(new JLabel("Account Type:"));

        accountTypeBox = new JComboBox<>();

        accountTypeBox.addItem("Savings Account");
        accountTypeBox.addItem("Current Account");

        inputPanel.add(accountTypeBox);


        inputPanel.add(new JLabel("Initial Balance:"));
        balanceField = new JTextField();
        inputPanel.add(balanceField);


        // ================= CREATE BUTTON =================

        JButton createButton = new JButton("CREATE ACCOUNT");

        JPanel createPanel = new JPanel(new BorderLayout(10, 10));

        createPanel.add(inputPanel, BorderLayout.CENTER);
        createPanel.add(createButton, BorderLayout.SOUTH);


        // ================= SEARCH =================

        JPanel searchPanel = new JPanel(
                new BorderLayout(10, 10)
        );

        searchPanel.setBorder(
                BorderFactory.createTitledBorder("Search Account")
        );

        searchField = new JTextField();

        JButton searchButton = new JButton("SEARCH");

        JButton showAllButton = new JButton("SHOW ALL");

        searchPanel.add(
                new JLabel("Account Number:"),
                BorderLayout.WEST
        );

        searchPanel.add(searchField, BorderLayout.CENTER);

        JPanel searchButtons = new JPanel();

        searchButtons.add(searchButton);
        searchButtons.add(showAllButton);

        searchPanel.add(searchButtons, BorderLayout.EAST);


        // ================= TABLE =================

        String[] columns = {
                "Account Number",
                "Account Holder",
                "Account Type",
                "Balance"
        };

        tableModel = new DefaultTableModel(columns, 0);

        accountTable = new JTable(tableModel);

        accountTable.setRowHeight(30);

        JScrollPane scrollPane =
                new JScrollPane(accountTable);

        scrollPane.setBorder(
                BorderFactory.createTitledBorder("Account List")
        );


        // ================= BOTTOM BUTTONS =================

        JButton depositButton = new JButton("DEPOSIT");

        JButton withdrawButton = new JButton("WITHDRAW");

        JButton resetButton = new JButton("RESET");

        JButton exitButton = new JButton("EXIT");


        JPanel bottomPanel = new JPanel(
                new GridLayout(1, 4, 10, 10)
        );

        bottomPanel.add(depositButton);
        bottomPanel.add(withdrawButton);
        bottomPanel.add(resetButton);
        bottomPanel.add(exitButton);


        // ================= CENTER =================

        JPanel centerPanel =
                new JPanel(new BorderLayout(10, 10));

        centerPanel.add(createPanel, BorderLayout.NORTH);

        centerPanel.add(searchPanel, BorderLayout.CENTER);


        JPanel tableAndButtons =
                new JPanel(new BorderLayout(10, 10));

        tableAndButtons.add(
                scrollPane,
                BorderLayout.CENTER
        );

        tableAndButtons.add(
                bottomPanel,
                BorderLayout.SOUTH
        );


        JPanel completeCenter =
                new JPanel(new BorderLayout(10, 10));

        completeCenter.add(
                createPanel,
                BorderLayout.NORTH
        );

        completeCenter.add(
                searchPanel,
                BorderLayout.CENTER
        );

        mainPanel.add(
                completeCenter,
                BorderLayout.NORTH
        );

        mainPanel.add(
                tableAndButtons,
                BorderLayout.CENTER
        );


        add(mainPanel);


        // ================= BUTTON ACTIONS =================

        // Create account
        createButton.addActionListener(e -> createAccount());


        // Search account
        searchButton.addActionListener(e -> searchAccount());


        // Show all accounts
        showAllButton.addActionListener(
                e -> displayAllAccounts()
        );


        // Deposit
        depositButton.addActionListener(
                e -> depositMoney()
        );


        // Withdraw
        withdrawButton.addActionListener(
                e -> withdrawMoney()
        );


        // Reset
        resetButton.addActionListener(e -> resetFields());


        // Exit
        exitButton.addActionListener(e -> {

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Do you want to exit?",
                    "Exit",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });


        setVisible(true);
    }


    // ================= CREATE ACCOUNT =================

    void createAccount() {

        String number =
                accountNumberField.getText();

        String name =
                accountHolderField.getText();

        String balanceText =
                balanceField.getText();

        if (number.isEmpty()
                || name.isEmpty()
                || balanceText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields."
            );

            return;
        }


        // Check if account already exists

        for (int i = 0; i < accountCount; i++) {

            if (accounts[i]
                    .getAccountNumber()
                    .equals(number)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Account number already exists!"
                );

                return;
            }
        }


        try {

            double balance =
                    Double.parseDouble(balanceText);


            if (balance < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Balance cannot be negative."
                );

                return;
            }


            String type =
                    (String) accountTypeBox
                            .getSelectedItem();


            // Inheritance

            if (type.equals("Savings Account")) {

                accounts[accountCount] =
                        new SavingsAccount(
                                number,
                                name,
                                balance
                        );

            } else {

                accounts[accountCount] =
                        new CurrentAccount(
                                number,
                                name,
                                balance
                        );
            }


            accountCount++;


            JOptionPane.showMessageDialog(
                    this,
                    "Account created successfully!"
            );


            displayAllAccounts();

            resetFields();


        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter a valid balance."
            );
        }
    }


    // ================= SHOW ALL ACCOUNTS =================

    void displayAllAccounts() {

        tableModel.setRowCount(0);


        for (int i = 0; i < accountCount; i++) {

            Account a = accounts[i];

            tableModel.addRow(
                    new Object[] {
                            a.getAccountNumber(),
                            a.getAccountHolder(),
                            a.getAccountType(),
                            String.format(
                                    "₹%.2f",
                                    a.getBalance()
                            )
                    }
            );
        }
    }


    // ================= SEARCH ACCOUNT =================

    void searchAccount() {

        String number =
                searchField.getText();


        if (number.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter an account number."
            );

            return;
        }


        tableModel.setRowCount(0);


        boolean found = false;


        for (int i = 0; i < accountCount; i++) {

            Account a = accounts[i];


            if (a.getAccountNumber()
                    .equals(number)) {

                tableModel.addRow(
                        new Object[] {
                                a.getAccountNumber(),
                                a.getAccountHolder(),
                                a.getAccountType(),
                                String.format(
                                        "₹%.2f",
                                        a.getBalance()
                                )
                        }
                );

                found = true;

                break;
            }
        }


        if (!found) {

            JOptionPane.showMessageDialog(
                    this,
                    "Account not found."
            );
        }
    }


    // ================= DEPOSIT =================

    void depositMoney() {

        int row =
                accountTable.getSelectedRow();


        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Select an account from the list."
            );

            return;
        }


        String number =
                tableModel
                        .getValueAt(row, 0)
                        .toString();


        Account selected = null;


        for (int i = 0; i < accountCount; i++) {

            if (accounts[i]
                    .getAccountNumber()
                    .equals(number)) {

                selected = accounts[i];

                break;
            }
        }


        String input =
                JOptionPane.showInputDialog(
                        this,
                        "Enter deposit amount:"
                );


        try {

            double amount =
                    Double.parseDouble(input);


            if (amount <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Enter a valid amount."
                );

                return;
            }


            selected.deposit(amount);


            displayAllAccounts();


            JOptionPane.showMessageDialog(
                    this,
                    "Money deposited successfully!"
            );


        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter a valid amount."
            );
        }
    }


    // ================= WITHDRAW =================

    void withdrawMoney() {

        int row =
                accountTable.getSelectedRow();


        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Select an account from the list."
            );

            return;
        }


        String number =
                tableModel
                        .getValueAt(row, 0)
                        .toString();


        Account selected = null;


        for (int i = 0; i < accountCount; i++) {

            if (accounts[i]
                    .getAccountNumber()
                    .equals(number)) {

                selected = accounts[i];

                break;
            }
        }


        String input =
                JOptionPane.showInputDialog(
                        this,
                        "Enter withdrawal amount:"
                );


        try {

            double amount =
                    Double.parseDouble(input);


            if (selected.withdraw(amount)) {

                displayAllAccounts();


                JOptionPane.showMessageDialog(
                        this,
                        "Money withdrawn successfully!"
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Insufficient balance or invalid amount."
                );
            }


        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter a valid amount."
            );
        }
    }


    // ================= RESET =================

    void resetFields() {

        accountNumberField.setText("");

        accountHolderField.setText("");

        balanceField.setText("");

        searchField.setText("");
    }


    // ================= MAIN =================

       // ================= MAIN =================

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPage());
    }
}


// ================= LOGIN PAGE =================

class LoginPage extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    LoginPage() {

        setTitle("Bank Login");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("MY BANK");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setBounds(135, 30, 200, 40);
        panel.add(title);

        JLabel subtitle = new JLabel("Bank Account Management");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setBounds(125, 70, 220, 25);
        panel.add(subtitle);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameLabel.setBounds(70, 120, 100, 25);
        panel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(70, 150, 300, 35);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setBounds(70, 195, 100, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(70, 225, 300, 35);
        panel.add(passwordField);

        JButton loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBounds(70, 285, 300, 40);
        panel.add(loginButton);

        loginButton.addActionListener(e -> login());

        passwordField.addActionListener(e -> login());

        add(panel);

        setVisible(true);
    }


    // ================= LOGIN METHOD =================

    private void login() {

        String username = usernameField.getText();

        String password =
                new String(passwordField.getPassword());

        if (username.equals("admin")
                && password.equals("1234")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Login Successful!"
            );

            dispose();

            new BankAccountManagement();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Username or Password!",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            passwordField.setText("");
        }
    }
}