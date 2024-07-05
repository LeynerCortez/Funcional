package co.com.bancolombia;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

class Transaction {
    private double amount;
    private String type; // "deposit" o "withdrawal"
    private String date;

    public Transaction(double amount, String type, String date) {
        this.amount = amount;
        this.type = type;
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }
}

class BankAccount {
    private List<Transaction> transactions;

    public BankAccount() {
        transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    // TODO 1: Implementar getTotalBalance utilizando streams y reduce
    public Optional<Double> getTotalBalance() {
        return Optional.of(transactions.stream()
                .map(transaction -> transaction.getType().equals("deposit") ? transaction.getAmount() : -transaction.getAmount())
                .reduce(0.0, Double::sum));
    }

    // TODO 2: Implementar getDeposits utilizando streams y filter
    public Optional<List<Transaction>> getDeposits() {
        return Optional.of(transactions.stream()
                .filter(transaction -> transaction.getType().equals("deposit"))
                .collect(Collectors.toList()));
    }

    // TODO 3: Implementar getWithdrawals utilizando streams y filter
    public Optional<List<Transaction>> getWithdrawals() {
        return Optional.of(transactions.stream()
                .filter(transaction -> transaction.getType().equals("withdrawal"))
                .collect(Collectors.toList()));
    }

    // TODO 4: Implementar filterTransactions utilizando Function y streams
    public Optional<List<Transaction>> filterTransactions(Function<Transaction, Boolean> predicate) {
        return Optional.of(transactions.stream()
                .filter(predicate::apply)
                .collect(Collectors.toList()));

    }

    // TODO 5: Implementar getTotalDeposits utilizando getDeposits y mapToDouble
    public Optional<Double> getTotalDeposits() {
        return getDeposits().map(transactions -> transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum());
    }

    // TODO 6: Implementar getLargestWithdrawal utilizando getWithdrawals y max
    public Optional<Transaction> getLargestWithdrawal() {
        return getWithdrawals().map(transactions -> transactions.stream()
                //.max(Comparator.comparingDouble(Transaction::getAmount))
                .max((t1, t2) -> Double.compare(t1.getAmount(), t2.getAmount()))
                .orElse(null));

    }

    // TODO 7: Implementar getTransactionsOnDate utilizando streams y filter
    public Optional<List<Transaction>> getTransactionsOnDate(String date) {
        return Optional.of(transactions.stream()
                .filter(transaction -> transaction.getDate().equals(date))
                .collect(Collectors.toList()));

    }

    // TODO 8: Implementar getAverageTransactionAmount utilizando streams y mapToDouble
    public OptionalDouble getAverageTransactionAmount() {
        return transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .average();

    }

    // TODO 9: Implementar getTransactionsWithAmountGreaterThan utilizando streams y filter
    public Optional<List<Transaction>> getTransactionsWithAmountGreaterThan(double amount) {
        return Optional.of(transactions.stream()
                .filter(transaction -> transaction.getAmount() > amount)
                .collect(Collectors.toList()));

    }

    // TODO 10: Implementar transfer utilizando addTransaction
    public static void transfer(BankAccount sourceAccount, BankAccount targetAccount, double amount) {
        sourceAccount.addTransaction(new Transaction(amount, "withdrawal", "2024-06-05"));
        targetAccount.addTransaction(new Transaction(amount, "deposit", "2024-06-05"));

    }

    // TODO 11: Implementar getTotalWithdrawals utilizando getWithdrawals y mapToDouble
    public Optional<Double> getTotalWithdrawals() {
        return getWithdrawals().map(transactions -> transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum());

    }

    // TODO 12: Implementar getTransactionsSummary utilizando streams, map y collect
    public Map<String, Double> getTransactionsSummary() {
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getType, Collectors.summingDouble(Transaction::getAmount)));

    }

}

public class Main {
    public static void main(String[] args) {
        BankAccount account = new BankAccount();
        account.addTransaction(new Transaction(100, "deposit", "2024-05-13"));
        account.addTransaction(new Transaction(50, "withdrawal", "2024-05-14"));
        account.addTransaction(new Transaction(200, "deposit", "2024-05-15"));
        account.addTransaction(new Transaction(150, "deposit", "2024-05-16"));
        account.addTransaction(new Transaction(75, "withdrawal", "2024-05-17"));

        // Prueba las funcionalidades aquí
        // Puedes comentar estas líneas y pedir a los estudiantes que las descomenten una vez que hayan completado los TODOs

        account.getTotalBalance().ifPresent(balance -> System.out.println("Saldo total: " + balance));
        account.getTotalDeposits().ifPresent(total -> System.out.println("Total de depósitos: " + total));
        account.getLargestWithdrawal().ifPresent(transaction -> System.out.println("Retiro de mayor monto: " + transaction.getAmount()));

        System.out.println("Transacciones del 13 de mayo:");
        account.getTransactionsOnDate("2024-05-13").ifPresent(transactions -> transactions.forEach(transaction -> System.out.println(transaction.getAmount())));
        account.getAverageTransactionAmount().ifPresent(average -> System.out.println("Promedio de montos: " + average));

        System.out.println("Transacciones con monto mayor a 100:");
        account.getTransactionsWithAmountGreaterThan(100).ifPresent(transactions -> transactions.forEach(transaction -> System.out.println(transaction.getAmount())));
        BankAccount targetAccount = new BankAccount();
        BankAccount.transfer(account, targetAccount, 50);
        targetAccount.getTotalBalance().ifPresent(balance -> System.out.println("Saldo total de la cuenta destino: " + balance));
        account.getTotalWithdrawals().ifPresent(total -> System.out.println("Total de retiros: " + total));
        account.getTransactionsSummary().forEach((type, sum) -> System.out.println("Tipo: " + type + ", Total: " + sum));

    }
}