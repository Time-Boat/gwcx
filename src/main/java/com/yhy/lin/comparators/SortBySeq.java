package com.yhy.lin.comparators;

import java.util.Comparator;
import java.util.Map;

/**
* Description : 区域站点数据自定义排序
* @author Administrator
* @date 2017年12月8日 上午11:09:43
*/
//自定义排序

public class SortBySeq implements Comparator {
	
	public int compare(Object o1, Object o2) {
		Map<String,Object> s1 = (Map<String,Object>) o1;
		Map<String,Object> s2 = (Map<String,Object>) o2;
		try {
			if (Integer.parseInt(s1.get("xy_seq") + "") > Integer.parseInt(s2.get("xy_seq") + ""))
				return 1;
			return -1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
		
	}
}
