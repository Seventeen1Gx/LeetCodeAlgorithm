// 332. 重新安排行程
//
// 给定一个机票的字符串二维数组 [from, to]，子数组中的两个成员分别表示飞机出发和降落的机场地点，对该行程进行重新规划排序。
// 所有这些机票都属于一个从 JFK（肯尼迪国际机场）出发的先生，所以该行程必须从 JFK 开始。
//
// 说明:
// 如果存在多种有效的行程，你可以按字符自然排序返回最小的行程组合。例如，行程 ["JFK", "LGA"] 与 ["JFK", "LGB"] 相比就更小，排序更靠前
// 所有的机场都用三个大写字母表示（机场代码）。
// 假定所有机票至少存在一种合理的行程。
//
// 示例 1:
//
// 输入: [["MUC", "LHR"], ["JFK", "MUC"], ["SFO", "SJC"], ["LHR", "SFO"]]
// 输出: ["JFK", "MUC", "LHR", "SFO", "SJC"]


package src;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReconstructItinerary {

    /**
     * 以 key 为起始的行程是第几张票
     */
    Map<String, List<Integer>> map = new HashMap<>();

    public List<String> findItinerary(List<List<String>> tickets) {
        // 从 JFK 开始，将所有票都用一遍

        int n = tickets.size();
        // 统计
        for (int i = 0; i < n; i++) {
            String from = tickets.get(i).get(0);
            if (!map.containsKey(from)) {
                map.put(from, new ArrayList<>());
            }
            map.get(from).add(i);
        }
        // 排序，to 字母序小的在前面
        for (List<Integer> ticketNums : map.values()) {
            ticketNums.sort(Comparator.comparing(i -> tickets.get(i).get(1)));
        }

        boolean[] visited = new boolean[n];

        // 要先添加 "JFK"
        ret.add("JFK");
        dfs(0, n, "JFK", visited, tickets);
        return ret;
    }

    List<String> ret = new ArrayList<>();
    boolean isFinished = false;

    private void dfs(int i, int n, String from, boolean[] visited, List<List<String>> tickets) {
        // i 表示已使用的机票数
        // n 表示总的机票数
        // from 表示现在从哪个机场出发

        if (isFinished) {
            // 剪枝
            return;
        }
        if (i == n) {
            isFinished = true;
            return;
        }
        // OrDefault 防止出现空的情况
        List<Integer> ticketNums = map.getOrDefault(from, new ArrayList<>());
        for (Integer ticketNum : ticketNums) {
            if (visited[ticketNum]) {
                // 防止重复使用票
                continue;
            }
            String to = tickets.get(ticketNum).get(1);
            ret.add(to);
            visited[ticketNum] = true;
            dfs(i + 1, n, to, visited, tickets);
            if (!isFinished) {
                // 回溯，但需要避免把我们的答案给移除了
                ret.remove(ret.size() - 1);
                visited[ticketNum] = false;
            }
        }
    }

    private static List<List<String>> input() {
        Scanner sc = new Scanner(System.in);
        Pattern p = Pattern.compile("[A-Z]{3}");
        Matcher m = p.matcher(sc.nextLine());
        List<String> strings = new ArrayList<>();
        while (m.find()) {
            strings.add(m.group());
        }
        List<List<String>> ret = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            List<String> list = new ArrayList<>();
            list.add(strings.get(i++));
            list.add(strings.get(i));
            ret.add(list);
        }
        return ret;
    }

    public static void main(String[] args) {
        new ReconstructItinerary().findItinerary(input());
    }
}
