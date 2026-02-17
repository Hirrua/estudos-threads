package exemplos_threads.exemplo_completable_future.pokemon;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pokemon {

    private final String name;
    private final HttpClient httpClient = new HttpClient(HttpResponse.BodyHandlers.ofString());

    public Pokemon(String name) {
        this.name = name;
    }

    public String find() {
        try {
            HttpResponse<?> response = httpClient.get("https://pokeapi.co/api/v2/pokemon/" + name);
            String habilidades = extrairHabilidades(response.body().toString());
            return "Habilidades de " + name + ": " + habilidades;
        } catch (IOException | InterruptedException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    private String extrairHabilidades(String body) {
        Pattern pattern = Pattern.compile("\"ability\":\\{\"name\":\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(body);

        StringBuilder habilidades = new StringBuilder();
        while (matcher.find()) {
            if (habilidades.length() > 0) {
                habilidades.append(", ");
            }
            habilidades.append(matcher.group(1));
        }

        if (habilidades.length() == 0) {
            return "nenhuma habilidade encontrada";
        }

        return habilidades.toString();
    }
}
