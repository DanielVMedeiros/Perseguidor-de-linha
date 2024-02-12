package br.com.aplicativo.filmesmatch;
import br.com.aplicativo.filmesmatch.principal.Principal;
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
            Principal principal = new Principal();
            principal.exibeMenu();
        }
    }
