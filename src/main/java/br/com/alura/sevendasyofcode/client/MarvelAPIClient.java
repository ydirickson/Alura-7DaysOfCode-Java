package br.com.alura.sevendasyofcode.client;

import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class MarvelAPIClient implements APIClient {


  private static final String MARVEL_BASE_URL = "https://gateway.marvel.com/v1/public/%s?ts=%s&apikey=%s&hash=%s";

  private final String privateApiKey;
  private final String publicApiKey;
  private final MessageDigest md;


  public MarvelAPIClient(String publicApiKey, String privateApiKey) throws NoSuchAlgorithmException{
    this.publicApiKey = publicApiKey;
    this.privateApiKey = privateApiKey;
    this.md = MessageDigest.getInstance("MD5");
  }


  @Override
  public URI buildURI(String path) throws URISyntaxException {
    Long time = new Date().getTime();
    String hashContent = time.toString()+privateApiKey+publicApiKey;
    BigInteger hash = new BigInteger(1, md.digest(hashContent.getBytes()));

    return new URI(
      String.format(
        MARVEL_BASE_URL, 
        path, time.toString(), publicApiKey, hash.toString(16)
      )
    );
  }
  
}
