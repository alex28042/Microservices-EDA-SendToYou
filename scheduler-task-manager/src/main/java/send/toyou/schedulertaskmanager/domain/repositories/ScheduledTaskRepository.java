package send.toyou.schedulertaskmanager.domain.repositories;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import send.toyou.schedulertaskmanager.domain.persistence.ScheduleTask;

public interface ScheduledTaskRepository extends R2dbcRepository<ScheduleTask, String> {
}
