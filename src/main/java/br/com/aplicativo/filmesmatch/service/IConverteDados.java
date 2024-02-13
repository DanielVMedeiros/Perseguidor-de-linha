package br.com.aplicativo.filmesmatch.service;

public interface IConverteDados {
    <T> T  obterDados(String json, Class<T> classe);
}
