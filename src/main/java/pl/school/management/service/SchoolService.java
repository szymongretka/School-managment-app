package pl.school.management.service;

import org.springframework.data.domain.Pageable;
import pl.school.management.model.dto.SchoolDto;

import java.util.List;

public interface SchoolService {

    List<SchoolDto> findAllSchools(Pageable pageable);

    SchoolDto addSchool(SchoolDto schoolDto);

    SchoolDto findById(Long id);

}
