package br.com.alura.sevendasyofcode.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public interface APIClient {

  default String getJSON(String path){
    URI uri = null;
    try {
        System.out.println("Construindo a URI de chamadas da API do IMDB");
        uri = this.buildURI(path);
    } catch (URISyntaxException e) {
        throw new RuntimeException("Erro ao tentar criar a URI de chamada ao IMDB", e);
    }
        System.out.println("Criando cliente e chamada para a API");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

        System.out.println("Executando a chamada");
        return client.sendAsync(request, BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .join();
  }

  URI buildURI(String path) throws URISyntaxException;
  
}
