package com.zhuxs.result.domain;

import com.zhuxs.result.domain.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by shusesshou on 2017/9/16.
 */
@Transactional
@Repository
public interface JobDao extends JpaRepository<Job,Long> {
    public List<Job> findAllByEndDate(Date date);
}
