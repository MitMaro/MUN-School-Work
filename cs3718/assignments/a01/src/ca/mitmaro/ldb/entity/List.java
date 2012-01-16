/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #1                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.ldb.entity;

import java.lang.Iterable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

public class List<T> implements Iterable<T> {
	
	private LinkedHashMap<String, T> list = new LinkedHashMap<String, T>();
	
	public Iterator<T> iterator() {
		return list.values().iterator();
	}
	
	public void put(String key, T item) {
		if (item != null && !this.list.containsKey(key)) {
			this.list.put(key, item);
		}
	}
	
	public T get(String key) {
		return this.list.get(key);
	}
	
	public Set<String> keySet() {
		return this.list.keySet();
	}
	
}
