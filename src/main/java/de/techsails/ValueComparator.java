/**
 *ValueComparator compares values of two keys in a Map
 *It is used for the CSV data
 *
 *@author yousuf.amanuel
 *@version 22.November.2018
 *
 */
package de.techsails;

import java.util.Comparator;
import java.util.Map;

public class ValueComparator implements Comparator<Integer> {
	private Map<Integer, Double> map;
	private boolean asc;
	
	public ValueComparator(Map<Integer, Double> map, boolean asc) {
		this.map = map;
		this.asc = asc;
	}
 
	@Override
	public int compare(Integer keyA, Integer keyB) {
		if(asc)
			return map.get(keyA).compareTo(map.get(keyB));
		return map.get(keyB).compareTo(map.get(keyA));
	}
}