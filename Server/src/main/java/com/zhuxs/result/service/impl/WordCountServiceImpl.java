package com.zhuxs.result.service.impl;

import com.zhuxs.result.bo.Count;
import com.zhuxs.result.bo.Word;
import com.zhuxs.result.bo.comparator.CountComparator;
import com.zhuxs.result.utils.RegsUtil;
import com.zhuxs.result.service.WordCountService;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.RelationalGroupedDataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apdplat.word.WordSegmenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.apache.spark.sql.functions.col;

/**
 * Created by shusesshou on 2017/9/4.
 */
@Service
public class WordCountServiceImpl implements WordCountService {
    @Autowired
    private JavaSparkContext javaSparkContext;

    @Autowired
    private SparkSession sparkSession;

    public List<Count> wordCount(String words){
        //format the words
        words = RegsUtil.filterString(words);
        List<org.apdplat.word.segmentation.Word> segWords = WordSegmenter.seg(words);
        String[] tempWords = segWords.stream()
                .map(word -> {
                    return word.getText();
                })
                .collect(Collectors.toList())
                .toArray(new String[segWords.size()]);


        //create the dataframe
        List<Word> wordList = Arrays.stream(tempWords).map(Word::new).collect(Collectors.toList());
        Dataset<Row> dataFrame = sparkSession.createDataFrame(wordList,Word.class);
        dataFrame.show();

        //count
        RelationalGroupedDataset groupedDataset = dataFrame.groupBy(col("word"));
        List<Row> rows = groupedDataset.count().collectAsList();
        return rows.stream().map(new Function<Row, Count>() {
            @Override
            public Count apply(Row row) {
                return new Count(row.getString(0),row.getLong(1));
            }
        }).sorted(new CountComparator()).collect(Collectors.toList());
    }
}
