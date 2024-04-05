package br.com.aplicativo.filmesmatch.dto;

import br.com.aplicativo.filmesmatch.model.Serie;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public record EpisodioDTO(
         long id,
         Integer temporada,
         String titulo,
         Integer numeroEpisodio,
         Double avaliacao,
         LocalDate dataLancamento
) {}
