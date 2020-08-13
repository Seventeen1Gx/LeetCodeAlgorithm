// 133. 克隆图
//
// 给你无向 连通 图中一个节点的引用，请你返回该图的 深拷贝（克隆）。
//
// 图中的每个节点都包含它的值 val（int） 和其邻居的列表（list[Node]）。
//
// class Node {
//    public int val;
//    public List<Node> neighbors;
// }
// 
//
// 测试用例格式：
// 简单起见，每个节点的值都和它的索引相同。
// 例如，第一个节点值为 1（val = 1），第二个节点值为 2（val = 2），以此类推。该图在测试用例中使用邻接列表表示。
//
// 邻接列表 是用于表示有限图的无序列表的集合。每个列表都描述了图中节点的邻居集。
//
// 给定节点将始终是图中的第一个节点（值为 1）。你必须将 给定节点的拷贝 作为对克隆图的引用返回。
//
// 注意：
// 节点数不超过 100 。
// 每个节点值 Node.val 都是唯一的，1 <= Node.val <= 100。
// 无向图是一个简单图，这意味着图中没有重复的边，也没有自环。
// 由于图是无向的，如果节点 p 是节点 q 的邻居，那么节点 q 也必须是节点 p 的邻居。
// 图是连通图，你可以从给定节点访问到所有节点。


package src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CloneGraph {
    // 递归拷贝

    // set 记录已拷贝的结点，避免重复
    Set<Node> set = new HashSet<>(100);

    public Node cloneGraph(Node node) {
        if (node == null) {
            return null;
        }

        for (Node hasCreatedNode : set) {
            if (hasCreatedNode.val == node.val) {
                return hasCreatedNode;
            }
        }

        Node newNode = new Node(node.val, new ArrayList<>());
        set.add(newNode);
        for (Node neighbor : node.neighbors) {
            newNode.neighbors.add(cloneGraph(neighbor));
        }
        return newNode;
    }


    class Node {
        public int val;
        public List<Node> neighbors;

        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }

    public static void main(String[] args) {
        CloneGraph c = new CloneGraph();
        Node node1 = c.new Node(1, new ArrayList<>());
        Node node2 = c.new Node(2, new ArrayList<>());
        Node node3 = c.new Node(3, new ArrayList<>());
        Node node4 = c.new Node(4, new ArrayList<>());

        node1.neighbors.add(node2);
        node1.neighbors.add(node4);

        node2.neighbors.add(node1);
        node2.neighbors.add(node3);

        node3.neighbors.add(node2);
        node3.neighbors.add(node4);

        node4.neighbors.add(node1);
        node4.neighbors.add(node3);

        c.cloneGraph(node1);
    }
}


