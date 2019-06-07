package com.jagrosh.jmusicbot.shutdown;

import java.util.List;

public abstract class Subject {
	protected List<Observer> observers;
	public void attach(Observer observer) { observers.add(observer); }
	public void dettach(Observer observer) { observers.remove(observer); }
	public void notify_() {
		for (Observer observer : observers) observer.shutdown();
	}
}
