package pl.school.management.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import pl.school.management.model.entity.School;

import java.math.BigDecimal;

public class SchoolDto {

    private Long id;

    @NotEmpty
    private String name;

    @NotNull
    private Float hourPrice;

    public SchoolDto fromEntity(School school) {
        SchoolDto dto = new SchoolDto();
        dto.setId(school.getId());
        dto.setName(school.getName());
        dto.setHourPrice(school.getHourPrice().floatValue());
        return dto;
    }

    public School toEntity() {
        School entity = new School();
        entity.setName(this.name);
        entity.setHourPrice(BigDecimal.valueOf(this.hourPrice));
        return entity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getHourPrice() {
        return hourPrice;
    }

    public void setHourPrice(Float hourPrice) {
        this.hourPrice = hourPrice;
    }

}
