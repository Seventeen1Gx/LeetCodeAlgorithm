//20. 有效的括号
//
//给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
//
//有效字符串需满足：
//左括号必须用相同类型的右括号闭合。
//左括号必须以正确的顺序闭合。
//
//注意空字符串可被认为是有效字符串。


import java.util.Stack;

public class ValidParentheses {
    //栈结构
    Stack<Character> stack = new Stack<>();
    //左括号
    String left = "({[";
    //右括号
    String right = ")}]";

    public boolean solution(String s) {
        if (s == null || s.length() == 0)
            return true;

        char c, d;
        //遍历字符串
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            //如果是左括号，则压入栈，表示等待一个右括号，越在栈顶的元素等待得越迫切
            if (left.indexOf(c) != -1) {
                stack.push(c);
            } else {
                //右括号，先看栈是否为空
                if (stack.empty())
                    return false;
                //栈顶元素
                d = stack.pop();
                if (d == '(' && c != ')')
                    return false;
                if (d == '[' && c != ']')
                    return false;
                if (d == '{' && c != '}')
                    return false;
            }
        }
        return stack.empty();
    }

    public static void main(String[] args) {
        ValidParentheses p = new ValidParentheses();
        p.solution("(]");
    }
}
