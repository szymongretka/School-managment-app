package pl.school.management.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.school.management.model.dto.SchoolDto;
import pl.school.management.model.entity.School;
import pl.school.management.repository.SchoolRepository;
import pl.school.management.service.SchoolService;

import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository schoolRepository;

    public SchoolServiceImpl(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Override
    public List<SchoolDto> findAllSchools(Pageable pageable) {
        return schoolRepository.findAll(pageable)
                .stream()
                .map(school -> new SchoolDto().fromEntity(school))
                .toList();
    }

    @Override
    public SchoolDto findById(Long id) {
        return schoolRepository.findById(id)
                .map(school -> new SchoolDto().fromEntity(school))
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public SchoolDto addSchool(SchoolDto schoolDto) {
        School school = schoolRepository.save(schoolDto.toEntity());
        return new SchoolDto().fromEntity(school);
    }

}
