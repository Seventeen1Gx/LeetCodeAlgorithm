// 8. 字符串转换为整数(atoi)
//
// 请你来实现一个 atoi 函数，使其能将字符串转换成整数。
//
// 首先，该函数会根据需要丢弃无用的开头空格字符，直到寻找到第一个非空格的字符为止。
//
// 当我们寻找到的第一个非空字符为正或者负号时，则将该符号与之后面尽可能多的连续数字组合起来，作为该整数的正负号；假如第一个非空字符是数字，则直接将其与之后连续的数字字符组合起来，形成整数。
//
// 该字符串除了有效的整数部分之后也可能会存在多余的字符，这些字符可以被忽略，它们对于函数不应该造成影响。
//
// 注意：假如该字符串中的第一个非空格字符不是一个有效整数字符、字符串为空或字符串仅包含空白字符时，则你的函数不需要进行转换。
//
// 在任何情况下，若函数不能进行有效的转换时，请返回 0。
//
// 说明：
//
// 假设我们的环境只能存储 32 位大小的有符号整数，那么其数值范围为 [−2^31,  2^31 − 1]。如果数值超过这个范围，请返回  INT_MAX (2^31 − 1) 或 INT_MIN (−2^31) 。


package src;

public class AtoI {
    public int solution(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        int len = str.length();

        int start, end;

        // 游标，出循环后，p 指向字符串第一个非空字符(这里假设字符串开头可能的空白符仅为空格字符)
        int p = 0;
        while (p < len && str.charAt(p) == ' ') {
            p++;
        }

        //字符串全为空格符
        if (p == len) {
            return 0;
        }

        //字符串第一个非空字符非数字也非正负符号
        char c = str.charAt(p);
        if (c != '+' && c != '-' && !Character.isDigit(c)) {
            return 0;
        }

        int signTag = 0;
        if (c == '+' || c == '-') {
            p++;
            if (c == '-') {
                signTag = 1;
            }
        }

        // 消除开头的数字 0 (否则就不能靠数字长度来判断是否溢出了)
        while (p < len && str.charAt(p) == '0') {
            p++;
        }

        //开始搜索连续的数字字符，边遍历边计算
        start = p;
        long x = 0;
        int n;
        while (p < len && Character.isDigit(str.charAt(p))) {
            n = str.charAt(p) - '0';
            x = x * 10 + n;
            p++;
            if (p - start > 10) {
                if (signTag == 0) {
                    return Integer.MAX_VALUE;
                } else {
                    return Integer.MIN_VALUE;
                }
            }
        }
        end = p;

        //字符串全0或者正负符号后没有数字
        if (start >= end) {
            return 0;
        }

        if (signTag == 0) {
            if ((int) x != x) {
                return Integer.MAX_VALUE;
            } else {
                return (int) x;
            }
        } else if ((int) (-x) != (-x)) {
            return Integer.MIN_VALUE;
        } else {
            return (int) (-x);
        }
    }

    public static void main(String[] args) {
        String s1 = "-91283472332";
        String s2 = "  0000000000012345678";

        AtoI atoI = new AtoI();

        int a = atoI.solution(s1);

        int b = 0;
    }
}
