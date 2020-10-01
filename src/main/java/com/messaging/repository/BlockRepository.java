package com.messaging.repository;

import com.messaging.models.BlockRelation;
import com.messaging.models.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlockRepository extends CrudRepository<BlockRelation, Long> {


    @Query("SELECT br from BlockRelation br WHERE br.user.username = ?1")
    List<BlockRelation> getAllBlockedUsers(String username);

    @Query("SELECT br from BlockRelation br WHERE br.user.username = ?1 and br.blockedUser.username = ?2")
    Optional<BlockRelation> findByUsernameAndBlockedUsername(String username, String blockedUsername);
}
