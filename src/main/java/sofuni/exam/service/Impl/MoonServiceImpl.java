package sofuni.exam.service.Impl;

import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sofuni.exam.models.dto.MoonImportDTO;
import sofuni.exam.models.dto.MoonImportRootDTO;
import sofuni.exam.models.entity.Moon;
import sofuni.exam.models.enums.Type;
import sofuni.exam.repository.DiscovererRepository;
import sofuni.exam.repository.MoonRepository;
import sofuni.exam.repository.PlanetRepository;
import sofuni.exam.service.MoonService;
import sofuni.exam.util.ValidationUtil;
import sofuni.exam.util.XmlParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

@Service
public class MoonServiceImpl implements MoonService {

    private final String MOONS_PATH = "src/main/resources/files/xml/moons.xml";

    private final MoonRepository moonRepository;

    private final DiscovererRepository discovererRepository;

    private final PlanetRepository planetRepository;

    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public MoonServiceImpl(MoonRepository moonRepository, DiscovererRepository discovererRepository, PlanetRepository planetRepository, XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.moonRepository = moonRepository;
        this.discovererRepository = discovererRepository;
        this.planetRepository = planetRepository;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.moonRepository.count() > 0;
    }

    @Override
    public String readMoonsFileContent() throws IOException {
        return Files.readString(Path.of(MOONS_PATH));
    }

    @Override
    public String importMoons() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        MoonImportRootDTO moonImportRootDTO = this.xmlParser.fromFile(MOONS_PATH, MoonImportRootDTO.class);
        for (MoonImportDTO moonImportDTO : moonImportRootDTO.getMoonImportDTOs()) {
            if (this.moonRepository.findByName(moonImportDTO.getName()).isPresent() ||
                    !this.validationUtil.isValid(moonImportDTO)) {
                sb.append(String.format("Invalid moon"))
                        .append(System.lineSeparator());
                continue;
            }

            Moon moon = this.modelMapper.map(moonImportDTO, Moon.class);
            moon.setDiscoverer(this.discovererRepository.findById((long) moonImportDTO.getDiscovererId()).get());
            moon.setPlanet(this.planetRepository.findById((long) moonImportDTO.getPlanetId()).get());

            this.moonRepository.saveAndFlush(moon);
            sb.append(String.format("Successfully imported moon %s", moonImportDTO.getName()))
                    .append(System.lineSeparator());
        }

        return sb.toString();
    }

    @Override
    public String exportMoons() {
        StringBuilder sb = new StringBuilder();
        this.moonRepository.findAllByPlanetTypeAndRadiusBetweenOrderByNameAsc(Type.GAS_GIANT, 700, 2000)
                .forEach(m -> {
                    sb.append(String.format(Locale.US,"***Moon %s is a natural satellite of %s and has a radius of %.2f km.\n" +
                                            "****Discovered by %s %s\n",    // Using Locale = US to prevent stubborn comma separator
                                    m.getName(),
                                    m.getPlanet().getName(),
                                    m.getRadius(),
                                    m.getDiscoverer().getFirstName(),
                                    m.getDiscoverer().getLastName()
                            ))
                            .append(System.lineSeparator());
                });
        return sb.toString();
    }
}
