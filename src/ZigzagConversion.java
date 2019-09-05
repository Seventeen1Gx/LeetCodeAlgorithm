//6. Z字形变换
//
//将一个给定字符串根据给定的行数，以从上往下、从左到右进行 Z 字形排列。
//
//比如输入字符串为 "LEETCODEISHIRING" 行数为 3 时，排列如下：
//
//L00     C04     I08     R12
//E01 T03 O05 E07 S09 I11 I13 G15
//E02     D06     H10     N14
//之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："LCIRETOESIIGEDHN"。
//
//另一个示例
//
//输入: s = "LEETCODEISHIRING", numRows = 4
//输出: "LDREOEIIECIHNTSG"
//解释:
//
//L00       D06       R12
//E01   O05 E07   I11 I13
//E02 C04   I08 H10   N14
//T03       S09       G15
//
//当numRows=5时
//
//L00             I08
//E01         E07 S09         G15
//E02     D06     H10     N14
//T03 O05         I11 I13
//C04             R12


package src;

import java.util.ArrayList;
import java.util.List;

public class ZigzagConversion {
    //按规律一行一行添加子元素
    public String solution(String s, int numRows) {
        //注意空串的情况
        if (s == null || s.length() == 0) {
            return "";
        }

        int len = s.length();

        //注意numRows为1的特殊情况
        if (numRows == 1) {
            return s;
        }

        StringBuffer newStr = new StringBuffer();

        int j, step, flag;
        for (int i = 0; i < numRows; i++) {
            j = i;
            //flag=1表示当前正在往下走，flag=0表示现在正在往上走
            flag = 1;
            while (j < len) {
                newStr.append(s.charAt(j));

                //当是第一行或最后一行时，每次步数都一定
                if (i == 0 || i == numRows - 1) {
                    step = 2 * (numRows - 1);
                } else if (flag == 1) {
                    //往下到底，再往右上到原来那行
                    step = (numRows - i - 1) + (numRows - i - 1);
                    //转变flag
                    flag = 0;
                } else {
                    //往右上走到顶，再往下走到原来那行
                    step = (i - 0) + (i - 0);
                    //转变flag
                    flag = 1;
                }
                j += step;
            }
        }
        return newStr.toString();
    }

    //官方给出的一个解法，其实类似使用二维数组将字符排列好，但此方法更高级
    public String solution_official(String s, int numRows) {

        if (numRows == 1) return s;

        //字符串列表，一行一个字符串
        List<StringBuilder> rows = new ArrayList<>();
        //初始化每行
        for (int i = 0; i < Math.min(numRows, s.length()); i++)
            rows.add(new StringBuilder());

        int curRow = 0;
        boolean goingDown = false;

        for (char c : s.toCharArray()) {
            rows.get(curRow).append(c);
            if (curRow == 0 || curRow == numRows - 1) goingDown = !goingDown;
            curRow += goingDown ? 1 : -1;
        }

        StringBuilder ret = new StringBuilder();
        for (StringBuilder row : rows) ret.append(row);
        return ret.toString();
    }

    public static void main(String[] args) {
        String s = "LEETCODEISHIRING";

        ZigzagConversion z = new ZigzagConversion();

        System.out.println(z.solution(s, 5));
    }
}
