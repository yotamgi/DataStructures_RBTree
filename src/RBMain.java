
public class RBMain {

	public static void main(String[] args)
	{
		RBTree rb = new RBTree();
		
		// according to https://www.youtube.com/watch?v=rcDF8IqTnyI
		
		rb.insert(10, "a");

		rb.insert(20, "b");
		
		rb.insert(30, "c");
		
		rb.insert(40, "d");
		
		rb.insert(50, "e");
		
		rb.insert(15, "f");
		
		rb.insert(18, "g");
		
		rb.insert(25, "h");
		
		rb.insert(38, "i");
			
		rb.insert(8, "j");
		
		GraphDraw.drawGraph(rb, "After all");
		
		int[] tmp = rb.keysToArray();
		String[] tmp2 = rb.valuesToArray();
		for (int i=0;i<10;i++){
			System.out.println("" + tmp[i] +" " + tmp2[i]);
		}	
		
	}

}
