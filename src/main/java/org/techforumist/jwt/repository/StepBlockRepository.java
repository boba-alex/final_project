package org.techforumist.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.techforumist.jwt.domain.step.StepBlock;

public interface StepBlockRepository extends JpaRepository<StepBlock, Long> {

}