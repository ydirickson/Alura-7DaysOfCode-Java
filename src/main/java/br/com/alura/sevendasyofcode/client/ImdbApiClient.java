package br.com.alura.sevendasyofcode.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;

import br.com.alura.sevendasyofcode.models.Movie;
import br.com.alura.sevendasyofcode.parser.ImdbMovieParser;

public class ImdbApiClient {

  private static final String IMDB_API_BASE_URL = "https://imdb-api.com/en/API";

  private static final String IMDB_TOP_250_MOVIES = "Top250Movies";

  private final String apiKey;

  private final ImdbMovieParser parser;

  public ImdbApiClient(String apiKey){
    this.apiKey = apiKey;
    this.parser = new ImdbMovieParser();
  }

  public List<Movie> listarTop250(){
    return this.parser.parseListMovies(
      this.getJSON(IMDB_TOP_250_MOVIES)
    );
  }

  private String getJSON(String path){
    URI uri = null;
    try {
        System.out.println("Construindo a URI de chamadas da API do IMDB");
        uri = new URI(String.format("%s/%s/%s", IMDB_API_BASE_URL, path, apiKey));
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


  
  
}
