package com.zhuxs.service;

import com.zhuxs.bo.Count;
import com.zhuxs.bo.Word;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.RelationalGroupedDataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.apache.spark.sql.functions.col;

/**
 * Created by shusesshou on 2017/9/4.
 */
@Component
public class WordCountService {
    @Autowired
    private JavaSparkContext javaSparkContext;

    @Autowired
    private SparkSession sparkSession;

    public List<Count> wordCount(){
        String testInput = "test test test test hello test world world hello test";
        String[] tempWords = testInput.split(" ");
        List<Word> words = Arrays.stream(tempWords).map(Word::new).collect(Collectors.toList());
        Dataset<Row> dataFrame = sparkSession.createDataFrame(words,Word.class);
        dataFrame.show();

        RelationalGroupedDataset groupedDataset = dataFrame.groupBy(col("word"));
        groupedDataset.count().show();

        List<Row> rows = groupedDataset.count().collectAsList();
        return rows.stream().map(new Function<Row, Count>() {
            @Override
            public Count apply(Row row) {
                return new Count(row.getString(0),row.getLong(1));
            }
        }).collect(Collectors.toList());

    }
}
