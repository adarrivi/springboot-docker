package io.adarrivi.springboot.model.config;

import com.google.common.collect.ImmutableList;
import com.netflix.nfgraph.build.NFBuildGraph;
import com.netflix.nfgraph.spec.NFGraphSpec;
import com.netflix.nfgraph.spec.NFNodeSpec;
import com.netflix.nfgraph.spec.NFPropertySpec;
import com.netflix.nfgraph.util.OrdinalMap;
import io.adarrivi.springboot.model.domain.FantasyCharacter;
import io.adarrivi.springboot.model.domain.Relationship;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import static com.netflix.nfgraph.spec.NFPropertySpec.COMPACT;
import static java.util.Arrays.asList;

@Configuration
public class DatabaseConfig {

    private static final Random RANDOM = new Random();
    private static final String FANTASY_CHARACTER_NODE_NAME = FantasyCharacter.class.getCanonicalName();
    private static final List<String> ALL_CHARACTERS = getAllCharacters();

    private static List<String> getAllCharacters() {
        try {
            URI uri = DatabaseConfig.class.getResource("/characterList.txt").toURI();
            return ImmutableList.copyOf(Files.readAllLines(Paths.get(uri)));
        } catch (URISyntaxException | IOException e) {
            throw new BeanInitializationException("Could not load the heroes list into the graph");
        }
    }


    @Bean
    public NFBuildGraph graphDb() {
        NFPropertySpec[] propertySpecs = asList(Relationship.values()).stream()
                .map(relationship -> new NFPropertySpec(relationship.name(), FANTASY_CHARACTER_NODE_NAME, COMPACT))
                .toArray(NFPropertySpec[]::new);
        NFGraphSpec schema = new NFGraphSpec(
                new NFNodeSpec(FANTASY_CHARACTER_NODE_NAME, propertySpecs)
        );
        return populateGraphRandomly(new NFBuildGraph(schema));
    }

    private NFBuildGraph populateGraphRandomly(NFBuildGraph graphDb) {
        OrdinalMap<String> nodeOrdinals = getCharacterNodesOrdinals();


        for (String name : ALL_CHARACTERS) {
            graphDb.addConnection(
                    FANTASY_CHARACTER_NODE_NAME,
                    nodeOrdinals.get(name),
                    getRandomRelationship(),
                    nodeOrdinals.get(getRandomCharacter())
            );
        }
        return graphDb;
    }

    private OrdinalMap<String> getCharacterNodesOrdinals() {
        OrdinalMap<String> nodeOrdinals = new OrdinalMap<>();
        ALL_CHARACTERS.stream().forEach(nodeOrdinals::add);
        return nodeOrdinals;
    }

    private String getRandomRelationship() {
        Relationship[] relationships = Relationship.values();
        return relationships[RANDOM.nextInt(relationships.length)].name();
    }

    private String getRandomCharacter() {
        return ALL_CHARACTERS.get(RANDOM.nextInt(ALL_CHARACTERS.size()));
    }


}
