package br.com.aplicativo.filmesmatch;
import br.com.aplicativo.filmesmatch.principal.Principal;
import br.com.aplicativo.filmesmatch.repositoy.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FilmesmatchApplication implements CommandLineRunner {

        @Autowired
        private SerieRepository serieRepository;

        public static void main(String[] args) {
            SpringApplication.run(FilmesmatchApplication.class, args);
        }

        @Override
        public void run(String... args) throws Exception {
            Principal principal = new Principal(serieRepository);
            principal.exibeMenu();
        }
    }
