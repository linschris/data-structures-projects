public class AVLTreeMap implements Map {

    class Node {
        String key;
        String value;
        Node left;
        Node right;
        Node parent;
        int height;
        int balanceFactor; 
        
        Node(String key, String value) {
        	this.key = key;
        	this.value = value;
        }
    }
    private int size;
    private Node root;

    public AVLTreeMap() {
        this.root = null;
    }
    public int size() {
        return this.size;
    }
    /*
     * put(String key, value)
     * - Entering in a key, value pair should create a new node with the key value pair
     * - If root is null, make the new root this new node
     * - Else, recursively find the next spot for the new node by using .compareTo() to compare
     * 		the values lexicographically
     *   - If it were to come on to a node with the same key(.compareTo() == 0) --> returns true
     *   - Else, returns false
     * - Once found spot, it'll update the height of the AVL Tree
     * - Next, will check the balance factor of the entire tree, return the current highest node in subtree
     * - Will updateTree(), checking if the root height < node height and make the new root the node in subtree as necessary
     * - Afterward, increments size(if the node is a new node in the tree and not a new value being inserted)
     * - Returns false/true
     * 
     * 
     */
    public boolean put(String key, String value) {
    	Node newNode = new Node(key, value);
    	if(this.root == null) {
    		this.root = newNode;
    		this.size++;
    	}
    	else {
    		if(put(this.root, newNode)) {
    			return true;
    		}
    	}
    	return false;
    }
    public boolean put(Node currentNode, Node newNode) {
    	if(currentNode.key.compareTo(newNode.key) == 0) { //Same key
    		currentNode.value = newNode.value;
    		return true;
    	}
    	else if(currentNode.key.compareTo(newNode.key) < 0) { //Parent node < key node
    		if(currentNode.right == null) {
    			currentNode.right = newNode;
    			newNode.parent = currentNode;
    			updateHeight();
    			Node nodeOfSubtree = checkBalanceFactor(newNode);
    			updateTree(this.root, nodeOfSubtree);
    			this.size++;
    			return false;
    		}
    		else {
    			return put(currentNode.right, newNode);
    		}	
    	}
    	else if(currentNode.key.compareTo(newNode.key) > 0) { //Parent node > key node
    		if(currentNode.left == null) {
    			currentNode.left = newNode;
    			newNode.parent = currentNode;
    			updateHeight();
    			Node nodeOfSubtree = checkBalanceFactor(newNode);
    			updateTree(this.root, nodeOfSubtree);
    			this.size++;
    			return false;
    		}
    		else {
    			return put(currentNode.left, newNode);
    		}	
    	}
    	return false;
    }
    
    
    /*
     * getHeight() 
     * - Recursively checks down the left and right subtree to gather the heights of the subtree of every node
     * - To get the correct height, takes the math.max(left, right) + 1, to take in the bigger subtree(since that's the correct height) 
     *  	and adds 1, to account for the root and null nodes
     */
    public int getHeight() {    
    	return getHeight(this.root, 0);
    }
    public int getHeight(Node node) {    
    	return getHeight(node, 0);
    }
    public int getHeight(Node node, int height) {
    	if(node == null) {
    		return -1;
    	}
    	else {
    		int rightSubTreeHeight = getHeight(node.right, 0);
    		int leftSubTreeHeight = getHeight(node.left, 0);
    		return Math.max(leftSubTreeHeight, rightSubTreeHeight)+1; 
    	}
    }
    
    /*
     * updateHeight()
     * - Recursively updates height starting at root(or given node) by:
     * 	 - If node is null, don't do anything(Why update height of a null node?)
     * 	 - Else, the node's height get changed by grabbing the value returned from getHeight()
     * - Updates going both down left and right subtree to update every node's height
     * 
     */
    
    public void updateHeight() {
    	updateHeight(this.root);
    }
    public void updateHeight(Node currentNode) {
    	if(currentNode == null) {
    		return;
    	}
    	currentNode.height = getHeight(currentNode);
    	updateHeight(currentNode.left);
    	updateHeight(currentNode.right);
    }
    
    /*
     * updateTree(rootNode, nodeOfSubtree) :
     * - Given the rootNode and the current leader of the subtree:
     * 	- Based on the heights of the two nodes, will change the root of the subtree as necessary 
     * 	  if a node is pushed upward after a rotation
     * 	- By checking the heights(after having done an updateHeight()) it will check if the root's height > node's height
     * 	  and if so, change the root, so the new root of the subtree is correct and will print correctly(since it starts
     * 	  at the correct node)
     *
     * 
     */
    public void updateTree(Node root, Node node) {  
    	System.out.println(root.key + "----" + node.key);
    	System.out.println(root.height + "----" + node.height);
    	if(this.root.height < node.height) {
    		this.root = node;
    	}
    }
    
    /*
     * getBalanceFactor():
     *  - Recursively obtains the balanceFactor of each of the nodes starting at the root,
     *    by using getHeight() to get the left and right subtree heights and doing (leftSubHeight - rightSubHeight)
     *    to obtain the balance factor
     * 
     * 
     */
    public int getBalanceFactor() {
    	return getBalanceFactor(this.root);
    }
    
    public int getBalanceFactor(Node node) {
    	int rightSubTreeHeight = getHeight(node.right);
    	int leftSubTreeHeight = getHeight(node.left);
    	int balanceFactor = leftSubTreeHeight - rightSubTreeHeight;
    	node.balanceFactor = balanceFactor;
    	return balanceFactor;
    }
    
    
    /* checkBalanceFactor(node):
     * 	Using getBalanceFactor(), it will check the heights and balance factors of each node starting at the node given
     * 	(usually being the node inserted).
     * 	Checks for any of the four cases: (bf = balancefactor)
     * 	- (2,1) case where the height and node bf is 2, and the node.left is height 1 and bf is 1 (LL)
     *  - (2,-1) case where the height and node bf is 2, and the node.left is height 1, but bf is -1 (LR)
     *  - (-2, 1) case where the node height is 2 and node bf is -2, and the node.right is height 1, and bf is -1 (RR)
     *  - (-2, -1) case where the node height is 2and node bf is -2, and the node.right is height -1, and bf is 1 (RL)
     *  and rotates accordingly.
     *  
     *  After rotation, recursively goes back up the subtree checking each of the parents until it reaches the end of the subtree
     *  where it will then return the node
     */
    
    
    public Node checkBalanceFactor(Node node) {
    	System.out.println(node.key + "-" + getBalanceFactor(node));
    	if(node.left != null) {
    		System.out.println(node.left.key + "-" + getBalanceFactor(node.left));
    	}
    	if(node.right != null) {
    		System.out.println(node.right.key + "-" + getBalanceFactor(node.right) + '+' + node.right.height);
    	}
    	//System.out.println(node.left.key + "-" + getBalanceFactor(node.left));
    	//LEFT LEFT CASE
    	if(node.height >= 2 && getBalanceFactor(node) == 2) {
    		if(node.left.height >= 1 && getBalanceFactor(node.left) == 1) {
    			return rightRotate(node);
    		}
    	}
    	//LEFT RIGHT CASE
    	if(node.height >= 2 && getBalanceFactor(node) == 2) {
    		if(node.left.height >= 1 && getBalanceFactor(node.left) == -1) {
    			return leftRightRotate(node);
    		}
    	}
    	//RIGHT RIGHT CASE
    	if(node.height >= 2 && getBalanceFactor(node) == -2) {
    		if(node.right.height >= 1 && getBalanceFactor(node.right) == -1) {
    			return leftRotate(node);
    		}
    	}
    	//RIGHT LEFT CASE
    	if(node.height >= 2 && getBalanceFactor(node) == -2) {
    		if(node.right.height >= 1 && getBalanceFactor(node.right) == 1) {
    			
    			return rightLeftRotate(node);
    		}
    	}
    	if(node.parent != null) {
        	return checkBalanceFactor(node.parent);
    	}
    	
    	return node;	
    }
    
   /*
    * RR Rotation --> rotate node a left
    * 
    * A
    *  \
    * 	B   -->       B
    *	 \			/   \
    *	  C 	   A	 C
    *
    * - returns b(king of subtree after rotation)
    */
   
    public Node leftRotate(Node a) {
    	Node b = a.right;	
    	Node bLeft = b.left;
    	b.left = a;
    	a.right = bLeft;
    	b.parent = a.parent;
    	a.parent = b;
    	if(a == this.root) {
    		this.root = b;
    	}
    	if(b.parent != null) {
    		if(b.parent.left == a) {
    			b.parent.left = b;
    		}
    		else {
    			b.parent.right = b;
    		}
    	}
    	updateHeight();
    	return b;
    }
    
    /*
     * LL Rotation --> rotate node a right
     * 
     *     A
     *    /
     *   B   -->   B
     *  /		  /	\
     * C         C   A
     * 
     *  - returns b(king of subtree after rotation)
     * 
     */
    
    public Node rightRotate(Node a) { 
    	Node b = a.left;
    	Node bRight = b.right;
    	if(bRight != null) {
    		bRight.parent = a;
    	}
    	b.right = a;
    	a.left = bRight;
    	b.parent = a.parent;
    	a.parent = b;
    	if(b.parent != null) {
    		if(b.parent.right == a) {
    			b.parent.right = b;
    		}
    		else {
    			b.parent.left = b;
    		}
    	}
    	updateHeight();
    	return b;
    }
    
    /*
     * LF Rotation --> rotate node b left, rotate node a right
     * 
     *     A		 A
     *    /			/
     *   B   -->   C   -->  C
     *    \		  /		   / \
     *     C     B        B   A
     *    
     *  - returns c(king of subtree after rotation)
     */
    public Node leftRightRotate(Node a) {
    	Node b = a.left;
    	Node c = a.left.right;
    	Node cLeft = c.left;
    	c.left = b;
    	c.parent = b.parent;
    	b.parent = c;
    	b.right = cLeft;
    	a.left = c;
    	updateHeight();
    	rightRotate(a);
    	return c;
    }
    /*
     * RL Rotation --> rotate node b right, rotate node c left
     * 
     *   A 		A
     *    \      \
     *     B -->  C   -->     C
     *    /		   \	     / \
     *   C 			B       A   B
     *   
     *   
     * - returns c(king of subtree after rotation)
     */
    public Node rightLeftRotate(Node a) {
    	Node b = a.right;
    	Node c = a.right.left;   
    	Node cRight = c.right;
    	c.right = b;
    	c.parent = b.parent;
    	b.parent = c;
    	b.left = cRight;
    	a.right = c;
    	if(a == this.root) {
    		this.root = b;
    	}
    	updateHeight();
    	leftRotate(a);
    	return c;
    }
    
    /*
     * search(key)
     * - Given a key, recursively search for the node using .compareTo() (rather than just value with get() method) to be used
     *   for the remove(node) function
     *   - returns node needed
     * 
     * 
     */
    
    
    public Node search(String key) {
    	return search(key, this.root);
    }
    
    public Node search(String key, Node currentNode) {
    	if(currentNode == null) {
    		return null;
    	}
    	String currentKey = currentNode.key;
    	if(key == currentKey) {
    		return currentNode;
    	}
    	else if(key.compareTo(currentNode.key) > 0) { //KEY > CURRENTNODE KEY
    		return search(key, currentNode.right);
    	}
    	else { 									//KEY < CURRENTNODE KEY
    		return search(key, currentNode.left);
    	}
    }
    
    
    
    public boolean remove(String key) {
    	Node node = search(key);
    	boolean nodeRemoved = remove(node);
    	if(nodeRemoved) { //if node is found and removed
    		size--;
    		return true;
    	}
    	else {
    		System.out.println("Node " + key + " not found");
    		return false;
    	}
    }
    
    /*
     * remove(node):
     * Given a node to remove, 
     * - find the successor Node based on 3 cases(internal node 2 children, internal node 1 child, leaf node)
     * 	 - IF NODE IS A LEAF(LEFT & RIGHT == NULL) --> no successor,
     * 												   remove and remove node.parent.right or remove.node.parent.left connections
     *   - IF NODE IS A INTERNAL NODE(2 CHILDREN) --> successorNode is found going right, then all the way left
     *   - IF NODE IS A INTERNAL NODE(1 CHILD) --> successorNode is either left or right(whichever isn't null)
     * - copy successor Node's values up to node
     * - remove succesorNode(since it's now a duplicate), which recursively removes it(and usually ends up being a leaf node)
     * - return true, if node is not null to begin with(if search(node) == null, then the node isn't there to begin with)
     */
    public boolean remove(Node node) {
    	/*
    	 * Given a node to remove, find the node and keep a pointer on the node's parent
    	 * - Remove said node.
    	 * - Check balance factors of the node's parent and upward(going to root)
    	 * - if node is root and is removed, replace with right subtree successor
    	 * 
    	 * 
    	 * HOW TO REMOVE SAID NODE
    	 * - IF NODE IS A LEAF(H:O) --> remove node.parent.right or node.parent.left pointer
    	 * - IF NODE IS A INTERNAL NODE W/ 2 CHILDREN --> replace given node with right(all the way left) successor first
    	 * if not present, replace with left successor.
    	 * - RETURN TRUE IF NODE IS REMOVED, FALSE IF NOT FOUND
    	 */
    	//System.out.println(node.left.key + " .-. " + node.right.key);
    	if(node == null) {
    		return false;
    	}
    	Node parentNode = node.parent;
    	if(node.right != null && node.left != null) {
    		Node successorNode = node.right;
    		while(successorNode.left != null) {
    			successorNode = successorNode.left;
    		}
    		//move successorNode up
    		copy(node, successorNode);
    		remove(successorNode);
    	}
    	else if(node == this.root) { //if is root w/ 1 or 0 children
    		if(node.right != null) {
    			node.right = null;
    			this.root = node.right;
    		}
    		else {
    			node.left = null;
    			this.root = node.left;
    		}
    		this.root.parent = null;
    	}
    	else if(node.left != null || node.right != null) {
    		if(node.parent.right == node) {
				node.parent.right = node.right;
			}
			else {
				node.parent.left = node.left;
			}
    		if(node.left != null) {
    			node.left.parent = node.parent;
    			node.left = null;
    		}
    		else {
    			node.parent.right = node.right;
    			node.right.parent = node.parent;
    			node.right = null;
    			
      		}
    		
    	}
    	//LEAF CASE
    	else {
    		System.out.println("LEAF CASE ON " + node.key);
    		System.out.println("NODE PARENT IS " + node.parent.key + "AND RIGHT IS " + node.parent.right.key);
    		if(node == node.parent.right) {
    			node.parent.right = null;	
    			node.parent = null;
    		}
    		else {
    			node.parent.left = null;
    		
    		}
    	}   	
    	updateHeight();
    	print();
    	if(parentNode != null) {
    		System.out.println("checking node" + parentNode.key);
    		updateTree(this.root, checkBalanceFactor(parentNode));
    	}
    	return true;
    }

    public void copy(Node a, Node b) {
    	//Node a takes in b key value pair
    	a.key = b.key;
    	a.value = b.value;
    }
   
    /*
     * get(key):
     * - returns a node's value based on the key, by recursively searching for the node using the .compareTo() method
     */
    public String get(String key) {
    	return get(key, this.root); 
    }
    public String get(String key, Node currentNode) {
    	if(currentNode.key.compareTo(key) == 0) {
    		return currentNode.value;
    	}
    	else if(currentNode.key.compareTo(key) < 0) {
    		if(currentNode.right != null) {
    			return get(key, currentNode.right);
    		}
    	}
    	else {
    		if(currentNode.left != null) {
    			return get(key, currentNode.left);
    		}
    	}
    	return null;
    }
    
    // clear() method clears the AVL Tree by setting the size to 0(so no keys & values) and no root(so, no more tree)
    // allows for a new tree to be inserted as necessary
    public void clear() {
    	this.size = 0;
    	this.root = null;
    }   
    public void print() {
        this.print(this.root, "", 0);
    }
    private void print(Node node, String prefix, int depth) {
        if (node == null) {
            return;
        }
        for (int i = 0; i < depth; i++) {
            System.out.print("  ");
        }
        if (!prefix.equals("")) {
            System.out.print(prefix);
            System.out.print(":");
        }
        System.out.print(node.key);
        System.out.print(" (");
        System.out.print("H:");
        System.out.print(node.height);
        System.out.println(")");
        this.print(node.left, "L", depth + 1);
        this.print(node.right, "R", depth + 1);
    }

    public String preorderString() {
        // DO NOT CHANGE THIS FUNCTION
        return this.preorderString(this.root);
    }

    private String preorderString(Node node) {
        // DO NOT CHANGE THIS FUNCTION
        if (node == null) {
            return "()";
        }
        return "(" + node.key + " "
            + this.preorderString(node.left) + " "
            + this.preorderString(node.right) + ")";
    }
    public static void main(String[] args) {
        AVLTreeMap map = new AVLTreeMap();
        String[] keys = {"7", "9", "6", "0", "4", "2", "1"};
        String[] values = {"seven", "nine", "six", "zero", "four", "two", "one"};

        // insert all keys
        for (int i = 0; i < keys.length; i++) {
            boolean exists = map.put(keys[i], values[i]);
            if (exists) {
                System.out.println("Failed to insert key " + keys[i] + " and value " + values[i]);
                return;
            }
        }
        map.print();   
        System.out.println("Root height: " + map.getHeight());
        map.remove("6");
        map.print();
        map.remove("7");
        map.print();
        map.remove("9");
        map.print();
       // map.remove("1");
        map.print();
        
        // check size
        if (map.size() != keys.length) {
            System.out.println(
                "Map should have size() = " + Integer.toString(keys.length) + " after insertion of numbers "
                + "but had size " + Integer.toString(map.size()) + " instead"
            );
            return;
        }

        // retrieve all keys and check their values
        for (int i = 0; i < keys.length; i++) {
            String value = map.get(keys[i]);
            if (!value.equals(values[i])) {
                System.out.println(
                    "Expected " + values[i] + " from retrieve key " + keys[i] + " "
                    + "got " + value + " instead"
                );
            }
        }

        map.clear();
        map.print();
        // check size
        if (map.size() != 0) {
            System.out.println(
                "Map should have size() = 0 after clear() "
                + "but had size " + Integer.toString(map.size()) + " instead"
            );
            return;
        }
        
       
        // check size
        if (map.size() != 4) {
            System.out.println(
                "Map should have size() = 4 after insertion of musical keys "
                + "but had size " + Integer.toString(map.size()) + " instead"
            );
            return;
        }

        if (!map.get("ray").equals("A drop of golden sun.")) {
            System.out.println(
                "Expected \"A drop of golden sun.\" from retrieve key \"ray\" "
                + "got \"" + map.get("ray") + "\" instead"
            );
            return;
        }

        return;
    
    }


}

