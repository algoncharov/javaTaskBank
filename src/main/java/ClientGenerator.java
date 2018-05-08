import bank.BankOperation;
import bank.Repository;
import client.Client;

import java.util.ArrayList;

public class ClientGenerator implements Runnable {
    private final int MAX_MONEY = 1000000;              // максимальное число денег, с которыми может оперировать клиент
    private final int MAX_SERVICE_TIME = 10000;        // максимальное вермя обслуживания
    private final int MAX_GENERATE_INTERVAL = 2000;    // максимальный интервал генерации нового клиента
    private final int CLIENTS_IN_POOL = 20;    // максимальный интервал генерации нового клиента
    private Repository repository;

    private Client[] clientPool;
    public ClientGenerator(Repository repository) {
        this.repository = repository;
        clientPool = new Client[CLIENTS_IN_POOL];
    }

    @Override
    public void run() {
        for (int i = 0; i < CLIENTS_IN_POOL; i++) {
            Client client = new Client();
            genClientTransaction(client);
            clientPool[i] = client;
        }
        int currentClient = 0;
        while (true) {
            Client client = clientPool[currentClient];
            genClientTransaction(client);
            repository.processClient(client);
            try {
                Thread.sleep((long) randomWithRange(0,MAX_GENERATE_INTERVAL));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            currentClient++;
            currentClient %= CLIENTS_IN_POOL;
        }
    }

    private void genClientTransaction(Client client) {
        client.setTransactionAmount(randomWithRange(0, MAX_MONEY));
        client.setServiceTime(randomWithRange(0, MAX_SERVICE_TIME));
        client.setOperation(Math.random() > 0.5 ? BankOperation.DEPOSIT : BankOperation.RETRIEVE);
    }

    private int randomWithRange(int min, int max)
    {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }
}
