package com.example.jobexecutor.job;

import com.example.common.bus.CommandBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class cho tất cả các Background Jobs.
 * Jobs có thể là scheduled jobs hoặc triggered jobs.
 */
public abstract class BaseJob {
    
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final CommandBus commandBus;

    protected BaseJob(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    /**
     * Execute job. Subclasses phải implement method này.
     * Note: ScheduledJob sẽ override để gọi executeScheduled().
     */
    public void execute() {
        // Default implementation - ScheduledJob sẽ override
    }

    /**
     * Handle error khi job execution fail.
     * Có thể override để implement retry logic, alerting, etc.
     * 
     * @param error Exception xảy ra
     */
    protected void handleError(Exception error) {
        logger.error("Job execution failed: {}", getClass().getSimpleName(), error);
    }
}

