package br.com.alura.sevendasyofcode;

import java.security.NoSuchAlgorithmException;

import br.com.alura.sevendasyofcode.client.MarvelAPIClient;

public class MarvelApp {
  
    public static void main(String[] args) throws NoSuchAlgorithmException {
      if(args.length == 0 || args[0] == null || args[0].isBlank()){
        throw new IllegalArgumentException("O parâmetro para a APIKey é obrigatório");
      }
      String publicApiKey = args[0];
      String privateApiKey = args[1];
      MarvelAPIClient client = new MarvelAPIClient(publicApiKey, privateApiKey);
      String json = client.getJSON("comics");
      System.out.println(json);
    }


}
