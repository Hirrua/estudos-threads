package exemplos_threads.race_condition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class ProdutorConsumidor {

    private static final List<Integer> LISTA = new ArrayList<Integer>(5);
    private static boolean produzindo = true;
    private static boolean consumindo = true;

    Thread produtor = new Thread(() -> {
        while (true) {
            try {
                processamento();
                if (produzindo) {
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
                    else {
                        System.out.println("!!!!!!!!!!!Produtor domindo!!!!!!!!!!");
                    }
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
                    else {
                        System.out.println("!!!!!!!Consumidor dormindo!!!!!!");
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    });

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
