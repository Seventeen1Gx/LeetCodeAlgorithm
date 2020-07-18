// 97. 交错字符串
//
// 给定三个字符串 s1, s2, s3, 验证 s3 是否是由 s1 和 s2 交错组成的。


package src;

public class InterleavingString {
    public boolean solution(String s1, String s2, String s3) {
        // 当 |s1|+|s2| != |s3| 时，一定不是
        // 当 |s1|+|s2| == |s3| 时，利用动态规划
        // dp[i,j] 表示 s1[0,i) 与 s2[0,j) 能否交错组成 s3[0,i+j)

        // 思路：s3[0,i+j-1) 增加一个字符 s3[i+j-1] 变成 s3[0,i+j)
        // 这个增加的字符要么来自 s1，要么来自 s2
        // ① 假设来自 s2，那么到底来自 s2 的哪一个字符呢
        // dp[i][j]
        // 可以看 dp[i][j-1]，以及 s3[i+j-1] 是否和 s2[j-1] 相同
        // 可以看 dp[i+1][j-2]，以及 s3[i+j-1] 是否和 s2[j-2] 相同
        // ...
        // 一直到行号 row=m || col=0
        // ② 假设来自 s1 同理
        // ...

        // 上面的思路错了！！！！！！
        // 比如这一句 “看 dp[i+1][j-2]，以及 s3[i+j-1] 是否和 s2[j-2] 相同”
        // dp[i+1][j-2] 表示 s1 前 i+1 个字符和 s2 前 j-2 个字符能够组成 s3 的前 i+j-1 个字符
        // 假设 dp[i+1][j-2]=true，且 s3[i+j-1]==s2[j-2]，那也是推出
        // s1 的前 i+1 个字符和 s2 的前 j-1 个字符能组成 s3 的前 i+j 个字符，即 dp[i+1][j-1]=true
        // 而跟 dp[i][j] 没关系

        // 所以，dp[i][j] 只取决于 dp[i-1][j] 和 dp[i][j-1]


        // 像下面这样，每个 dp[i][j] 只依赖其上一个和前一个位置的 dp 值，则可以将二维数组转化成一维数组，节省空间
        // 这种称为滚动数组

        int m = s1.length(), n = s2.length();
        if (m + n != s3.length())
            return false;

        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0 && j == 0)
                    continue;

                char c = s3.charAt(i + j - 1);
                boolean b1 = false, b2 = false;
                if (i > 0 && dp[i - 1][j] && c == s1.charAt(i - 1))
                    b1 = true;
                if (j > 0 && dp[i][j - 1] && c == s2.charAt(j - 1))
                    b2 = true;
                dp[i][j] = b1 || b2;
            }
        }

        return dp[m][n];
    }

    public static void main(String[] args) {
        new InterleavingString().solution("aabcc", "dbbca", "aadbbbaccc");
    }

    // 测试数组斜向遍历
    private void visit(int m, int n) {
        boolean[][] test = new boolean[m][n];
        int max = m + n - 2;
        for (int sum = 0; sum <= max; sum++) {
            for (int i = 0, j; i < m && (j = sum - i) >= 0; i++) {
                if (j >= n)
                    continue;
                test[i][j] = true;
            }
            print(test);
            System.out.println();
        }
    }

    private void print(boolean[][] test) {
        for (int i = 0; i < test.length; i++) {
            for (int j = 0; j < test[0].length; j++) {
                if (test[i][j])
                    System.out.print("true\t");
                else
                    System.out.print("false\t");
            }
            System.out.println();
        }
    }
}
