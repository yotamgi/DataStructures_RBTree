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
	
	/**
	 * public boolean empty()
	 *
	 * returns true if and only if the tree is empty
	 *
	 */
	public boolean empty() {
		return false; // to be replaced by student code
	}

	/**
	 * public String search(int k)
	 *
	 * returns the value of an item with key k if it exists in the tree
	 * otherwise, returns null
	 */
	public String search(int k)
	{
		return "42";  // to be replaced by student code
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
	public int delete(int k)
	{
		return 42;	// to be replaced by student code
	}

   /**
    * public String min()
    *
    * Returns the value of the item with the smallest key in the tree,
    * or null if the tree is empty
    */
   public String min()
   {
	   return "42"; // to be replaced by student code
   }

   /**
    * public String max()
    *
    * Returns the value of the item with the largest key in the tree,
    * or null if the tree is empty
    */
   public String max()
   {
	   return "42"; // to be replaced by student code
   }

  /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   */
  public int[] keysToArray()
  {
        int[] arr = new int[42]; // to be replaced by student code
        return arr;              // to be replaced by student code
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
        String[] arr = new String[42]; // to be replaced by student code
        return arr;                    // to be replaced by student code
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
	   return 42; // to be replaced by student code
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