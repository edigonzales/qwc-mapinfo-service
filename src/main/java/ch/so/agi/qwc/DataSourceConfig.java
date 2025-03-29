package ch.so.agi.qwc;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfig {
    
    private final RuntimeConfig runtimeConfig;
    
    public DataSourceConfig(RuntimeConfig runtimeConfig) {
        this.runtimeConfig = runtimeConfig;
    }
    
    @Bean
    public DataSource dataSource() {
        String dburl = runtimeConfig.get("dburl");
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setJdbcUrl(dburl);
        dataSource.setUsername("");
        dataSource.setPassword("");
        dataSource.setMaximumPoolSize(2);
        dataSource.setMinimumIdle(2);
        dataSource.setConnectionTimeout(30000);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
