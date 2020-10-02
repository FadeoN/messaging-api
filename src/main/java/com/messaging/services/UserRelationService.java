package com.messaging.services;

import com.messaging.models.user.BlockRelation;

import java.util.List;

public interface UserRelationService {

    List<BlockRelation> getAllBlocked(String username);

    BlockRelation blockUser(String loggedInUsername, String targetUsername);
    void unblockUser(String loggedInUsername, String blockedUsername);

    boolean isBlockedByRecipientUser(String loggedInUsername, String targetUsername);
}
