// 剑指 Offer 20. 表示数值的字符串
//
// 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
// 例如，字符串 "+100"、"5e2"、"-123"、"3.1416"、"-1E-16"、"0123" 都表示数值，但 "12e"、"1a3.14"、"1.2.3"、"+-5" 及 "12e+5.4" 都不是。


package src;

public class isNumberForString {
    public boolean isNumber(String s) {
        // .5 返回 true
        // 3. 返回 true
        // +.8 返回 true
        // -. 返回 false
        // e 之后可以带 + 或 -，也可以不带，然后必须有一个整数

        // 去除首位空格
        s = s.trim();
        int n = s.length();
        char state = 'A';
        char c = '0';
        for (int i = 0; i <= n; i++) {
            if (i < n) {
                c = s.charAt(i);
            }
            switch (state) {
                // A 开始遍历前的状态
                // B 遍历到符号的状态
                // C 遍历整数部分的状态
                // D 遍历到小数点且左边有数字的状态
                // E 遍历到小数点且左边没有数字的状态
                // F 遍历小数部分的状态
                // G 遍历到 e 的状态
                // H 遍历到 e 之后的符号的状态
                // I 遍历到 e 之后的整数的状态
                case 'A':
                    if (i == n) {
                        return false;
                    }
                    if (c == '+' || c == '-') {
                        state = 'B';
                    } else if (Character.isDigit(c)) {
                        state = 'C';
                    } else if (c == '.') {
                        state = 'E';
                    } else {
                        return false;
                    }
                    break;
                case 'B':
                    if (i == n) {
                        return false;
                    }
                    if (Character.isDigit(c)) {
                        state = 'C';
                    } else if (c == '.') {
                        state = 'E';
                    } else {
                        return false;
                    }
                    break;
                case 'C':
                    if (i == n) {
                        return true;
                    }
                    if (Character.isDigit(c)) {
                        continue;
                    } else if (c == '.') {
                        state = 'D';
                    } else if (c == 'e' || c == 'E') {
                        state = 'G';
                    } else {
                        return false;
                    }
                    break;
                case 'D':
                    if (i == n) {
                        return true;
                    }
                    if (Character.isDigit(c)) {
                        state = 'F';
                    } else if (c == 'e' || c == 'E') {
                        state = 'G';
                    } else {
                        return false;
                    }
                    break;
                case 'E':
                    if (i == n) {
                        return false;
                    }
                    if (Character.isDigit(c)) {
                        state = 'F';
                    } else {
                        return false;
                    }
                    break;
                case 'F':
                    if (i == n) {
                        return true;
                    }
                    if (Character.isDigit(c)) {
                        continue;
                    } else if (c == 'e' || c == 'E') {
                        state = 'G';
                    } else {
                        return false;
                    }
                    break;
                case 'G':
                    if (i == n) {
                        return false;
                    }
                    if (c == '+' || c == '-') {
                        state = 'H';
                    } else if (Character.isDigit(c)) {
                        state = 'I';
                    } else {
                        return false;
                    }
                    break;
                case 'H':
                    if (i == n) {
                        return false;
                    }
                    if (Character.isDigit(c)) {
                        state = 'I';
                    } else {
                        return false;
                    }
                    break;
                case 'I':
                    if (i == n) {
                        return true;
                    }
                    if (Character.isDigit(c)) {
                        continue;
                    } else {
                        return false;
                    }
                default:
                    break;
            }
        }

        return true;
    }
}
