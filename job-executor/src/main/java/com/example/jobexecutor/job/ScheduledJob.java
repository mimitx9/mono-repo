package com.example.jobexecutor.job;

import com.example.common.bus.CommandBus;

/**
 * Base class cho Scheduled Jobs.
 * Jobs này được chạy theo lịch định kỳ.
 */
public abstract class ScheduledJob extends BaseJob {

    protected ScheduledJob(CommandBus commandBus) {
        super(commandBus);
    }

    /**
     * Execute scheduled job.
     * Subclasses phải annotate method này với @Scheduled.
     */
    public abstract void executeScheduled();
}

