package com.zhuxs.result.domain.entity;

import com.zhuxs.result.domain.enums.JobStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * Created by shusesshou on 2017/9/15.
 */
@Entity
@Table(name = "RESULT_JOB")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "type")
    @NotNull
    private Integer type;

    @Column(name = "start_time")
    @NotNull
    private Timestamp startDate;

    @Column(name = "end_time")
    private Timestamp endDate;

    @Column(name = "status")
    @NotNull
    private JobStatus status;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public Job(int type, Timestamp startDate, JobStatus status) {
        this.type = type;
        this.startDate = startDate;
        this.status = status;
    }

    public Job() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Job job = (Job) o;

        if (id != null ? !id.equals(job.id) : job.id != null) return false;
        if (type != null ? !type.equals(job.type) : job.type != null) return false;
        if (startDate != null ? !startDate.equals(job.startDate) : job.startDate != null) return false;
        if (endDate != null ? !endDate.equals(job.endDate) : job.endDate != null) return false;
        if (status != job.status) return false;
        return user != null ? user.equals(job.user) : job.user == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}
