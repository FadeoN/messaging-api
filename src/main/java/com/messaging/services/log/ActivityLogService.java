package com.messaging.services.log;

import com.messaging.models.log.ActivityLog;

import java.util.List;

public interface ActivityLogService {

    List<ActivityLog> findAll();
}
