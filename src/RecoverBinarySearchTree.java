// 99. 恢复二叉搜索树
//
// 二叉搜索树中的两个节点被错误地交换。
//
// 请在不改变其结构的情况下，恢复这棵树。


package src;

import src.util.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

public class RecoverBinarySearchTree {
    // 中序遍历 BST 得到的是一个递增序列
    // 如果两个非相邻结点被交换，即小的到后面，大的到前面。
    // 中序遍历会发现两处 ai > ai+1，第一处我们取大数，第二处我们取小数。
    // 如果两个相邻结点被交换。
    // 中序遍历会发现一处 ai > ai+1，则这两个数就是我们要的。
    public void solution(TreeNode root) {
        TreeNode x = null, y = null, pre = null;

        int cnt = 0;

        Deque<TreeNode> stack = new ArrayDeque<>();

        while (!stack.isEmpty() || root != null) {
            // 先去访问左子树，所以先把根结点暂存起来
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            // 左子树为空
            // 遍历当前结点
            // 先把它弹出
            root = stack.pop();

            if (pre != null && pre.val > root.val) {
                if (cnt == 0) {
                    // 说明第一次找到
                    x = pre;
                    y = root;
                    cnt++;
                } else {
                    // 说明第二次找到
                    y = root;
                    break;
                }
            }
            pre = root;
            // 去访问右子树
            root = root.right;
        }
        swap(x, y);
    }

    private void swap(TreeNode p1, TreeNode p2) {
        int t = p1.val;
        p1.val = p2.val;
        p2.val = t;
    }
}
