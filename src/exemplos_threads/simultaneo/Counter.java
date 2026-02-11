package exemplos_threads.simultaneo;

public class Counter {

    public static void main(String args[]) {
        CounterTask task = new CounterTask();
        Thread counter1 = new Thread(task);
        Thread counter2 = new Thread(task);
        Thread counter3 = new Thread(task);

        counter1.start();
        counter2.start();
        counter3.start();
    }
}
