// 653. 两数之和Ⅳ-输入BST
//
// 给定一个二叉搜索树和一个目标结果，如果 BST 中存在两个元素且它们的和等于给定的目标结果，则返回 true。

package src;

public class TwoSumNo4InputIsBST {
    // 一种方法是将BST转化成Set，然后用哈希表方法
    // 一种方法是中序遍历BST得到有序列表，然后用双指针
    // 第三种方法
    // 由于树结构的天生递归性，我们想到用递归的方法
    // 中序遍历，遍历到的每个结点作为第一个结点node，然后用target - node.val在整个树搜寻另一个结点
    // 其实也是试验了第一个结点的每种可能
    public boolean solution(TreeNode root, int target) {
        return recursion(root, root, target);
    }

    // node是我们确定的其中一个结点，另一个结点在root表示的树中搜寻
    private boolean recursion(TreeNode node, TreeNode root, int target) {
        if (node == null) {
            return false;
        }
        int anotherNodeVal = target - node.val;
        TreeNode anotherNode = search(root, anotherNodeVal);
        if (anotherNode != null && anotherNode != node) {
            return true;
        } else {
            return recursion(node.left, root, target) || recursion(node.right, root, target);
        }
    }

    // BST中查找具有target值的结点
    private TreeNode search(TreeNode root, int target) {
        if (root == null) {
            return null;
        } else if (root.val == target) {
            return root;
        } else if (root.val > target) {
            return search(root.left, target);
        } else {
            return search(root.right, target);
        }
    }
}


// Definition for a binary tree node.
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}
