//22. 括号生成
//
//给出 n 代表生成括号的对数，请你写出一个函数，使其能够生成所有可能的并且有效的括号组合。
//
//例如，给出 n = 3，生成结果为：
//
//[
//  "((()))",
//  "(()())",
//  "(())()",
//  "()(())",
//  "()()()"
//]
//
//对于这样一种括号的有效组合，可从左到右遍历，维护一个初值为0的变量left
//当遇到左括号，left++，遇到右括号，left--，整个遍历过程left要>=0，且遍历结束时，left要=0


package src;

import java.util.ArrayList;
import java.util.List;

public class GenerateParentheses {
    List<String> ansList;

    //回溯算法
    public List<String> solution1(int n) {
        ansList = new ArrayList<>();
        recursive1("", 0, 2 * n);
        return ansList;
    }

    //表示当前已形成部分串s，其中未匹配的左括号有left个，len为最终的串的长度
    public void recursive1(String s, int left, int len) {
        //形成了有效的括号组合
        if (s.length() == len && left == 0) {
            ansList.add(s);
        } else if (s.length() < len && left >= 0) {
            //还能添加
            recursive1(s + "(", left + 1, len);
            recursive1(s + ")", left - 1, len);
        }
        //剩下的时不满足的情况：
        //s.length() == len && left > 0
        //s.length() == len && left < 0
        //s.length() < len && left < 0
    }

    //闭合数方法(看官方题解学会)
    //每个闭合数c，在0和2c+1位置分别为"("、")"，然后两个位置之间2c个位置是一个有效的括号组合，2c+1之后的串又是一个有效的括号组合
    public List<String> solution2(int n) {
        List<String> list = new ArrayList<>();

        //递归出口
        if (n == 1) {
            list.add("()");
            return list;
        }

        List<String> l1, l2;
        for (int c = 0; 2 * c + 1 < 2 * n; c++) {
            if (2 * c + 1 == 1) {
                //闭合数之间长度为0
                l1 = solution2(n - 1);
                for (String s: l1)
                    list.add("()" + s);
            } else if (2 * c + 1 == 2 * n - 1) {
                //闭合数之后长度为0
                l1 = solution2(n - 1);
                for (String s : l1)
                    list.add("(" + s + ")");
            } else {
                l1 = solution2(c);
                l2 = solution2(n - c - 1);
                for (String s1 : l1)
                    for (String s2 : l2)
                        list.add("(" + s1 + ")" + s2);
            }
        }
        return list;
    }


    //闭合数法的官方代码，统一了我方法中的三个条件
    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList();
        if (n == 0) {
            ans.add("");
        } else {
            for (int c = 0; c < n; ++c)
                for (String left: generateParenthesis(c))
                    for (String right: generateParenthesis(n-1-c))
                        ans.add("(" + left + ")" + right);
        }
        return ans;
    }

    public static void main(String[] args) {
        GenerateParentheses g = new GenerateParentheses();
        g.solution2(3);
    }
}
