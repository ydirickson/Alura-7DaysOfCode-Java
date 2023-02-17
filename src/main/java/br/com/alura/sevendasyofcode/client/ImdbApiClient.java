package br.com.alura.sevendasyofcode.client;

import java.net.URI;
import java.net.URISyntaxException;

public class ImdbApiClient implements APIClient {

  private static final String IMDB_API_BASE_URL = "https://imdb-api.com/en/API";

  private final String apiKey;

  public ImdbApiClient(String apiKey){
    this.apiKey = apiKey;
  }

  @Override
  public URI buildURI(String path) throws URISyntaxException {
    return new URI(String.format("%s/%s/%s", IMDB_API_BASE_URL, path, apiKey));
  }  
  
}
