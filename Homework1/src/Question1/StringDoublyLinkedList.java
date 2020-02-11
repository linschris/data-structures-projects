package Question1;
/*
 * A class meant to represent a doubly-linked list (with both previous and next pointers).
 */
public class StringDoublyLinkedList {

	/**
	 * A private class to represent a link in the linked list.
	 */
	private class Node {
		String value;
		Node next;
		Node prev;
		
		// FIXME add additional member variables here

		Node(String value) {
			this.value = value;
		}
	}

	private int size = 0;
	private Node head = null;
	private Node tail = null;
	private Node reDoable = null;
	
	// FIXME add addition member variables here


	/**
	 * Add a String to the end of the list.
	 * 
	 * @param value The String to add.
	 */
	public void add(String value) {
		// FIXME to be updated for part a.
		Node newNode = new Node(value);
		if (this.size == 0) {
			this.head = newNode;
			this.tail = newNode;
		} else {
			this.tail.next = newNode;
			newNode.prev = this.tail;
			this.tail = newNode;
		}
		this.size += 1;
	}
	/**
	 * Get the number of elements in the list.
	 * 
	 * @return The number of elements in the list.
	 */
	public int size() {
		return this.size;
	}

	/**
	 * Get the element at the specified index.
	 * 
	 * Assumes the index < this.size().
	 * 
	 * @param index The index of the desired element.
	 * @return The String at that index.
	 */
	public String get(int index) {
		return this.getNode(index).value;
	}

	/**
	 * Remove the element at the specified index.
	 * 
	 * Assumes the index < this.size().
	 * 
	 * @param index The index of the element to remove.
	 */
	public void remove(int index) {
		// FIXME to be updated for part a.
		if(size == 1) {
			this.head = null;
			this.tail = null;
		}
		else if (index == 0) {
			this.head = this.head.next;
			this.head.prev = null;
		}
		else if (index == size - 1) {
			this.tail = this.tail.prev;
			this.tail.next = null;
		}
		else {
			Node node = this.getNode(index - 1);
			node.next = node.next.next;
			node.next.prev = node;
		}
		this.size -= 1;
	}

	/**
	 * A helper function to get the Node at a given index.
	 * 
	 * Assumes the index < this.size().
	 * 
	 * @param index The index of the desired Node.
	 * @return The Node.
	 */
	private Node getNode(int index) {
		Node curr = head;
		for (int i = 0; i < index; i++) {
			curr = curr.next;
		}
		return curr;
	}

	/**
	 * Undo an action.
	 * 
	 * @return True if there was an action to undo; false otherwise.
	 */
	public boolean undo() {
		if(this.size == 0) {
			return false;
		}
		Node newNode = new Node(this.tail.value);
		if(this.reDoable == null){
			this.reDoable = newNode;
		}
		else {
			this.reDoable.next = newNode;
			newNode.prev = this.reDoable;
			this.reDoable = newNode;
		}
		this.remove(this.size - 1);
		return true;
	}

	/**
	 * Redo an action.
	 * 
	 * @return True if there was an action to redo; false otherwise.
	 */
	public boolean redo() {
		if(this.reDoable == null) {
			return false;
		}
		else {
			this.add(this.reDoable.value);
			reDoable = reDoable.prev;
			return true;
		}
	}

	/**
	 * Record an action.
	 * 
	 * @param action The action to record.
	 */
	public void doAction(String action) {
		this.add(action);
		this.reDoable = null;
	}

	/**
	 * Get the number of actions recorded. Does *not* include actions that were undone.
	 * 
	 * @return The number of actions recorded.
	 */
	public int getNumActions() {
		return this.size;
	}
	
	/**
	 * Get the action at the specified index.
	 * 
	 * Assumes the index < this.getNumActions().
	 * 
	 * @param index The index of the desired action.
	 * @return The action (String).
	 */
	public String getAction(int index) {
		return this.getNode(index).value;
	}

	/**
	 * Print out the list. Note this function assumes that all your pointers are correct.
	 */
	public void print() {
		Node curr = this.head;
		while (curr != null) {
			System.out.print(curr.value);
			System.out.print(", ");
			curr = curr.next;
		}
		System.out.println();
	}

	/**
	 * A small test case for this linked list.
	 * 
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		// tests for our doubly linked list
		StringDoublyLinkedList list = new StringDoublyLinkedList();
		list.add("42");
		list.add("37");
		list.add("61");
		list.add("13");
		list.print(); // should be 42, 37, 61, 13

		list.remove(0);
		list.print(); // should be 37, 61, 13

		list.remove(1);
		list.print(); // should be 37, 13

		list.remove(1);
		list.print(); // should be 37

		list.remove(0);
		list.print(); // (empty list)
		
		
		StringDoublyLinkedList actions = new StringDoublyLinkedList();
		actions.doAction("create outline");
		actions.doAction("write introduction paragraph");
		actions.doAction("write paragraph 1a");
		actions.undo();
		actions.doAction("write paragraph 1b");
		actions.doAction("write paragraph 2a");
		actions.undo();
		actions.undo();
		actions.undo();
		actions.doAction("write paragraph 2b");
		actions.doAction("write paragraph 3");
		actions.doAction("write paragraph 4");
		actions.undo();
		actions.doAction("write conclusion paragraph");
		actions.doAction("add expletive about how long this assignment took");
		actions.undo();
		
		int n = actions.getNumActions();
		for (int i = 0; i < n; i++) {
		    System.out.println(actions.getAction(i));
		}
	}
}