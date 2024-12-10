package sofuni.exam.service.Impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sofuni.exam.models.dto.PlanetImportDTO;
import sofuni.exam.models.entity.Planet;
import sofuni.exam.repository.PlanetRepository;
import sofuni.exam.service.PlanetService;
import sofuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PlanetServiceImpl implements PlanetService {

    private static final String PLANETS_PATH = "src/main/resources/files/json/planets.json";

    private final PlanetRepository planetRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;


    public PlanetServiceImpl(Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil, PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {

        return this.planetRepository.count() > 0;
    }

    @Override
    public String readPlanetsFileContent() throws IOException {
        return Files.readString(Path.of(PLANETS_PATH));
    }

    @Override
    public String importPlanets() throws IOException {
        StringBuilder sb = new StringBuilder();

        PlanetImportDTO[] planetImportDTOs = this.gson.fromJson(readPlanetsFileContent(), PlanetImportDTO[].class);
        for (PlanetImportDTO planetImportDTO : planetImportDTOs) {
            if (this.planetRepository.findByName(planetImportDTO.getName()).isPresent() ||
            !this.validationUtil.isValid(planetImportDTO)) {
                sb.append("Invalid planet").append(System.lineSeparator());
                continue;
            }

            Planet planet = this.modelMapper.map(planetImportDTO, Planet.class);
            this.planetRepository.saveAndFlush(planet);
            sb.append(String.format("Successfully imported planet %s", planetImportDTO.getName()))
                    .append(System.lineSeparator());
        }

        return sb.toString();
    }
}
