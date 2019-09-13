//32. 最长有效括号
//
//给定一个只包含 '(' 和 ')' 的字符串，找出最长的包含有效括号的子串的长度。
//
//根据22.括号生成产生灵感
//根据子结构，想到可以动态规划


package src;

import java.util.Stack;

public class LongestValidParentheses {
    //利用栈思维--O(n2)超时
    public int solution1(String s) {
        if (s == null)
            return 0;

        int len = s.length();
        if (len == 0 || len == 1)
            return 0;

        int maxLen = 0;
        //寻找从i开始的最长有效括号长度
        for (int i = 0; i < len; i++) {
            //以i开始的最长有效括号长度的可能的最大值
            int possibleMaxLen = len - i;
            //二进制表示下，偶数的最后一位为0，奇数的最后一位为1
            if ((possibleMaxLen & 1) != 0)
                possibleMaxLen--;
            if (possibleMaxLen <= maxLen)
                //如果可能的最大长度都不及已求得的最大长度，再往后的子串更不可能，就可以结束循环了
                break;

            int left = 0, j;
            for (j = i; j < len; j++) {
                if (s.charAt(j) == '(') {
                    left++;
                } else if (s.charAt(j) == ')' && left > 0) {
                    left--;
                    if (left == 0 && j - i + 1 > maxLen) {
                        //第一个条件说明[i:j]是一个有效的括号组合
                        maxLen = j - i + 1;
                    }
                } else {
                    //(s.charAt(j) + "").equals(")") && left <= 0
                    if (j - i > maxLen)
                        maxLen = j - i;
                    //再往后也是无效的括号组合，所以直接退出循环
                    break;
                }
            }
            //前两个条件说明[i:j)是一个有效的括号组合
            if (j == len && left == 0 && j - i > maxLen)
                maxLen = j - i;
        }
        return maxLen;
    }

    //利用栈，记录遇到的未匹配的'('的下标，每次匹配到一个')'，则二者构成一个有效括号的头尾
    //弹出一个元素(表示匹配用掉)，然后使用当前元素和栈顶元素(若存在，不存在使用我们维护的start)的差作为当前有效长度
    //每次发现当前不满足，则从这个不满足的子串的下一位置重新开始(更新start)
    public int solution2(String s) {
        if (s == null)
            return 0;

        int len = s.length();
        if (len == 0 || len == 1)
            return 0;

        Stack<Integer> stack = new Stack<>();

        //维护一个当前最长有效括号长度，和当前有效括号的起始位置(的前一位置，便于计算长度，所以开始时，start=-1)
        //栈来确定此时的匹配情况，从(start:i]是否有效，start来确定
        int maxLen = 0, start = -1;

        for (int i = 0; i < len; i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                //遇到右括号，先弹出栈顶元素，表示跟他匹配用掉了
                ///然后我们认为该右括号位置
                if (!stack.empty()) {
                    stack.pop();
                    if (stack.empty()) maxLen = Math.max(maxLen, i - start);
                        //下面这个是防止像这样的串"((())"，这里遇到')'，就不能说是从start开始的了
                        //这里说明还存在未匹配的括号，这部分是无效的，所以这里用stack.peek()作为开始
                        //这些未匹配的括号可能以后会被匹配
                    else maxLen = Math.max(maxLen, i - stack.peek());
                } else {
                    //遇到不匹配的情况，则从不匹配的位置的下一位置开始计算新的最长有效长度
                    start = i;
                }
            }
        }
        return maxLen;
    }

    //动态规划，dp[i]表示以s[i]为结尾的最长有效括号长度
    //首先有效括号长度一定以')'为结尾，故s[i]='('时，dp[i]=0
    //当s[i]=')'时：
    //  对于s[i-1]='('，dp[i]=dp[i-2]+2，即形如"dp[i-2]()"的最长有效括号长度
    //  对于s[i-1]=')'，形如"dp[i-1])"，则要看这个形式的有效括号长度之前两个字符
    //  如果s[i-dp[i-1]-1]="("，形如"(dp[i-1])"，则dp[i]=dp[i-1]+2+dp[i-dp[i-1]-2]
    //根据上述，按i递增顺序求出所有dp[i](每个dp[i]只依赖其前面的dp[j])，最后答案是所有dp[i]中的最大值
    //剩下就是注意数组别越界
    public int solution3(String s) {
        if (s == null)
            return 0;

        int len = s.length();
        if (len == 0 || len == 1)
            return 0;

        int maxLen = 0;
        //整型数组成默认值为0
        int[] dp = new int[s.length()];
        //按顺序求出dp[i]，dp[0]肯定为0，不用考虑
        for (int i = 1; i < len; i++) {
            if (s.charAt(i) == ')') {
                if (s.charAt(i-1) == '(')
                    dp[i] = i>=2 ? dp[i - 2] + 2 : 2;
                else if (i - dp[i - 1] - 1 >= 0 && s.charAt(i - dp[i - 1] - 1) == '(')
                    dp[i] = dp[i - 1] + 2 + (i - dp[i - 1] >= 2 ? dp[i - dp[i - 1] - 2] : 0);
                maxLen = Math.max(maxLen, dp[i]);
            } //else中将dp[i] = 0，但默认值就是0，所以不用操作
        }
        return maxLen;
    }


    //不使用栈，前往后、后往前两次遍历
    public int solution4(String s) {
        if (s == null)
            return 0;

        int len = s.length();
        if (len == 0 || len == 1)
            return 0;

        int maxLen = 0;
        int left = 0, right = 0;

        //从左往右
        for (int i = 0; i < len; i++) {
            if (s.charAt(i) == '(') left++;
            else right++;

            if (left == right)
                maxLen = Math.max(maxLen, 2 * left);
            else if (left < right)
                left = right = 0;
        }

        //从右往左
        left = right = 0;
        for (int j = len-1; j >= 0; j--) {
            if (s.charAt(j) == '(') left++;
            else right++;

            if (left == right)
                maxLen = Math.max(maxLen, 2 * left);
            else if (left > right)
                left = right = 0;
        }

        return maxLen;
    }


    public static void main(String[] args) {
        LongestValidParentheses l = new LongestValidParentheses();
        l.solution3(")()())");
        l.solution3("(()())");
        l.solution2(")()())");
        l.solution2("(()");
        //下面是最基本的两种情况
        l.solution2("()()");
        l.solution2("(())");
    }
}
