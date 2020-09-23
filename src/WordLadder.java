// 127. 单词接龙
//
// 给定两个单词 beginWord 和 endWord 和一个字典，找到从 beginWord 到 endWord 的最短转换序列的长度。
//
// 转换需遵循如下规则：
// 每次转换只能改变一个字母。
// 转换过程中的中间单词必须是字典中的单词。
//
// 说明:
// 如果不存在这样的转换序列，返回 0。
// 所有单词具有相同的长度。
// 所有单词只由小写字母组成。
// 字典中不存在重复的单词。
// 你可以假设 beginWord 和 endWord 是非空的，且二者不相同。


package src;

import java.util.*;

public class WordLadder {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // 思路
        // 只有一个字符不相同的两个单词之间存在一条边
        // 对词典建造图
        // 转化为从起点到终点的最短距离问题

        // 对于一个单词，关键是在单词表中寻找只相差一个字母的单词

        // 预处理获得一个 map
        // 键是通配模式，值是满足该通配模式的字符串
        HashMap<String, List<String>> map = new HashMap<>();
        int L = beginWord.length();
        wordList.forEach(
                s -> {
                    for (int i = 0; i < L; i++) {
                        String newWord = s.substring(0, i) + '.' + s.substring(i + 1);
                        List<String> list = map.getOrDefault(newWord, new ArrayList<>());
                        list.add(s);
                        map.put(newWord, list);
                    }
                }
        );

        // BFS
        Queue<Node> queue = new LinkedList<>();
        queue.offer(new Node(beginWord, 1));
        Set<String> visited = new HashSet<>();
        visited.add(beginWord);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            String s = node.s;
            int level = node.level;
            if (s.equals(endWord)) {
                return level;
            }
            for (int i = 0; i < L; i++) {
                String newWord = s.substring(0, i) + '.' + s.substring(i + 1);
                List<String> list = map.getOrDefault(newWord, new ArrayList<>());
                list.forEach(
                        str -> {
                            if (!visited.contains(str)) {
                                queue.offer(new Node(str, level + 1));
                                visited.add(str);
                            }
                        }
                );
            }
        }
        return 0;
    }

    static class Node {
        String s;
        int level;

        Node(String s, int level) {
            this.s = s;
            this.level = level;
        }
    }
}