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

    private synchronized void action(int numberInMethodThread) {
        try {
            int countPrint = 1;

            int counter = 1;
            int compute = 1;

            while (countPrint < 20) {
                if (numberCurrentThread == numberInMethodThread) {
                    this.wait();
                }

                log.info("Val {}", counter);

                if (counter == 10) {
                    compute = -1;
                }
                if (counter == 1) {
                    compute = 1;
                }
                counter = counter + compute;
                numberCurrentThread = numberInMethodThread;

                countPrint++;
                notifyAll();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
