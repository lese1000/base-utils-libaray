package com.base.utils.libaray.util;

public class SortUtil {
	/**
	 * 快速排序算法
	 * 
	 * @param a
	 * @param low
	 * @param high
	 */
	public static void sort(String[] a, int low, int high) {
		int start = low;
		int end = high;
		String key = a[low];

		while (end > start) {
			// 从后往前比较

			String endStr = a[end];
			int endCompare = endStr.compareTo(key);

			while (end > start && endCompare >=1) // 如果没有比关键值小的，比较下一个，直到有比关键值小的交换位置，然后又从前往后比较
				end--;
			if (endCompare <0) {
				String temp = a[end];
				a[end] = a[start];
				a[start] = temp;
			}
			// 从前往后比较
			String startStr = a[start];
			int startCompare = startStr.compareTo(key);
			while (end > start && startCompare<0)// 如果没有比关键值大的，比较下一个，直到有比关键值大的交换位置
				start++;
			if (startCompare >=1) {
				String temp = a[start];
				a[start] = a[end];
				a[end] = temp;
			}
			// 此时第一次循环比较结束，关键值的位置已经确定了。左边的值都比关键值小，右边的值都比关键值大，但是两边的顺序还有可能是不一样的，进行下面的递归调用
		}
		// 递归
		if (start > low)
			sort(a, low, start - 1);// 左边序列。第一个索引位置到关键值索引-1
		if (end < high)
			sort(a, end + 1, high);// 右边序列。从关键值索引+1到最后一个
	}

}
