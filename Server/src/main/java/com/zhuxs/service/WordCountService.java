package com.zhuxs.service;

import com.zhuxs.bo.Count;

import java.util.List;

/**
 * Created by shusesshou on 2017/9/11.
 */
public interface WordCountService {
    List<Count> wordCount(String words);
}
