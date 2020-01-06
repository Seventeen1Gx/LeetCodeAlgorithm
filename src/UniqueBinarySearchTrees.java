package src;

/**
 * 96. 不同的二叉搜索树
 *
 * 给定一个整数 n，求以 1 ... n 为节点组成的二叉搜索树有多少种？
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class UniqueBinarySearchTrees {
    public int solution1(int n) {
        if (n == 1) {
            return 1;
        } else if (n == 2) {
            return 2;
        } else {
            // 以 1 为根结点，2...n 组成根结点的右子树
            // 以 n 为根结点，1...n-1 组成根结点的左子树
            int sum = solution1(n - 1) * 2;
            // 分别以 2，3，...，n-1 为根结点
            for (int i = 2; i <= n - 1; i++) {
                sum += solution1(i - 1) * solution1(n - i);
            }
            return sum;
        }
    }

    /**
     * 看方法一的递归过程，易知可用动态规划
     */
    public int solution2(int n) {
        // dp[i] 表示 1 ... i 可以组成的二叉搜索树的种类
        int[] dp = new int[n + 1];
        for (int i = 1; i < n + 1; i++) {
            if (i == 1) {
                dp[i] = 1;
            } else if (i == 2) {
                dp[i] = 2;
            } else {
                int sum = dp[i - 1] * 2;
                for (int j = 2; j <= i - 1; j++) {
                    sum += dp[j - 1] * dp[i - j];
                }
                dp[i] = sum;
            }
        }
        return dp[n];
    }

    /**
     * 直接公式法 → 卡特兰数
     */
}
