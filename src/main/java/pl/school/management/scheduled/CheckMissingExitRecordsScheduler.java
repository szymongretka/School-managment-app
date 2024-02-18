package pl.school.management.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.school.management.model.redis.ChildAttendance;
import pl.school.management.repository.redis.ChildAttendanceRepository;

/**
 * We have to check whether there are missing exit records in the cache.
 * We can assume that every exit of a child has to be saved.
 */
@Component
public class CheckMissingExitRecordsScheduler {

    private final Logger logger = LoggerFactory.getLogger(CheckMissingExitRecordsScheduler.class);

    private final ChildAttendanceRepository redisAttendanceRepository;

    public CheckMissingExitRecordsScheduler(ChildAttendanceRepository redisAttendanceRepository) {
        this.redisAttendanceRepository = redisAttendanceRepository;
    }

    @Scheduled(cron = "@midnight")
    public void check() {
        logger.info("Started checking cache.");
        Iterable<ChildAttendance> records = redisAttendanceRepository.findAll();
        records.forEach(record ->
                logger.warn("Found child with id: {} who potentially was not picked up from school! Entrance date: {}",
                        record.getChildId(), record.getEntryDate()));
    }

}
