// 841. 钥匙与房间
//
// 有 N 个房间，开始时你位于 0 号房间。每个房间有不同的号码：0，1，2，...，N-1，并且房间里可能有一些钥匙能使你进入下一个房间。
//
// 在形式上，对于每个房间 i 都有一个钥匙列表 rooms[i]，每个钥匙 rooms[i][j] 由 [0,1，...，N-1] 中的一个整数表示，其中 N = rooms.length。
// 钥匙 rooms[i][j] = v 可以打开编号为 v 的房间。
//
// 最初，除 0 号房间外的其余所有房间都被锁住。
//
// 你可以自由地在房间之间来回走动。
//
// 如果能进入每个房间返回 true，否则返回 false。


package src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KeysAndRooms {
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        if (rooms == null || rooms.size() <= 0) {
            throw new IllegalArgumentException("参数错误");
        }

        int n = rooms.size();
        boolean[] visited = new boolean[n];
        // 0 号房间是可进的
        visited[0] = true;
        int visitedNum = 1;
        Set<Integer> keys = new HashSet<>(rooms.get(0));
        Set<Integer> newKeys;
        while (true) {
            newKeys = new HashSet<>();

            for (int i : keys) {
                if (!visited[i]) {
                    newKeys.addAll(rooms.get(i));
                    visited[i] = true;
                    visitedNum++;
                }
            }

            if (newKeys.isEmpty()) {
                break;
            }

            keys = newKeys;
        }

        return visitedNum == n;
    }

    // 当 x号房间中有 y 号房间的钥匙时
    // 我们就可以从 x 号房间去往 y 号房间
    // 如果我们将这 n 个房间看成有向图中的 n 个节点
    // 那么上述关系就可以看作是图中的 x 号点到 y 号点的一条有向边
    // 然后就是从 0 结点出发，能否遍历整个图 → DFS and BFS

    public static void main(String[] args) {
        List<List<Integer>> input = new ArrayList<>();
        List<Integer> subInput = new ArrayList<>();
        subInput.add(1);
        input.add(subInput);
        subInput = new ArrayList<>();
        subInput.add(2);
        input.add(subInput);
        subInput = new ArrayList<>();
        subInput.add(3);
        input.add(subInput);
        subInput = new ArrayList<>();
        input.add(subInput);
        new KeysAndRooms().canVisitAllRooms(input);
    }
}
