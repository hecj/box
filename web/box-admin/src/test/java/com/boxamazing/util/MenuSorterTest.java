package com.boxamazing.util;

import com.boxamazing.service.m.model.M;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * * ¶ÔMenuListÅÅÐò
 * Created by jhl on 2015/7/22.
 */
public class MenuSorterTest {
    @Test
    public void sort() {
        List<M> sourceList = new ArrayList<M>();
        M m = new M();
        m.put("id", 1L).put("n", "").put("aurl", "").put("pid", 0L);
        sourceList.add(m);
        m = new M();
        m.put("id", 2L).put("n", "").put("aurl", "").put("pid", 1L);
        sourceList.add(m);
        m = new M();
        m.put("id", 3L).put("n", "").put("aurl", "").put("pid", 0L);
        sourceList.add(m);
        m = new M();
        m.put("id", 4L).put("n", "").put("aurl", "").put("pid", 1L);
        sourceList.add(m);
        m = new M();
        m.put("id", 5L).put("n", "").put("aurl", "").put("pid", 3L);
        sourceList.add(m);
        m = new M();
        m.put("id", 6L).put("n", "").put("aurl", "").put("pid", 3L);
        sourceList.add(m);

        m = new M();
        m.put("id", 9L).put("n", "").put("aurl", "").put("pid", 0L);
        sourceList.add(m);

        m = new M();
        m.put("id", 10L).put("n", "").put("aurl", "").put("pid", 0L);
        sourceList.add(m);

        m = new M();
        m.put("id", 11L).put("n", "").put("aurl", "").put("pid", 9L);
        sourceList.add(m);

        m = new M();
        m.put("id", 12L).put("n", "").put("aurl", "").put("pid", 0L);
        sourceList.add(m);

        m = new M();
        m.put("id", 13L).put("n", "").put("aurl", "").put("pid", 12L);
        sourceList.add(m);

        List<M> resultList = MenuSorter.sort(sourceList);
        for (M tmp : resultList) {
            System.out.println("id:" + tmp.get("id") + " pid:" + tmp.get("pid") + tmp.get("n"));
        }
    }

}
