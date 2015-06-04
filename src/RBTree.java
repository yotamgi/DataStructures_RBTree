

/**
 *
 * RBTree
 *
 * An implementation of a Red Black Tree with
 * non-negative, distinct integer keys and values
 *
 *@author Yotam Gigi 
 *@user yotamgigi
 *@id 303053144
 *
 *
 *@author Ofer Privman
 *@user oferprivman
 *@id 304991714
 *
 *
 */
public class RBTree {
	
	/**
	 * A default node with key=MAX_INT
	 */
	RBNode dummyNode = new RBNode(0, "", null, null, null, false);
	RBNode head = new RBNode(Integer.MAX_VALUE, "", dummyNode, dummyNode, null, false);
	int numberOfNodes = 0;	//holds the current number of nodes  
	RBNode maximum;			//holds the current maximum
	RBNode minimum;			//holds the current minimum
	
	
	/**
	 * public boolean empty()
	 *
	 * returns true if and only if the tree is empty
	 *
	 */
	public boolean empty() { //O(1)
		if (this.numberOfNodes == 0)
			return true;
		return false;
	}

	/**
	 * public String search(int k)
	 *
	 * returns the value of an item with key k if it exists in the tree
	 * otherwise, returns null
	 */
	public String search(int k) //O(logn)
	{
		if (numberOfNodes == 0)
			return null;
		RBNode place = treePosition(k, head.left);
		if (IsLeaf(place))
			return null;
		if (place.key == k)
			return place.val;
		return null;
	}

    /**
	 * public int insert(int k, String v)
	 *
	 * inserts an item with key k and value v to the red black tree.
	 * the tree must remain valid (keep its invariants).
	 * returns the number of color switches, or 0 if no color switches were necessary.
	 * returns -1 if an item with key k already exists in the tree.
	 */
	public int insert(int k, String v) { //O(logn)
		
	   // insert the node in a normal binary tree way
	   RBNode y = treePosition(k, head);
	   
	   if (y.key == k) {
		   return -1;
	   }
	   RBNode z = new  RBNode(k, v, dummyNode, dummyNode, y);
	   
	   if (k < y.key) {
		   y.setLeft(z);
	   } else {
		   y.setRight(z);
	   }
	   if (numberOfNodes == 0){
		   minimum = z;
		   maximum = z;
	   } else {
		   if (minimum.key>k)
			   minimum = z;
		   if (maximum.key<k)
			   maximum = z;
	   }	   
	   numberOfNodes++;
	   return insertFixup(z);
	}
	
	private int insertFixup(RBNode z) {
		RBNode y;
		int counter = 0;

		while (z.parent.color == true) { // z.p.color == RED
			
			// if the father is a left child
			if (z.parent == z.parent.parent.left) {
				
				// CASE 1
				y = z.parent.parent.right; // uncle
				if (y.color == true) { 
					counter++;
					z.parent.color = false;
					counter++;
					y.color = false;
					if (!z.parent.parent.color)
						counter++;
					z.parent.parent.color = true;
					
					z = z.parent.parent;
				} else {
					if (z == z.parent.right) {
						// CASE 2
						z = z.parent;
						leftRotate(z);
					}
					// CASE 3
					if (z.parent.color)
						counter++;
					z.parent.color = false;
					if (!z.parent.parent.color)
						counter++;
					z.parent.parent.color = true;
					rightRotate(z.parent.parent);
				}
			}
			
			// if the father is a right child
			else { 
				
				// CASE 1
				y = z.parent.parent.left; // uncle 
				if (y.color == true) { 
					if(z.parent.color)
						counter++;
					z.parent.color = false;
					counter++;
					y.color = false;
					if (!z.parent.parent.color)
						counter++;
					z.parent.parent.color = true;
					
					z = z.parent.parent;
				} else {
					if (z == z.parent.left) {
						// CASE 2
						z = z.parent;
						rightRotate(z);
					}
					// CASE 3
					if(z.parent.color)
						counter++;
					z.parent.color = false;
					if(!z.parent.parent.color)
						counter++;
					z.parent.parent.color = true;
					leftRotate(z.parent.parent);
				}
			}
		}
		
		// fix the real head to be black
		if (head.left.color)
			counter++;
		head.left.color = false;
		
		return counter;
	}
	
	private void leftRotate(RBNode x) { //O(1)
		RBNode y = x.right;
		
		// transplant(x, y)
		if (x == x.parent.left) {
			x.parent.setLeft(y);
		} else {
			x.parent.setRight(y);
		}
		
		x.setRight(y.left);
		y.setLeft(x);
	}
	
