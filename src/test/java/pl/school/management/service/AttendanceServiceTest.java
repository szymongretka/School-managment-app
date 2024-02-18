package pl.school.management.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.school.management.model.redis.ChildAttendance;
import pl.school.management.repository.AttendanceRepository;
import pl.school.management.repository.ChildRepository;
import pl.school.management.repository.redis.ChildAttendanceRepository;
import pl.school.management.service.impl.AttendanceServiceImpl;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class AttendanceServiceTest {

    @Mock
    private ChildAttendanceRepository redisAttendanceRepository;
    @Mock
    private AttendanceRepository attendanceRepository;
    @Mock
    private ChildRepository childRepository;

    @InjectMocks
    private AttendanceServiceImpl attendanceService;

    @Test
    void recordEntrance() {
        //given
        Long childId = 9L;

        //when
        LocalDateTime result = attendanceService.recordEntrance(childId);

        //then
        verify(redisAttendanceRepository, times(1))
                .save(any());
    }

    @Test
    void recordExit_expectException() {
        //given
        Long childId = 9L;

        when(redisAttendanceRepository.findById(childId))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> attendanceService.recordExit(childId));
    }

    @Test
    void recordExit_expectRecorded() {
        //given
        Long childId = 9L;

        when(redisAttendanceRepository.findById(childId))
                .thenReturn(Optional.of(new ChildAttendance(childId, LocalDateTime.now())));

        //when
        LocalDateTime result = attendanceService.recordExit(childId);

        //then
        verify(attendanceRepository, times(1))
                .save(any());
        verify(redisAttendanceRepository, times(1))
                .delete(any());
    }

}