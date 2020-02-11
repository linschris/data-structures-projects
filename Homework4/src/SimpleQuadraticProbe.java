public class SimpleQuadraticProbe implements ProbeStrategy {

    public int probe(int arraySize, int originalHash, int attemptNum) {
		int newVal = (originalHash + (attemptNum * attemptNum)) % arraySize;
		return newVal; 
    }
}