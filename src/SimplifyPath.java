//71. 简化路径
//
//以 Unix 风格给出一个文件的绝对路径，你需要简化它。或者换句话说，将其转换为规范路径。
//
//在 Unix 风格的文件系统中，一个点（.）表示当前目录本身；此外，两个点 （..） 表示将目录切换到上一级（指向父目录）；两者都可以是复杂相对路径的组成部分。更多信息请参阅：Linux / Unix中的绝对路径 vs 相对路径
//
//请注意，返回的规范路径必须始终以斜杠 / 开头，并且两个目录名之间必须只有一个斜杠 /。最后一个目录名（如果存在）不能以 / 结尾。此外，规范路径必须是表示绝对路径的最短字符串。


package src;

import java.util.Stack;

public class SimplifyPath {
    //利用栈模拟路径的层级
    public String solution(String path) {
        Stack<String> stack = new Stack<>();

        //去除结尾的斜杠
        int n = path.length();
        int end = n - 1;
        while (end >= 0 && path.charAt(end) == '/') end--;

        //游标
        int i = 0;
        while (i <= end) {
            char c = path.charAt(i);
            //过滤多余的'/'
            while (c == '/') {
                i++;
                c = path.charAt(i);
            }
            //获取目录名
            int j = i;
            while (j <= end && path.charAt(j) != '/') j++;
            String d = path.substring(i, j);

            //目录名有三种："."或".."或其他
            if (!d.equals(".")) {
                if (d.equals("..")) {
                    //上一目录
                    if (!stack.empty())
                        stack.pop();
                } else {
                    stack.push("/" + d);
                }
            }

            i = j;
        }

        if (stack.empty()) {
            return "/";
        } else {
            StringBuffer sb = new StringBuffer();
            while (!stack.empty()) {
                //每次加在前面
                StringBuffer sbTemp = new StringBuffer(stack.pop());
                sbTemp.append(sb);
                sb = sbTemp;
            }
            return sb.toString();
        }
    }
}
