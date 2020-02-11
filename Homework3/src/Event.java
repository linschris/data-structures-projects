
public class Event {

	public int reportTime;
	public EventType type;
	public int duration;
	public int unit;
	public int resolutionTime;

	public Event(int reportTime, EventType type, int duration, int unit, int resolutionTime) {
		this.init(reportTime, type, duration, unit, resolutionTime);
	}

	public Event(int reportTime, EventType type, int duration) {
		this.init(reportTime, type, duration, -1, -1);
	}

	public Event(int reportTime) {
		this.init(reportTime, EventType.WELLNESS_CHECK, -1, -1, -1);
	}

	private void init(int reportTime, EventType type, int duration, int unit, int resolutionTime) {
		this.reportTime = reportTime;
		this.type = type;
		this.duration = duration;
		this.unit = unit;
		this.resolutionTime = resolutionTime;
	}

	public int getTime() {
		if (this.resolutionTime != -1) {
			return this.resolutionTime;
		} else {
			return this.reportTime;
		}
	}

	public int getTimePriority() {
		if (this.resolutionTime != -1) {
			return 2 * this.getTime() + 1;
		} else {
			return 2 * this.getTime();
		}
	}

	public int getTriagePriority() {
		// assumes the time will never be > 200,000
		int multiplier = 0;
		if (this.type == EventType.WELLNESS_CHECK) {
			multiplier = 3;
		} else if (this.type == EventType.TRAFFIC_COLLISION) {
			multiplier = 2;
		} else if (this.type == EventType.ROBBERY) {
			multiplier = 1;
		} else if (this.type == EventType.MURDER) {
			multiplier = 0;
		}
		return multiplier * 200000 + this.reportTime;
	}

	public String getEventTypeString() {
		if (this.type == EventType.WELLNESS_CHECK) {
			return "wellness check";
		} else if (this.type == EventType.TRAFFIC_COLLISION) {
			return "traffic collision";
		} else if (this.type == EventType.ROBBERY) {
			return "robbery";
		} else if (this.type == EventType.MURDER) {
			return "murder";
		}
		return "";
	}
}