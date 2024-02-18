package pl.school.management.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ATTENDANCE")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;

    @Column(name = "ENTRY_DATE", nullable = false)
    private LocalDateTime entryDate;

    @Column(name = "EXIT_DATE", nullable = false)
    private LocalDateTime exitDate;

    public Attendance() {}

    public Attendance(Child child, LocalDateTime entryDate, LocalDateTime exitDate) {
        this.child = child;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public void setExitDate(LocalDateTime exitDate) {
        this.exitDate = exitDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Child getChild() {
        return child;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public LocalDateTime getExitDate() {
        return exitDate;
    }

}
