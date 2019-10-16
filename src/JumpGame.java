//55. 跳跃游戏
//
//给定一个非负整数数组，你最初位于数组的第一个位置。
//
//数组中的每个元素代表你在该位置可以跳跃的最大长度。
//
//判断你是否能够到达最后一个位置。


package src;

public class JumpGame {
    //回溯试错--但超时了
    public boolean solution1(int[] nums) {
        if (nums == null)
            return false;

        return canJump1(nums, 0);
    }

    //当前所处位置为k，是否能到达最后一个位置
    public boolean canJump1(int[] nums, int k) {
        //如果当前处于最后一个位置
        if (k == nums.length - 1)
            return true;

        //如果当前超出最后一个位置
        if (k >= nums.length)
            return false;

        //当前位置所可以跳的长度都试一遍，假设nums[k]=3
        //return canJump1(nums, k + 3) || canJump1(nums, k + 2) || canJump1(nums, k + 1)
        int step = nums[k];
        while (step > 0) {
            if (canJump1(nums, k + step))
                return true;
            step--;
        }
        return false;
    }

    //考虑到上面的试错，有重复的递归，所以要将重复的地方记下来，避免进行多余的运算
    public boolean solution2(int[] nums) {
        if (nums == null)
            return false;

        int n = nums.length;
        //dp[i]表示在位置i能否到达最后一个位置
        boolean[] dp = new boolean[n];
        dp[n - 1] = true;
        //从后往前，当前位置之后的dp都是已知的
        for (int i = n - 2; i >= 0; i--) {
            int step = nums[i];
            while (step > 0) {
                if (i + step < n && dp[i + step]) {
                    dp[i] = true;
                    break;
                }
                step--;
            }
        }
        return dp[0];
    }

    enum Index {
        GOOD, BAD, UNKNOWN
    }

    //还有一种，则是在方法一的回溯过程中，保留已知的情况
    public boolean solution3(int[] nums) {
        if (nums == null)
            return false;

        Index[] memo = new Index[nums.length];
        for (int i = 0; i < memo.length; i++) {
            memo[i] = Index.UNKNOWN;
        }
        memo[memo.length - 1] = Index.GOOD;
        return canJump2(nums, memo, 0);
    }

    //返回所处位置k时，是否能到达最后一个位置
    public boolean canJump2(int[] nums, Index[] memo, int k) {
        //如果当前处于最后一个位置
        if (k == nums.length - 1)
            return true;

        int step = nums[k];
        while (step > 0) {
            //每次循环中判断处于k+step上能否到达最终位置
            if (k + step >= nums.length) {
                //跳过
            } else if (memo[k + step] == Index.GOOD) {
                memo[k] = Index.GOOD;
                return true;
            } else if (memo[k + step] == Index.BAD) {
                //跳过
            } else if (canJump2(nums, memo, k + step)) {
                //memo[k + step]未知的情况，正常利用回溯试错
                memo[k] = Index.GOOD;
                return true;
            }
            step--;
        }
        memo[k] = Index.BAD;
        return false;
    }

    //贪心法
    public boolean solution4(int[] nums) {
        //保存当前已知的能跳到最后一个位置的位置的最左边那个
        //处理过程是从后往前
        int lastPos = nums.length - 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            //当前处于位置i，其能跳到的最远位置超过了lastPos，说明当前位置能跳到最后一个位置，且更新lastPos值
            if (i + nums[i] >= lastPos)
                lastPos = i;
        }
        return lastPos == 0;
    }
}
