
public class RBMain {

	public static void main(String[] args)
	{
		RBTree rb = new RBTree();
		
		// according to https://www.youtube.com/watch?v=rcDF8IqTnyI
		rb.insert(10, "a");
		rb.insert(20, "a");
		rb.insert(30, "a");
		rb.insert(40, "a");
		rb.insert(50, "a");
		rb.insert(15, "a");
		rb.insert(18, "a");
		rb.insert(25, "a");
		rb.insert(38, "a");
		rb.insert(28, "a");
		
		GraphDraw.drawGraph(rb, "After all");
	}

}
