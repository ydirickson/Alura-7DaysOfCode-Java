package br.com.alura.sevendasyofcode.parser;

import java.util.List;

import br.com.alura.sevendasyofcode.models.Content;

public interface JsonParser {
  
  public List<? extends Content> parseContentList(String json);

}
