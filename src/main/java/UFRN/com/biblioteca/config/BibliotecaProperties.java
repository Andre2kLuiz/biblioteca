package UFRN.com.biblioteca.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "emprestimo")
@Getter
@Setter
public class BibliotecaProperties {
    private int prazoDias;
}
