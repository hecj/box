package com.boxamazing.util;

import com.boxamazing.service.m.model.M;

import java.util.LinkedList;
import java.util.List;

/**
 * 对MenuList排序
 * Created by jhl on 2015/7/22.
 */
public class MenuSorter {
    /**
     * 对MenuList排序
     *
     * @param menList
     * @return
     */
    public static List<M> sort(List<M> menList) {
        return MenuSorter._sort(new MenuSorter(), menList);
    }

    public static List<M> _sort(MenuSorter menuSorter, List<M> menList) {
        List<M> resultList = new LinkedList<M>();
        for (M m : menList) {
            if ((Long)m.get("pid") == 0) {
                resultList.add(m);
            } else {
                int parentid =  ((Long) m.get("pid")).intValue();
                int index = menuSorter.findParentId(parentid, resultList);
                if (index > -1) {
                    resultList.add(index, m);
                } else {
                    resultList.add(m);
                }
            }
        }
        return resultList;
    }

    private int findParentId(int parentId, List<M> resultList) {
        for (int i = 0; i < resultList.size(); i++) {
            int j = ((Long)resultList.get(i).get("id")).intValue();
            if (parentId == j) {
                return i + 1;
            }
        }
        return -1;
    }
}
