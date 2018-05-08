package bank;

import client.Client;

public class Operator  implements Runnable {
    private Repository repository;

    public Operator(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void run() {
        Client client;
        while (true){
            try {
                client = repository.queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
            System.out.println("Оператор: " + this.hashCode() + " Обслуживается клиент " + client.toString());

            switch (client.getOperation()) {
                case RETRIEVE:
                    try {
                        repository.retreive(client,client.getTransactionAmount());
                        System.err.println("Клиент" + client.toString() + " снял со счета " + client.getTransactionAmount());
                    } catch (NotEnoughMoneyException e) {
                        System.err.println("Недостаточно средств на счете!");
                    }
                    break;
                case DEPOSIT:
                    repository.deposit(client,client.getTransactionAmount());
                    System.err.println(client.toString() + " положил на счет " + client.getTransactionAmount());

                    break;
            }
            try {
                Thread.sleep(client.getServiceTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
