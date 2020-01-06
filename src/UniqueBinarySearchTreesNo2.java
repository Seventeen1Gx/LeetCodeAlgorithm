package src;

import java.util.ArrayList;
import java.util.List;

/**
 * 95. 不同的二叉搜索数Ⅱ
 * <p>
 * 给定一个整数 n，生成所有由 1 ... n 为节点所组成的二叉搜索树。
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class UniqueBinarySearchTreesNo2 {
    /**
     * 返回值为各棵树的根结点组成的列表
     */
    public List<TreeNode> solution(int n) {
        if (n == 0) {
            return new ArrayList<>();
        }

        return generateTrees(1, n);
    }

    /**
     * 生成 i ... j 为结点组成的二叉搜索树
     */
    private List<TreeNode> generateTrees(int i, int j) {
        List<TreeNode> ans = new ArrayList<>();
        if (i == j) {
            // 只有一个结点
            ans.add(new TreeNode(i));
            return ans;
        } else if (i == j + 1) {
            // 两个结点，有两种二叉搜索树
            //   i              j
            //     ↘           ↙
            //       j       i
            TreeNode node = new TreeNode(j);
            node.left = new TreeNode(i);
            ans.add(node);

            node = new TreeNode(i);
            node.right = new TreeNode(j);
            ans.add(node);

            return ans;
        } else {
            // i 作为根结点，i+1 ... j 作为它的右子树
            List<TreeNode> rightRoots = generateTrees(i + 1, j);
            for (TreeNode root : rightRoots) {
                TreeNode node = new TreeNode(i);
                node.right = root;
                ans.add(node);
            }

            // j 作为根结点，i ... j-1 作为它的左子树
            List<TreeNode> leftRoots = generateTrees(i + 1, j);
            for (TreeNode root : leftRoots) {
                TreeNode node = new TreeNode(i);
                node.left = root;
                ans.add(node);
            }

            // i+1 ... j-1 作为根结点
            for (int k = i + 1; k <= j - 1; k++) {
                leftRoots = generateTrees(i, k - 1);
                rightRoots = generateTrees(k + 1, j);
                for (TreeNode leftRoot : leftRoots) {
                    for (TreeNode rightRoot : rightRoots) {
                        TreeNode node = new TreeNode(k);
                        node.left = leftRoot;
                        node.right = rightRoot;
                        ans.add(node);
                    }
                }
            }
            return ans;
        }
    }

    /**
     * 进一步改进
     */
    private List<TreeNode> generateTrees2(int start, int end) {
        List<TreeNode> ans = new ArrayList<>();
        if (start > end) {
            ans.add(null);
            return ans;
        }

        for (int i = start; i <= end; i++) {
            List<TreeNode> l = generateTrees2(start, i - 1);
            List<TreeNode> r = generateTrees2(i + 1, end);

            for (TreeNode leftRoot : l) {
                for (TreeNode rightRoot : r) {
                    TreeNode node = new TreeNode(i);
                    node.left = leftRoot;
                    node.right = rightRoot;
                    ans.add(node);
                }
            }
        }
        return ans;
    }
}
