package pl.school.management.service;

import pl.school.management.model.dto.ChildDto;
import pl.school.management.model.dto.ParentDto;

import java.util.List;

public interface PersonService {

    ChildDto addChild(ChildDto childDto);

    ChildDto findChildById(Long id);

    List<ChildDto> findAllChildren();

    ParentDto addParent(ParentDto parentDto);

    ParentDto findParentById(Long id);

    List<ParentDto> findAllParents();

}
