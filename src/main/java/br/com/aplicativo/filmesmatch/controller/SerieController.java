package br.com.aplicativo.filmesmatch.controller;


import br.com.aplicativo.filmesmatch.dto.EpisodioDTO;
import br.com.aplicativo.filmesmatch.dto.SerieDTO;
import br.com.aplicativo.filmesmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("series")
public class SerieController {

    @Autowired
    private SerieService serieService;

    @GetMapping
    public List<SerieDTO> getSerie() {
        return serieService.getSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> getTopSerie() {
        return serieService.getTopSeries();
    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> getLancamentos() {
        return serieService.getEpisodiosRecentes();
    }

    @GetMapping("/{id}")
    public SerieDTO getSeriePorId(@PathVariable Long id) {
        return serieService.getSerieById(id);
    }

    @GetMapping("/{id}/Episodios")
    public List<EpisodioDTO> getTodosEpisodios(@PathVariable Long id) {
        return serieService.getTodosEpisodios(id);
    }


}
