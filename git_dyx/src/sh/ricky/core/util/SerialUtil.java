package sh.ricky.core.util;

import java.sql.Date;

/**
 * 生成编号相关方法
 * 
 * @author 万达信息股份有限公司 shichenyue
 * @version 1.0
 * 
 * <pre>
 * ■变更记录■
 * Aug 11, 2011 创建
 * </pre>
 */
public class SerialUtil {
    /** 企业登记申请号的numberId */
    public static final String ETPS_APP_NUMBER_ID = "7";

    /** 外省市企业标识的numberId */
    public static final String ETPS_OUTLAND_NUMBER_ID = "22";

    /** 其它实体标识(企业) */
    public static final String ETPS_OTHER_NUMBER_ID = "23";

    /** 投资自然人标识的numberId */
    public static final String PERSON_INVT_NUMBER_ID = "20";

    /** 出资者的sequenceName */
    public static final String SEQ_INVESTOR = "SEQ_INVESTOR";

    /**
     * 得到新的编号
     * 
     * @param formulae 公式，通过number_id从sys_numbs中取得
     * @param organId 从session中获得
     * @param currentDate 当前时间，由容器产生
     * @param threadId 条线编码
     * @param currentNumb 当前的顺序号, 利用getParamFormDate方法得到formDate参数，再根据formDate执行一系列数据库查询得到
     * @see sh.ricky.sabic.util.GenNumUtil#getParamFormDate(String)
     * @return String 编号
     */
    public static String genRegularNum(String formulae, String organId, Date currentDate, String threadId, String currentNumb) {

        // 分别替换formulae中的连续A、R、Y、M、D、T、#和X,前面带~的予以保留
        formulae = replaceCertainMark(formulae, "A", "R");
        formulae = replaceCertainMark(formulae, "R", organId);
        String dataStr = "" + currentDate;
        formulae = replaceCertainMark(formulae, "Y", dataStr.substring(0, 4));
        formulae = replaceCertainMark(formulae, "M", dataStr.substring(5, 7));
        formulae = replaceCertainMark(formulae, "D", dataStr.substring(8));
        formulae = replaceCertainMark(formulae, "T", threadId);
        formulae = replaceCertainMark(formulae, "#", currentNumb);
        formulae = replaceCertainMark(formulae, "X", null);

        // 去掉所有的的~符号
        formulae = formulae.replaceAll("~", "");

        return formulae;
    }

    /**
     * 根据公式生成formDate参数
     * 
     * @param formulae
     * @return 时间的形式
     */
    public static String getParamFormDate(String formulae) {
        // 有非~开头的D则返回D，有非~开头的M返回M，有非~开头的Y则返回Y
        if (formulae.matches(".*[^~]D+.*")) {
            return "D";
        } else if (formulae.matches(".*[^~]M+.*")) {
            return "M";
        } else if (formulae.matches(".*[^~]Y+.*")) {
            return "Y";
        } else {
            return null;
        }
    }

    /**
     * 替换相应的formulae中相应的mark
     * 
     * @param formulae 公式
     * @param mark 要替换的字符
     * @param repl 用于替换的字符串
     * @return String 被替换过的formulae
     */
    private static String replaceCertainMark(String formulae, String mark, String repl) {
        // 找到不以~开头的每个字符为mark的连续字符串
        int posFound = -1;
        int posMark = formulae.indexOf(mark);

        while (posMark != -1) {
            if (posMark == 0 || !"~".equals(formulae.substring(posMark - 1, posMark))) {
                posFound = posMark;
                posMark = -1;
            } else {
                posMark = formulae.indexOf(mark, posMark + 1);
            }
        }
        // 将该字符串用替换算法进行替换
        if (posFound >= 0) {
            posMark = posFound;
            int len = 0;
            while (posMark + len + 1 <= formulae.length() && mark.equals(formulae.substring(posMark + len, posMark + len + 1))) {
                len++;
            }
            formulae = replaceMarkAlgorithm(formulae, mark, posMark, len, repl);
        }
        return formulae;
    }

    /**
     * mark的替换算法
     * 
     * @param formulae 公式
     * @param mark 要替换的字符
     * @param startPos 要替换的开始位置
     * @param len 替换的长度
     * @param repl 用于替换的字符串
     * @return String 被替换过的formulae
     */
    private static String replaceMarkAlgorithm(String formulae, String mark, int startPos, int len, String repl) {
        if ("A".equals(mark)) {
            // A全替换成R
            StringBuffer sb = new StringBuffer("");
            for (int i = 0; i < len; i++) {
                sb.append(repl);
            }
            return new StringBuffer(formulae.substring(0, startPos)).append(sb).append(formulae.substring(startPos + len)).toString();

        } else if ("R".equals(mark) || "T".equals(mark)) {
            // R则用organId替换(T用threadId替换)，太长左trim到合适长度

            return new StringBuffer(formulae.substring(0, startPos)).append(repl.substring(0, len)).append(formulae.substring(startPos + len)).toString();

        } else if ("Y".equals(mark)) {
            // Y用年份代替，Y不足4位时取最右边的两位

            return new StringBuffer(formulae.substring(0, startPos)).append(len < 4 ? repl.substring(repl.length() - 2) : repl).append(
                    formulae.substring(startPos + len)).toString();

        } else if ("M".equals(mark) || "D".equals(mark) || "#".equals(mark)) {
            // M用月份替换(D用日替换,#替换成当前序列号)，位数不足前面补零

            StringBuffer sb = new StringBuffer("");
            for (int i = 0; i < len - repl.length(); i++) {
                sb.append("0");
            }
            sb.append(repl);
            return new StringBuffer(formulae.substring(0, startPos)).append(sb).append(formulae.substring(startPos + len)).toString();

        } else if ("X".equals(mark)) {
            // 校验位算法，对前20位数字使用加权和取模的混合算法

            int pos = formulae.indexOf(mark);
            if (pos != -1) {
                // 20位的加权因子数组
                int[] weightArr = { 3, 6, 2, 7, 3, 1, 9, 7, 1, 10, 9, 4, 5, 2, 6, 8, 5, 2, 4, 8 };
                int iMod = 0;
                for (int i = 0; i < weightArr.length; i++) {
                    iMod += Integer.parseInt(formulae.substring(i, i + 1)) * weightArr[i];
                }
                int i21 = 11 - iMod % 11;
                String c21;
                if (i21 == 10) {
                    c21 = "X";
                } else if (i21 == 11) {
                    c21 = "0";
                } else {
                    c21 = i21 + "";
                }
                return formulae.replaceFirst("X", c21);
            }
            return formulae;
        }
        return null;
    }
}
