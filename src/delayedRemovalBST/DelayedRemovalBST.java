package delayedRemovalBST;

public class DelayedRemovalBST<Type extends Comparable<? super Type>> {
	/**
	 * Root of the BST
	 */
	private BinaryNode<Type> root;
	
	/**
	 * Number of nodes satisfying isRemoved = false.
	 */
	private int numNodes;
	
	/**
	 * Total number of nodes (regardless of isRemoved = false or true)
	 */
	private int numTotalNodes;
	
	public DelayedRemovalBST() {
	}
	
	public boolean isEmpty() {
            return (numTotalNodes == 0); //If the total number of nodes is equal to 0, it is empty.
	}
	
	/**
	 * Number of non-removed nodes in the BST
	 * @return Number of nodes satsifying isRemoved = false
	 */
	public int size() {
		return numNodes; //Returns the node number of the tree.
	}
	
	/**
	 * Height of the BST disregarding removed nodes
	 * @return BST height disregarding removed nodes
	 */
	public int height() {
            int h= heigth(root);
		return h; 
	}
	private int heigth(BinaryNode <Type> dummy ){
            if (dummy==null){ 
                return 0;
            }
            else {
                int l = heigth(dummy.left); //Counts the left side of the tree.
                int r = heigth(dummy.right); //Counts the rigth side of the tree.
                if(l > r){ 
                    return l+1; 
                }
                else{
                    return r+1;
                }
            }
        }
	/**
	 * Searches for the specified key in this BST
	 * @param key Value to be searched
	 * @return true if key appears as a node in the BST, false otherwise 
	 */
	public boolean contains(Type key) {
            return find(key,root) != null; //Returns false if the requested value is not present in the tree, and true if it is present.
  	}
	
	/**
	 * Searches for the node that contains the specified key in this BST.
	 * If the node found is tagged with isRemoved = true, find will fail. 
	 * @param key The data to be located
	 * @return Reference of the node containing the key
	 */
	public BinaryNode<Type> find(Type key) {
		return find(key,root);
	}
        private BinaryNode<Type> find(Type key,BinaryNode <Type> dummy){
            if(dummy == null){ 
		return null; //Checks if the tree is empty and returns null because it is empty.
		} 
		int comparison = key.compareTo(dummy.data); //Checks if the tree is empty and compares it because it is not empty.
		if (comparison == 0){ 
			return dummy; //Checks if we are looking for the top element of the tree.
		} 
                else if(comparison < 0){ 
			return find(key, dummy.left); //If what we are looking for is small, it continues to search from the left.
		} 
                else 
			return find(key, dummy.right); //If what we are looking for is small, it continues to search from the rigth.
        }
	
	/**
	 * Finds the node that contains the smallest data in the BST.
	 * Nodes marked removed will be disregarded.
	 * @return Reference to the min-data node in the BST
	 */
	public BinaryNode<Type> findMin() {
            BinaryNode tree=root; 
            if(tree != null) 
                while(tree.left != null) //Min items are written on the left, so they look for the left.
                    tree = tree.left;
            return tree; //If the smallest element is root, it returns tree.
	}
        
	/**
	 * Finds the node that contains the highest data in the BST.
	 * Nodes marked removed will be disregarded.
	 * @return Reference to the max-data node in the BST
	 */
	public BinaryNode<Type> findMax() {
            BinaryNode tree=root;
            if(tree != null) 
                while(tree.right != null) //Max items are written on the rigth, so they look for the rigth.
                    tree = tree.right;
            return tree; //If the biggest element is root, it returns tree.
	}
	
	/**
	 * Prints ALL nodes of the BST using pre-order traversal. 
	 * For removed nodes, an underscore character ('_')
	 * will be printed before the node data.
	 */
	public void printTree() {
		if(isEmpty()){ //Checks if the tree is empty.And if it is empty, it gives output.
			System.out.println("Empty tree");
		} 
                else{ //The tree is not empty and prints the tree.
			print(root);
                        System.out.println();
		}
	}
        private void print(BinaryNode<Type> node) {
		if (node == null){
			return;
		}
                if(node.isRemoved){ //Firstly root with chechking isRemoved statue.
                    System.out.print("_" + node.data + " -> ");
                }
                else{
                    System.out.print(node.data + " -> ");
                }
		print(node.left); //Traverse left
		print(node.right); //Traverse right
	}
	
