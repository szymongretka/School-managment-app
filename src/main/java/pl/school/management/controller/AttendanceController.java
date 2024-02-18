package pl.school.management.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import pl.school.management.model.dto.AttendanceDto;
import pl.school.management.service.AttendanceService;

import java.time.LocalDateTime;

@RestController
@RequestMapping(path = "/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/entrance/{id}")
    public LocalDateTime recordEntrance(@PathVariable Long id) {
        return attendanceService.recordEntrance(id);
    }

    @PostMapping("/exit/{id}")
    public LocalDateTime recordExit(@PathVariable Long id) {
        return attendanceService.recordExit(id);
    }

    @PostMapping
    public AttendanceDto addAttendance(@Valid @RequestBody AttendanceDto attendanceDto) {
        return attendanceService.addAttendance(attendanceDto);
    }

}
