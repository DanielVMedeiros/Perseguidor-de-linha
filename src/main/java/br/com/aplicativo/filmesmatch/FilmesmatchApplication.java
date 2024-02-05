package br.com.aplicativo.filmesmatch;
import io.github.cdimascio.dotenv.Dotenv;
import br.com.aplicativo.filmesmatch.model.DadosSerie;
import br.com.aplicativo.filmesmatch.service.ConsumoApi;
import br.com.aplicativo.filmesmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FilmesmatchApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(FilmesmatchApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        Dotenv dotenv = Dotenv.load();
        String key = dotenv.get("API_KEY");
        ConsumoApi consumoApi = new ConsumoApi();
        System.out.println(consumoApi.obterDados("https://www.omdbapi.com/?t=The+sopranos&plot=full&apikey=" + key));
        var json = consumoApi.obterDados("https://www.omdbapi.com/?t=The+sopranos&plot=full&apikey=" + key);
        ConverteDados converteDados = new ConverteDados();
        DadosSerie serie = converteDados.obterDados(json, DadosSerie.class);
        System.out.println(serie);
    }


}
