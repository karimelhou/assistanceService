package ma.fstt.dto;

import ma.fstt.entity.Status;

import java.time.LocalDate;

public class AssistanceDTO {

    private Long assistanceId;
    private String description;
    private Status status;
    private LocalDate date;
    private Long userId;

    public AssistanceDTO() {
    }

    public AssistanceDTO(Long assistanceId, String description, Status status, LocalDate date, Long userId) {
        this.assistanceId = assistanceId;
        this.description = description;
        this.status = status;
        this.date = date;
        this.userId = userId;
    }

    public Long getAssistanceId() {
        return assistanceId;
    }

    public void setAssistanceId(Long assistanceId) {
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

