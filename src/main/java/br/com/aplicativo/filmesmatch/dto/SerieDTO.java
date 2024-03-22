package br.com.aplicativo.filmesmatch.dto;

import br.com.aplicativo.filmesmatch.model.Categoria;

import java.util.List;
import java.util.stream.Collector;


public record SerieDTO( long id,
                        String titulo,
                        Integer totalTemporadas,
                        Double avaliacao,
                        Categoria genero,
                        String atores,
                        String poster,
                        String sinopse
) {
}
