package sh.fable.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import sh.fable.persistence.routing.RoutingDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

/**
 * @author Dominic Gunn
 */
@Configuration
public class DataSourceConfig {

	private static final String PRIMARY_DATASOURCE_PREFIX = "spring.primary.datasource";
	private static final String REPLICA_DATASOURCE_PREFIX = "spring.replica.datasource";

	@Autowired
	private Environment environment;

	@Bean
	@Primary
	public DataSource dataSource() {
		final RoutingDataSource routingDataSource = new RoutingDataSource();

		final DataSource primaryDataSource = buildDataSource("PrimaryHikariPool", PRIMARY_DATASOURCE_PREFIX);
		final DataSource replicaDataSource = buildDataSource("ReplicaHikariPool", REPLICA_DATASOURCE_PREFIX);

		final Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put(RoutingDataSource.Route.PRIMARY, primaryDataSource);
		targetDataSources.put(RoutingDataSource.Route.REPLICA, replicaDataSource);

		routingDataSource.setTargetDataSources(targetDataSources);
		routingDataSource.setDefaultTargetDataSource(primaryDataSource);

		return routingDataSource;
	}

	private DataSource buildDataSource(String poolName, String dataSourcePrefix) {
		final HikariConfig hikariConfig = new HikariConfig();

		hikariConfig.setPoolName(poolName);
		hikariConfig.setJdbcUrl(environment.getProperty(String.format("%s.url", dataSourcePrefix)));
		hikariConfig.setUsername(environment.getProperty(String.format("%s.username", dataSourcePrefix)));
		hikariConfig.setPassword(environment.getProperty(String.format("%s.password", dataSourcePrefix)));
		hikariConfig.setDriverClassName(environment.getProperty(String.format("%s.driver", dataSourcePrefix)));

		return new HikariDataSource(hikariConfig);
	}
}
