package org.techforumist.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.techforumist.jwt.domain.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}