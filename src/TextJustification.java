//68. 文本左右对齐
//
//给定一个单词数组和一个长度 maxWidth，重新排版单词，使其成为每行恰好有 maxWidth 个字符，且左右两端对齐的文本。
//
//你应该使用“贪心算法”来放置给定的单词；也就是说，尽可能多地往每行中放置单词。必要时可用空格 ' ' 填充，使得每行恰好有 maxWidth 个字符。
//
//要求尽可能均匀分配单词间的空格数量。如果某一行单词间的空格不能均匀分配，则左侧放置的空格数要多于右侧的空格数。
//
//文本的最后一行应为左对齐，且单词之间不插入额外的空格。
//
//说明:
//单词是指由非空格字符组成的字符序列。
//每个单词的长度大于 0，小于等于 maxWidth。
//输入单词数组 words 至少包含一个单词。
//
//注意：
//若一行只能放下一个单词时，保持左对齐


package src;

import java.util.ArrayList;
import java.util.List;

public class TextJustification {
    //前i-1行已放好j-1个单词
    //现处理第i行，从第j个单词开始
    //对于第i行，先放入第一个单词，之后每空一格，再放一个单词
    //能得到放不下的第k个单词
    //即第i行，能放words[j]~words[k-1]，然后均匀分配单词间隔中的空白符
    public List<String> solution(String[] words, int maxWidth) {
        List<String> resultList = new ArrayList<>();
        process(resultList, words, maxWidth, 0);
        return resultList;
    }

    //resultList是将words[0]~words[start-1]处理好的结果，现要处理剩下的单词(尾递归)
    public void process(List<String> resultList, String[] words, int maxWidth, int start) {
        while (start < words.length) {
            int leftLength = maxWidth;
            int i = start;
            //添加第一个单词
            leftLength -= words[i].length();
            i++;
            while (leftLength >= 0) {
                if (i >= words.length)
                    break;
                //一个空白符+一个单词
                leftLength -= (words[i].length() + 1);
                i++;
            }
            if (leftLength >= 0) {
                //最后一行的情况，空白符有富余或刚好放满的情况
                StringBuffer sb = new StringBuffer();
                int cnt = 0;
                sb.append(words[start]);
                cnt += words[start].length();
                for (int j = start + 1; j < words.length; j++) {
                    sb.append(' ').append(words[j]);
                    cnt += 1 + words[j].length();
                }
                for (int j = 0; j < maxWidth - cnt; j++) {
                    sb.append(' ');
                }
                resultList.add(sb.toString());
                break;
            } else {

                resultList.add(adjust(words, start, i - 2, maxWidth));
                start = i - 1;
            }
        }
    }

    //将[start, end]放在一行上，并设置左右对齐
    public String adjust(String[] words, int start, int end, int maxWidth) {
        StringBuffer sb = new StringBuffer();
        if (start == end) {
            //一行只能放一个单词，这时是左对齐的
            sb.append(words[start]);
            //剩下都是空白符
            for (int i = 0; i < maxWidth - words[start].length(); i++) {
                sb.append(' ');
            }
        } else {
            //间隔数 = 单词数 - 1
            int n = end - start;
            //总空白符个数
            int m = maxWidth;
            for (int i = start; i <= end; i++) {
                m -= words[i].length();
            }
            //平均每个间隔的空白符数
            int a = m / n;
            //余下空白符(从左往右，每个间隔分得一个)
            int b = m % n;

            //添加第一个单词
            sb.append(words[start]);
            //添加空白符+单词
            for (int i = start + 1; i <= end; i++) {
                //添加平均该有的a个空白符
                for (int j = 0; j < a; j++) {
                    sb.append(' ');
                }
                //添加多余的空白符(前b个间隔，每个多一个空白符)
                if (b > 0) {
                    sb.append(' ');
                    b--;
                }
                //添加单词
                sb.append(words[i]);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        TextJustification t = new TextJustification();
        t.solution(new String[]{"ask", "not", "what", "your", "country", "can", "do", "for", "you", "ask", "what", "you", "can", "do", "for", "your", "country"},
                16);
    }

}

