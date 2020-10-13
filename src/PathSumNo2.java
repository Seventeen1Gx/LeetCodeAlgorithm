package src;

import src.util.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 113. 路径总和Ⅱ
 *
 * 给定一个二叉树和一个目标和，找到所有从根节点到叶子节点路径总和等于给定目标和的路径。
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class PathSumNo2 {
    public List<List<Integer>> solution1(TreeNode root, int sum) {
        recursion(root, sum, new ArrayList<>());
        return results;
    }

    private List<List<Integer>> results = new ArrayList<>();

    /**
     * 当前走到 p 结点，总和还剩 sum，当前走过的结点列表为 result
     * recursion 保证执行完毕后，result 的内容没发生改变 → 进来时什么样，出去时什么样
     */
    private void recursion(TreeNode p, int sum, List<Integer> result) {
        if (p == null) {
            return;
        }

        result.add(p.val);
        if (p.left == null && p.right == null && p.val == sum) {
            results.add(new ArrayList<>(result));
        } else {
            // 向左走
            recursion(p.left, sum - p.val, result);

            // 向右走
            recursion(p.right, sum - p.val, result);
        }
        result.remove(result.size() - 1);
    }

    public List<List<Integer>> solution2(TreeNode root, int sum) {
        List<List<Integer>> ans = new ArrayList<>();

        if (root == null) {
            return ans;
        }

        // 用栈记录来模拟递归方法
        Stack<TreeNode> nodeStack = new Stack<>();
        Stack<Integer> remainStack = new Stack<>();
        //还需要多一个栈来记录 list
        Stack<List<Integer>> listStack = new Stack<>();

        nodeStack.push(root);
        remainStack.push(sum - root.val);
        listStack.push(new ArrayList<>());

        while (!nodeStack.empty()) {
            // 访问当前结点
            TreeNode node = nodeStack.pop();
            int remain = remainStack.pop();
            List<Integer> list = listStack.pop();

            if (node.left == null && node.right == null && remain == 0) {
                // 走到叶结点，满足要求
                list.add(node.val);
                ans.add(new ArrayList<>(list));
            }

            if (node.left != null) {
                // 向左走
                nodeStack.push(node.left);
                remainStack.push(remain - node.left.val);
                List<Integer> newList = new ArrayList<>(list);
                newList.add(node.val);
                listStack.push(newList);
            }
            if (node.right != null) {
                // 向右走
                nodeStack.push(node.right);
                remainStack.push(remain - node.right.val);
                List<Integer> newList = new ArrayList<>(list);
                newList.add(node.val);
                listStack.push(newList);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(4);
        root.right = new TreeNode(8);
        root.left.left = new TreeNode(11);
        root.left.left.left = new TreeNode(7);
        root.left.left.right = new TreeNode(2);
        root.right.left = new TreeNode(13);
        root.right.right = new TreeNode(4);
        root.right.right.left = new TreeNode(5);
        root.right.right.right = new TreeNode(1);

        PathSumNo2 p = new PathSumNo2();
        p.solution1(root, 22);
    }
}
