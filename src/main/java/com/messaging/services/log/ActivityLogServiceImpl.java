package com.messaging.services.log;

import com.messaging.models.log.ActivityLog;
import com.messaging.repository.log.ActivityLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityLogServiceImpl implements ActivityLogService {

    @Autowired private ActivityLogRepository activityLogRepository;

    @Override
    public List<ActivityLog> findAll() {
        return (List<ActivityLog>) activityLogRepository.findAll();
    }
}
