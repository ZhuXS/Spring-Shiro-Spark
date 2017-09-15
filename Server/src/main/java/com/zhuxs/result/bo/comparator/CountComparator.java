package com.zhuxs.result.bo.comparator;

import com.zhuxs.result.bo.Count;

import java.util.Comparator;

import static java.lang.Math.toIntExact;

/**
 * Created by shusesshou on 2017/9/14.
 */
public class CountComparator implements Comparator<Count>{

    @Override
    public int compare(Count o1, Count o2) {
        return toIntExact(o2.getCount() - o1.getCount());
    }
}
