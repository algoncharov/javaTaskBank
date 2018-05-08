package bank;

import client.Client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

public class Repository {
    private final int QUEUE_SIZE = 20;
    private Map<Client,Long> balance;    // баланс счета

    ArrayBlockingQueue<Client> queue;

    public synchronized void deposit(Client client, long amount) {
        if (amount < 0){
            throw new RuntimeException("Tried to deposit a negative amount of money");
        }
        long newBalance = this.balance.getOrDefault(client, (long) 0) + amount;
        this.balance.put(client,newBalance);
    }


    public synchronized void retreive(Client client, long amount) throws NotEnoughMoneyException {
        if (amount < 0){
            throw new RuntimeException("Tried to retreive a negative amount of money");
        }
        if (!this.balance.containsKey(client)) {
            this.balance.put(client, (long) 0);
            throw new NotEnoughMoneyException();
        }
        if (this.balance.get(client) - amount < 0){
            throw new NotEnoughMoneyException();
        }
        long newBalance = this.balance.get(client) - amount;
        this.balance.put(client,newBalance);
    }

    public long getBalance(Client client) {
        return balance.get(client);
    }

    public void processClient(Client client){
        queue.add(client);
    }

    public Repository() {
        balance = new HashMap<Client, Long>();
        queue = new ArrayBlockingQueue<Client>(QUEUE_SIZE);
    }

}
