//30. 串联所有单词的子串
//
//给定一个字符串 s 和一些长度相同的单词 words。找出 s 中恰好可以由 words 中所有单词串联形成的子串的起始位置。
//
//注意子串要与 words 中的单词完全匹配，中间不能有其他字符，但不需要考虑 words 中单词串联的顺序。


package src;

import java.util.*;

public class SubstringWithConcatenationOfAllWords {
    //一种方法是，考虑所有单词的组合，每种组合再去s中看是否存在匹配的子串
    //但对于较大的排列数，就超时了
    //从提交结果看到，words的长度可以达到18，这就有(18!)种可能要测试
    //而且这种方法，没有用到题目中words数组长度相同的条件
    public List<Integer> solution1(String s, String[] words) {
        List<Integer> ansList = new ArrayList<>();
        if (s != null && s.length() != 0 && words != null && words.length != 0)
            recursion(ansList, words, 0, s);
        return ansList;
    }

    //与求全排列的过程相同，只是在得到一个排列的时候，判断是否匹配
    public void recursion(List<Integer> ansList, String[] words, int k, String s) {
        if (k == words.length) {
            getIndex(ansList, s, words);
        } else {
            Set<String> hs = new HashSet<>();
            for (int i = k; i < words.length; i++) {
                swap(words, k, i);
                //这里的包含函数是否符合我们所想？测试过，是！
                if (!hs.contains(words[k])) {
                    hs.add(words[k]);
                    recursion(ansList, words, k + 1, s);
                }
                swap(words, k, i);
            }
        }
    }

    public void swap(String[] words, int i, int j) {
        String t = words[i];
        words[i] = words[j];
        words[j] = t;
    }

    //将words按顺序串联，然后判断是否在s中出现，出现的位置加入ansList
    public void getIndex(List<Integer> ansList, String s, String[] words) {
        StringBuffer sb = new StringBuffer();
        for (String word : words)
            sb.append(word);

        String wordsConcat = sb.toString();
        int index = s.indexOf(wordsConcat);
        while (index != -1) {
            //这里包含函数是否符合我们所想？测试过，是！
            if (!ansList.contains(index))
                ansList.add(index);
            index = s.indexOf(wordsConcat, index + 1);
        }
    }

    //写一个全排列，来获得一些启发，假定数组nums中有重复元素
    //递归思路：len=1时，nums的全排列就是nums本身，n>=2时，nums的全排列是每次选择一个元素作为头元素，然后接上剩下元素的全排列
    //对于重复元素，我们保证它只做一次头元素
    public List<int[]> getPermutation(int[] nums) {
        //准备数据
        List<int[]> result = new ArrayList<>();

        //求排列
        recursion(result, nums, 0);

        return result;
    }

    //0到k-1已排好，现在要将剩余元素排好加上去
    //nums[0:len)的全排列=nums[0:k)(已排好) + nums[k:len)(每种排列可能)
    public void recursion(List<int[]> result, int[] nums, int k) {
        if (k == nums.length) {
            //不能简单将此时的nums加入result，因为添加的是一个引用，那么nums改变，result存nums的这个位置也要改变
            int[] perm = nums.clone();
            result.add(perm);
        } else {
            //记录 当前 层级，用做过头节点的元素值，后面相同的元素将不会再做头节点
            Set<Integer> hs = new HashSet<>();
            //给nums[k+1:len-1]排列
            //即将nums[k+1]...nums[len-1]依次放到当前开头，然后去排剩下的元素
            for (int i = k; i < nums.length; i++) {
                swap(nums, k, i);
                //看看当前放的头节点之前有没有放过
                if (!hs.contains(nums[k])) {
                    hs.add(nums[k]);
                    //没有才能进入下一步
                    recursion(result, nums, k + 1);
                }
                //恢复，使用下一个元素放开头
                swap(nums, k, i);
            }
        }
    }

