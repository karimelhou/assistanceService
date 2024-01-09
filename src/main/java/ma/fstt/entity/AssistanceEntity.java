package ma.fstt.entity;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "assistance")
public class AssistanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long assistanceId;

    private String description;

    private Status status;

    private LocalDate date;

    private Long userId;


    public AssistanceEntity() {
    }

    public AssistanceEntity(Long assistanceId, String description, Status status, LocalDate date, Long userId) {
        this.assistanceId = assistanceId;
        this.description = description;
        this.status = status;
        this.date = date;
        this.userId = userId;
    }

    public Long getAssitanceId() {
        return assistanceId;
    }

    public void setAssitanceId(Long assistanceId) {
        this.assistanceId = assistanceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
