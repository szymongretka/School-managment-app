package pl.school.management.model.dto;

import jakarta.validation.constraints.NotNull;
import pl.school.management.model.annotation.ValidDateRange;
import pl.school.management.model.entity.Attendance;

public class AttendanceDto {

    @NotNull
    private Long childId;

    @ValidDateRange
    private DateRange dateRange;

    public AttendanceDto() {}

    public AttendanceDto(Long childId, DateRange dateRange) {
        this.childId = childId;
        this.dateRange = dateRange;
    }

    public AttendanceDto fromEntity(Attendance attendance) {
        Long childId = attendance.getChild().getId();
        DateRange dateRange = new DateRange(attendance.getEntryDate(), attendance.getExitDate());
        return new AttendanceDto(childId, dateRange);
    }

    public Long getChildId() {
        return childId;
    }

    public void setChildId(Long childId) {
        this.childId = childId;
    }

    public DateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }

}
