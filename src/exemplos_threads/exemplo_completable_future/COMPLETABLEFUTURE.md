
Implementa a interface CompletionStage que permite adicionar callbacks após a conclusão da tarefa
- Para aguardar o resultado, usamos o método `.get()` que é bloqueante
```java
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Result");
String result = future.get();
```
- Completando um CompletableFuture
```java
CompletableFuture<String> future = new CompletableFuture<>();
future.complete("Completado manualmente");
System.out.println(future.get());
```

- Executar uma tarefa que o resultado não importa `runAsync()` recebendo uma lambda expression
    - O método `fetch()` não me retorna nada, logo preciso usar o `runAsync`
```java
CompletableFuture.runAsync(() -> {
	PokemonFetch pokemonFetch = new PokemonFetch("pikachu");
	pokemonFetch.fetch();
});
```

- Por exemplo, quando quero os dados de uma busca de API e realizar um tratamento logo em seguida, posso utilizar `.supplyAsync`
    - Necessitamos adicionar um método de callback ao final para pegar esse resultado
    - Preciso adicionar um `return` dentro do método do `.supplyAsync`
```java
CompletableFuture.supplyAsync(() -> {
	PokemonFetch pokemonFetch = new PokemonFetch("pikachu");
	String response = pokemonFetch.fetch();
	return response;
}).thenAccept((response) -> {
	System.out.println(response.body);
});
```

- Posso usar o `.thenRun()` que não receber nenhum retorno
```java
CompletableFuture.supplyAsync(() -> {
	PokemonFetch pokemonFetch = new PokemonFetch("pikachu");
	String response = pokemonFetch.fetch();
	return response;
}).thenRun(() -> {
	System.out.println("Busca no banco finalizada"); // log
});

```

- Duas chamadas paralelas `.thenCombine()`, onde recebe 2 parâmetros
    - Primeiro parâmetro: `CompletableFuture.supplyAsync()` para segunda chamada
    - Segundo parâmetro: outro método para combinar os resultado (preciso retornar algo)
```java
.thenCombine(CompletableFuture.supplyAsync(() -> {
	PokemonFetch pokemonFetch = new PokemonFetch("charmander");
	String response = pokemonFetch.fetch();
	return response;
}), (pokemonA, pokemonB) -> {
	return "Pokemons: %s and %s", pokemonA, pokemonB;
}).get();

```
- Código completo
```java
CompletableFuture.supplyAsync(() -> {
	PokemonFetch pokemonFetch = new PokemonFetch("pikachu");
	String response = pokemonFetch.fetch();
	return response;
}).thenCombine(CompletableFuture.supplyAsync(() -> {
	PokemonFetch pokemonFetch = new PokemonFetch("charmander");
	String response = pokemonFetch.fetch();
	return response;
}), (pokemonA, pokemonB) -> {
	return "Pokemons: %s and %s", pokemonA, pokemonB;
}).get();

```

- Para mais de 2 chamadas usamos o `.allOf` (retorna void) que concatena todos os resultados
```java
CompletableFuture resultado = CompletableFuture.allOf(a, b, c, d).get(); 
```
- Disparando a execução de todos os `fetch` de forma paralela e combinando o resultado no final
```java
CompletableFuture a = CompletableFuture.supplyAsync(() -> {
	PokemonFetch pokemonFetch = new PokemonFetch("pikachu");
	String response = pokemonFetch.fetch();
	return response;
});

CompletableFuture b = CompletableFuture.supplyAsync(() -> {
	PokemonFetch pokemonFetch = new PokemonFetch("charmander");
	String response = pokemonFetch.fetch();
	return response;
});

CompletableFuture c = CompletableFuture.supplyAsync(() -> {
	PokemonFetch pokemonFetch = new PokemonFetch("mewtwo");
	String response = pokemonFetch.fetch();
	return response;
});

CompletableFuture d = CompletableFuture.supplyAsync(() -> {
	PokemonFetch pokemonFetch = new PokemonFetch("grounded");
	String response = pokemonFetch.fetch();
	return response;
});

CompletableFuture resultado = CompletableFuture.allOf(a, b, c, d).get(); 
```

- Posso adicionar um `.thenRun()` para exibir os nomes/habilidades
    - O `.get()` dentro do `.thenRun()` é um método bloqueante por definição, porém **não irá bloquear na prática nesse caso**, pois o `.thenRun()` só é executado após a finalização de todos os `CompletableFuture` (via `allOf`), ou seja, os resultados já estão disponíveis e o `.get()` retorna imediatamente.
```java
CompletableFuture resultado = CompletableFuture.allOf(a, b, c, d);

resultado.thenRun(() -> {
	try {
		System.out.println("Pikachu: " + a.get());
		System.out.println("Charmander: " + b.get());
		System.out.println("Mewtwo: " + c.get());
		System.out.println("Grounded: " + d.get());
	} catch (InterruptedException | ExecutionException e) {
		e.printStackTrace();
	}
}).get();
 
```

```
Thread1 → Pikachu -------- done
Thread2 → Charmander ---- done
Thread3 → Mewtwo ------- done
Thread4 → Grounded ----- done

allOf completa

thenRun executa

get() retorna instantâneo
```

- Existe o `.join()` que é boqueante também, porém é unchecked exception
```java
CompletableFuture.allOf(a, b, c, d)
.thenRun(() -> {

    System.out.println("Pikachu: " + a.join());
    System.out.println("Charmander: " + b.join());
    System.out.println("Mewtwo: " + c.join());
    System.out.println("Grounded: " + d.join());

});
```

