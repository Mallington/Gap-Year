package de.techsails;

import java.util.Comparator;
import java.util.Map;

public class ValueComparatorInteger implements Comparator<Integer> {
	private Map<Integer, Integer> map;
	private boolean asc;
	
	public ValueComparatorInteger(Map<Integer, Integer> map, boolean asc) {
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
