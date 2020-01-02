package src;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 107. 二叉树的层次遍历Ⅱ
 *
 * 给定一个二叉树，返回其节点值自底向上的层次遍历。 （即按从叶子节点所在层到根节点所在的层，逐层从左向右遍历）
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class BinaryTreeLevelOrderTraversalNo2 {
    /**
     * 递归解法，类似第 102 题，只是添加顺序发生改变
     */
    public List<List<Integer>> solution1(TreeNode root) {
        if (root == null) {
            return results;
        }
        recursion(root, 0);
        return results;
    }

    List<List<Integer>> results = new ArrayList<>();

    /**
     * 将所属层级为 level 的结点 p 记录到 results 中
     * 关键在于确定 index 与 level 的关系
     */
    private void recursion(TreeNode p, int level) {
        if (p != null) {
            if (results.size() <= level) {
                // 首次遇见某层元素，前端插入一个新的空列表
                // set(index, Object) 才是替换 index 处的元素为 Object
                results.add(0, new ArrayList<>());
            }
            // 当前若遍历到深度为 4 的元素，则 results 肯定有 5 个元素，这是上面这个 if 语句确定的
            // 此时对应关系
            // index: 0 1 2 3 4
            // level: 4 3 2 1 0
            // 这时，index = 4 - level
            // 4 是 results 的末尾元素下标，即 results.size-1
            // 故添加语句如下
            results.get(results.size() - 1 - level).add(p.val);

            // 先左后右处理子节点
            recursion(p.left, level + 1);
            recursion(p.right, level + 1);
        }
    }

    /**
     * 使用队列
     * 每次子列表从头插入到 results 中
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
            List<Integer> subList = new ArrayList<>();

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
                    subList.add(node.val);
                    if (node.left != null) {
                        queue.offer(node.left);
                    }
                    if (node.right != null) {
                        queue.offer(node.right);
                    }
                }
            }
            // 每次子循环结束，subList 为 level 层元素从左往右组成的列表
            // 该列表每次从头部添加到 levels 中
            levels.add(0, subList);
            level++;
        }
        return levels;

    }
}
