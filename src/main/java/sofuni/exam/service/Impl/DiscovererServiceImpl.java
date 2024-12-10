package sofuni.exam.service.Impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sofuni.exam.models.dto.DiscovererImportDTO;
import sofuni.exam.models.entity.Discoverer;
import sofuni.exam.repository.DiscovererRepository;
import sofuni.exam.service.DiscovererService;
import sofuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class DiscovererServiceImpl implements DiscovererService {

    private static final String DISCOVERERS_PATH = "src/main/resources/files/json/discoverers.json";

    private final DiscovererRepository discovererRepository;

    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public DiscovererServiceImpl(DiscovererRepository discovererRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.discovererRepository = discovererRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.discovererRepository.count() > 0;
    }

    @Override
    public String readDiscovererFileContent() throws IOException {
        return Files.readString(Path.of(DISCOVERERS_PATH));
    }

    @Override
    public String importDiscoverers() throws IOException {
        StringBuilder sb = new StringBuilder();

        DiscovererImportDTO[] discovererImportDTOs = this.gson.fromJson(readDiscovererFileContent(), DiscovererImportDTO[].class);
        for (DiscovererImportDTO discovererImportDTO : discovererImportDTOs) {
            if (this.discovererRepository.findByFirstNameAndLastName(discovererImportDTO.getFirstName(), discovererImportDTO.getLastName()).isPresent() ||
                !this.validationUtil.isValid(discovererImportDTO)) {
                sb.append("Invalid discoverer").append(System.lineSeparator());
                continue;
            }

            Discoverer discoverer = this.modelMapper.map(discovererImportDTO, Discoverer.class);
            this.discovererRepository.saveAndFlush(discoverer);
            sb.append(String.format("Successfully imported discoverer %s %s", discovererImportDTO.getFirstName(),
                            discovererImportDTO.getLastName()))
                    .append(System.lineSeparator());
        }

        return sb.toString();
    }
}
