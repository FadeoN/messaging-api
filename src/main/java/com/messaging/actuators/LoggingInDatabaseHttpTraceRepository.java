package com.messaging.actuators;

import com.messaging.models.log.ActivityLog;
import com.messaging.repository.log.ActivityLogRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class LoggingInDatabaseHttpTraceRepository implements HttpTraceRepository {

    private static final Logger HTTP_LOGGER = LoggerFactory.getLogger(LoggingInDatabaseHttpTraceRepository.class);

    @Autowired
    private ActivityLogRepository activityLogRepository;


    @Override
    public List<HttpTrace> findAll() {
        return null;
    }


    @Override
    public void add(HttpTrace trace) {

        ActivityLog activityLog = ActivityLog.builder().timestamp(Date.from(trace.getTimestamp()))
                .requestURI(trace.getRequest().getUri().toString())
                .responseStatus(String.valueOf(trace.getResponse().getStatus())).build();

        activityLogRepository.save(activityLog);


    }

}