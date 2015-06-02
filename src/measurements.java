import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


public class measurements {

	public static void main(String[] args) throws IOException {
		
		for (int i = 1; i < 11; i++) {
			int n = i * 10000;
			double[] averages = new double[2];
			averages = experiment(i);
			averages[0] = averages[0]/(n);
			averages[1] = averages[1]/(n);
			System.out.println("" + n + ": average num of switches for insert = " + averages[0] + "; average num of switches for delete = " + averages[1]);
			
		}
		
		
	}

	public static double[] experiment(int i) {
		Random rnd = new Random();
		RBTree rb = new RBTree();
		double[] numOfSwitches = new double[2];

		// insert n=i*10000 random items to rb
		for (int j = 0; j < i * 10000; j++) {
			int curr = rnd.nextInt(Integer.MAX_VALUE - 1);
			numOfSwitches[0] += rb.insert(curr, Integer.toString(curr));
		}

		// delete all items from rb from the min value
		while (!rb.empty()) {
			RBTree.RBNode node = rb.getMin();
			numOfSwitches[1] += rb.delete(node.key);
		}
		return numOfSwitches;

	}

}
