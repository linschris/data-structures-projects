import java.lang.IllegalArgumentException;

/*
 * A class meant to represent a police dispatch simulator, using the Event, EventPriorityQueue, and EventType
 */
public class PoliceDispatchSimulator {

	private EventPriorityQueue triageQueue;
	private EventPriorityQueue eventQueue;
	private EventPriorityQueue unitQueue;
	// FIXME add addition member variables here

	public PoliceDispatchSimulator(int numUnits) {
		
		if (numUnits < 1) {
			throw new IllegalArgumentException("There must be at least one unit to dispatch.");
		}
		this.triageQueue = new EventPriorityQueue();
		this.eventQueue = new EventPriorityQueue();
		this.unitQueue = new EventPriorityQueue();
		for(int i = 0; i < numUnits; i++) {
			this.unitQueue.insert(new Event(i), i);
		}
	}

	public void addSimulationEvent(Event event) {
		this.eventQueue.insert(event, event.getTimePriority());
	} 
	
	private void logReport(int time, Event event) {
		System.out.println("Time " + time + ": " + event.getEventTypeString() + " reported");
	}

	private void logDispatch(int time, int unit, Event event) {
		System.out.println(
			"Time " + time + ": unit " + unit + " dispatched to the " 
			+ event.getEventTypeString() + " from time " + event.reportTime
		);
	}

	private void logResolution(int time, int unit, Event event) {
		System.out.println(
			"Time " + time + ": unit " + unit + " resolved the " 
			+ event.getEventTypeString() + " from time " + event.reportTime
		);
	}

	public void run() {
		int currentTime = 0;
		boolean done = false;
		while(!done && currentTime <= 1000000000) {
			for(int i = 0; i < eventQueue.size(); i++) {
				Event currentEvent = eventQueue.getArray()[i].event;
				//System.out.println(currentEvent.type + " " + currentEvent.reportTime + " " + currentEvent.resolutionTime);
				if(currentTime == currentEvent.reportTime) {
					this.eventQueue.poll();
					logReport(currentTime, currentEvent);
					this.triageQueue.insert(currentEvent, currentEvent.getTriagePriority());
					i=-1;
					continue;
				}
				else if(currentTime == currentEvent.resolutionTime) {
					this.eventQueue.poll();
					this.unitQueue.insert(new Event(currentEvent.unit), currentEvent.unit);
					logResolution(currentTime, currentEvent.unit, currentEvent);
					i=-1;
					continue;
				}
			}
			for(int j = 0; j < triageQueue.size(); j++) {
				Event currTriEvent = triageQueue.getArray()[j].event;
				for(int i = 0; i < unitQueue.size(); i++) {
					Event currentUnit = unitQueue.getArray()[i].event;
					//System.out.println(currentUnit.getTime() + " " + currTriEvent.resolutionTime);
					if(currentUnit != null && currTriEvent.resolutionTime == -1) {
						this.triageQueue.poll();
						this.unitQueue.poll();
						logDispatch(currentTime, currentUnit.getTime(), currTriEvent);
						currTriEvent.resolutionTime = currentTime + currTriEvent.duration;
						this.eventQueue.insert(new Event(currTriEvent.reportTime, currTriEvent.type, currTriEvent.duration, currentUnit.getTime(), currTriEvent.resolutionTime), currTriEvent.getTimePriority());
						j=-1;
						continue;
					}
				}	
			}
			currentTime++;	
			if(eventQueue.size() == 0 && triageQueue.size() == 0) {
				done = true;
			}
		}
	}


	public static void main(String[] args) {
		PoliceDispatchSimulator sim = new PoliceDispatchSimulator(2);
		sim.addSimulationEvent(new Event(0, EventType.WELLNESS_CHECK, 10));
		sim.addSimulationEvent(new Event(3, EventType.WELLNESS_CHECK, 2));
		sim.addSimulationEvent(new Event(4, EventType.WELLNESS_CHECK, 3));
		sim.addSimulationEvent(new Event(5, EventType.ROBBERY, 10));
		sim.addSimulationEvent(new Event(100, EventType.MURDER, 100));
		sim.addSimulationEvent(new Event(100, EventType.ROBBERY, 100));
		sim.run();
	}

}