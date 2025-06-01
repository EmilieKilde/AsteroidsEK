package dk.sdu.cbse;

import dk.sdu.cbse.common.*;
import dk.sdu.cbse.common.service.IEntityProcessingService;
import dk.sdu.cbse.common.service.IGamePluginService;
import dk.sdu.cbse.common.service.IPostEntityProcessingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

@Configuration
public class MainConfig {

    @Bean
    public Game game() {
        return new Game(
                entityProcessingServiceList(),
                postEntityProcessingServices(),
                gamePluginServices()
        );
    }

    @Bean
    public List<IEntityProcessingService> entityProcessingServiceList() {
        return ServiceLoader.load(IEntityProcessingService.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .collect(Collectors.toList());
    }

    @Bean
    public List<IGamePluginService> gamePluginServices() {
        return ServiceLoader.load(IGamePluginService.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .collect(Collectors.toList());
    }

    @Bean
    public List<IPostEntityProcessingService> postEntityProcessingServices() {
        return ServiceLoader.load(IPostEntityProcessingService.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .collect(Collectors.toList());
    }
}