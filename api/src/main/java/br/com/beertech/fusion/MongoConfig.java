package br.com.beertech.fusion;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = { "br.com.beertech.fusion.repository" })
@EnableAutoConfiguration
@ComponentScan
public class MongoConfig {

    private static String dbName = "beertech";

}
