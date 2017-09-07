package com.zhuxs.controller;

import com.zhuxs.bo.Count;
import com.zhuxs.service.WordCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by shusesshou on 2017/9/4.
 */
@RequestMapping("WordCount")
@Controller
public class WordCountController {
    @Autowired
    private WordCountService wordCountService;

    @GetMapping("/test")
    public ResponseEntity<List<Count>> getCounts(){
        return new ResponseEntity<>(wordCountService.wordCount(), HttpStatus.OK);
    }
}
