package com.messaging.models.log;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
public class ActivityLog {

    //TODO: This can be expanded to store valuable information such as  headers, user agents.

    @Id
    private String id = UUID.randomUUID().toString();

    @NonNull private Date timestamp;
    private String requestURI;
    private String responseStatus;

    @Builder
    public ActivityLog(@NonNull Date timestamp, String requestURI, String responseStatus) {
        this.timestamp = timestamp;
        this.requestURI = requestURI;
        this.responseStatus = responseStatus;
    }
}
