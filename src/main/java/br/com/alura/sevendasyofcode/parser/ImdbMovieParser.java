package br.com.alura.sevendasyofcode.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.alura.sevendasyofcode.models.Movie;

public class ImdbMovieParser {
  
  private static final String ATTRIBUTE_PATTERN = "\":\"";

  private final Pattern patternArray = Pattern.compile("\\[(.*?)\\]");
  private final Pattern patternObjects = Pattern.compile("\\{(.*?)\\}");


  public List<Movie> parseListMovies(String json){
    List<Movie> lista = new ArrayList<>();
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
                atributos[3].split(ATTRIBUTE_PATTERN)[1],
                atributos[5].split(ATTRIBUTE_PATTERN)[1],
                Double.parseDouble(atributos[7].split(ATTRIBUTE_PATTERN)[1]),
                Integer.parseInt(atributos[4].split(ATTRIBUTE_PATTERN)[1])
            );
            lista.add(movie);
        }
    }
    return lista;
  }

}
