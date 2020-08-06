package com.jon.thirdpay.utils;

import java.math.BigDecimal;

/**
 * @author testjon 2020-08-05
 */
public class MoneyUtil {

    /**
     * 元转分
     * @param yuan
     * @return
     */
    public static Integer Yuan2Fen(BigDecimal yuan) {
        return yuan.movePointRight(2).intValue();
    }

    /**
     * 分转元
     * @param fen
     * @return
     */
    public static BigDecimal Fen2Yuan(Integer fen) {
        return new BigDecimal(fen).movePointLeft(2);
    }
}
