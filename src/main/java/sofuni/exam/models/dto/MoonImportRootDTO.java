package sofuni.exam.models.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "moons")
@XmlAccessorType(XmlAccessType.FIELD)
public class MoonImportRootDTO {

    @XmlElement(name = "moon")
    private List<MoonImportDTO> moonImportDTOs;

    public List<MoonImportDTO> getMoonImportDTOs() {
        return moonImportDTOs;
    }

    public void setMoonImportDTOs(List<MoonImportDTO> moonImportDTOs) {
        this.moonImportDTOs = moonImportDTOs;
    }
}
