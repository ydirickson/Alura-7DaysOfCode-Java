package br.com.alura.sevendasyofcode.html;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.List;

import br.com.alura.sevendasyofcode.models.Movie;

public class HTMLGenerator {

  private static final String BODY_OPEN_TAG = "<body class=\"text-bg-secondary\">";
  private static final String BODY_CLOSE_TAG = "</body>";
  private static final String CARD_OPEN_TAG = "<div class=\"card text-bg-dark border-dark\" style=\"width: 22em\">";
  private static final String CARD_CLOSE_TAG = "</div>";
  private static final String CARD_BODY_OPEN_TAG = "<div class=\"card-body\">";
  private static final String CARD_BODY_CLOSE_TAG = "</div>"; 
  private static final String CARD_HEADER_OPEN_TAG = "<div class=\"card-header\">";
  private static final String CARD_HEADER_CLOSE_TAG = "</div>";
  private static final String COL_OPEN_TAG = "<div class=\"col\">";
  private static final String COL_CLOSE_TAG = "</div>";
  private static final String CONTAINER_OPEN_TAG = "<div class=\"container container-fluid\">";
  private static final String CONTAINER_CLOSE_TAG = "</div>";
  private static final String H1_TAG = "<h1 class=\"text-center mb-4\"> %s </h1>";

  private static final String HEAD_OPEN_TAG = "<head>";
  private static final String HEAD_CLOSE_TAG = "</head>";
  private static final String HTML_OPEN_TAG = "<html lang=\"pt-br\">";
  private static final String HTML_CLOSE_TAG = "</html>";
  private static final String HTML_DOCTYPE = "<!DOCTYPE html>";
  private static final String IMG_TAG = "<img src=\"%s\" class=\"card-img-top\" alt=\"%s\" />";
  private static final String LINK_BOOSTRAP_TAG = "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD\" crossorigin=\"anonymous\">";
  private static final String META_CHARSET_TAG = "<meta charset=\"utf-8\" />";
  private static final String META_VIEWPORT_TAG = "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">";
  private static final String P_TAG = "<p class=\"card-text\">Nota: %,.1f - Ano: %d</p>";
  private static final String ROW_OPEN_TAG = "<div class=\"row row-cols-1 row-cols-md-3 g-4\">";
  private static final String ROW_CLOSE_TAG = "</div>";
  private static final String TITLE_OPEN_TAG = "<title>";
  private static final String TITLE_CLOSE_TAG = "</title>";
  
  private Writer writer;

  public HTMLGenerator(Writer writer) {
    this.writer = writer;
  }

  public void generate(List<Movie> list) throws IOException{
    StringBuilder content = new StringBuilder(HTML_DOCTYPE+"\n");
    content.append(HTML_OPEN_TAG).append("\n");
    content.append(this.gerarHead());
    content.append(this.gerarBody(list));
    content.append(HTML_CLOSE_TAG).append("\n");
    writer.write(content.toString());
    writer.flush();
  }

  private String gerarHead() {
    StringBuilder head = new StringBuilder("\t"+HEAD_OPEN_TAG);
    head.append("\t\t").append(META_CHARSET_TAG).append("\n");
    head.append("\t\t").append(META_VIEWPORT_TAG).append("\n");
    head.append("\t\t").append(LINK_BOOSTRAP_TAG).append("\n");
    head.append("\t\t").append(TITLE_OPEN_TAG).append("Top 250 Filmes IMDB").append(TITLE_CLOSE_TAG).append("\n");
    head.append("\t").append(HEAD_CLOSE_TAG).append("\n");
    return head.toString();
  }

  private String gerarBody(List<Movie> list) {
    StringBuilder body = new StringBuilder();
    body.append("\t").append(BODY_OPEN_TAG).append("\n");
    body.append("\t\t").append(String.format(H1_TAG, "Top 250 Filmes - IMDB")).append("\n");
    body.append("\t\t").append(CONTAINER_OPEN_TAG).append("\n");
    body.append(this.gerarListaCards(list));
    body.append("\t\t").append(CONTAINER_CLOSE_TAG).append("\n");
    body.append("\t").append(BODY_CLOSE_TAG).append("\n");
    return body.toString();
  }

  private String gerarListaCards(List<Movie> list) {
    StringBuilder lista = new StringBuilder();
    lista.append("\t\t\t").append(ROW_OPEN_TAG).append("\n");
    list.forEach(filme -> lista.append(this.gerarCartaoFilme(filme)));
    lista.append("\t\t\t").append(ROW_CLOSE_TAG).append("\n");
    return lista.toString();
  }

  private String gerarCartaoFilme(Movie movie) {
    StringBuilder cartao = new StringBuilder();
    cartao.append("\t\t\t\t").append(COL_OPEN_TAG).append("\n");
    cartao.append("\t\t\t\t\t").append(CARD_OPEN_TAG).append("\n");
    
    cartao.append("\t\t\t\t\t\t").append(CARD_HEADER_OPEN_TAG).append("\n");
    cartao.append("\t\t\t\t\t\t\t").append(movie.title()).append("\n");
    cartao.append("\t\t\t\t\t\t").append(CARD_HEADER_CLOSE_TAG).append("\n");
    
    cartao.append("\t\t\t\t\t\t").append(String.format(IMG_TAG, movie.image(), "Poster do filme "+movie.title())).append("\n");
    
    cartao.append("\t\t\t\t\t\t").append(CARD_BODY_OPEN_TAG).append("\n");
    
    cartao.append("\t\t\t\t\t\t\t").append(String.format(P_TAG, movie.rating(), movie.year())).append("\n");
    cartao.append("\t\t\t\t\t\t").append(CARD_BODY_CLOSE_TAG).append("\n");
    cartao.append("\t\t\t\t\t").append(CARD_CLOSE_TAG).append("\n");
    cartao.append("\t\t\t\t").append(COL_CLOSE_TAG).append("\n");
    return cartao.toString();
  }
  

}
