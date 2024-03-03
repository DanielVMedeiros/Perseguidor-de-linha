package br.com.aplicativo.filmesmatch.model;

import br.com.aplicativo.filmesmatch.service.ConsultaChatGPT;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;


@Entity
@Table(name = "Series")
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private String titulo;

    @Getter
    @Setter
    private Integer totalTemporadas;

    @Getter
    @Setter
    private Double avaliacao;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private Categoria genero;

    @Getter
    @Setter
    private String atores;

    @Getter
    @Setter
    private String poster;

    @Getter
    @Setter
    private String sinopse;

    @Getter
    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Episodio> episodios = new ArrayList<>();


    public void setEpisodios(List<Episodio> episodios) {
        episodios.forEach(episodio -> episodio.setSerie(this));
        this.episodios = episodios;
    }

    public Serie(DadosSerie dadosSerie){
        this.titulo = dadosSerie.titulo();
        this.totalTemporadas = dadosSerie.totalTemporadas();
        this.avaliacao = OptionalDouble.of(Double.valueOf(dadosSerie.avaliacao())).orElse(0);
        this.genero = Categoria.fromString(dadosSerie.genero().split(",")[0].trim());
        this.atores = dadosSerie.atores();
        this.poster = dadosSerie.poster();
        //Comentado porque ultrapassei os limites diários
        //this.sinopse = ConsultaChatGPT.obterTraducao(dadosSerie.sinopse()).trim();
        this.sinopse =  dadosSerie.sinopse();
    }

    //Construtor obrigatório para o JPA
    public Serie() {}

    @Override
    public String toString() {
        return
                "genero=" + genero +
                        ", titulo='" + titulo + '\'' +
                        ", totalTemporadas=" + totalTemporadas +
                        ", avaliacao=" + avaliacao +

                        ", atores='" + atores + '\'' +
                        ", poster='" + poster + '\'' +
                        ", sinopse='" + sinopse + '\''+
                        ", episodios='" + episodios + '\'';
    }

}
