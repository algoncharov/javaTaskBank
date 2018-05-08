import bank.Operator;
import bank.Repository;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Repository repository = new Repository();

        for (int i = 0; i < 5; i++) {
            Operator operator = new Operator(repository);
            (new Thread(operator)).start();
        }

        (new Thread(new ClientGenerator(repository))).start();
    }
}
