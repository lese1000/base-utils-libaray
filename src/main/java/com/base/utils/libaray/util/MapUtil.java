package com.base.utils.libaray.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class MapUtil {

	public static void main(String[] args) {

		// Map<Long, Integer> map = new TreeMap<Long, Integer>();
		//
		// map.put(3543245l, 3);
		// map.put(3543243l, 2);
		// map.put(3543247l, 1);
		// map.put(35432452l, 5);
		// int i = 0;
		// Map<Integer, Long> mapKey = new HashMap<Integer, Long>();
		// Map<Integer, Integer> mapValue = new HashMap<Integer, Integer>();
		//
		// // Map<String, String> resultMap = sortMapByKey(map); //按Key进行排序
		// Map<Long, Integer> resultMap = sortMapByValue(map); // 按Value进行排序
		// for (Map.Entry<Long, Integer> entry : resultMap.entrySet()) {
		// System.out.println(entry.getKey() + " " + entry.getValue());
		// mapValue.put(i, entry.getValue());
		// mapKey.put(i, entry.getKey());
		// i++;
		// }
		// System.out.println(mapKey.get(0));
		// System.out.println(mapValue.get(0));

		Map<Long, BigDecimal> bigMap = new HashMap<Long, BigDecimal>();
		bigMap.put(4l, BigDecimal.valueOf(10.2));
		bigMap.put(2l, BigDecimal.valueOf(15.71));
		bigMap.put(1l, BigDecimal.valueOf(10.2));
		System.out.println(getKeysByMinValue(bigMap));

	}

	public static Set<Long> getKeysByMinValue(Map<Long, BigDecimal> map) {
		Set<Long> keySet = new HashSet<Long>();
		Map<Long, BigDecimal> resultMap = sortMapByValueBigDecimal(map);
		Collection<BigDecimal> c = resultMap.values();
		BigDecimal[] big = new BigDecimal[] {};
		big = c.toArray(big);
		return getKeysByValue(resultMap, big[0]);
	}

	// 根据Value取Key
	public static Set<Long> getKeysByValue(Map<Long, BigDecimal> map, Object value) {
		Set<Long> keys = new HashSet<Long>();
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Entry) it.next();
			Object obj = entry.getValue();
			if (obj != null && obj.equals(value)) {
				keys.add((Long) entry.getKey());
			}
		}
		return keys;
	}

	public static Long getKeyByMinValueBigDecimal(Map<Long, BigDecimal> map) {
		int i = 0;
		Map<Integer, Long> mapKey = new HashMap<Integer, Long>();

		Map<Long, BigDecimal> resultMap = sortMapByValueBigDecimal(map); // 按Value进行排序
		for (Map.Entry<Long, BigDecimal> entry : resultMap.entrySet()) {
			mapKey.put(i, entry.getKey());
			i++;
		}
		return mapKey.get(0);
	}

	public static Long getKeyByMinValue(Map<Long, Integer> map) {
		int i = 0;
		Map<Integer, Long> mapKey = new HashMap<Integer, Long>();

		Map<Long, Integer> resultMap = sortMapByValue(map); // 按Value进行排序
		for (Map.Entry<Long, Integer> entry : resultMap.entrySet()) {
			mapKey.put(i, entry.getKey());
			i++;
		}
		return mapKey.get(0);
	}

	/**
	 * 使用 Map按value进行排序
	 * 
	 * @param map
	 * @return
	 */
	public static Map<Long, Integer> sortMapByValue(Map<Long, Integer> oriMap) {
		if (oriMap == null || oriMap.isEmpty()) {
			return null;
		}
		Map<Long, Integer> sortedMap = new LinkedHashMap<Long, Integer>();
		List<Map.Entry<Long, Integer>> entryList = new ArrayList<Map.Entry<Long, Integer>>(oriMap.entrySet());
		Collections.sort(entryList, new MapValueComparator());

		Iterator<Map.Entry<Long, Integer>> iter = entryList.iterator();
		Map.Entry<Long, Integer> tmpEntry = null;
		while (iter.hasNext()) {
			tmpEntry = iter.next();
			sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
		}
		return sortedMap;
	}

	/**
	 * 使用 Map按value进行排序
	 * 
	 * @param map
	 * @return
	 */
	public static Map<Long, BigDecimal> sortMapByValueBigDecimal(Map<Long, BigDecimal> oriMap) {
		if (oriMap == null || oriMap.isEmpty()) {
			return null;
		}
		Map<Long, BigDecimal> sortedMap = new LinkedHashMap<Long, BigDecimal>();
		List<Map.Entry<Long, BigDecimal>> entryList = new ArrayList<Map.Entry<Long, BigDecimal>>(oriMap.entrySet());
		Collections.sort(entryList, new BigDecimalComparator());

		Iterator<Map.Entry<Long, BigDecimal>> iter = entryList.iterator();
		Map.Entry<Long, BigDecimal> tmpEntry = null;
		while (iter.hasNext()) {
			tmpEntry = iter.next();
			sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
		}
		return sortedMap;
	}
}