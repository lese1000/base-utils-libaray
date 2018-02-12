package com.base.utils.libaray.util;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

class BigDecimalComparator implements Comparator<Map.Entry<Long, BigDecimal>> {

	@Override
	public int compare(Entry<Long, BigDecimal> me1, Entry<Long, BigDecimal> me2) {
		return me1.getValue().compareTo(me2.getValue());
	}
}