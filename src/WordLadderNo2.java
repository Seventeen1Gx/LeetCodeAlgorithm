// 126. 单词接龙 II
//
// 给定两个单词 beginWord 和 endWord 和一个字典 wordList，找出所有从 beginWord 到 endWord 的最短转换序列。
//
// 转换需遵循如下规则：
// 每次转换只能改变一个字母。
// 转换后得到的单词必须是字典中的单词。
//
// 说明:
// 如果不存在这样的转换序列，返回一个空列表。
// 所有单词具有相同的长度。
// 所有单词只由小写字母组成。
// 字典中不存在重复的单词。
// 你可以假设 beginWord 和 endWord 是非空的，且二者不相同。


package src;

import java.util.*;

public class WordLadderNo2 {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> ret = new ArrayList<>();

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

        boolean flag = false;

        Queue<List<String>> queue = new LinkedList<>();
        List<String> root = new ArrayList<>();
        root.add(beginWord);
        queue.offer(root);
        Set<String> visited = new HashSet<>();
        visited.add(beginWord);
        while (!queue.isEmpty()) {
            if (flag) {
                break;
            }
            int size = queue.size();
            // 这一层添加的元素记在 temp 中，等这一层遍历完，再加到总的 visited 中
            Set<String> temp = new HashSet<>();
            while (size-- > 0) {
                List<String> node = queue.poll();
                String s = node.get(node.size() - 1);
                if (s.equals(endWord)) {
                    ret.add(node);
                    flag = true;
                }
                for (int i = 0; i < L; i++) {
                    String newWord = s.substring(0, i) + '.' + s.substring(i + 1);
                    map.getOrDefault(newWord, new ArrayList<>()).forEach(
                            str -> {
                                if (!visited.contains(str)) {
                                    List<String> nextNode = new ArrayList<>(node);
                                    nextNode.add(str);
                                    queue.offer(nextNode);
                                    temp.add(str);
                                }
                            }
                    );
                }
            }
            visited.addAll(temp);
        }
        return ret;
    }

    public static void main(String[] args) {
        new WordLadderNo2().findLadders("hit", "cog", Arrays.asList("hot", "dot", "dog", "lot", "log", "cog"));
    }
}
