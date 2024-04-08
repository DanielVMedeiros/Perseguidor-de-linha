package br.com.aplicativo.filmesmatch.service;


import br.com.aplicativo.filmesmatch.dto.EpisodioDTO;
import br.com.aplicativo.filmesmatch.dto.SerieDTO;
import br.com.aplicativo.filmesmatch.model.Episodio;
import br.com.aplicativo.filmesmatch.model.Serie;
import br.com.aplicativo.filmesmatch.repositoy.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {
    @Autowired
    private SerieRepository serieRepository;

    public List<SerieDTO> getSeries() {
        return  converteDadosSeries(serieRepository.findAll());
    }

    public List<SerieDTO> getTopSeries() {
        return  converteDadosSeries(serieRepository.findTop5ByOrderByAvaliacaoDesc());
    }

    private List<SerieDTO> converteDadosSeries(List<Serie> series){
        return series.stream()
                .map(s -> new SerieDTO(s.getId(),s.getTitulo(),s.getTotalTemporadas(), s.getAvaliacao(),s.getGenero(),s.getAtores(),s.getPoster(),s.getSinopse()))
                .collect(Collectors.toList());
    }

    private List<EpisodioDTO> converteDadosEpisodios(List<Episodio> episodios){
        return episodios.stream()
                .map(e -> new EpisodioDTO(e.getId(),e.getTemporada(),e.getTitulo(), e.getNumeroEpisodio(),e.getAvaliacao(),e.getDataLancamento()))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> getEpisodiosRecentes() {
        return converteDadosSeries(serieRepository.findEpisodiosRecentes());
    }

    public SerieDTO getSerieById(Long id) {
        Optional<Serie> serie = serieRepository.findSerieByid(id);
        if(serie.isPresent()){
            Serie s = serie.get();
            return new SerieDTO(s.getId(),s.getTitulo(),s.getTotalTemporadas(), s.getAvaliacao(),s.getGenero(),s.getAtores(),s.getPoster(),s.getSinopse());
        }else{
            return null;
        }
    }

    public List<EpisodioDTO> getTodosEpisodios(Long id) {
        return converteDadosEpisodios(serieRepository.findEpisodiosPorSerie(id));
    }
}
