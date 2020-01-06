package src;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 103. 二叉树的锯齿形层次遍历
 *
 * 给定一个二叉树，返回其节点值的锯齿形层次遍历。
 * （即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class BinaryTreeZigzagLevelOrderTraversal {
    public List<List<Integer>> solution1(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();

        if (root == null) {
            return ans;
        }

        Queue<Pair<TreeNode, Integer>> queue = new LinkedList<>();
        queue.offer(new Pair<>(root, 0));
        while (!queue.isEmpty()) {
            Pair<TreeNode, Integer> pair = queue.poll();
            TreeNode treeNode = pair.getKey();
            int level = pair.getValue();

            if (level == ans.size()) {
                ans.add(new ArrayList<>());
            }
            // 在添加到列表时，保持锯齿形
            if (level % 2 == 0) {
                ans.get(level).add(treeNode.val);
            } else {
                ans.get(level).add(0, treeNode.val);
            }

            if (treeNode.left != null) {
                queue.offer(new Pair<>(treeNode.left, level + 1));
            }
            if (treeNode.right != null) {
                queue.offer(new Pair<>(treeNode.right, level + 1));
            }

            // 第一次的想法
            // 在进入队列时，保证锯齿形 -- 输出结果错误，比如[1,2,3,4,null,null,5]
            // if (level % 2 == 0) {
            //     // 先右后左
            //     if (treeNode.right != null) {
            //         queue.offer(new Pair<>(treeNode.right, level + 1));
            //     }
            //     if (treeNode.left != null) {
            //         queue.offer(new Pair<>(treeNode.left, level + 1));
            //     }
            // } else {
            //     // 先左后右
            //     if (treeNode.left != null) {
            //         queue.offer(new Pair<>(treeNode.left, level + 1));
            //     }
            //     if (treeNode.right != null) {
            //         queue.offer(new Pair<>(treeNode.right, level + 1));
            //     }
            // }
        }
        return ans;
    }

    public List<List<Integer>> solution2(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int level = 0;
        while (!queue.isEmpty()) {
            int levelLength = queue.size();
            for (int i = 0; i <levelLength; i++) {
                TreeNode treeNode = queue.poll();
                if (treeNode != null) {
                    if (ans.size() == level) {
                        ans.add(new ArrayList<>());
                    }
                    if (level % 2 == 0) {
                        ans.get(level).add(treeNode.val);
                    } else {
                        ans.get(level).add(0, treeNode.val);
                    }

                    if (treeNode.left != null) {
                        queue.offer(treeNode.left);
                    }
                    if (treeNode.right != null) {
                        queue.offer(treeNode.right);
                    }
                }
            }
            level++;
        }
        return ans;
    }

    /**
     * 先序遍历每个结点，遍历到每个结点时，知晓该结点的层级
     * 再根据该层级加入到对应的结果列表中
     */
    public List<List<Integer>> solution3(TreeNode root) {
        recursion(root, 0);
        return results;
    }

    private List<List<Integer>> results = new ArrayList<>();

    private void recursion(TreeNode p, int level) {
        if (p != null) {
            if (results.size() == level) {
                results.add(new ArrayList<>());
            }
            if (level % 2 == 0) {
                results.get(level).add(p.val);
            } else {
                results.get(level).add(0, p.val);
            }

            recursion(p.left, level + 1);
            recursion(p.right, level + 1);
        }
    }
}
