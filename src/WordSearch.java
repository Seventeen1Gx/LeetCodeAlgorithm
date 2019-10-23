//79. 单词搜索
//
//给定一个二维网格和一个单词，找出该单词是否存在于网格中。
//
//单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。


package src;

public class WordSearch {
    //遍历矩阵每个位置，然后横向+竖向比较
    public boolean solution(char[][] board, String word) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board.length - j >= word.length()) {

                }
                if (board[0].length - i >= word.length()) {
                    
                }
            }
        }
    }
}