	private void rightRotate(RBNode x) { //O(1)
		RBNode y = x.left;
		
		// transplant(x, y)
		if (x == x.parent.left) {
			x.parent.setLeft(y);
		} else {
			x.parent.setRight(y);
		}
		
		x.setLeft(y.right);
		y.setRight(x);
		
	}
	
	/**
	 * public int delete(int k)
	 *
	 * deletes an item with key k from the binary tree, if it is there;
	 * the tree must remain valid (keep its invariants).
	 * returns the number of color switches, or 0 if no color switches were needed.
	 * returns -1 if an item with key k was not found in the tree.
	 */
	public int delete(int k) //O(logn)
	{ 
		RBNode z = treePosition(k, head);//special case- 		   
		if (z.key != k)         //element does not exist
		   return -1;
		
		RBNode y;  // y is the element we will delete eventually
		if ((z.left.equals(dummyNode)) || (z.right.equals(dummyNode))){
			y = z;		//either the original	
		} else {
			y = findSuccesor(z);  //or its successor
		}
		
		RBNode x;  // making pointer for the child of y: which is x
		if (!y.left.equals(dummyNode)){
			x = y.left;
		} else {
			x = y.right;
		} 
			
		x.parent = y.parent;	//linking the grandson to the grandfather 
		if ((y.parent.equals(head))||(AmILeft(y))){
			y.parent.left = x;	// and vice versa
		} else {
				y.parent.right = x;			
		}
		
		if (!y.equals(z)) // copying data in case we delete the successor
			z.key = y.key;
			z.val = y.val;
		
		int counter = 0;	
		if (y.color == false) {	//fixing the tree if we deleted black		
			counter = deleteFixup(x);
		}
		
		if (size() != 1){ //checking in case we deleted the max or min		
			if (k == minimum.key)
				minimum = getMin();
			if (k == maximum.key)
				maximum = getMax();
		}
		
		numberOfNodes--;
		return counter;
	}


	private int deleteFixup(RBNode x) { //O(logn)
		int counter = 0;
		RBNode brother;
		RBNode parent = x.parent;
		
		if (size() == 1)
			return 0;    //special case, we just removed the last element
		
		while ((!x.equals(head.left)) && (x.color == false)){

			if (x.equals(parent.left)){	// initializing the brother
				brother = parent.right;
			} else {
				brother = parent.left;
			}	
			
			if (brother.color == true){ //case 1: when brother is red
				counter++;
				brother.color = false;			
				if (!x.parent.color)
					counter++;
				x.parent.color = true;
				if (AmILeft(x)){
					leftRotate(parent);
					brother = parent.right;
				} else {
					rightRotate(parent);
					brother = parent.left;
				}//brother is black in both cases - leads to cases 2,3 or 4
			}	
			
			if ((brother.left.color == false) && (brother.right.color == false)){
				
				//case 2: both children of brother are black
				if (!brother.color)
					counter++;
				brother.color = true;
				if (!parent.parent.equals(head)){
					x = x.parent; //pushing the blackness problem upwards
					parent = x.parent;
				} else{
					break;	//we reached the head, problem solved
				}
			} else {
				if ((x.equals(parent.left))&&(brother.right.color == false)){
					//case 3 left: brother and brother.right are black 
					//brother.left is red
					if (brother.left.color)	
						counter++;
					brother.left.color = false;
					if (!brother.color)
						counter++;
					brother.color = true;
					rightRotate(brother);
					brother = parent.right; //leads to case 4 left
				} else if ((x.equals(parent.right))&&(brother.left.color == false)){
					//case 3 left: brother and his left son are black 
					//his right son is red					
					if (brother.right.color)
						counter++;
					brother.right.color = false;
					if (!brother.color)
						counter++;
					brother.color = true;
					leftRotate(brother);
					brother = parent.left; //leads to case 4 right
				}	
				if (parent.left.equals(x)){ 
					//case 4 left: brother is black, brother.right red					
					if (brother.color != parent.color){
						counter++;
						brother.color = parent.color;
					}
					if (parent.color = true){
						counter++;
						parent.color = false;
					}
					if (brother.right.color)
						counter++;
					brother.right.color = false;
					leftRotate(parent);
					break;
				}
				 else if (parent.right.equals(x)){
					 //case 4 right: brother is black, brother.left red
					if (brother.color != parent.color){
						counter++;
						brother.color = parent.color;
					}
					if (parent.color = true){
						counter++;
						parent.color = false;
					}
					if (brother.left.color)
						counter++;
					brother.left.color = false;
					rightRotate(parent);
					break;
				}
			}
		}
		if (x.color == true){
			counter++;
			x.color = false;
		}	
		return counter;
	}

