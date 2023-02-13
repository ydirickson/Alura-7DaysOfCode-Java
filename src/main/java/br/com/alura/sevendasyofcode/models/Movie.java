package br.com.alura.sevendasyofcode.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/**
 * Documentando o record Movie conforme o enunciado do desafio.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Movie(String title, String image, Double rating, Integer year){  } 
