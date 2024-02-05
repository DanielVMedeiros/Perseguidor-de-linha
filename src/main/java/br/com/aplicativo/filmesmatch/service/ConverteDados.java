package br.com.aplicativo.filmesmatch.service;

import tools.jackson.databind.ObjectMapper;

public class ConverteDados {
    private ObjectMapper mapper = new ObjectMapper();

    public <T> T obterDados(String json, Class<T> classe){
        return mapper.readValue(json, classe);
    }
}
