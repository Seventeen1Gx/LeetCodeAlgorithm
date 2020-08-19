// 337. 打家劫舍Ⅲ
//
// 在上次打劫完一条街道之后和一圈房屋后，小偷又发现了一个新的可行窃的地区。这个地区只有一个入口，我们称之为“根”。 除了“根”之外，每栋房子有且只有一个“父“房子与之相连。一番侦察之后，聪明的小偷意识到“这个地方的所有房屋的排列类似于一棵二叉树”。 如果两个直接相连的房子在同一天晚上被打劫，房屋将自动报警。
//
// 计算在不触动警报的情况下，小偷一晚能够盗取的最高金额。


package src;

import src.twoSum.TreeNode;

public class HouseRobberNo3 {
    // 两种策略
    // 1. 偷了根结点，再去孙子结点中开始偷
    // 2. 不偷根结点，再去儿子结点中开始偷
    public int solution(TreeNode root) {
        if (root == null) {
            return 0;
        }
        // 儿子结点
        int leftSon = solution(root.left);
        int rightSon = solution(root.right);
        // 孙子结点
        int leftSonLeftSon = 0;
        int leftSonRightSon = 0;
        int rightSonLeftSon = 0;
        int rightSonRightSon = 0;
        if (root.left != null) {
            leftSonLeftSon = solution(root.left.left);
            leftSonRightSon = solution(root.left.right);
        }
        if (root.right != null) {
            rightSonLeftSon = solution(root.right.left);
            rightSonRightSon = solution(root.right.right);
        }
        return Math.max(leftSon + rightSon,
                root.val + leftSonLeftSon + leftSonRightSon + rightSonLeftSon + rightSonRightSon);
    }
}
