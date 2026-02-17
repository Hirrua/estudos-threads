package exemplos_threads.exemplo_completable_future.pokemon;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class PokemonCompletableFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture pokemonA = CompletableFuture.supplyAsync(() -> {
            Pokemon pokemon = new Pokemon("pikachu");
            return pokemon.find();
        });

        CompletableFuture pokemonB = CompletableFuture.supplyAsync(() -> {
            Pokemon pokemon = new Pokemon("charmander");
            return pokemon.find();
        });

        CompletableFuture pokemonC = CompletableFuture.supplyAsync(() -> {
            Pokemon pokemon = new Pokemon("bulbasaur");
            return pokemon.find();
        });

        CompletableFuture pokemonD = CompletableFuture.supplyAsync(() -> {
            Pokemon pokemon = new Pokemon("squirtle");
            return pokemon.find();
        });

        CompletableFuture resultado = CompletableFuture.allOf(pokemonA, pokemonB, pokemonC, pokemonD);

        resultado.thenRun(() -> {
            System.out.println(pokemonA.join());
            System.out.println(pokemonB.join());
            System.out.println(pokemonC.join());
            System.out.println(pokemonD.join());
        }).get();
    }
}
