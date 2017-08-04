package org.techforumist.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.techforumist.jwt.domain.Instruction;

import java.util.List;

public interface InstructionRepository extends JpaRepository<Instruction, Long> {
    public List<Instruction> findAll();
    public List<Instruction> findAllByCreatorName(String creatorName);
    public List<Instruction> findOneById(Long id);
}