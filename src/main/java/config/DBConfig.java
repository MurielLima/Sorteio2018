package config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import model.Pessoa;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import repository.PessoaRepository;


@Configuration
@EnableMongoRepositories(basePackageClasses = PessoaRepository.class)

public class DBConfig extends AbstractMongoConfiguration {
    
    @Override
    protected String getDatabaseName() {
        return "sorteio";
    }

    @Override
    public Mongo mongo() throws Exception {
        MongoClient client = new MongoClient("localhost", 27017);

        return client;

    }
}
