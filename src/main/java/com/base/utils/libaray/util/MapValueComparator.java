package com.base.utils.libaray.util;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

class MapValueComparator implements Comparator<Map.Entry<Long, Integer>> {

    @Override
    public int compare(Entry<Long, Integer> me1, Entry<Long, Integer> me2) {

        return me1.getValue().compareTo(me2.getValue());
    }
}