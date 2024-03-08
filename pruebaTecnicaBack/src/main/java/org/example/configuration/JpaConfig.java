package org.example.configuration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableJpaRepositories(basePackages = "org.example.repository")
@EntityScan(basePackages = "org.example.domain")
@EnableTransactionManagement
public class JpaConfig {
    // Configuración adicional si es necesaria
}
