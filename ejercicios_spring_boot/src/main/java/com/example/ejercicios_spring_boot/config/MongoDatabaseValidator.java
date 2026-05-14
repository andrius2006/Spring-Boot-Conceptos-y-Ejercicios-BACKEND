package com.example.ejercicios_spring_boot.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.mongodb.client.MongoClient;

@Component
public class MongoDatabaseValidator implements CommandLineRunner {
    private final MongoClient mongoClient;

    public MongoDatabaseValidator(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    public void run(String... args) {

        List<String> databases = mongoClient
                .listDatabaseNames()
                .into(new ArrayList<>());

        if (!databases.contains("inventariodb")) {
            throw new RuntimeException("ERROR: La base de datos 'inventariodb' no existe en MongoDB");
        }

        System.out.println("Base de datos 'inventariodb' verificada correctamente.");
    }
}
