import java.util.Random;

import junit.framework.TestCase;


public class TestRBTree extends TestCase {
	
	public void testAdd() throws Exception {
		Random rnd = new Random();
		RBTree rb = new RBTree();
		
		for (int i=0; i<100000; i++) {
			int curr = rnd.nextInt();
			rb.insert(curr, Integer.toString(curr));
		}
		
		testTreeValid(rb);
	}

	private void testTreeValid(RBTree rb) {
		Random rnd = new Random();
		
		// validate that the RBTree assumtions are preserved
		int numBlacksInRoute = -1;
		for (int i=0; i<1000; i++) {
			
			// go through a specific route in the tree
			int numBlacks = 0;
			boolean prevRed = false;
			RBTree.RBNode curr = rb.head.left;
			while (curr != null) {
				if(curr.color == false) { 
					numBlacks++;
					prevRed = false;
				} else {
					if (prevRed) fail();
				}
				curr = rnd.nextBoolean()?curr.left:curr.right;
			}
			
			if (numBlacksInRoute == -1) numBlacksInRoute = numBlacks;
			else assertEquals(numBlacksInRoute, numBlacks);
		}
	}

}
