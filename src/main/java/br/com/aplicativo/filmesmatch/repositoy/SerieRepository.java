package br.com.aplicativo.filmesmatch.repositoy;

import br.com.aplicativo.filmesmatch.model.Categoria;
import br.com.aplicativo.filmesmatch.model.Episodio;
import br.com.aplicativo.filmesmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Integer> {
    Optional<Serie> findByTituloContainingIgnoreCase(String titulo);

    List<Serie> findByAtoresContainingIgnoreCase(String nomeAtor);

    List<Serie> findTop5ByOrderByAvaliacaoDesc();

    List<Serie> findByGenero(Categoria genero);

    @Query("SELECT s FROM Serie s WHERE s.totalTemporadas <= :totalTemporada AND s.avaliacao >= :Avaliacao")
    List<Serie> findSerieTemporadaAvaliacao(Integer totalTemporada, Double Avaliacao);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:titulo%")
    List<Episodio> findEpisodioPorTitulo(String titulo);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.serie = :serie ORDER BY e.avaliacao DESC LIMIT 5")
    List<Episodio> findMelhoresEpisodiosPorSerie(Optional<Serie> serie);
}
