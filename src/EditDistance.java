//72. 编辑距离
//
//给定两个单词 word1 和 word2，计算出将 word1 转换成 word2 所使用的最少操作数 。
//
//你可以对一个单词进行如下三种操作：
//插入一个字符
//删除一个字符
//替换一个字符
//
//思路
//1. 删除一个字符c，然后在被删除字符处插入一个字符d，等于将这个字符c直接替换成字符d
//2. 当word1比word2长度小时，一定会有len2-len1个插入操作，最少操作数>=len2-len1
//3. 当word1比word2长度大时，一定会有len1-len2个删除操作，最少操作数>=len1-len2


package src;

public class EditDistance {
    //动态规划
    //dp[i,j]表示word1[0:i)转换到word2[0:j)所需最少操作步数
    //转移方程
    //当word1[i-1]==word2[j-1]时，dp[i][j]=dp[i-1][j-1]
    //当word1[i-1]!=word2[j-1]时，dp[i][j]=min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])+1
    //min中三项分别表示：
    //word1[0, i-1)变成word2[0, j)，然后删除word1[i-1]
    //word1[0, i)变成word2[0, j-1)，然后插入word2[j-1]
    //word1[0, i-1)变成word2[0, j-1)，然后word1[i-1]变成word2[j-1]
    public int solution(String word1, String word2) {
        //考虑到空串
        //dp[i][j]表示word1[0:i)转换到word2[0:j)所需最少操作步数
        //i取值[0:len1]，j取值[0:len2]
        int m = word1.length() + 1;
        int n = word2.length() + 1;

        int[][] dp = new int[m][n];

        //初始化
        for (int i = 0; i < m; i++) {
            //word1[0:i)变成空串
            dp[i][0] = i;
        }
        for (int i = 0; i < n; i++) {
            //空串变成word2[0:i)
            dp[0][i] = i;
        }

        //根据状态转移，计算全部dp
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                }
            }
        }

        return dp[m - 1][n - 1];
    }
}
