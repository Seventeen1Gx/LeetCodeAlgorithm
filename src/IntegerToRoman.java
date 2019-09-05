//12. 整数转换成罗马数字
//罗马数字包含以下七种字符： I， V， X， L，C，D 和 M。
//
//  字符          数值
//  I             1
//  V             5
//  X             10
//  L             50
//  C             100
//  D             500
//  M             1000
//
//例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
//
//通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
//
//I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
//X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。 
//C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
//
//给定一个整数，将其转为罗马数字。输入确保在 1 到 3999 的范围内。


public class IntegerToRoman {
    public String solution(int x) {
        StringBuffer ans = new StringBuffer();

        int n;

        //x有几个1000
        n = x / 1000;
        //几个1000，就加几个M(n<=3)
        while (n > 0) {
            ans.append('M');
            n--;
        }
        //除去1000，剩下的部分
        x = x % 1000;

        //x有几个100
        n = x / 100;
        if (n == 4)
            ans.append("CD");
        else if (n == 5)
            ans.append('D');
        else if (n == 9)
            ans.append("CM");
        else {
            //n不为4，不为5，不为9
            while (n > 0) {
                //注意800是DCCC，而不是8个C
                if (n > 5) {
                    ans.append('D');
                    //这个结束之后n不为0，否则就应该直接退出，而不是还加个C
                    n -= 5;
                }
                ans.append('C');
                n--;
            }
        }
        x = x % 100;

        //x有几个10
        n = x / 10;
        if (n == 4)
            ans.append("XL");
        else if (n == 5)
            ans.append('L');
        else if (n == 9)
            ans.append("XC");
        else {
            while (n > 0) {
                if (n > 5) {
                    ans.append('L');
                    n -= 5;
                }
                ans.append('X');
                n--;
            }
        }
        x = x % 10;

        //x有几个1
        n = x;
        if (n == 4)
            ans.append("IV");
        else if (n == 5)
            ans.append('V');
        else if (n == 9)
            ans.append("IX");
        else {
            while (n > 0) {
                if (n > 5) {
                    ans.append('V');
                    n -= 5;
                }
                ans.append('I');
                n--;
            }
        }

        return ans.toString();
    }

    public static void main(String[] args) {
        int m = 58;

        IntegerToRoman i = new IntegerToRoman();

        i.solution(m);
    }
}
