// 501. 二叉搜索树中的众数
//
// 给定一个有相同值的二叉搜索树（BST），找出 BST 中的所有众数（出现频率最高的元素）。
//
// 假定 BST 有如下定义：
// 结点左子树中所含结点的值小于等于当前结点的值
// 结点右子树中所含结点的值大于等于当前结点的值
// 左子树和右子树都是二叉搜索树


package src.tree;

import src.util.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class FindModeInBinarySearchTree {
    public int[] findMode(TreeNode root) {
        if (root == null) {
            return new int[0];
        }

        inOrder(root);
        if (cnt == candidateCnt) {
            ret.add(lastNum);
        } else if (cnt > candidateCnt) {
            ret.clear();
            ret.add(lastNum);
        }
        return ret.stream().mapToInt(Integer::intValue).toArray();
    }

    List<Integer> ret = new ArrayList<>();
    int candidateCnt = -1;
    int lastNum = Integer.MIN_VALUE;
    int cnt = 0;

    private void inOrder(TreeNode root) {
        // 二叉树遍历得到有序序列
        if (root == null) {
            return;
        }
        inOrder(root.left);
        if (root.val != lastNum) {
            // 遇到新的数字
            // 统计上一个数字是否为众数
            if (cnt == candidateCnt) {
                ret.add(lastNum);
            } else if (cnt > candidateCnt) {
                ret.clear();
                ret.add(lastNum);
                candidateCnt = cnt;
            }
            lastNum = root.val;
            cnt = 1;
        } else {
            cnt++;
        }
        inOrder(root.right);
    }
}
