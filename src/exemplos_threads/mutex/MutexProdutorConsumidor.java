package exemplos_threads.mutex;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MutexProdutorConsumidor {


    private static final BlockingQueue<Integer> LISTA = new LinkedBlockingQueue<>(5);
    private static boolean produzindo = true;
    private static boolean consumindo = true;
    private static final Lock LOCK = new ReentrantLock();

    public static void main (String args[]) {
        Thread produtor = new Thread(() -> {
            while (true) {
                try {
                    processamento();
                    if (produzindo) {
                        LOCK.lock();
                        int num = new Random().nextInt(100);
                        LISTA.add(num);
                        if (LISTA.size() == 5) {
                            System.out.println("Pausando consumidor");
                            produzindo = false;
                        }
                        if (LISTA.size() == 1) {
                            System.out.println("Iniciando consumidor");
                            consumindo = true;
                        }
                        LOCK.unlock();
                    } else {
                        System.out.println("!!!!!!!!!!!Produtor domindo!!!!!!!!!!");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        Thread consumidor = new Thread(() -> {
            while (true) {
                try {
                    processamento();
                    if (consumindo) {
                        LOCK.lock();
                        System.out.println("Consumindo");
                        Optional<Integer> numero = LISTA.stream().findFirst();
                        numero.ifPresent(LISTA::remove);
                        if (LISTA.size() == 0) {
                            System.out.println("Pausando consumidor");
                            consumindo = false;
                        }
                        if (LISTA.size() == 4) {
                            System.out.println("Iniciando produtor");
                            produzindo = true;
                        }
                        LOCK.unlock();
                    } else {
                        System.out.println("!!!!!!!Consumidor dormindo!!!!!!");
                        }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        produtor.start();
        consumidor.start();
    }

    private static final void processamento() {
        int tmp = new Random().nextInt(10);
        try {
            Thread.sleep(tmp);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

}
