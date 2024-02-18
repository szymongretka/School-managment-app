package pl.school.management.controller;

import org.springframework.web.bind.annotation.*;
import pl.school.management.model.dto.ChildDto;
import pl.school.management.model.dto.ParentDto;
import pl.school.management.service.PersonService;

import java.util.List;

@RestController
@RequestMapping(path = "/api/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/child/add")
    public ChildDto addChild(@RequestBody ChildDto childDto) {
        return personService.addChild(childDto);
    }

    @GetMapping("/child/{id}")
    public ChildDto getChild(@PathVariable Long id) {
        return personService.findChildById(id);
    }

    @GetMapping("/child/list")
    public List<ChildDto> getChildren() {
        return personService.findAllChildren();
    }

    @PostMapping("/parent/add")
    public ParentDto addParent(@RequestBody ParentDto parentDto) {
        return personService.addParent(parentDto);
    }

    @GetMapping("/parent/{id}")
    public ParentDto getParent(@PathVariable Long id) {
        return personService.findParentById(id);
    }

    @GetMapping("/parent/list")
    public List<ParentDto> getParents() {
        return personService.findAllParents();
    }

}
