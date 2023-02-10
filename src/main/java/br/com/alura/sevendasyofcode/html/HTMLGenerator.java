package br.com.alura.sevendasyofcode.html;

import java.io.Writer;

public class HTMLGenerator {

  private static final String HTML_DOCTYPE = "<!DOCTYPE html>";
  private static final String HTML_HEAD = """
      <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
      </head>
      """;
  
  private Writer writer;

  public HTMLGenerator(Writer writer) {
    this.writer = writer;
  }

  public void generate(List<Movie> list){
    StringBuilder content = new StringBuilder(HTML_DOCTYPE);


    writer.write(content.toString());
    writer.flush();
  }

}
