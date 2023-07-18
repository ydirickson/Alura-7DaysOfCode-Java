package br.com.alura.sevendasyofcode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import br.com.alura.sevendasyofcode.client.ImdbApiClient;
import br.com.alura.sevendasyofcode.html.HTMLGenerator;
import br.com.alura.sevendasyofcode.models.Movie;
import br.com.alura.sevendasyofcode.parser.ImdbMovieParser;

/**
 * Hello world!
 *
 */
public class IMDBApp 
{
    public static void main( String[] args ) {
        if(args.length == 0 || args[0] == null || args[0].isBlank()){
            throw new IllegalArgumentException("O parâmetro para a APIKey é obrigatório");
        }
        String apiKey = args[0];
        try (FileWriter writer = new FileWriter(new File("imdb_list.html"))){
            System.out.println("Obtendo o JSON da chamada da API");
            String json = new ImdbApiClient(apiKey).getJSON("Top250Movies");
            System.out.println("Parseando lista de conteúdo");
            List<Movie> lista = new ImdbMovieParser().parseContentList(json);
            System.out.println("Escrevendo resultado no HTML");
            HTMLGenerator generator = new HTMLGenerator(writer);
            generator.generate(lista);
        } catch(IOException e){
            System.err.println("Erro ao processar HTML: "+e.toString());
        }
    }

    
}