package exemplos_threads.exemplo_completable_future.pokemon;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClient {

    private final java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
    private final HttpResponse.BodyHandler<?> responseHandler;

    public HttpClient(HttpResponse.BodyHandler<?> responseHandler) {
        this.responseHandler = responseHandler;
    }

    public HttpResponse <?> get(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        return client.send(request, responseHandler);
    }
}
