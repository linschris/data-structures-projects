public class SimpleLinearProbe implements ProbeStrategy {
	
    public int probe(int arraySize, int originalHash, int attemptNum) {
    	int newNum = (originalHash + attemptNum) % arraySize;
		return newNum; 
    }

}
