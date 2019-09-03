//14. 最长公共前缀
//编写一个函数来查找字符串数组中的最长公共前缀。
//
//如果不存在公共前缀，返回空字符串 ""。


import java.util.SplittableRandom;

public class LongestCommonPrefix {
    public String solution1(String[] strs) {
        if (strs == null || strs.length == 0)
            return "";

        //先找出字符串数组中最短的字符串(若最短的字符串有多个，也不影响最后结果)
        String prefix = "";
        int minLen = Integer.MAX_VALUE;
        for (int i = 0; i < strs.length; i++) {
            if (strs[i].length() < minLen)
                prefix = strs[i];
        }

        //以最短字符串的前缀来检验所有的串
        for (int i = prefix.length(); i > 0; i--) {
            boolean flag = true;
            for (int j = 0; flag && j < strs.length; j++) {
                if (!strs[j].startsWith(prefix.substring(0, i)))
                    flag = false;
            }
            if (flag)
                return prefix.substring(0, i);
        }
        return "";
    }

    //看了题解，第一种方法运用思路，一组字符串S1,S2,...,Sn的最长公共前缀LCP，有：
    //LCP(S1,S2,...,Sn) = LCP(LCP(LCP(S1,S2),S3)...,Sn)
    public String solution2(String[] strs) {
        if (strs == null || strs.length == 0)
            return "";

        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++) {
            //当LCP(S1,S2,...,Si-1)的最长公共前缀不为Si的前缀时，则缩短前缀长度
            while (strs[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.length() == 0) return "";
            }
            //循环结束，prefix为LCP(S1,S2,...,Si)的公共前缀
        }

        return prefix;
    }

    //从前往后，比较每个字符串相同列的元素
    public String solution3(String[] strs) {
        if (strs == null || strs.length == 0)
            return "";
        for (int i = 0; i < strs[0].length(); i++) {
            char c = strs[0].charAt(i);
            for (int j = 1; j < strs.length; j++) {
                if (i == strs[j].length() || strs[j].charAt(i) != c)
                    //str[j]不符合当前列元素c的要求，再往后找也没可能了
                    return strs[0].substring(0, i);
            }
        }
        return strs[0];
    }

    //分治--LCP(strs)=LCP(LCP(strs左半部分),LCP(strs右半部分))
    public String solution4(String[] strs) {
        if (strs == null || strs.length == 0)
            return "";
        return divideConquer(strs, 0, strs.length - 1);
    }

    //返回[left, right]区间的strs的最长公共前缀
    public String divideConquer(String[] strs, int left, int right) {
        //数组中只有一个字符串
        if (left == right)
            return strs[left];
        if (left > right)
            return "";

        int mid = (left + right) / 2;
        String prefix1 = divideConquer(strs, left, mid);
        String prefix2 = divideConquer(strs, mid + 1, right);

        int i;
        for (i = 0; i < prefix1.length() && i < prefix2.length(); i++) {
            if (prefix1.charAt(i) != prefix2.charAt(i))
                return prefix1.substring(0, i);
        }
        if (i == prefix1.length())
            return prefix1;
        if (i == prefix2.length())
            return prefix2;

        return "";
    }


    //二分查找法--前缀长度在[1, minLen]中查找
    public String solution6(String[] strs) {
        if (strs == null || strs.length == 0)
            return "";

        //比方法1中获取最短字符串长度更简洁
        int minLen = Integer.MAX_VALUE;
        for (String str : strs) {
            minLen = Math.min(minLen, str.length());
        }

        //二分查找
        int low = 1;
        int high = minLen;
        while (low <= high) {
            int mid = (low + high) / 2;
            //认为最长公共前缀的长度为mid
            if (isCommonPrefixWithN(strs, mid))
                //认为前缀长度还可以更长
                low = mid + 1;
            else
                //前缀长度应该减小
                high = mid - 1;
        }
        return strs[0].substring(0, (low + high) / 2);
    }

    //strs数组是否有存在长度为n的公共前缀
    public boolean isCommonPrefixWithN(String[] strs, int n) {
        String prefix = strs[0].substring(0, n);
        for (int i = 1; i < strs.length; i++) {
            if (!strs[i].startsWith(prefix))
                return false;
        }
        return true;
    }

    //还有一种使用字典树的方法
}
