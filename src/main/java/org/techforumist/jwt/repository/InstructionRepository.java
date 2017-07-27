package org.techforumist.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.techforumist.jwt.domain.Instruction;

public interface InstructionRepository extends JpaRepository<Instruction, Long> {
}