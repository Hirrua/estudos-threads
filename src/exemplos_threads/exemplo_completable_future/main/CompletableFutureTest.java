package exemplos_threads.exemplo_completable_future.main;

import exemplos_threads.exemplo_completable_future.service.StoreService;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureTest {

    public static void main (String args[]) {
        var store = new StoreService();
        getPrice(store);
    }

    public static void getPrice(StoreService storeService){
        long start = System.currentTimeMillis();
        CompletableFuture<Double> store1 = storeService.getPriceCompletableFuture("Loja A");
        CompletableFuture<Double> store2 = storeService.getPriceCompletableFuture("Loja B");
        CompletableFuture<Double> store3 = storeService.getPriceCompletableFuture("Loja C");
        CompletableFuture<Double> store4 = storeService.getPriceCompletableFuture("Loja D");
        CompletableFuture<Double> store5 = storeService.getPriceCompletableFuture("Loja E");
        long end = System.currentTimeMillis();

        System.out.printf("PREÇO: %d\n", store1.join());
        System.out.printf("PREÇO: %d\n", store2.join());
        System.out.printf("PREÇO: %d\n", store3.join());
        System.out.printf("PREÇO: %d\n", store4.join());
        System.out.printf("PREÇO: %d\n", store5.join());

        System.out.printf("\n&d\n", (end - start));
    }
}
