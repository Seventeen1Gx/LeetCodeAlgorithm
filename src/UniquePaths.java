//62. 不同路径
//
//一个机器人位于一个 m x n 网格的左上角。
//
//机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角。
//
//问总共有多少条不同的路径？
//
//
//衍生：m个不同的盒子，放n个相同的球，允许有空盒，共多少种方法
//比如m=5，n=5
//1、5个球放在一个盒子中(1份)，共5种，
//2、1个球和4个球(2份)，共5*4种，
//3、2个球和3个球(2份)，共5*4种，
//4、1个球、1个球、3个球(3份)，共5*4*3/2种，
//5、1个球、2个球、2个球(3份)，共5*4*3/2种，
//6、1个球、1个球、1个球、2个球(4份)，共5*4*3*2/3!种
//7、1个球、1个球、1个球、1个球、1个球(5份)，共1种


package src;

public class UniquePaths {
    //m*n的表格从左上角到右下角，必是(m-1)次向右与(n-1)次向下的排列组合(重复元素全排列)
    //超时
    public int solution1(int m, int n) {
        return recursion(m - 1, n - 1);
    }

    //递归--向右可用次数m，向下可用次数n
    private int recursion(int m, int n) {
        if (m == 0 || n == 0) {
            return 1;
        } else {
            //向下
            int a = recursion(m, n - 1);
            //向右
            int b = recursion(m - 1, n);
            return a + b;
        }
    }

    //本题即m+n-2个盒子，放入m-1个"向右"，放入n-1个"向下"，每个盒子放1个
    //则结果就是m+n-2挑出m-1个位置放"向右"，剩下的位置放"向下"即可
    public int solution2(int m, int n) {
        return f(m + n - 2, m - 1);
    }

    //求组合数C_n^k
    private int f(int n, int k) {
        long res = 1;
        for (int i = 1; i <= k; i++)
            res = res * (n - k + i) / i;
        return (int) res;
    }

    //动态规划dp[i,j]表示从左上角到达i,j位置的路径数
    public int solution3(int m, int n) {
        int[][] dp = new int[m][n];
        //初始化已知的，即仅能一直向右或一直向下的，即上面递归法中，m==0||n==0的情况
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int i = 0; i < n; i++) {
            dp[0][i] = 1;
        }
        //剩下位置，可以从其上一个格子向下一步或者其左边的格子向右一步得到
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }
}
