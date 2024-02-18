package pl.school.management.service;

import pl.school.management.model.dto.AttendanceDto;

import java.time.LocalDateTime;

public interface AttendanceService {

    LocalDateTime recordEntrance(Long childId);

    LocalDateTime recordExit(Long childId);

    AttendanceDto addAttendance(AttendanceDto attendanceDto);

}