	/**
	 * Inserts the given data into the tree. Notice that there are 3 cases:
	 * (i) data already appears in the tree and isRemoved = false for this node. In this case, do nothing.
	 * (ii) data already appears in the tree and isRemoved = true for this node. In this case, set isRemoved = false for the node.
	 * (iii) data does not appear in the tree. In this case, insert a new node as usual.
	 * @param data Data to be inserted
	 */
	public void insert(Type data) {
            if(root==null){ //When the tree is empty, we print the new data and increase the values as it adds.
		root=new BinaryNode<Type>(data);
		numNodes++;
		numTotalNodes++; 
	    }
	    if(contains(data)== true){ //If it is an existing value in the tree, it makes it underlined.
            BinaryNode<Type> dummy = find(data);
            if(dummy.isRemoved == true){
                dummy.isRemoved = false;
                numNodes++;
            }   
            else{
                //Do anything because the data is not in the tree.
            }
            }
            else if(contains(data) == false){ //If it is not present in the tree, it adds to the tree.Therefore increases the numbers.
                insert(data,root);
                numNodes++;
 		numTotalNodes++;
            }        
	}
        private BinaryNode<Type> insert(Type data, BinaryNode<Type> subTree){
		if(subTree == null) { //If the tree is empty, it adds new data.
			return new BinaryNode<>(data);
		}
		int comparison = data.compareTo(subTree.data); 
		if(comparison == 0){
                    //Do anything because the data is not in the tree.
		} 
                else if(comparison < 0){ //It compares to the left, as small values go to the left. 
			subTree.left = insert(data, subTree.left);
		} 
                else{ //It compares to the rigth, as small values go to the rigth.
			subTree.right = insert(data, subTree.right);
		}
		return subTree;
	}
	
	/**
	 * Removes the given data from the tree. Notice that there are 3 cases:
	 * (i) data does not appear in the tree. In this case, do nothing.
	 * (ii) data appears in the tree and isRemoved = false for this node. In this case, set isRemoved = true for the node.
	 * (iii) data appears in the tree and isRemoved = true for this node. In this case, do nothing.
	 * @param data
	 */
	public void remove(Type data) {
            if(!contains(data)){
                //Do anything because the data is not in the tree.
            }
            else if(contains(data)){
                BinaryNode dummy = find(data);
                if(dummy.isRemoved==true){
                    //Do anything because the data is not in the tree.
                }
                else if(dummy.isRemoved==false){ 
                    dummy.isRemoved = true;
                    numNodes--;
                }
            }
	}
        
	/**
	 * Removes all nodes of the BST that are marked removed.
	 * Resulting BST will have the same numNodes, and numNodes = numTotalNodes after the call.
	 */
	public void clearAllRemoved() { 
            findRemovedNodes(root);
	}
        
        private void findRemovedNodes(BinaryNode<Type> subTree){
        if(subTree == null){ //If subTree is null, so do nothing.
            return;
        }
        if(subTree.isRemoved == true){ //If found wanted node push to list.
            remove(subTree.data,root);
        }
        else{ //If not found, keep searching with recursive algorithm.
            findRemovedNodes(subTree.left);
            findRemovedNodes(subTree.right);
        }
        }
        private BinaryNode<Type> remove(Type data, BinaryNode<Type> subTree) {	
	if( subTree == null){ //Data not found,therefore,not removed.
            return subTree; 
	}
            int comparison = data.compareTo(subTree.data); //It compares the value we want to remove with the value in the tree.
	    if(comparison < 0){                            //Compares the left side if the value is small, and the right side if the value is greater.
		subTree.left = remove(data, subTree.left);
	    } 
            else if(comparison > 0){
		subTree.right = remove(data, subTree.right);
	    } 
            else{
		if(subTree.left != null && subTree.right != null){ //Has both two children
			                                            //Replace with either min of right or max of left
				                                    //Will replace with min of right
		    subTree.data = findMin(subTree.right).data;
		    subTree.right = remove(subTree.data, subTree.right);
		} 
                else if(subTree.left != null){
		    subTree = subTree.left;
		} 
                else{ //curr.right != null
		    subTree = subTree.right;
		}
	    }
	    return subTree;
	}
        
	private BinaryNode<Type> findMin(BinaryNode<Type> subTree){  //Returns null if the tree is empty.
                                                                     //Controls the left side of the tree because we are looking for the smallest value.
	if(subTree == null){                                        
		return null;
	} 
        else if(subTree.left == null){
		return subTree;
	}
	return findMin(subTree.left);
	}
}
