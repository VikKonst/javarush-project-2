package com.javarush.island.services;

import com.javarush.island.annotations.Injectable;
import com.javarush.island.species.plants.Plant;

@Injectable
public class PlantService {
    private final AppService appService;

    public PlantService(AppService appService) {
        this.appService = appService;
    }

    public Plant createNewPlant() {
        return new Plant(1, 200);
    }
}
