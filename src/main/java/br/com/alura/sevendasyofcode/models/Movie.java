package br.com.alura.sevendasyofcode.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Movie(String title, String image, Double ratign, Integer year){  } 
