package pl.school.management.model.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class DateRange {

    @NotNull
    private LocalDateTime entryDate;
    @NotNull
    private LocalDateTime exitDate;

    public DateRange(LocalDateTime entryDate, LocalDateTime exitDate) {
        this.entryDate = entryDate;
        this.exitDate = exitDate;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public LocalDateTime getExitDate() {
        return exitDate;
    }

    public void setExitDate(LocalDateTime exitDate) {
        this.exitDate = exitDate;
    }

}
