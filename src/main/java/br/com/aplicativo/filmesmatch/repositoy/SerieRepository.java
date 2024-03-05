package br.com.aplicativo.filmesmatch.repositoy;

import br.com.aplicativo.filmesmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Integer> {
    Optional<Serie> findByTituloContainingIgnoreCase(String titulo);
}
