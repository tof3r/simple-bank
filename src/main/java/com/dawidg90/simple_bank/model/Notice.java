package com.dawidg90.simple_bank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter @Setter
@Table(name = "notice_details")
public class Notice {

    @Id
    @Column(name = "notice_id")
    private long noticeId;

    @Column(name = "notice_summary")
    private String noticeSummary;

    @Column(name = "notice_details")
    private String noticeDetails;

    @Column(name = "notice_begin_date")
    private Date noticeBeginDate;

    @Column(name = "notice_end_date")
    private Date noticeEndDate;

    @JsonIgnore
    @Column(name = "created")
    private Date createDate;

    @JsonIgnore
    @Column(name = "updated")
    private Date updateDate;

}
