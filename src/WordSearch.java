// 79. 单词搜索
//
// 给定一个二维网格和一个单词，找出该单词是否存在于网格中。
//
// 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。
//
// 注意
// 单词在矩阵中可以拐弯→回溯试错


package src;

public class WordSearch {
    public boolean solution(char[][] board, String word) {
        if (board == null || word == null) {
            return false;
        }

        int m = board.length;
        if (m == 0) {
            return false;
        }

        int n = board[0].length;

        // 每个元素都作为开头
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                boolean[][] tag = new boolean[m][n];
                if (backtrack(board, i, j, word, 0, tag)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean backtrack(char[][] board, int i, int j, String word, int k, boolean[][] tag) {
        // 已保证进入函数时的 i，j 都是没有越界和没被访问过的

        // 表示从 i,j 位置开始，寻找 word[k:]，tag 标记了可访问的元素
        if (board[i][j] != word.charAt(k)) {
            return false;
        }

        // 当前对应位置元素相等，且 word 已匹配到最后一个字符
        if (k == word.length() - 1) {
            return true;
        }

        // 当前对应位置元素相等，但 word 还有字符需要匹配

        // 标记 board 当前位置为已访问
        tag[i][j] = true;
        // 从 4 个方向：上下左右，进入下一层
        for (int l = 0; l < 4; l++) {
            switch (l) {
                case 0:
                    // 向上
                    if (i - 1 >= 0 && !tag[i - 1][j] && backtrack(board, i - 1, j, word, k + 1, tag)) {
                        return true;
                    }
                    break;
                case 1:
                    // 向下
                    if (i + 1 < board.length && !tag[i + 1][j] && backtrack(board, i + 1, j, word, k + 1, tag)) {
                        return true;
                    }
                    break;
                case 2:
                    // 向左
                    if (j - 1 >= 0 && !tag[i][j - 1] && backtrack(board, i, j - 1, word, k + 1, tag)) {
                        return true;
                    }
                    break;
                case 3:
                    // 向右
                    if (j + 1 < board[0].length && !tag[i][j + 1] && backtrack(board, i, j + 1, word, k + 1, tag)) {
                        return true;
                    }
                    break;
                default:
            }
        }
        // 当前位置的 4 个方向都走不通，回到走到该位置的上一个状态
        tag[i][j] = false;
        return false;
    }
}
