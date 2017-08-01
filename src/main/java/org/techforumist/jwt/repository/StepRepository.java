package org.techforumist.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.techforumist.jwt.domain.step.Step;

public interface StepRepository extends JpaRepository<Step, Long> {

}