    //nums[i]和nums[j]交换位置
    public void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }

    //由于words数组每个元素的长度相同，其实我们是直到匹配的子串的长度的
    //然后我们遍历原串中每个这样长度的子串
    //然后每个子串我们又能把它等长分成words.length个等长部分
    //使用类似覆盖的方式，用我们有的words中的每个单词去盖着与单词相同的部分，每个单词使用一次
    //而如果能刚好覆盖，则表示是匹配的
    public List<Integer> solution2(String s, String[] words) {
        List<Integer> ansList = new ArrayList<>();

        if (s == null || s.length() == 0 || words == null || words.length == 0)
            return ansList;

        //表示words中每个单词出现的次数
        Map<String, Integer> wordsHm = new HashMap<>();
        //表示每个子串分成words.length个等长部分，每个部分出现的次数
        Map<String, Integer> subStrHm;

        //初始化wordsHm
        for (String word : words) {
            if (wordsHm.containsKey(word)) {
                wordsHm.put(word, wordsHm.get(word) + 1);
            } else {
                wordsHm.put(word, 1);
            }
        }

        //每个单词长度
        int l = words[0].length();
        //单词串联后的长度
        int len = l * words.length;

        //遍历每个len长度的子串[start, end)
        int start = 0, end = start + len, subStart, subEnd;
        String subStr, subSubStr;
        while (end <= s.length()) {
            //初始化当前子串的map
            subStrHm = new HashMap<>();

            subStr = s.substring(start, end);
            //对于子串的每个部分做统计(这里首尾下标在子串里重新计数了)
            subStart = 0;
            subEnd = subStart + l;
            while (subEnd <= len) {
                subSubStr = subStr.substring(subStart, subEnd);
                if (!wordsHm.containsKey(subSubStr)) {
                    //这部分无法用words中的串覆盖，则整个子串肯定不能匹配，跳到下一个子串
                    break;
                } else {
                    //这部分可以用words中的串覆盖，但要看是不是用光了
                    if (subStrHm.containsKey(subSubStr)) {
                        //已使用的次数
                        int n = subStrHm.get(subSubStr);
                        //可用次数
                        int m = wordsHm.get(subSubStr);
                        //如果之前已经用完了，现在出现的这个就不能被覆盖，跳到下一个子串
                        if (n == m)
                            break;
                        else
                            //没用完，就用一次
                            subStrHm.put(subSubStr, n + 1);
                    } else {
                        //使用words中的一个与subSubStr单词来覆盖
                        subStrHm.put(subSubStr, 1);
                    }
                }
                subStart = subEnd;
                subEnd = subStart + l;
            }
            //若当前子串的所有子部分都检查过关，则找到满足条件的结果
            if (subEnd > len && !ansList.contains(start))
                ansList.add(start);

            //下一个子串
            start++;
            end = start + len;
        }
        return ansList;
    }

    //上面的方法子串每次移动一格，然后遍历了所有可能的子串
    //我们换种遍历方法，start=0时，检查子串[start, start+len)，下一次检查[start+l, start+l+len]，即跳过一个单词
    //直到碰到结尾，然后start才后移一格，继续每次跳着单词检查
    //最终，这种遍历方式也能遍历所有长度为len的子串
    //
    //但在这种遍历方式下，我们有三种情况可以优化
    //1.当上一次子串完全匹配时，每个子部分和数组单词对应
    //下一个子串，是上一个子串舍去开头l长度，加入之后l长度
    //则对于该子串，我们不清空subStrHm，仅仅对舍去部分对应的单词的使用次数减1
    //然后对新单词进行判断
    //2.出现子串的子部分不存在于words中
    //则下一个子串应该从这一个不存在的子部分的下一处开始，不然仍旧包含这个不存在的子部分
    //3.出现符合的子部分，但是该部分对应的单词使用次数超了
    //下一个子串等于每次舍弃上一个子串开头单词，直到舍去一个这个用超的单词
    //不然这个用超的单词还存在于子串中的话，那肯定也是不匹配的
    public List<Integer> solution2_optimization(String s, String[] words) {
        List<Integer> ansList = new ArrayList<>();

        if (s == null || s.length() == 0 || words == null || words.length == 0)
            return ansList;

        //表示words中每个单词出现的次数
        Map<String, Integer> wordsHm = new HashMap<>();
        //表示每个子串分成words.length个等长部分，每个部分出现的次数
        Map<String, Integer> subStrHm = new HashMap<>();

        //初始化wordsHm
        for (String word : words) {
            if (wordsHm.containsKey(word)) {
                wordsHm.put(word, wordsHm.get(word) + 1);
            } else {
                wordsHm.put(word, 1);
            }
        }

        //每个单词长度
        int l = words[0].length();
        //单词串联后的长度
        int len = l * words.length;

        //新的遍历方式
        for (int i = 0; i < l; i++) {
            //开始新一次的，每隔一格单词长度遍历子串
            int start = i;
            int end = start + len;
            //标记上一子串是否匹配
            boolean flag = false;
            while (end <= s.length()) {
                if (!flag)
                    subStrHm = new HashMap<>();

                String subStr = s.substring(start, end);
                //遍历子串的每个子部分(仍采用在整个串s中的计数，方便跳到下一子串)
                int subStart = start;
                int subEnd = subStart + l;

                if (flag) {
                    //只用检查最后一个子部分
                    subEnd = end;
                    subStart = subEnd - l;
                }

                //记录退出循环的原因
                int situation = 1;
                while (subEnd <= end) {
                    String subSubStr = s.substring(subStart, subEnd);
                    if (!wordsHm.containsKey(subSubStr)) {
                        //对应情况2，跳到下一子串
                        start = subEnd;
                        end = start + len;
                        situation = 2;
                        break;
                    } else {
                        if (subStrHm.containsKey(subSubStr)) {
                            int n = subStrHm.get(subSubStr);
                            int m = wordsHm.get(subSubStr);
                            if (n == m) {
                                //对应情况3
                                //寻找当前子串中该单词第一次出现的位置
                                //(注意要在当前子串中，注释掉的就不是当前子串的第一，而是整个串里第一次出现的)
                                //然后下一子串在这个位置之后的一个位置
                                //int index = s.indexOf(subSubStr);
                                int index = s.indexOf(subSubStr, start);
                                start = index + l;
                                end = start + len;
                                situation = 3;
                                break;
                            } else {
                                subStrHm.put(subSubStr, n + 1);
                            }
                        } else {
                            subStrHm.put(subSubStr, 1);
                        }
                    }
                    subStart = subEnd;
                    subEnd = subStart + l;
                }
                if (situation == 1) {
                    //处理之前先把本次匹配的子串的对应答案保存
                    if (!ansList.contains(start))
                        ansList.add(start);

                    //对应情况1
                    flag = true;
                    //取匹配子串的第一个单词
                    String firstWord = s.substring(start, start + l);
                    //下一个子串跳过这个单词
                    start = start + l;
                    end = start + len;
                    //更新subStrHm，表示移出这个单词
                    subStrHm.put(firstWord, subStrHm.get(firstWord) - 1);
                } else {
                    //情况1只能只能用在其后一次子串，所以每次退出循环都要置为假
                    //不然我们假设，情况1的下一次子串比较时，只比较子串末尾长度为l的部分
                    //若是以情况2或3退出，没有下面这个重置语句，则再下一次的子串还以为上一次是情况1
                    //可以注释下面这句，然后试试main函数中的样例2
                    flag = false;
                }
            }
        }

        return ansList;
    }


    //优化的方法二，别人的代码
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> res = new ArrayList<>();
        int wordNum = words.length;
        if (wordNum == 0) {
            return res;
        }
        int wordLen = words[0].length();

        HashMap<String, Integer> allWords = new HashMap<>();
        for (String w : words) {
            int value = allWords.getOrDefault(w, 0);
            allWords.put(w, value + 1);
        }

        //将所有移动分成 wordLen 类情况
        for (int j = 0; j < wordLen; j++) {
            HashMap<String, Integer> hasWords = new HashMap<>();
            int num = 0; //记录当前 HashMap2（这里的 hasWords 变量）中有多少个单词
            //每次移动一个单词长度
            for (int i = j; i < s.length() - wordNum * wordLen + 1; i = i + wordLen) {
                boolean hasRemoved = false; //防止情况三移除后，情况一继续移除
                while (num < wordNum) {
                    String word = s.substring(i + num * wordLen, i + (num + 1) * wordLen);
                    if (allWords.containsKey(word)) {
                        int value = hasWords.getOrDefault(word, 0);
                        hasWords.put(word, value + 1);
                        //出现情况三，遇到了符合的单词，但是次数超了
                        if (hasWords.get(word) > allWords.get(word)) {
                            // hasWords.put(word, value);
                            hasRemoved = true;
                            int removeNum = 0;
                            //一直移除单词，直到次数符合了
                            while (hasWords.get(word) > allWords.get(word)) {
                                String firstWord = s.substring(i + removeNum * wordLen, i + (removeNum + 1) * wordLen);
                                int v = hasWords.get(firstWord);
                                hasWords.put(firstWord, v - 1);
                                removeNum++;
                            }
                            num = num - removeNum + 1; //加 1 是因为我们把当前单词加入到了 HashMap 2 中
                            i = i + (removeNum - 1) * wordLen; //这里依旧是考虑到了最外层的 for 循环，看情况二的解释
                            break;
                        }
                        //出现情况二，遇到了不匹配的单词，直接将 i 移动到该单词的后边（但其实这里
                        //只是移动到了出现问题单词的地方，因为最外层有 for 循环， i 还会移动一个单词
                        //然后刚好就移动到了单词后边）
                    } else {
                        hasWords.clear();
                        i = i + num * wordLen;
                        num = 0;
                        break;
                    }
                    num++;
                }
                if (num == wordNum) {
                    res.add(i);

                }
                //出现情况一，子串完全匹配，我们将上一个子串的第一个单词从 HashMap2 中移除
                if (num > 0 && !hasRemoved) {
                    String firstWord = s.substring(i, i + wordLen);
                    int v = hasWords.get(firstWord);
                    hasWords.put(firstWord, v - 1);
                    num = num - 1;
                }

            }

        }
        return res;
    }

    public static void main(String[] args) {
        SubstringWithConcatenationOfAllWords s = new SubstringWithConcatenationOfAllWords();

        //测试contains函数是否符合我们所想
        //List<Integer> list = new ArrayList<>();
        //list.add(10);
        //list.add(20);
        //if (list.contains(10))    //这里判断结果是真
        //    list.add(30);

        //Set<String> set = new HashSet<>();
        //set.add("abc");
        //set.add("def");
        //if (set.contains("abc"))  //这里判断也为真
        //    set.add("ghj");

        //因为这里两种量都是字面值，相同字面的放在相同的地方


        //s.solution2_optimization("wordgoodgoodgoodbestword", new String[]{"word", "good", "best", "word"});
        s.solution2_optimization("cbaacacbaa", new String[]{"cb", "aa"});
        //s.solution2_optimization("barfoothefoobarman", new String[]{"foo", "bar"});
    }
}
