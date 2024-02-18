package pl.school.management.model.redis;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;

@RedisHash(value = "ChildAttendance", timeToLive = 24 * 3600)
public class ChildAttendance implements Serializable {

    @Id
    private Long childId;
    private LocalDateTime entryDate;

    public ChildAttendance(Long childId, LocalDateTime entryDate) {
        this.childId = childId;
        this.entryDate = entryDate;
    }

    public Long getChildId() {
        return childId;
    }

    public void setChildId(Long childId) {
        this.childId = childId;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }

}
