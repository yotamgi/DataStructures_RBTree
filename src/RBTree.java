/**
 *
 * RBTree
 *
 * An implementation of a Red Black Tree with
 * non-negative, distinct integer keys and values
 *
 */
public class RBTree {
	
	/**
	 * A default node with key=MAX_INT
	 */
	RBNode dummyNode = new RBNode(0, "", null, null, null, false);
	RBNode head = new RBNode(Integer.MAX_VALUE, "", dummyNode, dummyNode, null, false);
	int numberOfNodes = 0;
	
	/**
	 * public boolean empty()
	 *
	 * returns true if and only if the tree is empty
	 *
	 */
	public boolean empty() {
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
	public String search(int k)
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
	public int insert(int k, String v) {
		
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
	   numberOfNodes++;
	   return insertFixup(z);
	}
	
	private int insertFixup(RBNode z) {
		RBNode y;
		int numOfRotations = 0;

		while (z.parent.color == true) { // z.p.color == RED
			
			// if the father is a left child
			if (z.parent == z.parent.parent.left) {
				
				// CASE 1
				y = z.parent.parent.right; // uncle
				if (y.color == true) { 
					z.parent.color = false;
					y.color = false;
					z.parent.parent.color = true;
					
					z = z.parent.parent;
				} else {
					if (z == z.parent.right) {
						// CASE 2
						z = z.parent;
						leftRotate(z);
						numOfRotations++;
					}
					// CASE 3
					z.parent.color = false;
					z.parent.parent.color = true;
					rightRotate(z.parent.parent);
					numOfRotations++;
				}
			}
			
			// if the father is a right child
			else { 
				
				// CASE 1
				y = z.parent.parent.left; // uncle 
				if (y.color == true) { 
					z.parent.color = false;
					y.color = false;
					z.parent.parent.color = true;
					
					z = z.parent.parent;
				} else {
					if (z == z.parent.left) {
						// CASE 2
						z = z.parent;
						rightRotate(z);
						numOfRotations++;
					}
					// CASE 3
					z.parent.color = false;
					z.parent.parent.color = true;
					leftRotate(z.parent.parent);
					numOfRotations++;
				}
			}
		}
		
		// fix the real head to be black
		head.left.color = false;
		
		return numOfRotations;
	}
	
	private void leftRotate(RBNode x) {
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
	
	private void rightRotate(RBNode x) {
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
	public int delete(int k) // really isn't working, need to be completed
	{ 
		RBNode z = treePosition(k, head);		   
		if (z.key != k) 
		   return -1;
		
		RBNode y;
		if (IsLeaf(z.left) || IsLeaf(z.right)){
			y = z;
		} else{
			y = findSuccesor(z);
		}
		
		RBNode x;
		if (!IsLeaf(y.left)){
			x = y.left;
		}else {
			x = y.right;
		}
		if (!IsLeaf(x))
			x.parent = y.parent;
		if (y.parent.equals(head)){
			head.left = x;
		} else {
			if (AmILeft(y)){
				y.parent.left = x;
			} else {
				y.parent.right = x;
			}
		}
		
		if (!y.equals(z))
			z.key = y.key;
		numberOfNodes--;
		if (y.color == false)
			return deleteFixup(x);
		return 0;
	}


private int deleteFixup(RBNode x) {
		// TODO Auto-generated method stub
		return 4;
	}

/**
    * public String min()
    *
    * Returns the value of the item with the smallest key in the tree,
    * or null if the tree is empty
    */
   public String min()
   {
	   if (this.empty())
		   return null;			 
	   RBNode x = getMin();
	   return x.val;
   }
   
   public RBNode getMin()
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
   public String max()
   {
	   if (this.empty())
		   return null;
	   RBNode x = getMax();
	   return x.val;
   }

   public RBNode getMax()
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
  public int[] keysToArray()
  {
        int[] arr = new int[0];
        if (empty())
        	return arr;
        arr = new int[numberOfNodes];
        RBNode x = getMin();
        RBNode max = getMax();
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
  public String[] valuesToArray()
  {
	  String[] arr = new String[0];
      if (empty())
      	return arr;
      arr = new String[numberOfNodes];
      RBNode x = getMin();
      RBNode max = getMax();
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
   public int size()
   {
	   return this.numberOfNodes;
   }
   
   public boolean IsLeaf (RBNode node){
	   if (node.key != 0)
		   return false;
	   if (node.left != null)
		   return false;
	   if (node.right != null)
		   return false;
	   return true;
   }
   
   public boolean AmILeft (RBNode node){
	   RBNode parent = node.parent;
	   return parent.left.equals(node);
   }
   
   public RBNode findSuccesor(RBNode node) {
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
   
   private RBNode treePosition(int key, RBNode node) {

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

	  
	  void setRight(RBNode r) {
		  right = r;
		  r.parent = this;
	  }
	  void setLeft(RBNode l) {
		  left = l;
		  l.parent = this;
	  }

  }

}