//65. 有效数字
//
//验证给定的字符串是否可以解释为十进制数字。
//
//例如:
//
//"0" => true
//" 0.1 " => true
//"abc" => false
//"1 a" => false
//"2e10" => true
//" -90e3   " => true
//" 1e" => false
//"e3" => false
//" 6e-1" => true
//" 99e2.5 " => false
//"53.5e93" => true
//" --6 " => false
//"-+3" => false
//"95a54e53" => false
//
//说明: 我们有意将问题陈述地比较模糊。在实现代码之前，你应当事先思考所有可能的情况。这里给出一份可能存在于有效十进制数字中的字符列表：
//
//数字 0-9
//指数 - "e"
//正/负号 - "+"/"-"
//小数点 - "."
//
//当然，在输入中，这些字符的上下文也很重要。


package src;

public class ValidNumber {
    //"0"是一个有效数字
    //"有效数字e整数"是一个有效数字
    //"-/+正数"是一个有效数字
    //正数是一个有效数字，其不以零开头，完全由数字组成的正数部分+小数点+完全有数字组成的小数部分，后两项能省略或者整数部分为0时，整数部分可省略
    //注意"01"、".2"、"3."都是有效数字
    //"4e+"、"3e6.5"不是有效数字
    public boolean solution(String s) {
        //非法情况
        if (s == null)
            return false;

        //去除开头与结尾的空白符
        s = s.trim();

        //"0"为有效字符
        if (s.equals("0"))
            return true;

        //首次出现的"e"
        int i = s.indexOf("e");
        if (i < 0) {
            //不存在"e"
            return isValidNumber(s);
        } else {
            //存在"e"，则判断是否为"有效数字e整数"
            return isValidNumber(s.substring(0, i)) && !s.substring(i + 1).isEmpty() && isInteger(s.substring(i + 1));
        }
    }

    //排除了"0"和包含"e"的情况
    private boolean isValidNumber(String s) {
        if (s.length() == 0)
            //空串
            return false;

        //去除开头的符号
        if (s.charAt(0) == '+' || s.charAt(0) == '-') {
            s = s.substring(1);
            if (s.length() == 0)
                //"+"和"-"不是有效数字
                return false;
        }

        //首次出现'.'的位置
        int i = s.indexOf('.');
        if (i < 0) {
            //不存在'.'
            return isPureNumber(s);
        } else {
            if (s.equals("."))
                return false;
            return isPureNumber(s.substring(0, i)) && isPureNumber(s.substring(i + 1));
        }
    }

    //判断s是否为有效整数
    private boolean isInteger(String s) {
        if (s.charAt(0) == '+' || s.charAt(0) == '-')
            s = s.substring(1);

        if (s.length() == 0)
            return false;

        return isPureNumber(s);
    }

    //判断s是否为纯数字或空串
    private boolean isPureNumber(String s) {
        for (char c : s.toCharArray()) {
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
}
