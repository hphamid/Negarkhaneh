package ir.abrstudio.negarkhaneh.list;

import java.util.Observer;
import java.util.WeakHashMap;

public class ExtendedWeakObservable {
	private WeakHashMap<Observer, Void> observers = new WeakHashMap<Observer, Void>();
	private boolean hasChanged = false;

	public void addObserver(Observer observer) {
		this.observers.put(observer, null);
	}

	public synchronized void deleteObserver(Observer observer) {
		this.observers.remove(observer);
	}

	public int countObservers() {
		return this.observers.size();
	}

	public synchronized void deleteObservers() {
		this.observers.clear();
	}

	public void notifyObservers() {
		this.notifyObservers(null);
	}

	protected void setChanged() {
		this.hasChanged = true;
	}

	public void notifyObservers(Object data) {
		if (this.hasChanged) {
			for (Observer observer : observers.keySet()) {
				observer.update(null, data);
			}
			this.clearChanged();
		}
	}

	protected void clearChanged() {
		this.hasChanged = false;
	}

	protected void changeAndNotifyObservers() {
		changeAndNotifyObservers(null);
	}

	protected void changeAndNotifyObservers(Object data) {
		this.setChanged();
		this.notifyObservers(data);
	}

}
