package org.techforumist.jwt.domain;

import org.techforumist.jwt.domain.comment.StepComment;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Step {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type; // text|image|video
    private int number; // serial number
    private Long instructionId;
    private String name;
    private String creatorName;

    @Column(length = 5000)
    private String text;
    private String imageLink;
    private String videoLink;

    private Date creationDate;
    private Date lastEditDate;

    @OneToMany(cascade = CascadeType.ALL)
    @ElementCollection
    private List<StepComment> stepComments;


    public Step() {
        this.creationDate = new Date();
        this.lastEditDate = this.creationDate;
    }

    public Date getLastEditDate() {
        return lastEditDate;
    }

    public void setLastEditDate(Date lastEditDate) {
        this.lastEditDate = lastEditDate;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getName() {
        return name;
    }

    public List<StepComment> getStepComments() {
        return stepComments;
    }

    public void setStepComments(List<StepComment> stepComments) {
        this.stepComments = stepComments;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getInstructionId() {
        return instructionId;
    }

    public void setInstructionId(Long instructionId) {
        this.instructionId = instructionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }
}
