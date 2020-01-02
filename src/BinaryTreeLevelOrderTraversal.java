package src;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 102. 二叉树的层次遍历
 *
 * 给定一个二叉树，返回其按层次遍历的节点值。 （即逐层地，从左到右访问所有节点）。
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class BinaryTreeLevelOrderTraversal {
    /**
     * 借用队列层次遍历
     */
    public List<List<Integer>> solution1(TreeNode root) {
        List<List<Integer>> results = new ArrayList<>();

        if (root == null) {
            return results;
        }

        // 使用队列
        Queue<Pair<TreeNode, Integer>> queue = new LinkedList<>();

        // 根结点入队列
        queue.offer(new Pair<>(root, 0));
        // 当队列不为空时循环
        while (!queue.isEmpty()) {
            // 出队
            Pair<TreeNode, Integer> pair = queue.poll();
            TreeNode treeNode = pair.getKey();
            int level = pair.getValue();

            // 出队时，做记录
            if (results.size() > level) {
                results.get(level).add(treeNode.val);
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(treeNode.val);
                results.add(list);
            }

            // 子结点不为空时入队
            if (treeNode.left != null) {
                queue.offer(new Pair<>(treeNode.left, level + 1));
            }
            if (treeNode.right != null) {
                queue.offer(new Pair<>(treeNode.right, level + 1));
            }
        }
        return results;
    }

    /**
     * 同样是借用队列进行层次遍历，但每次循环直接处理一整层
     */
    public List<List<Integer>> solution2(TreeNode root) {
        List<List<Integer>> levels = new ArrayList<>();

        if (root == null) {
            return levels;
        }

        Queue<TreeNode> queue = new LinkedList<>();

        // 根结点入队列
        queue.offer(root);
        // 当前层级
        int level = 0;
        // 当队列不为空时循环
        while (!queue.isEmpty()) {
            // 到达新的一层，创建新列表
            levels.add(new ArrayList<>());

            // 下面代码确保一次循环直接处理一层
            // 每次循环，队列中存的是一层的结点数

            // 当前层的结点数
            int levelLength = queue.size();
            // 遍历当前层每个结点
            for (int i = 0; i < levelLength; i++) {
                // 出队
                TreeNode node = queue.poll();
                if (node != null) {
                    // 记录到当前层对应的列表中
                    levels.get(level).add(node.val);
                    if (node.left != null) {
                        queue.offer(node.left);
                    }
                    if (node.right != null) {
                        queue.offer(node.right);
                    }
                }
            }
            level++;
        }
        return levels;
    }



    /**
     * 递归解法
     * 中序遍历每一个结点，然后将其添加到对应的列表中去
     */
    public List<List<Integer>> solution3(TreeNode root) {
        if (root == null) {
            return results;
        }
        recursion(root, 0);
        return results;
    }

    List<List<Integer>> results = new ArrayList<>();

    /**
     * 将所属层级为 level 的结点 p 记录到 results 中
     */
    private void recursion(TreeNode p, int level) {
        if (p != null) {
            if (results.size() <= level) {
                // 首次遇见某元素
                results.add(new ArrayList<>());
            }
            // index与level对应
            results.get(level).add(p.val);

            // 先左后右处理子节点
            recursion(p.left, level + 1);
            recursion(p.right, level + 1);
        }
    }
}
