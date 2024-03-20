package br.com.aplicativo.filmesmatch.controller;


import br.com.aplicativo.filmesmatch.model.Serie;
import br.com.aplicativo.filmesmatch.repositoy.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SerieController {
    @Autowired
    private SerieRepository serieRepository;

    @GetMapping("/series")
    public List<Serie> getSerie() {
        return serieRepository.findAll();
    }
}
