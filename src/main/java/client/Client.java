package client;

import bank.BankOperation;

import java.util.Objects;

public class Client {
    private static int defaultId = 0;
    private int id;
    private long transactionAmount;                      // сумма денег, который оперирует клиент
    private int serviceTime;                        // время, необходимое на обслуживание
    private BankOperation operation;  // тип оперции с деньгами

    public Client(long transactionAmount, int serviceTime, BankOperation operation) {
        this.id = defaultId++;
        this.transactionAmount = transactionAmount;
        this.serviceTime = serviceTime;
        this.operation = operation;
    }

    public Client() {
        this.id = defaultId++;
    }

    private int randomWithRange(int min, int max)
    {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public long getTransactionAmount() {
        return transactionAmount;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public BankOperation getOperation() {
        return operation;
    }

    public void setTransactionAmount(long transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public void setOperation(BankOperation operation) {
        this.operation = operation;
    }
}
