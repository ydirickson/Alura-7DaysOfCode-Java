package br.com.alura.sevendasyofcode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.alura.sevendasyofcode.html.HTMLGenerator;
import br.com.alura.sevendasyofcode.models.Movie;

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
                    .thenAccept((App::exportarHTML))
                    .join();
    }

    public static void exportarHTML(String json){
        List<Movie> resultado = parseJsonManual(json);

        try (FileWriter writer = new FileWriter(new File("imdb_list.html"))){
            HTMLGenerator generator = new HTMLGenerator(writer);
            generator.generate(resultado);
        } catch(IOException e){
            System.err.println("Erro ao processar HTML: "+e.toString());
        }
        
    }

    public static void imprimirResultado(String json){
        List<Movie> resultado = parseJsonLib(json);
        //List<Movie> resultado = parseJsonManual(json);

        IntStream.range(0, resultado.size())
            .forEach(idx -> {
                String numeracao = String.valueOf(idx+1);
                if(idx+1 < 10){
                    numeracao = "00"+numeracao;
                } else if(idx+1 < 100){
                    numeracao = "0"+numeracao;
                }
                
                System.out.println(
                    String.format(
                        "Filme %s - Título: %s URL: %s", 
                        numeracao, 
                        resultado.get(idx).title(),
                        resultado.get(idx).image()
                    )
                );
            });
    }

    public static List<Movie> parseJsonManual(String json){
        List<Movie> lista = new ArrayList<>();


        Pattern patternArray = Pattern.compile("\\[(.*?)\\]");
        Pattern patternObjects = Pattern.compile("\\{(.*?)\\}");
        Matcher matcherArray = patternArray.matcher(json);
        if(matcherArray.find()){
            List<String> filmes = new ArrayList<>();
            String jsonArray = matcherArray.group(1);
            Matcher matcherObjects = patternObjects.matcher(jsonArray);
            while(matcherObjects.find()){
                filmes.add(matcherObjects.group(1));
            }

            for(String filme : filmes){
                String[] atributos = filme.split("\",\"");
                Movie movie = new Movie(
                    atributos[3].split("\":\"")[1],
                    atributos[5].split("\":\"")[1],
                    Double.parseDouble(atributos[7].split("\":\"")[1]),
                    Integer.parseInt(atributos[4].split("\":\"")[1])
                );
                lista.add(movie);
            }
        }
        return lista;
    }

    public static List<Movie> parseJsonLib(String json){
        ObjectMapper mapper = new ObjectMapper();
        List<Movie> lista = new ArrayList<>();
        try {
            System.out.println("Começando a parsear o resultado usando o Jackson");
            JsonNode tree = mapper.readTree(json);
            

            JsonNode items = tree.get("items");
            if(items.isArray()){
                for(JsonNode item : items){
                    lista.add(mapper.convertValue(item, Movie.class));
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao traduzir os dados retornados pelo Jackson", e);
        }
        return lista;
    }
}
