package org.techforumist.jwt.domain;

import org.techforumist.jwt.domain.comment.InstructionComment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Instruction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Date creationDate;
    private Date lastEditDate;
    private String creatorName;

    @OneToMany(cascade = CascadeType.ALL)
    @ElementCollection
    private List<Step> steps;

    @OneToMany(cascade = CascadeType.ALL)
    @ElementCollection
    private List<InstructionComment> instructionComments;

    public Instruction() {
        this.creationDate = new Date();
        this.lastEditDate = this.creationDate;
    }

    public Instruction(String name) {
        this.creationDate = new Date();
        this.lastEditDate = this.creationDate;
        this.name = name;
    }

    public Date getLastEditDate() {
        return lastEditDate;
    }

    public void setLastEditDate(Date lastEditDate) {
        this.lastEditDate = lastEditDate;
    }

    public List<InstructionComment> getInstructionComments() {
        return instructionComments;
    }

    public void setInstructionComments(List<InstructionComment> instructionComments) {
        this.instructionComments = instructionComments;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
