package com.example.jobexecutor.job.example;

import com.example.common.bus.CommandBus;
import com.example.jobexecutor.job.ScheduledJob;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Ví dụ Scheduled Job - CleanupJob.
 * Job này chạy định kỳ để cleanup dữ liệu cũ.
 */
@Component
public class CleanupJob extends ScheduledJob {

    public CleanupJob(CommandBus commandBus) {
        super(commandBus);
    }

    /**
     * Chạy mỗi ngày lúc 2:00 AM để cleanup dữ liệu cũ.
     */
    @Scheduled(cron = "0 0 2 * * ?")
    @Override
    public void executeScheduled() {
        logger.info("Starting cleanup job...");
        
        try {
            // Thực hiện cleanup logic
            // Ví dụ: xóa logs cũ, archive data, etc.
            // commandBus.send(new CleanupOldDataCommand(...));
            
            logger.info("Cleanup job completed successfully");
        } catch (Exception e) {
            handleError(e);
        }
    }
    
    @Override
    public void execute() {
        executeScheduled();
    }
}

