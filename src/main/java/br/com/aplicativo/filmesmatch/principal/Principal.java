package br.com.aplicativo.filmesmatch.principal;

import br.com.aplicativo.filmesmatch.model.*;
import br.com.aplicativo.filmesmatch.repositoy.SerieRepository;
import br.com.aplicativo.filmesmatch.service.ConsumoApi;
import br.com.aplicativo.filmesmatch.service.ConverteDados;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Dotenv dotenv = Dotenv.load();
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO =  dotenv.get("ENDERECO");
    private final String API_KEY = "&apikey=" + dotenv.get("API_KEY");
    private List<DadosSerie> dadosSeries = new ArrayList<>();
    private SerieRepository serieRepository;
    private List<Serie> series = new ArrayList<>();

    public Principal(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    public void exibeMenu() {
        var opcao = -1;
        while(opcao != 0) {
            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar séries buscadas
                    4 - Buscar série por título
                    5 - Buscar série por ator
                    6 - Buscar top 5 melhores séries
                    7 - Buscar por gênero
                    8 - Buscar pela quantidade de temporadas e avaliação
                    9 - Buscar Episodio por titulo
                    10 - Top episódios de uma série
                    
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarSeriePorAtor();
                    break;
                case 6:
                    buscarMelhoresSeries();
                    break;
                case 7:
                    buscarPorGenero();
                    break;
                case 8:
                    buscarPorTemporadasAvaliacao();
                    break;
                case 9:
                    buscarEpisodioTitulo();
                    break;
                case 10:
                    buscarMelhoresEpisodios();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarMelhoresEpisodios() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        Optional<Serie> serie = serieRepository.findByTituloContainingIgnoreCase(nomeSerie);
        if (serie.isPresent()) {
            List<Episodio> episodiosEncontrados= serieRepository.findMelhoresEpisodiosPorSerie(serie);
            System.out.println("Episódios encontrados:");
            episodiosEncontrados.forEach(episodio -> System.out.println(episodio.getSerie().getTitulo() +" Titulo Episódio:" + episodio.getTitulo() +
                    " Avaliação:" + episodio.getAvaliacao() + " Temporada:" + episodio.getTemporada()));
        }else{
            System.out.println("Série não encontrada");
        }

    }

    private void buscarEpisodioTitulo() {
        System.out.println("Digite o título do episódio:");
        var titulo = leitura.nextLine();
        List<Episodio> episodiosEncontrados = serieRepository.findEpisodioPorTitulo(titulo);
        System.out.println("Episódios encontrados:");
        episodiosEncontrados.forEach(episodio -> System.out.println(episodio.getSerie().getTitulo() +" Titulo Episódio:" + episodio.getTitulo() +
                " Avaliação:" + episodio.getAvaliacao() + " Temporada:" + episodio.getTemporada()));
    }

    private void buscarPorTemporadasAvaliacao() {
        System.out.println("Digite o máximo de temporadas:");
        var totalTemporadas = leitura.nextInt();
        leitura.nextLine();
        System.out.println("Digite a nota mínima de avaliação");
        var avaliacao = leitura.nextDouble();
        List<Serie> seriesEncontradas = serieRepository.findSerieTemporadaAvaliacao(totalTemporadas, avaliacao);
        System.out.println("Séries encontradas:");
        seriesEncontradas.forEach(serie -> System.out.println(serie.getTitulo() + " Avaliação:" + serie.getAvaliacao() + " Temporadas:" + serie.getTotalTemporadas()));
    }

    private void buscarPorGenero() {
        System.out.println("Digite o nome da gênero");
        var nomeCategoria = leitura.nextLine();
        Categoria categoria = Categoria.fromPortugues(nomeCategoria);
        List<Serie> serieEncontradas = serieRepository.findByGenero(categoria);
        System.out.println("Séries encontradas na categoria: " + categoria + ":");
        serieEncontradas.forEach(serie -> System.out.println(serie.getTitulo() + "->" + serie.getAvaliacao()));
    }

    private void buscarMelhoresSeries() {
        List<Serie> serieEncontradas = serieRepository.findTop5ByOrderByAvaliacaoDesc();
        System.out.println("Top 5 melhores séries");
        serieEncontradas.forEach(serie -> System.out.println(serie.getTitulo() + "->" + serie.getAvaliacao()));
    }

    private void buscarSeriePorAtor() {
        System.out.println("Digite o nome do ator para busca");
        var nomeAtor = leitura.nextLine();
        List<Serie> serieEncontradas = serieRepository.findByAtoresContainingIgnoreCase(nomeAtor);
        System.out.println("Séries encontradas:");
        serieEncontradas.forEach(serie -> System.out.println(serie.getTitulo()));
    }

    private void buscarSeriePorTitulo() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        Optional<Serie> serie = serieRepository.findByTituloContainingIgnoreCase(nomeSerie);
        if (serie.isPresent()) {
            System.out.println("Serie:" + serie.get());
        }else{
            System.out.println("Serie não está cadastrada");
        }
    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
        //dadosSeries.add(dados);
        serieRepository.save(serie);
        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }


    private void buscarEpisodioPorSerie(){

        listarSeriesBuscadas();
        System.out.println("Digite o nome da série de uma das séries acima:");
        var nomeSerie = leitura.nextLine();

        Optional<Serie> serie = serieRepository.findByTituloContainingIgnoreCase(nomeSerie);

        if(serie.isPresent()){
           List<DadosTemporada> temporadas = new ArrayList<>();
           var serieEncontrada = serie.get();
            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumo.obterDados(ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);
            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d ->d.episodios().stream().map(e->new Episodio(d.numero(),e)))
                    .collect(Collectors.toList());
            serieEncontrada.setEpisodios(episodios);
            serieRepository.save(serieEncontrada);
        }else{
            System.out.println("Nenhum serie encontrado.");
        }

    }

    private void listarSeriesBuscadas(){
        series =  serieRepository.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }
}