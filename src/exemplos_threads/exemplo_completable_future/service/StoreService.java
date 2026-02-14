package exemplos_threads.exemplo_completable_future.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class StoreService {

    public CompletableFuture<Double> getPriceCompletableFuture(String loja) {
        System.out.printf("%s gerando preço", loja);
        return CompletableFuture.supplyAsync(this::priceGenerator);
    }

    public double priceGenerator() {
        System.out.printf("\nTHREAD ATUAl: %s", Thread.currentThread().getName());
        delay();
        return ThreadLocalRandom.current().nextDouble();
    }

    public void delay() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
