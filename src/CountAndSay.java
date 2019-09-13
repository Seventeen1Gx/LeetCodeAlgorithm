//38. 报数
//
//报数序列是一个整数序列，按照其中的整数的顺序进行报数，得到下一个数。其前五项如下：
//
//1.     1
//2.     11
//3.     21
//4.     1211
//5.     111221
//6.     312211
//
//1 被读作  "one 1"  ("一个一") , 即 11。
//11 被读作 "two 1s" ("两个一"）, 即 21。
//21 被读作 "one 2",  "one 1" （"一个二" ,  "一个一") , 即 1211。
//
//给定一个正整数 n（1 ≤ n ≤ 30），输出报数序列的第 n 项。
//
//注意：整数顺序将表示为一个字符串。
//
//关键在于理解题目
//报数序列是有顺序的一组数
//第一个数为1，后面的每个数都是对其前一个数的"报数"
//比如第一个数为1，是"一个一"，所以第二个数写成11
//同理第三个数是第二个数念出来"二个1"，故为21
//
//所以除了第一个数，其他的数都是"几个几"这样的循环
//假设xy表示x个y，循环每次求出一对x、y


package src;

public class CountAndSay {
    public String solution(int n) {
        if (n <= 0)
            return "";

        if (n == 1)
            return "1";

        int x;
        char y;
        StringBuffer sb = new StringBuffer("1");

        //第一个循环控制求序列中的第n项
        int cnt = 1;
        while (cnt != n) {
            //第cnt项
            String s = sb.toString();
            //第二个循环根据前一项，求后一项
            sb = new StringBuffer();
            while (s.length() > 0) {
                y = s.charAt(0);
                x = 0;
                //统计几个y
                while ( x < s.length() && s.charAt(x) == y) x++;
                //等到x个y
                sb.append(x).append(y);
                //统计剩余部分
                s = s.substring(x);
            }
            cnt++;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        CountAndSay c = new CountAndSay();
        //打印前5项
        for (int i = 0; i < 5; i++) {
            System.out.println(c.solution(i + 1) + "\n");
        }

    }
}
