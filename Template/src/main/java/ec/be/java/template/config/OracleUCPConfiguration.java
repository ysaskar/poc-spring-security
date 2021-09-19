package ec.be.java.template.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;

//@Configuration
//@Profile("oracle-ucp")
public class OracleUCPConfiguration {
	
	@Value("${spring.datasource.username}")
	private String user;
	
	@Value("${spring.datasource.password}")
	private String password;
	
	@Value("${spring.datasource.driver.class}")
	private String url;
	
	@Value("${spring.datasource.hikari.minimumIdle}")
	private Integer minPool;
	
	@Value("${spring.datasource.hikari.maximumPoolSize}")
	private Integer maxPool;

    @Bean
    public DataSource dataSource() throws SQLException {
        PoolDataSource dataSource = PoolDataSourceFactory.getPoolDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
        dataSource.setURL(url);
        dataSource.setFastConnectionFailoverEnabled(true);
        dataSource.setInitialPoolSize(2);
        dataSource.setMinPoolSize(minPool);
        dataSource.setMaxPoolSize(maxPool);
        return dataSource;
    }
}