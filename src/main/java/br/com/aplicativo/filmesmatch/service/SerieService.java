package br.com.aplicativo.filmesmatch.service;


import br.com.aplicativo.filmesmatch.dto.SerieDTO;
import br.com.aplicativo.filmesmatch.model.Serie;
import br.com.aplicativo.filmesmatch.repositoy.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SerieService {
    @Autowired
    private SerieRepository serieRepository;

    public List<SerieDTO> getSeries() {
        List<SerieDTO> serie = serieRepository.findAll()
                .stream()
                .map(s -> new SerieDTO(s.getId(),s.getTitulo(),s.getTotalTemporadas(), s.getAvaliacao(),s.getGenero(),s.getAtores(),s.getPoster(),s.getSinopse()))
                .collect(Collectors.toList());
        return serie;
    }

    public List<SerieDTO> getTopSeries() {
        List<SerieDTO> serieEncontradas = serieRepository.findTop5ByOrderByAvaliacaoDesc().stream()
                .map(s -> new SerieDTO(s.getId(),s.getTitulo(),s.getTotalTemporadas(), s.getAvaliacao(),s.getGenero(),s.getAtores(),s.getPoster(),s.getSinopse()))
                .collect(Collectors.toList());
        return  serieEncontradas;
    }
}
