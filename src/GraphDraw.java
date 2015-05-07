

import javax.swing.JFrame;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class GraphDraw extends JFrame
{

	private static final long serialVersionUID = -2707712944901661771L;
	mxGraph graph;

	public GraphDraw(RBTree rb, String name)
	{
		super(name);

		graph = new mxGraph();
		Object parent = graph.getDefaultParent();

		graph.getModel().beginUpdate();
		try {
			addNodeToGraph(rb.head.left, parent, 0, 400);
		}
		finally {
			graph.getModel().endUpdate();
		}

		mxGraphComponent graphComponent = new mxGraphComponent(graph);
		getContentPane().add(graphComponent);
	}
	
	void addNodeToGraph(RBTree.RBNode node, Object parent, int rank, double x) {
		if (node != null) {
			String str = Integer.toString(node.key) + ":" + node.val;
			Object self = graph.insertVertex(null, null, str, x, 20 + 40*rank, 30, 30, 
					"fillColor=" + (node.color?"red":"black")+";fontColor=white");
			addNodeToGraph(node.right, self, rank+1, x + (400/Math.pow(2, rank))/2);
			addNodeToGraph(node.left,  self, rank+1, x - (400/Math.pow(2, rank))/2);
			
			graph.insertEdge(parent, null, "", parent, self);
		}
	}
	
	public static void drawGraph(RBTree rb, String name) {
		GraphDraw frame = new GraphDraw(rb, name);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setVisible(true);		
	}
}