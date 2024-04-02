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
        return  converteDados(serieRepository.findAll());
    }

    public List<SerieDTO> getTopSeries() {
        return  converteDados(serieRepository.findTop5ByOrderByAvaliacaoDesc());
    }

    private List<SerieDTO> converteDados(List<Serie> series){
        return series.stream()
                .map(s -> new SerieDTO(s.getId(),s.getTitulo(),s.getTotalTemporadas(), s.getAvaliacao(),s.getGenero(),s.getAtores(),s.getPoster(),s.getSinopse()))
                .collect(Collectors.toList());
    }
}
