//28. 实现strStr
//
//实现 strStr() 函数。
//
//给定一个 haystack 字符串和一个 needle 字符串，在 haystack 字符串中找出 needle 字符串出现的第一个位置 (从0开始)。如果不存在，则返回  -1。
//
//对于本题而言，当 needle 是空字符串时我们应当返回 0 。这与C语言的 strstr() 以及 Java的 indexOf() 定义相符。


public class ImplementStrstr {
    public int solution1(String haystack, String needle) {
        if (needle.length() == 0)
            return 0;

        for (int i = 0; i < haystack.length(); i++) {
            //从i开始匹配
            int j;
            for (j = 0; j < needle.length() && i + j < haystack.length(); j++) {
                if (haystack.charAt(i + j) != needle.charAt(j))
                    //失配
                    break;
            }
            if (j == needle.length())
                return i;
        }
        return -1;
    }

    public int solution1_official(String haystack, String needle) {
        int sLen = haystack.length();
        int pLen = needle.length();

        //两个游标
        int i = 0;
        int j = 0;
        while (i < sLen && j < pLen) {
            if (haystack.charAt(i) == needle.charAt(j)) {
                i++;
                j++;
            } else {
                //失配i回退，j重新开始
                i = i - j + 1;
                j = 0;
            }
        }

        if (j == pLen)
            return i - j;
        else
            return -1;
    }


    //KMP算法--next数组:代表当前字符之前的字符串中，有多大长度的相同真前缀真后缀，即串自身不算；游标i不回退，而根据next数组确定。
    public int solution2(String haystack, String needle) {
        if (needle.length() == 0)
            return 0;

        int sLen = haystack.length();
        int pLen = needle.length();

        int[] next = getNextArr(needle);

        //两个游标
        int i = 0;
        int j = 0;
        while (i < sLen && j < pLen) {
            //j==-1表示第一个元素就失配，则i从下一个元素开始，j从0开始
            if (j == -1 || haystack.charAt(i) == needle.charAt(j)) {
                i++;
                j++;
            } else {
                //失配时i不用回退，j取next数组中的值
                j = next[j];
            }
        }

        if (j == pLen)
            return i - j;
        else
            return -1;
    }

    public int[] getNextArr(String needle) {
        int[] next = new int[needle.length()];

        next[0] = -1;

        int k = -1;
        int j = 0;

        //下标
        //0 1 ... k-1 k ... j-k ... j-2 j-1 j j+1
        //
        //当前在求next[j+1]，而j+1之前的next值都已知
        //令k=next[j]，则意味j之前的串s[0:j-1]有s[0:k-1]=s[j-k:j-1]
        //现在对于next[j+1]，它之前的串为s[0:j]，若s[k]==s[j]，则意味着s[0:k-1]+s[k]=s[j-k:j-1]+s[j]，即s[0:k]=s[j-k:j]
        //则next[j+1]=k+1(最长相同真前缀真后缀的长度)
        //若不相等，令t=next[k]
        //(可以减小k一个个试下去，但没必要，这样更快，也是KMP的思想，后缀是主串--j不回退，前缀是模式串，在k失配，新的匹配位置为next[k])
        //因为s[0:t-1]=s[k-t:k-1]，而s[0:k-1]=s[j-k:j-1] → s[k-t:k-1]=s[j-t:j-1](整个相等则后一小部分相等) → s[0:t-1]=s[j-t:j-1]
        //然后再比较s[t]与s[j]，不等的话，继续，u=next[t]，比较s[u]与s[j]，重复下去，直到二者相等或这个索引为-1，
        //索引为-1时next[j+1]=0，二者相等时，next[j+1]=这个索引+1
        //可以看出二种情况都是这个索引+1
        //而求next[0]时也可以看做是这样

        //求next[j+1]，所以循环条件为j+1<len
        while (j < needle.length() - 1) {
            if (k == -1 || needle.charAt(j) == needle.charAt(k)) {
                ++k;
                ++j;
                next[j] = k;
                //等同于
                //next[j+1]=k+1;
                //(求下一个next[j+1]，于是)
                //j++;
                //(k=当前j+1前一个j的next的值，即刚才得到的k+1)
                //k=k+1;
            } else {
                k = next[k];
            }
        }
        return next;
    }

    //改进的KMP算法--next数组的优化
    //未优化的next数组，对next[j+1]是看j+1之前的元素s[j]与s[next[j]]去比较
    //优化中，对于next[j+1]还要注意s[j+1]和s[next[j+1]]是否相同，不同则还按未优化的方法取next[j+1]的值
    //否则next[j+1]=next[next[j+1]]
    //因为在模式匹配过程中，在j+1位置失配时，即str[i]!=s[j+1]，模式串的指针将变为k=next[j+1]
    //接下来换str[i]与s[k]比较
    //而若s[k]==s[j+1]，那显然要失配，多此一举，所以next[j+1]=next[next[j+1]](变更小)
    //结合next数组生成过程，那么失配时，要么k=-1，要么s[k]!=s[j+1]，则str[i]和s[k]比较就不会多此一举了
    public int[] getNextArr_optimization(String needle) {
        int[] next = new int[needle.length()];

        next[0] = -1;

        int k = -1;
        int j = 0;
        while (j + 1 < needle.length()) {
            if (k == -1 || needle.charAt(j) == needle.charAt(k)) {
                k++;
                j++;
                //next[j+1]的值不再简单等于k+1
                if (needle.charAt(j) != needle.charAt(k))
                    //还按原来的方法
                    next[j] = k;
                else
                    next[j] = next[k];
            } else {
                k = next[k];
            }
        }
        return next;
    }
}
