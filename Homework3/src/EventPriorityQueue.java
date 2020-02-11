// A priority queue class meant to handle the events and choose the best one to look into first accordingly
public class EventPriorityQueue {

	class Node {
		Event event;
		int priority;

		Node(Event event, int priority) {
			this.event = event;
			this.priority = priority;
		}
	}

	private int size;
	private Node[] array;

	public EventPriorityQueue() {
		this.array = new Node[15];
	}
	
	public Node[] getArray() {
		return array;
	}
	public void setArray(Node[] arr) {
		this.array = arr;
	}
	
	
	public int size() {
		return this.size; // FIXME remove when ready
	}

	

	public void insert(Event event, int priority) {
		Node newNode = new Node(event, priority);
		if(this.size >= this.array.length) {
			resizeArray();
		}
		this.array[size] = newNode;
		size++;
		percolateUp();
	}
	
	private void resizeArray() {
		Node[] tempArray = this.array;
		tempArray = new Node[array.length * 2];
		for(int i = 0; i < this.array.length; i++) {
			tempArray[i] = this.array[i];
		}
		this.array = tempArray;
	}
	
	public void percolateUp() {
		percolateUp(this.size - 1);
	}
	public void percolateUp(int currentIndex) {
		int parentIndex = (int) Math.floor((currentIndex - 1) / 2);
		int currentIndexPrior = this.array[currentIndex].priority;
		int parentIndexPrior = this.array[parentIndex].priority;
		if(currentIndexPrior < parentIndexPrior) {
			swap(parentIndex, currentIndex);
			percolateUp(parentIndex);
		}
		else {
			return;
		}
		
	}
	
	public void swap(int index1, int index2) {
		Node temp = this.array[index1];
		this.array[index1] = this.array[index2];
		this.array[index2] = temp;
	}
	

	
	public void percolateDown(int currentIndex) {
		int currPrior = this.array[currentIndex].priority;
		int childIndex = currentIndex * 2 + 1;
		while(childIndex < this.size) {
			int minValue = this.array[currentIndex].priority;
			int minIndex = 999999999;
			for(int i = 0; i < 2; i++) {
				if(this.array[childIndex + i] != null) {
					int currChildPrior = this.array[childIndex + i].priority;
					if(currChildPrior < minValue) {
						minValue = currChildPrior;
						minIndex = childIndex + i;
					}
				}
			}
			if(minValue == currPrior) {
				return;
			}
			else {
				swap(currentIndex, minIndex);
				currentIndex = minIndex;
				childIndex = currentIndex * 2 + 1;
			}
		}
	}
	
	
	public Event peek() {
		if(this.size == 0 || this.array[0] == null) {
			return null;
		}
		else {
			return this.array[0].event; 
		}	
	}

	public Event poll() {
		//remove top node
		//replace with bottom node
		//percolate
		if(this.size() == 0) {
			return null;
		}
		else {
			Event event = this.array[0].event;
			this.array[0] = this.array[this.size-1];
			this.array[this.size - 1] = null;
			this.size--;
			if(size > 1) {
				percolateDown(0);
			}
			return event;
		}
	}
	
// FIXME remove when ready
	

	public int[] toPrioritiesArray() {
		// DO NOT CHANGE THIS FUNCTION
		int[] result = new int[this.size];
		for (int i = 0; i < this.size; i++) {
			result[i] = this.array[i].priority;
		}
		return result;
	}

	public static boolean checkHeapProperty(int[] priorities) {
		for (int i = 0; i < priorities.length; i++) {
			if (2 * i + 1 < priorities.length && priorities[i] > priorities[2 * i + 1]) {
				return false;
			}
			if (2 * i + 2 < priorities.length && priorities[i] > priorities[2 * i + 2]) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		// initialize the queue
		EventPriorityQueue queue = new EventPriorityQueue();

		// add some numbers to the queue
		
		int NUM_EVENTS = 20000;
		for (int i = 0; i < NUM_EVENTS; i++) {
			queue.insert(new Event(i), i);
			System.out.println("inserted " + i);
		}
		int[] priorities = queue.toPrioritiesArray();
		for(int i = 0; i < priorities.length; i++) {
			System.out.print(priorities[i] + " ");
		}
		System.out.println(checkHeapProperty(priorities));
		System.out.println();

		
		// poll everything
		for (int i = 1; i <= NUM_EVENTS; i++) {
			int next = queue.poll().getTime();
			System.out.println("polled " + next);
		}

		System.out.println();
		
		System.out.println();
		int[] priorities2 = queue.toPrioritiesArray();
		for(int i = 0; i < priorities2.length; i++) {
			System.out.print(priorities2[i] + " ");
		}
		System.out.println(checkHeapProperty(priorities2));
		
		
		
		//System.out.println(queue.peek().getTime());
		
	}

}