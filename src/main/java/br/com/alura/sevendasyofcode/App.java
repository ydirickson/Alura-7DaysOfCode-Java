package br.com.alura.sevendasyofcode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import br.com.alura.sevendasyofcode.client.ImdbApiClient;
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
        try (FileWriter writer = new FileWriter(new File("imdb_list.html"))){
            System.out.println("Listando os top 250 filmes");
            List<Movie> lista = new ImdbApiClient(apiKey).listarTop250();
            System.out.println("Escrevendo resultado no HTML");
            HTMLGenerator generator = new HTMLGenerator(writer);
            generator.generate(lista);
        } catch(IOException e){
            System.err.println("Erro ao processar HTML: "+e.toString());
        }
    }

   
}
