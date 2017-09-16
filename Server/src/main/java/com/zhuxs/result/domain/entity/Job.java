package com.zhuxs.result.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by shusesshou on 2017/9/15.
 */
@Entity
@Table(name = "RESULT_JOB")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "type")
    @NotNull
    private  int type;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time")
    @NotNull
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time")
    @NotNull
    private Date endDate;

    public Job(int type, Date startDate, Date endDate) {
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Job() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Job job = (Job) o;

        if (type != job.type) return false;
        if (id != null ? !id.equals(job.id) : job.id != null) return false;
        if (startDate != null ? !startDate.equals(job.startDate) : job.startDate != null) return false;
        return endDate != null ? endDate.equals(job.endDate) : job.endDate == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + type;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        return result;
    }
}
