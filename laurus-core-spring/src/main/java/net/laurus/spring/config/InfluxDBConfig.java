package net.laurus.spring.config;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.laurus.spring.properties.InfluxDBProperties;

/**
 * Configuration class for connecting to InfluxDB.
 */
@Configuration
@RequiredArgsConstructor
public class InfluxDBConfig {

    @NonNull
    private InfluxDBProperties influxProps;

    /**
     * Configures and provides an InfluxDB client.
     *
     * @return a configured {@link InfluxDB} instance.
     */
    @Bean
    public InfluxDB influxDB() {
        InfluxDB influxDB = InfluxDBFactory.connect(influxProps.getUrl(), influxProps.getUsername(), influxProps.getPassword());
        influxDB.setDatabase(influxProps.getDatabase());
        return influxDB;
    }
}
