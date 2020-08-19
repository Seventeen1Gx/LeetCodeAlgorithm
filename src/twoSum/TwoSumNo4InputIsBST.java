package src.twoSum;

import src.util.TreeNode;

/**
 * 653. 两数之和Ⅳ - 输入BST
 *
 * 给定一个二叉搜索树和一个目标结果，如果 BST 中存在两个元素且它们的和等于给定的目标结果，则返回 true。
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class TwoSumNo4InputIsBST {
    /**
     * 方法一：将 BST 转化成 Set，使用哈希表方法。
     * 方法二：中序遍历 BST，得到有序列表，然后使用双指针法。
     * 方法三：遍历每个结点，每遍历到一个结点，在整个树上搜索是否存在 target - node.val
     *
     * 下面是方法三的代码
     */
    public boolean solution(TreeNode root, int target) {
        return recursion(root, root, target);
    }

    /**
     * @param node 选定的两数之一
     * @param root BST 的根结点
     * @param target 两数之和的目标值
     * @return 返回 root 表示的 BST 上是否存在值为 target - node.val 的结点
     */
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

    /**
     * @param root 一棵 BST 的根结点
     * @param target 搜索目标值
     * @return 返回 root 表示的 BST 上值为 target 的结点引用
     */
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


