package br.com.alura.sevendasyofcode;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        if(args.length == 0 || args[0] == null || args[0].isBlank()){
            throw new IllegalArgumentException("O parâmetro para a APIKey é obrigatório");
        }
        String apiKey = args[0];
        URI uri = null;
        try {
            System.out.println("Construindo a URI de chamadas da API do IMDB");
            uri = new URI("https://imdb-api.com/en/API/Top250Movies/"+apiKey);
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
        client.sendAsync(request, BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept((App::imprimirResultado))
                    .join();
    }

    public static void imprimirResultado(String json){
        List<String> resultado = parseJsonLib(json);

        IntStream.range(0, resultado.size())
            .forEach(idx -> {
                System.out.println("Filme "+(idx+1)+": "+resultado.get(idx));
            });
    }

    public static List<String> parseJsonManual(String json){
        List<String> lista = new ArrayList<>();
        return lista;
    }

    public static List<String> parseJsonLib(String json){

        try {
            System.out.println("Começando a parsear o resultado usando o Jackson");
            Map<String, List<Map<String, String>>> parsed = new ObjectMapper()
                .readValue(json, new TypeReference<Map<String, List<Map<String, String>>>>() {});
            return parsed.get("items").stream()
                         .map(item -> item.get("fullTitle"))
                         .collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao traduzir os dados retornados pelo Jackson", e);
        }
    }
}
