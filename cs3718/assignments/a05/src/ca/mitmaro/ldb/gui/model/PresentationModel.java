package ca.mitmaro.ldb.gui.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;


public class PresentationModel {
	
	LinkedHashMap<String, ArrayList<Observer>> observers = new LinkedHashMap<String, ArrayList<Observer>>();
	
	protected void registerEvent(String event) {
		if (!this.observers.containsKey(event)) {
			this.observers.put(event, new ArrayList<Observer>());
		}
	}
	
	protected void registerEvents(String[] events) {
		for (String event: events) {
			this.registerEvent(event);
		}
	}
	
	public void registerObserver(String event, Observer observer) {
		if (!this.observers.containsKey(event)) {
			throw new RuntimeException(String.format("%s is not a registered event,", event));
		}
		
		this.observers.get(event).add(observer);
	}

	public void triggerEvent(String event) {
		if (!this.observers.containsKey(event)) {
			throw new RuntimeException(String.format("%s is not a registered event,", event));
		}
		
		for(Observer observer: this.observers.get(event)) {
			observer.notify(event);
		}
	}
	
}
