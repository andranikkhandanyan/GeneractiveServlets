package am.aca.generactive.config;

import org.postgresql.Driver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class DataSourceConfig {
    @Bean
    public DataSource dataSource(@Qualifier("hibernateProperties") Properties hibernateProperties){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setConnectionProperties(hibernateProperties);
//        Properties properties = new Properties();
//        properties.setProperty("spring.datasource.url", "jdbc:postgresql://localhost:5432/generactive_aca");
//        properties.setProperty("spring.datasource.username", "generactive");
//        properties.setProperty("spring.datasource.password", "123456");
//        properties.setProperty("spring.datasource.driver-class-name", "org.postgresql.Driver");
        dataSource.setConnectionProperties(hibernateProperties);
        dataSource.setUrl("jdbc:postgresql://localhost:5432/generactive_aca");
        dataSource.setUsername("generactive_aca");
        dataSource.setPassword("123456");
        dataSource.setDriverClassName("org.postgresql.Driver");
        return dataSource;
    }
}
