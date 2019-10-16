//45. 跳跃游戏Ⅱ
//
//给定一个非负整数数组，你最初位于数组的第一个位置。
//
//数组中的每个元素代表你在该位置可以跳跃的最大长度。
//
//你的目标是使用最少的跳跃次数到达数组的最后一个位置。
//
//示例:
//
//输入: [2,3,1,1,4]
//输出: 2
//解释: 跳到最后一个位置的最小跳跃数是 2。
//从下标为 0 跳到下标为 1 的位置，跳 1 步，然后跳 3 步到达数组的最后一个位置。
//
//说明:
//假设你总是可以到达数组的最后一个位置。
//
//
//跳跃游戏1在第55题(判断能否到达终点)，与本题的区别是，本题总能到达终点，需要求到达终点所用的最少跳跃次数


package src;

public class JumpGameNo2 {
    //dp[i]表示位置i到达终点所用的最少跳跃次数
    public int solution1(int[] nums) {
        if (nums == null)
            return -1;

        int n = nums.length;
        int[] dp = new int[n];
        dp[n - 1] = 0;
        for (int i = n - 2; i >= 0; i--) {
            int farthestPos = Math.min(n - 1, i + nums[i]);
            int minStep = Integer.MAX_VALUE;
            for (int j = i + 1; j <= farthestPos; j++) {
                if (dp[j] >= 0 && dp[j] + 1 < minStep)
                    minStep = dp[j] + 1;
            }
            if (minStep != Integer.MAX_VALUE)
                dp[i] = minStep;
            else
                dp[i] = -1;
        }

        return dp[0];
    }

    //求第step跳能到达的范围[start, end]，直到这个范围包含终点
    public int solution2(int[] nums) {
        if (nums == null)
            return -1;

        int step = 0, start = 0, end = 0;
        while (end < nums.length - 1) {
            //在[start, end]上每个位置，做一次跳跃，能跳到的最远距离
            int farthestPos = 0;
            for (int i = start; i <= end; i++) {
                if (i + nums[i] > farthestPos)
                    farthestPos = i + nums[i];
            }
            //原地跳不考虑(开始位置总能跳到最后终点)
            start = end + 1;
            end = farthestPos;
            step++;
        }
        return step;
    }

    //贪心--每次局部最优，最终全局最优
    //[0, i-1]中，跳step次能跳到的范围为[i, end]
    //检查[i, end]每个位置，找其能跳到的最远位置maxPos，则跳一次
    //[0, i-1]跳step+1次能跳到的范围为[end+1, maxPos]，i=end+1，end=maxPos
    //直观点
    //每个位置怎么跳，才能更远
    //要看该位置i能跳(A)到的位置[i+1, i+nums[i]]中，再做一次最大距离跳跃(B)，能到达更远位置的那个B起跳位置做为A跳的目标位置
    //如23114，我们处于位置0时，nums[0]=2，能跳(A)到nums[1]，nums[2]，前者1+nums[1]>后者2+nums[2]则第一次跳选择跳到nums[1]
    public int solution3(int[] nums) {
        //end是上一次跳跃能到达的最远位置
        int end = 0;
        int maxPosition = 0;
        int steps = 0;
        for(int i = 0; i < nums.length - 1; i++){
            //[i, end]每个位置，找能跳到的最远位置
            maxPosition = Math.max(maxPosition, nums[i] + i);
            //处理完[i, end]范围每个位置，则作一次跳跃，新的要检查的区间为[end+1, maxPos]
            if (i == end) {
                end = maxPosition;
                steps++;
            }
        }
        return steps;
    }

    //从后往前找
    public int solution4(int[] nums) {
        //要找的位置
        int position = nums.length - 1;
        int steps = 0;
        //是否到了第 0 个位置
        while (position != 0) {
            //遍历position之前的位置(从前往后)
            for (int i = 0; i < position; i++) {
                //从前往后第一次(贪心)能跳到position的位置i
                if (nums[i] >= position - i) {
                    //更新要找的位置
                    position = i;
                    steps++;
                    break;
                }
            }
        }
        return steps;
    }
}