	/**
     * public String min()
     *
     * Returns the value of the item with the smallest key in the tree,
     * or null if the tree is empty
     */
	public String min() //O(1)
	{
	   if (this.empty())
		   return null;			 
	   return minimum.val;
	}
   
	public RBNode getMin() //O(logn)
	{
	   RBNode x = head;
	   while (this.IsLeaf(x.left)== false)
		   x = x.left;
	   return x;
	}
	
	/**
	 * public String max()
	 *
	 * Returns the value of the item with the largest key in the tree,
	 * or null if the tree is empty
	 */
	public String max() // O(1)
	{
	   if (this.empty())
		   return null;
	   return maximum.val;
	}
	
	public RBNode getMax() //O(logn)
	{
	   RBNode x = head;
	   x = x.left;
	   while (this.IsLeaf(x.right)== false)
		   x = x.right;
	   return x;
	}
   
  /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   */
  public int[] keysToArray()   //O(n)
  {
        int[] arr = new int[0];
        if (empty())
        	return arr;
        arr = new int[numberOfNodes];
        RBNode x = minimum;
        RBNode max = maximum;
        int counter = 0;
        while (x.key < max.key){
        	arr[counter] = x.key;
        	x = findSuccesor(x);
        	counter++;
        }
        arr[counter] = max.key;
        return arr;
  }

  /**
   * public String[] valuesToArray()
   *
   * Returns an array which contains all values in the tree,
   * sorted by their respective keys,
   * or an empty array if the tree is empty.
   */
  public String[] valuesToArray()   //O(n)
  {
	  String[] arr = new String[0];
      if (empty())
      	return arr;
      arr = new String[numberOfNodes];
      RBNode x = minimum;
      RBNode max = maximum;
      int counter = 0;
      while (x.key < max.key){
      	arr[counter] = x.val;
      	x = findSuccesor(x);
      	counter++;
      }
      arr[counter] = max.val;
      return arr;
  }

   /**
    * public int size()
    *
    * Returns the number of nodes in the tree.
    *
    * precondition: none
    * postcondition: none
    */
   public int size()  //O(1)
   {
	   return this.numberOfNodes;
   }
   
   public boolean IsLeaf (RBNode node){  //O(1)
	   if (node.key != 0)
		   return false;
	   if (node.left != null)
		   return false;
	   if (node.right != null)
		   return false;
	   return true;
   }
   
   public boolean AmILeft (RBNode node){  //O(1)
	   RBNode parent = node.parent;
	   return parent.left.equals(node);
   }
   
   public RBNode findSuccesor(RBNode node) { //O(logn)
	   RBNode x;
	   if (IsLeaf(node.right)== false){
		   x = node.right;
		   while (IsLeaf(x.left)== false)
			   x = x.left;
		   return x;
	   }
	   if (AmILeft(node) == true)
		   return node.parent;
	   x = node.parent;
	   while (AmILeft(x) == false)
		   x = x.parent;
	   return x.parent;
   }
   
   private RBNode treePosition(int key, RBNode node) {  //O(logn)

	   if (key == node.key) {
		   return node;
	   }
	   else if (key < node.key) {
		   if (node.left == dummyNode) {
			   return node;
		   } else {
			   return treePosition(key, node.left);
		   }
	   } else {
		   if (node.right == dummyNode) {
			   return node;
		   } else {
			   return treePosition(key, node.right);
		   } 
	   }
   }

  /**
   * public class RBNode
   *
   * If you wish to implement classes other than RBTree
   * (for example RBNode), do it in this file, not in 
   * another file.
   * This is an example which can be deleted if no such classes are necessary.
   */
  public class RBNode{
	  int key;
	  String val;
	  RBNode left;
	  RBNode right;
	  RBNode parent;
	  boolean color; // false = black
	  
	  /**
	   * Useful constructor for the RBnode.
	   * @param _key
	   * @param _val
	   * @param _left
	   * @param _right
	   */
	  RBNode(int _key, String _val, RBNode _left, RBNode _right, RBNode _parent) {
		  key = _key;
		  val = _val;
		  left = _left;
		  right = _right;
		  parent = _parent;
		  color = true;
	  }
	  
	  RBNode(int _key, String _val, RBNode _left, RBNode _right, RBNode _parent, boolean _color) {
		  key = _key;
		  val = _val;
		  left = _left;
		  right = _right;
		  parent = _parent;
		  color = _color;
	  }

	  
	  void setRight(RBNode r) {  //O(1)
		  right = r;
		  r.parent = this;
	  }
	  void setLeft(RBNode l) {   //O(1)
		  left = l;
		  l.parent = this;
	  }

  }

}