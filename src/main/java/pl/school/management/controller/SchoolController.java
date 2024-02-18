package pl.school.management.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import pl.school.management.model.dto.SchoolDto;
import pl.school.management.service.SchoolService;

import java.util.List;

@RestController
@RequestMapping(path = "/api/school")
public class SchoolController {

    private final SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @GetMapping("/list")
    public List<SchoolDto> getSchools(@PageableDefault(value = 10, page = 0) Pageable pageable) {
        return schoolService.findAllSchools(pageable);
    }

    @GetMapping("/{id}")
    public SchoolDto getSchool(@PathVariable Long id) {
        return schoolService.findById(id);
    }

    @PostMapping("/add")
    public SchoolDto addSchool(@RequestBody SchoolDto schoolDto) {
        return schoolService.addSchool(schoolDto);
    }

}
