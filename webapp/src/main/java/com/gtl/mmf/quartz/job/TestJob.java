package com.gtl.mmf.quartz.job;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class TestJob extends QuartzJobBean {

    private TestTask testTask;

    @Override
    protected void executeInternal(JobExecutionContext jContext)
            throws JobExecutionException {
        try {
            testTask.sayHello();
        } catch (Exception ex) {
            Logger.getLogger(TestJob.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public TestTask getTestTask() {
        return testTask;
    }

    public void setTestTask(TestTask testTask) {
        this.testTask = testTask;
    }
}
