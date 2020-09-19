// 684. 冗余连接
//
// 在本问题中, 树指的是一个连通且无环的无向图。
//
// 输入一个图，该图由一个有着 N 个节点 (节点值不重复 1, 2, ..., N) 的树及一条附加的边构成。
// 附加的边的两个顶点包含在 1 到 N 中间，这条附加的边不属于树中已存在的边。
//
// 结果图是一个以边组成的二维数组。每一个边的元素是一对 [u, v] ，满足 u < v，表示连接顶点 u 和 v 的无向图的边。
//
// 返回一条可以删去的边，使得结果图是一个有着 N 个节点的树。
// 如果有多个答案，则返回二维数组中最后出现的边。答案边 [u, v] 应满足相同的格式 u < v。


package src;

import src.util.UnionFindSet;

public class RedundantConnection {
    public int[] findRedundantConnection(int[][] edges) {
        // 结点数
        int n = edges.length;
        // 开始时，使用并查集
        // 每个结点是独立的一个集合
        // 添加边，每次将两个集合合并
        // 如果发现，边连接的两个结点在同一个集合中，说明出现了环

        UnionFindSet unionFindSet = new UnionFindSet(n + 1);
        for (int[] edge : edges) {
            if (unionFindSet.union(edge[0], edge[1])) {
                return edge;
            }
        }
        return null;
    }
}
