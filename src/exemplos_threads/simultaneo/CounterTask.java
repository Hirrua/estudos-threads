package exemplos_threads.simultaneo;

public class CounterTask implements Runnable {
    private int counter;
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ": " + counter++);
    }
}
