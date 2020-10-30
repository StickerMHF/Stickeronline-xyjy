package com.tb.service.cdxyh.utils;

import net.sourceforge.pinyin4j.PinyinHelper;

public class Pinyin4jUtil {
    public static String getFirstPinYinHeadChar(String str) {
        StringBuffer pybf = new StringBuffer();
        char word = str.charAt(0);
        // 提取汉字的首字母
        String[] pinyinArray;
        // 是否是因为字母
        if (word > 128) {
            pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            // 如果不是汉字，就返回*
            if (pinyinArray != null) {
                pybf.append(pinyinArray[0].charAt(0));
            } else {
                pybf.append("*");
            }
        } else {
            // 是字母直接返回，不是返回*
            if (Character.isLetter(word)) {
                pybf.append(word);
            } else {
                pybf.append("*");
            }
        }
        // 全部返回大写
        return pybf.toString().toUpperCase();
    }

    public static void main(String[] args) {
        System.out.println(Pinyin4jUtil.getFirstPinYinHeadChar("!@#"));
    }
}
