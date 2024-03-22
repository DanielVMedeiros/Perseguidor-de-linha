package br.com.aplicativo.filmesmatch.controller;


import br.com.aplicativo.filmesmatch.dto.SerieDTO;
import br.com.aplicativo.filmesmatch.model.Serie;
import br.com.aplicativo.filmesmatch.repositoy.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SerieController {
    @Autowired
    private SerieRepository repository;

    @GetMapping("/series")
    public List<SerieDTO> getSerie() {
        List<SerieDTO> serie = repository.findAll()
                .stream()
                .map(s -> new SerieDTO(s.getId(),s.getTitulo(),s.getTotalTemporadas(), s.getAvaliacao(),s.getGenero(),s.getAtores(),s.getPoster(),s.getSinopse()))
                .collect(Collectors.toList());
        return serie;
    }
}
