package exemplos_threads.syncronized;

public class CounterTask implements Runnable {
    private int counter;
    @Override
    public void run() {
        synchronized (this) { // um thread por vez vai entrar no bloco, porém não garante ordem de execução
            System.out.println(Thread.currentThread().getName() + ": " + counter++);
        }
    }
}
