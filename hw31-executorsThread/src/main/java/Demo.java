import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Demo {
    private final Logger log = LoggerFactory.getLogger(Demo.class);
    private int numberCurrentThread = 2;

    public static void main(String[] args) {
        Demo demo = new Demo();
        new Thread(() -> demo.action(1)).start();
        new Thread(() -> demo.action(2)).start();
    }

    private synchronized void action(int numberThread) {
        try {
            int countPrint = 1;

            int counter = 1;
            int compute = 1;

            while (countPrint < 20) {
                while (numberCurrentThread == numberThread) {
                    this.wait();
                }
                log.info("Res {}", counter);

                if (counter == 10) {
                    compute = -1;
                }

                counter = counter + compute;
                numberCurrentThread = numberThread;

                countPrint++;
                notifyAll();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
