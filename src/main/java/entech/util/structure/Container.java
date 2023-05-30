package entech.util.structure;

import java.util.List;
import java.util.ArrayList;
public class Container implements Node {
    List<Node> nodes = new ArrayList<Node>();
    
    public void addNode(Node node) {
        nodes.add(node);
    }

    public void removeNode(int nodeIndex) {
        nodes.remove(nodeIndex);
    }

    public Node getNode(int nodeIndex) {
        return nodes.get(nodeIndex);
    }

    public void Operation(Object input) {
        for (Node node : nodes) {
            node.Operation(input);
        }
    }
}
