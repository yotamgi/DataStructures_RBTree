import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.List;


import junit.framework.TestCase;


public class TestRBTree extends TestCase {
	
	final int COUNT = 1000;
	
	public void testAdd() throws Exception {
		Random rnd = new Random();
		RBTree rb = new RBTree();
		int counter = 0;
		
		for (int i=0; i<100000; i++) {
			int curr = rnd.nextInt();
			int x = rb.insert(curr, Integer.toString(curr));
			if (x!=-1)
				counter+=x;
		}
		
		testTreeValid(rb);
	}
	
	public void testGetElements() throws Exception {
		final int NUM_OF_ELEMS = 10000;
		Random rnd = new Random();
		RBTree rb = new RBTree();
		int min = Integer.MAX_VALUE;
		
		int[] elems = new int[NUM_OF_ELEMS];
		for (int i=0; i<NUM_OF_ELEMS; i++) {
			elems[i] = rnd.nextInt();
			rb.insert(elems[i], Integer.toString(elems[i]));
			
			if (elems[i] < min)  min = elems[i];
		}
		
		int[] tree_elems = rb.keysToArray();

		assertEquals(Integer.toString(min), rb.min());
		assertEquals(elems.length, tree_elems.length);
		Arrays.sort(elems);
	
		for (int i=0; i<NUM_OF_ELEMS; i++) {
			assertEquals(tree_elems[i], elems[i]);
		}
	}
    private void testTreeSearch(RBTree rb, Integer[] keys, String[] values) {
        boolean success = true;
        for (int i = 0; i < keys.length; i++) {
            if (!rb.search(keys[i]).equals(values[i])){
                success = false;
                break;
            }
        }

        assertEquals(success, true);
    }

    private void testTreeSize(RBTree rb, int i) {
        assertEquals(rb.size(), i+1);
    }

    private void testTreeMin(RBTree rb, Integer[] keys) throws Exception {
        Set<Integer> set = new HashSet<>();
        set.addAll(Arrays.asList(keys));
        int minVal = Collections.min(set);

        assertEquals(rb.search(minVal), rb.min());

    }
    

    private void testTreeDel(RBTree rb, Integer[] keys) {
        Random rnd = new Random();
        List<Integer> keysL = new ArrayList<Integer>(Arrays.asList(keys));

        for (int i=0; i<COUNT; i++) {
            int curr = rnd.nextInt(keysL.size());
            rb.delete(keysL.get(curr));
            //GraphDraw.drawGraph(rb, "After deleting " + keysL.get(curr));
            testTreeValid(rb);
            keysL.remove(curr);
        }

        testTreeEmpty(rb, true);
    }

    private void testTreeEmpty(RBTree rb, boolean empty) {
        assertEquals(rb.empty(), empty);
    }


    private void testTreeMax(RBTree rb, Integer[] keys) throws Exception {
        Set<Integer> set = new HashSet<>();
        set.addAll(Arrays.asList(keys));
        int maxVal = Collections.max(set);

        assertEquals(rb.search(maxVal), rb.max());

    }

    private void testTreeKeys(RBTree rb, Integer[] keys) {
        Set<Integer> set = new HashSet<>();
        set.addAll(Arrays.asList(keys));
        keys = set.toArray(new Integer[0]);
        Arrays.sort(keys);

        int[] treeKeys = rb.keysToArray();
        for (int i = 0; i < keys.length; i++)
            assertEquals(treeKeys[i], (int) keys[i]);
    }
	
	public void testDelete() throws Exception {
		Random rnd = new Random();
		RBTree rb = new RBTree();
		int counter = 0;
		
		for (int i=0; i<10000; i++) {
			int curr = rnd.nextInt();
			rb.insert(curr, Integer.toString(curr));
		}
		
		assertEquals(10000, rb.keysToArray().length);
		int[] keys = rb.keysToArray();					
		int curr = rnd.nextInt();
		curr = 1345;
		
		for (int i=0; i<1000; i++) {	
			int x = rb.delete(keys[curr+2*i]);			
			counter += x;
			curr++;
		}
		System.out.println(counter);
		
		testTreeValid(rb);
	}
	
	public void testBegam() throws Exception  { 
        Random rnd = new Random();
        RBTree rb = new RBTree();
        Integer[] keys = new Integer[COUNT];
        String[] values = new String[COUNT];

        for (int i=0; i<COUNT; i++) {
            int curr = rnd.nextInt(Integer.MAX_VALUE-1);
            rb.insert(curr, Integer.toString(curr));
            keys[i] = curr;
            values[i] = Integer.toString(curr);
            testTreeSize(rb, i);
            testTreeEmpty(rb, false);
        }

        testTreeValid(rb);
        testTreeKeys(rb, keys);
        testTreeMax(rb, keys);
        testTreeMin(rb, keys);
        testTreeSearch(rb, keys, values);

        testTreeDel(rb, keys);
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
