package org.techforumist.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.techforumist.jwt.domain.user.Achievement;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {
}