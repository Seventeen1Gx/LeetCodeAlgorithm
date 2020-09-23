// 968. 监控二叉树
//
// 给定一个二叉树，我们在树的节点上安装摄像头。
//
// 节点上的每个摄影头都可以监视其父对象、自身及其直接子对象。
//
// 计算监控树的所有节点所需的最小摄像头数量。


package src.tree;

import src.util.TreeNode;

public class BinaryTreeCameras {

    public int minCameraCover(TreeNode root) {
        // 被摄像头监控到的结点，依然可以安装摄像头
        //     O
        //   O  O
        //     O O
        // 如上图，两个摄像头即可
        // 一开始我想的是被覆盖的结点就不用摄像头了，后来发现这种方式不是最优的

        // 每个结点维护三个变量值
        // a: 根节点安装摄像头情况下，覆盖整棵树需要的摄像头数
        // b: 覆盖整棵树需要的摄像头数，无论根结点是否放置摄像头
        // c: 覆盖两颗子树所需要的摄像头数，无论根节点是否被覆盖
        // a >= b >= c
        //
        // a = lc + rc + 1
        // b = min(a, la + rb, ra + lb)
        // 对于 c 要么等于 a，要么 lb + rb
        //
        // 最后，根节点的 b 值就是答案

        int[] array = dfs(root);
        return array[1];
    }

    public int[] dfs(TreeNode root) {
        if (root == null) {
            // 除二是为了不溢出
            // 设置成大值是为了 min 的时候不考虑
            return new int[]{Integer.MAX_VALUE / 2, 0, 0};
        }
        // 后序遍历
        int[] leftArray = dfs(root.left);
        int[] rightArray = dfs(root.right);
        int[] array = new int[3];
        array[0] = leftArray[2] + rightArray[2] + 1;
        array[1] = Math.min(array[0], Math.min(leftArray[0] + rightArray[1], rightArray[0] + leftArray[1]));
        array[2] = Math.min(array[0], leftArray[1] + rightArray[1]);
        return array;
    }
}
