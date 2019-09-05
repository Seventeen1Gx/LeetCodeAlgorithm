//9. 回文数
//
//判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。


import java.util.ArrayList;
import java.util.List;

public class PalindromeNum {
    public boolean solution1(int x) {
        //负数肯定不是回文数
        if (x < 0)
            return false;

        //用list保存x的每位数字
        List<Integer> list = new ArrayList<>();

        while (x > 0) {
            list.add(x % 10);
            x /= 10;
        }

        //注意，列表中保存的是引用
        for (int i = 0, j = list.size() - 1; i < j; i++, j--) {
            if (!list.get(i).equals(list.get(j)))
                return false;
        }

        return true;
    }

    //将整数反转，再与原值比较(注意可能溢出的情况)
    public boolean solution2(int x) {
        if (x < 0)
            return false;

        long y = 0;
        int n, xTemp = x;
        while (xTemp > 0) {
            n = xTemp % 10;
            xTemp /= 10;
            y = y * 10 + n;
        }

        if (y == (long) x) {
            return true;
        }
        return false;
    }



    public static void main(String[] args) {
        int x = 121;

        PalindromeNum p = new PalindromeNum();

        p.solution2(x);
    }
}
