package demo;

public class BackpackProblem {

    private int zeroOneBackpack(int[] w, int[] v, int capacity) {
        // 输入为 n 个物品的重量和价值，从 1 开始计数
        // dp[i][j] 表示使用容量为 j 的背包去装前 i 个物品，能得到的最大价值
        // 对于第 i 个物品，有装或不装两种情况，这就是为什么叫 01 背包
        // dp[i][j] = max(dp[i-1][j], dp[i-1][j-w[i]]+v[i])，且有 j >= w[i]

        int n = w.length - 1;

        int[][] dp = new int[n + 1][capacity + 1];
        // 初始值
        for (int i = 0; i < capacity + 1; i++) {
            if (w[1] <= i) {
                dp[1][i] = v[1];
            }
        }
        for (int i = 2; i < n + 1; i++) {
            for (int j = 1; j < capacity + 1; j++) {
                if (j >= w[i]) {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - w[i]] + v[i]);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[n][capacity];

        // 如果需要恰好装满
        // 初始值
        // w[i] == i → dp[1][i] = v[1]
        // else dp[1][i] = -∞
    }

    private int absoluteBackpack(int[] w, int[] v, int capacity) {
        // 每种物品可以拿无穷件
        // 如果物品可以拆分，而不是以件为单位，直接贪心取性价比即可
        // 但本题不行
        // 对于第 i 件物品，我们可以不拿、拿 1 件、拿 2 件 ... 拿 k 件，k * w[i] <= j
        // dp[i][j] = max(dp[i-1][j], dp[i-1][j-w[i]] + v[i], dp[i-1][j-2 * w[i]] + 2 * v[i], ...)

        int n = w.length - 1;

        int[][] dp = new int[n + 1][capacity + 1];
        // 初始化
        for (int i = 0; i < capacity + 1; i++) {
            int k = i / w[1];
            if (k > 0) {
                dp[1][i] = k * w[1];
            }
        }
        for (int i = 2; i < n + 1; i++) {
            for (int j = 0; j < capacity + 1; j++) {
                int k = j / w[i];
                for (int l = 0; l <= k; l++) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - l * w[i]] + l * v[i]);
                }
            }
        }

        return dp[n][capacity];

        // 将每个物品换成 k 个相同物品，就转变成了 01 背包问题
    }

    private int multipleBackpack(int[] w, int[] v, int[] m, int capacity) {
        // 每个物品指定最多多少件 m[i]
        int n = w.length - 1;

        int[][] dp = new int[n + 1][capacity + 1];
        for (int i = 1; i < n + 1; i++) {
            for (int j = 0; j < capacity + 1; j++) {
                for (int k = 0; k <= m[i] && k * w[i] <= j; k++) {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - k * w[i]] + k * v[i]);
                }
            }
        }
        return dp[n][capacity];
    }

    public static void main(String[] args) {
        int[] P={0,2,3,4};
        int[] V={0,3,4,5};
        int[] M={0,4,3,2};
        int T = 15;
        new BackpackProblem().multipleBackpack(V, P, M, T);
    }
}
