
package cn.org.supay.demo;

import java.math.BigDecimal;
import java.util.*;


public class Test {




    public static void printNode(ListNode listNode) {
        System.out.printf("[");
        while (listNode != null) {
            System.out.printf(listNode.val + ",");
            listNode = listNode.next;
        }
        System.out.println("]");
    }

    /**
     *
     */
    public static void nodeAdd() {
        ListNode l1 = new ListNode(9);
        ListNode l2 = new ListNode(9);
        int i = 0;
        ListNode head1 = l1;
        ListNode head2 = l2;
        while (i < 6) {
            head1.next = new ListNode(9);
            head1 = head1.next;
            if (i < 3) {
                head2.next = new ListNode(9);
                head2 = head2.next;
            }
            i++;
        }
        printNode(l1);
        printNode(l2);
        ListNode result = nextSum2(l1, l2, 0);
        printNode(result);
    }
    public static ListNode nextSum2(ListNode l1, ListNode l2, int carray) {
        ListNode head = new ListNode(0);
        ListNode sumNode = head;
        while(l1 != null || l2 != null) {
            int nextSum = (l1 != null?l1.val:0) + (l2 != null?l2.val:0) + carray;
            carray = nextSum / 10;
            sumNode.val = nextSum % 10;

            l1 = l1 != null?l1.next:null;
            l2 = l2 != null?l2.next:null;
            // if (l1 != null || l2 != null || carray > 0) {
            //     sumNode.next = new ListNode(carray);
            // }
            sumNode.next = new ListNode(carray);
            sumNode = sumNode.next;
        }
        return head;
    }

    /**
     *
     * @param nums
     * @return
     */
    public static int singleNumber(int[] nums) {
        Map<Integer, Integer> count = new HashMap<>();
        for(int i=0;i < nums.length;i++) {
            int current = nums[i];
            if (count.containsKey(current)) {
                count.remove(current);
            } else {
                count.put(current, current);
            }
        }
        if (!count.isEmpty()) {
            for (Integer value : count.values()) {
                return value;
            }
        }
        return -1;
    }

    /**
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring(String s) {
//        if (s == null || "".equals(s)) {
//            return 0;
//        }
//        char[] chars = s.toCharArray();
//        int result = 1;
//        List list = new ArrayList();
//        for (int i = 0; i < chars.length; i++) {
//            if (list.contains(chars[i])) {
//                result = list.size() > result?list.size():result;
//                list.add(chars[i]);
//                list = list.subList(list.indexOf(chars[i]) + 1, list.size());
//            } else {
//                list.add(chars[i]);
//            }
//        }
//        result = list.size() > result?list.size():result;
//        System.out.println("before = " + result);
//        return result;

        // 算法2 滑动窗口
//        int n = s.length(), ans = 0;
//        Map<Character, Integer> map = new HashMap<>(chars.length);
//        for (int end = 0, start = 0; end < n; end++) {
//            // 从头开始取每一个元素
//            char alpha = s.charAt(end);
//            // 如果元素之前存在过
//            if (map.containsKey(alpha)) {
//                // 取出重复元素的下标
//                start = Math.max(map.get(alpha) + 1, start);
//            }
//            // 计算从上个重复元素的下一个开始，到当前重复元素至的字符串的长度
//            ans = Math.max(ans, end - start + 1);
//            // 将当前元素放入
//            map.put(s.charAt(end), end);
//        }
//
//        return ans;

        //a b c a b c d a d e
        //0 1 2 3 4 5 6 7 8 9
        int maxSize = 0;
        //记录ASCII 码字符出现的位置，以字符作为下标
        // 所有的字符由128个ASCII编码构成，所以定义一个数组，每个字符的ASCII编码作为数组下标，值是当前字符在字符串中的实际位置下标，默认值为0
        int[] dict = new int[128];

        //为了方便理解，这里把数组内容全部设为 -1，之后在记录的时候就可以从 0 开始，方便理解
//        Arrays.fill(dict, -1);
        //用于记录重复 ASCII 码字符出现的位置的值
        int repeatIndex = 0;
        // 当前下标
        int i = 0;
        int ASCII;
        // 遍历每个字符
        while (i < s.length()) {
            // 取出当前字符的ASCII值
            ASCII = s.charAt(i);
            //如果当前位置的值 > 0,说明之前已经赋值了，也说明当前字符重复
            if (dict[ASCII] > 0) {
                repeatIndex = Math.max(dict[ASCII], repeatIndex);
            }
            dict[ASCII] = i + 1;
            maxSize = Math.max(maxSize, i - (repeatIndex - 1));
            i++;
        }
        System.out.println("maxSize = " + maxSize);
        return maxSize;
    }


    /**
     * 给定两个大小为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的中位数。
     * @param nums1
     * @param nums2
     * @return
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        double result = 0.0;
        if ((nums1 == null && nums2 == null) || (nums1.length == 0 && nums2.length == 0)) {
            return result;
        }

        // 有1个元素，或2个元素时，相加
        if (nums1.length < 2 && nums2.length < 2) {
            return (double)((nums1.length == 1?nums1[0]:0) + (nums2.length == 1?nums2[0]:0)) / (nums1.length + nums2.length);
        }

        // 计算合并后的数组长度
        int totalLength = nums1.length + nums2.length;
        int[] mid = new int[2];
        // 计算中位数下标
        int midIndex = (totalLength / 2);

        // 构建长度为合并后中位数下标长度
        int midLength = midIndex + 1;
        int index1 = 0;
        int index2 = 0;
        for (int i = 0; i < midLength; i++) {
            if ((index1 >= nums1.length)) {
                mid[i%2] = nums2[index2];
                index2 ++;
            } else if ((index2 >= nums2.length)) {
                mid[i%2] = nums1[index1];
                index1 ++;
            } else {
                if (nums1[index1] - nums2[index2] > 0) {
                    mid[i%2] = nums2[index2];
                    index2 ++;
                } else {
                    mid[i%2] = nums1[index1];
                    index1 ++;
                }
            }
        }
        return totalLength % 2 == 0?(double) (mid[0] + mid[1]) / 2:mid[0];
    }

    public static void main(String[] args) {
//        lengthOfLongestSubstring("abba");
//        lengthOfLongestSubstring("abcabcbb");
//        lengthOfLongestSubstring("bbbbb");
//        lengthOfLongestSubstring("pwwkew");
//        lengthOfLongestSubstring("");
//        lengthOfLongestSubstring(" ");

        int[] nums1 = new int[2];
        nums1[0] = 1;
        nums1[1] = 3;

        int[] nums2 = new int[2];
        nums2[0] = 2;
        nums2[1] = 4;
//        nums2[0] = -3;
//        nums2[1] = -2;
//        nums2[2] = -1;
//        nums2[3] = 1;
//        nums2[4] = 5;
        System.out.println(findMedianSortedArrays(nums1, nums2));
    }
}

class ListNode {
    public int val;
    public ListNode next;

    public ListNode(int val) {
        this.val = val;
    }
}