package pl.school.management.service.impl;

import org.springframework.stereotype.Service;
import pl.school.management.model.dto.ChildDto;
import pl.school.management.model.dto.ParentDto;
import pl.school.management.model.dto.SchoolDto;
import pl.school.management.model.entity.Child;
import pl.school.management.model.entity.School;
import pl.school.management.model.entity.builder.ChildEntityBuilder;
import pl.school.management.repository.ChildRepository;
import pl.school.management.repository.ParentRepository;
import pl.school.management.repository.SchoolRepository;
import pl.school.management.service.PersonService;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    private final ChildRepository childRepository;
    private final ParentRepository parentRepository;
    private final SchoolRepository schoolRepository;

    public PersonServiceImpl(ChildRepository childRepository,
                             ParentRepository parentRepository,
                             SchoolRepository schoolRepository) {
        this.childRepository = childRepository;
        this.parentRepository = parentRepository;
        this.schoolRepository = schoolRepository;
    }

    @Override
    public ChildDto addChild(ChildDto childDto) {
        Child child = new ChildEntityBuilder()
                .withFirstName(childDto.getFirstName())
                .withLastName(childDto.getLastName())
                .withParent(parentRepository.getReferenceById(childDto.getParentId()))
                .withSchool(schoolRepository.getReferenceById(childDto.getSchoolId()))
                .build();
        child = childRepository.save(child);
        return new ChildDto().fromEntity(child);
    }

    @Override
    public ParentDto addParent(ParentDto parentDto) {
        return null;
    }

    @Override
    public ChildDto findChildById(Long id) {
        return null;
    }

    @Override
    public List<ChildDto> findAllChildren() {
        return null;
    }

    @Override
    public ParentDto findParentById(Long id) {
        return null;
    }

    @Override
    public List<ParentDto> findAllParents() {
        return null;
    }

}
