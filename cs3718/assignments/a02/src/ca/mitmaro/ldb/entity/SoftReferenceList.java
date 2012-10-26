/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #2                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.ldb.entity;

import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.Set;
import java.lang.ref.SoftReference;

// similar to the entity.List class but it contains soft references, this will make deleting easier
// later if it's needed
public class SoftReferenceList<T> extends List<T> {
	
	// overwrite the default iterator to return the actual object and not the soft reference
	private class ReferenceIterator implements Iterator<T> {
		
		Iterator<SoftReference<T>> iter;
		
		public ReferenceIterator(Iterator<SoftReference<T>> iter) {
			this.iter = iter;
		}
		
		public boolean hasNext() {
			return this.iter.hasNext();
		}
		
		public T next() {
			return this.iter.next().get();
		}
		
		public void remove() {
			this.iter.remove();
		}
		
	}
	
	private LinkedHashMap<String, SoftReference<T>> list = new LinkedHashMap<String, SoftReference<T>>();
	
	public Iterator<T> iterator() {
		return new ReferenceIterator(list.values().iterator());
	}
	
	public T get(String key) {
		return this.list.get(key).get();
	}
	
	public void put(String key, T item) {
		if (!this.list.containsKey(key)) {
			this.list.put(key, new SoftReference<T>(item));
		}
	}
	
	public void clear() {
		this.list.clear();
	}
	
	public Set<String> keySet() {
		return this.list.keySet();
	}
	
	public int length() {
		return this.list.size();
	}
	
}
