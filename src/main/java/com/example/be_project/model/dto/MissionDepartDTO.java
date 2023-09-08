package com.example.be_project.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MissionDepartDTO {
    private long missionId;
    private String missionName;
    private long departId;
    private String departName;
    private String missionKey;
    private String departKey;
    private String missionCreatedBy;
    private String departCreatedBy;
    private Date missionCreatedDate;
    private Date departCreatedDate;

    public MissionDepartDTO(long missionId, String missionName, String departName, String missionCreatedBy, Date missionCreatedDate) {
        this.missionId = missionId;
        this.missionName = missionName;
        this.departName = departName;
        this.missionCreatedBy = missionCreatedBy;
        this.missionCreatedDate = missionCreatedDate;
    }
}
