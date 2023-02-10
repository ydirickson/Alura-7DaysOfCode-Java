package br.com.alura.sevendasyofcode;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
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
                    //.thenAccept((App::imprimirResultado))
                    .thenAccept(App::exportarHTML)
                    .join();
    }

    public static void exportarHTML(String json){
        List<Map<String,String>> resultado = parseJsonManual(json);
    }

    public static void imprimirResultado(String json){
        //List<Map<String,String>> resultado = parseJsonLib(json);
        List<Map<String,String>> resultado = parseJsonManual(json);

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
                        resultado.get(idx).get("fullTitle"),
                        resultado.get(idx).get("image")
                    )
                );
            });
    }

    public static List<Map<String,String>> parseJsonManual(String json){
        List<Map<String,String>> lista = new ArrayList<>();


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
                Map<String, String> objeto = new HashMap<>();
                String[] atributos = filme.split("\",\"");
                for(String atributo : atributos){
                    String[] partes = atributo.split("\":\"");
                    String chave = partes[0];
                    String valor = partes[1];
                    objeto.put(chave, valor);
                }
                lista.add(objeto);
            }
            
        }   



        return lista;
    }

    public static List<Map<String,String>> parseJsonLib(String json){
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String,String>> lista = new ArrayList<>();
        try {
            System.out.println("Começando a parsear o resultado usando o Jackson");
            JsonNode tree = mapper.readTree(json);
            

            JsonNode items = tree.get("items");
            if(items.isArray()){
                for(JsonNode item : items){
                    lista.add(mapper.convertValue(item, new TypeReference<Map<String,String>>() {}));
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao traduzir os dados retornados pelo Jackson", e);
        }
        return lista;
    }
}
