public class HashSet {
    private int[] values;
    private ProbeStrategy strategy;
    private int ratio;
    // FIXME more member variables here

    public HashSet(int initArrayLength, int ratio, ProbeStrategy strategy) {
		this.values = new int[initArrayLength];
		this.ratio = ratio;
		this.strategy = strategy;
		for(int i = 0; i < this.values.length; i++) {
			this.values[i] = -1;
		}
    }
    
    public int size() {
    	int size = 0;
    	for(int i: this.values) {
    		if(i != -1 && i != -2) {
    			size++;	
    		}
    	}
    	return size;
    }

    public boolean add(int value) {
    	if(this.contains(value)) {
    		return false;
    	}
    	int hashValue = hash(value);
    	boolean foundSpot = false;
    	if(this.values[hashValue] == -1 || this.values[hashValue] == -2) {
    		this.values[hashValue] = value;
    		foundSpot = true;
    	}
    	else {
			int attemptNum = 0;
			int probeValue = this.strategy.probe(this.values.length, hashValue, attemptNum);
			while(!foundSpot) {
				if(this.values[probeValue] == -1 || this.values[probeValue] == -2) {
					this.values[probeValue] = value;
					foundSpot = true;
				}
				else {
					attemptNum++;
					probeValue = this.strategy.probe(this.values.length, hashValue, attemptNum);
				}
			}
    	}
		if(this.values.length <= this.ratio * this.size()) {
			resize();
		}
    	return foundSpot;    	
    }
   
    public int hash(int value) {
    	int arrSize = this.values.length;
    	return value % arrSize;
    }
    
    public int hash(int value, int newArrLength) {
    	return value % newArrLength;
    }
    
    public void resize() {
    	int newArrLen = this.values.length * 2 + 1;
    	int[] temp = values;
    	this.values = new int[newArrLen];
    	for(int i = 0; i < this.values.length; i++) {
    		values[i] = -1;
    	}
    	for(int i: temp) {
    		if(i != -1 && i != -2) {
    			add(i);
    		}
    	}
    }
    

    public boolean contains(int value) {
    	int hashValue = hash(value);
    	int attemptNum = 0;
    	if(this.values[hashValue] == value) {
    		return true;
    	}
    	else {
    		int probeValue = this.strategy.probe(this.values.length, hashValue, attemptNum);
    		//System.out.println(probeValue);
    		while(this.values[probeValue] != -1 && attemptNum < this.values.length) {
    			if(this.values[probeValue] == value) {
    				return true;
    			}
    			else {
    				attemptNum++;
    				
    				probeValue = this.strategy.probe(this.values.length, hashValue, attemptNum);
    				//System.out.println(this.values.length);
    				//System.out.println(value + " " + probeValue + " " + attemptNum + " " + this.values[probeValue]);
    			}
    		}
    		return false;
    	}
    }


    public boolean remove(int value) {
    	int hashValue = hash(value);
    	int attemptNum = 0;
    	if(this.values[hashValue] == value) {
    		this.values[hashValue] = -2;
    		return true;
    	}
    	else {
    		int probeValue = this.strategy.probe(this.values.length, hashValue, attemptNum);
    		while(this.values[probeValue] != -1) {
    			if(this.values[probeValue] == value) {
    				this.values[probeValue] = -2;
    				return true;
    			}
    			else {
    				attemptNum++;
    				probeValue = this.strategy.probe(this.values.length, hashValue, attemptNum);
    			}
    		}
    		return false;
    	}
    }

    public int[] toArray() {
        int[] result = new int[this.values.length];
        for (int i = 0; i < this.values.length; i++) {
            result[i] = this.values[i];
        }
        return result;
    }

    public static void main(String[] args) {
    	final long startTime = System.currentTimeMillis();
        ProbeStrategy linear = new SimpleLinearProbe();
        ProbeStrategy quad = new SimpleQuadraticProbe();
        HashSet set = new HashSet(7, 3, quad);
        
        
        int[] numbers = new int[10];
        for(int i = 0; i < 10; i++) {
        	numbers[i] = (int) (Math.random() * 100);
        	set.add(numbers[i]);
        	set.remove(numbers[i]);
        	//System.out.println(set.contains(numbers[i]));
        }
        //set.add(1);

        
            // print out the array
            int[] array = set.toArray();
            for (int j = 0; j < array.length; j++) {
                System.out.print(array[j] + ", ");
            }
            System.out.println();
            final long endTime = System.currentTimeMillis();

            System.out.println("Total execution time: " + (endTime - startTime));
        }
    
    }
