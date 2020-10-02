package com.messaging.controllers;

import com.messaging.models.log.ActivityLog;
import com.messaging.services.log.ActivityLogService;
import com.messaging.utils.URLConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Controller
@RestController
@RequestMapping(URLConstants.LOG_ACTIVITY_URL)
public class LogActivityController {

    //TODO: Allow for only Admin, Admin currently does not exists
    @Autowired
    private ActivityLogService activityLogService;

    @GetMapping
    public ResponseEntity<List<ActivityLog>> getActivity(@AuthenticationPrincipal Principal user) {


        return ResponseEntity
                .ok(activityLogService.findAll());
    }


}
