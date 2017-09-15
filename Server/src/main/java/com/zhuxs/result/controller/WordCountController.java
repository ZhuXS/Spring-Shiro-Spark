package com.zhuxs.result.controller;

import com.zhuxs.result.bo.Count;
import com.zhuxs.result.dto.TextDto;
import com.zhuxs.result.service.WordCountService;
import com.zhuxs.result.utils.ApplicationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Created by shusesshou on 2017/9/4.
 */
@RequestMapping(value = WordCountController.PATH,produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
public class WordCountController {
    public static final String PATH = "wordCount";

    @Autowired
    private WordCountService wordCountService;

    @PostMapping
    public ResponseEntity<List<Count>> getCounts(@RequestBody TextDto words,
                                                 UriComponentsBuilder uriComponentsBuilder){
        HttpHeaders headers = ApplicationUtil.getHttpHeaders(uriComponentsBuilder,PATH);
        return new ResponseEntity<>(wordCountService.wordCount(words.getWords()),HttpStatus.OK);
    }
}
