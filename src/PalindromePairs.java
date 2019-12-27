//336. 回文对
//
//给定一组唯一的单词， 找出所有不同 的索引对(i, j)，使得列表中的两个单词， words[i] + words[j] ，可拼接成回文串。


package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PalindromePairs {
    //暴力法，对所有可能的x+y，判断其是否为回文串，但此方法超时
    public List<List<Integer>> solution1(String[] words) {
        List<List<Integer>> results = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            for (int j = i + 1; j < words.length; j++) {
                if (isPalindrome(words[i] + words[j])) {
                    List<Integer> list = new ArrayList<>();
                    list.add(i);
                    list.add(j);
                    results.add(list);
                }
                if (isPalindrome(words[j] + words[i])) {
                    List<Integer> list = new ArrayList<>();
                    list.add(j);
                    list.add(i);
                    results.add(list);
                }
            }
        }
        return results;
    }

    //s是否为回文串
    private boolean isPalindrome(String s) {
        StringBuffer sb = new StringBuffer(s);
        return sb.reverse().toString().equals(s);
    }

    //字典树根结点，用来存储words，便于快速寻找word
    private Node root;

    //假设x.len >= y.len，若x+y是回文串，则x.sub(0,i)=y.rev，且x.sub(i)是回文串
    //假设x.len < y.len，则与上情况相反
    public List<List<Integer>> solution2_FromOthers(String[] words) {
        int n = words.length;

        //构造字典树
        //i用来遍历每个单词
        this.root = new Node();
        for (int i = 0; i < n; i++) {
            //考虑的都是每个单词的逆置
            String rev = new StringBuilder(words[i]).reverse().toString();

            Node cur = root;

            if (isPalindrome(rev)) {
                cur.suffixs.add(i);
            }

            //j用来遍历word[i]的每个字符
            for (int j = 0; j < rev.length(); j++) {
                char ch = rev.charAt(j);
                if (cur.children[ch - 'a'] == null) {
                    cur.children[ch - 'a'] = new Node();
                }
                //指针下移
                cur = cur.children[ch-'a'];

                if (isPalindrome(rev.substring(j + 1))) {
                    cur.suffixs.add(i);
                }
            }
            cur.words.add(i);
        }

        //用以存放答案的列表
        List<List<Integer>> ans = new ArrayList<>();

        //i用来遍历单词
        for (int i = 0; i < n; i++) {
            String word = words[i];
            Node cur = root;
            //遍历单词的字符
            int j = 0;
            for ( ;j < word.length(); j++) {
                //到j位置，word[j:]为回文串，根据word[0:j)的逆置去找y
                //y其实就是在该节点位置上所有单词
                //因为我们插入的时候是用每个单词的逆序插入的:)
                if (isPalindrome(word.substring(j))) {
                    for (int k : cur.words) {
                        if (k != i) {
                            ans.add(Arrays.asList(i, k));
                        }
                    }
                }

                char ch = word.charAt(j);
                if (cur.children[ch - 'a'] == null) {
                    //之后也不可能找到y
                    break;
                }
                //指针下移
                cur = cur.children[ch - 'a'];

            }
            // words[i]的每个字符遍历完了，现在找所有大于words[i]长度且符合要求的单词
            // 即y>x的情况
            // suffixs列表就派上用场了:)
            if (j == word.length()) {
                for (int k : cur.suffixs) {
                    if (k != i) {
                        ans.add(Arrays.asList(i, k));
                    }
                }
            }
        }
        return ans;

    }

}

//字典树结点，字典树的边上是26个字母
class Node {
    //子节点数组
    public Node[] children;

    //存储以当前节点为终止节点的单词的在words中的下标
    public List<Integer> words;

    //保存所有到当前节点为止，其之后剩余字符可以构成回文对的单词在words中的下标
    public List<Integer> suffixs;


    public Node() {
        this.children = new Node[26];
        this.words = new ArrayList<>();
        this.suffixs = new ArrayList<>();
    }
}
