package van.planifolia.license.util;

import lombok.extern.slf4j.Slf4j;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xiaoxu.yxx on 2014/6/23.
 */
@Slf4j
public final class Strings {
    /**
     * key切割符
     */
    private static final String KEY_SPLIT = ":";
    /**
     * 空字符串
     */
    private static final String EMPTY_STR = "";

    private static final String DEF_PWD_END = "@#*2024";

    /**
     * 连接字符串数组,
     *
     * @param array
     * @return
     */
    public static String join(CharSequence... array) {
        return join(array, "");
    }

    /**
     * 连接字符串数组,
     *
     * @return
     */
    public static String join(String separator, CharSequence... array) {
        return join(array, separator);
    }

    /**
     * 连接字符串数组,
     *
     * @return
     */
    public static String join(Object[] array) {
        return join(array, "");
    }

    /**
     * 连接字符串数组,
     *
     * @return
     */
    public static String join(Object[] array, String separator) {
        return join(array, separator, 0, array.length);
    }

    /**
     * 连接字符串数组,
     *
     * @return
     */
    public static String join(Object[] array, char separator) {
        return join(array, separator, 0, array.length);
    }

    /**
     * 连接字符串数组,
     *
     * @return
     */
    public static String join(Object[] array, String separator, int startIndex, int endIndex) {
        if (array == null) return null;
        if (separator == null) separator = "";
        int L = endIndex - startIndex;
        if (L <= 0) return "";
        StringBuilder sb = new StringBuilder(L * (array[startIndex] == null ? 16 : array[startIndex].toString().length())
                + separator.length());
        for (int i = startIndex; i < endIndex; i++) {
            if (array[i] != null) {
                sb.append(array[i]).append(separator);
            }
        }
        int index = sb.lastIndexOf(separator);
        if (index != -1) sb.delete(index, sb.length());
        return sb.toString();
    }

    /**
     * 连接字符串数组,
     *
     * @return
     */
    public static String join(Object[] array, char separator, int startIndex, int endIndex) {
        if (array == null) return null;
        int L = endIndex - startIndex;
        if (L <= 0) return "";
        StringBuilder sb = new StringBuilder(L * (array[startIndex] == null ? 16 : array[startIndex].toString().length()) + 1);
        for (int i = startIndex; i < endIndex; i++) {
            if (array[i] != null) {
                sb.append(array[i]).append(separator);
            }
        }
        int index = sb.lastIndexOf(separator + "");
        if (index != -1) sb.deleteCharAt(index);
        return sb.toString();
    }

    /**
     * 连接字符串数组,
     *
     * @return
     */
    public static <T> String join(Iterable<T> iterable, String separator) {
        if (iterable == null) return null;
        StringBuilder sb = new StringBuilder();
        for (T t : iterable) {
            if (t != null) sb.append(t).append(separator);
        }
        int index = sb.lastIndexOf(separator);
        if (index != -1) sb.delete(index, separator.length() + index);
        return sb.toString();
    }

    /**
     * 字符串切割成List
     *
     * @param regex 切割的正则
     * @param input 被切割的字符串
     * @return 切割后的List
     */
    public static List<String> split(String regex, CharSequence input) {
        int index = 0;
        List<String> matchList = new ArrayList<String>();
        Matcher m = Pattern.compile(regex).matcher(input);
        while (m.find()) {
            matchList.add(input.subSequence(index, m.start()).toString());
            index = m.end();
        }
        if (index < input.length())
            matchList.add(input.subSequence(index, input.length()).toString());
        return matchList;
    }


    /**
     * 将字符串每个单词首字母大写
     *
     * @param s 字符串
     * @return 首字母大写后的新字符串
     */
    public static String capitalize(CharSequence s) {
        return capitalize(s, " \t\r\n");
    }

    public static String capitalize(CharSequence s, String separator) {
        StringTokenizer st = new StringTokenizer(s.toString(), separator, true);
        StringBuilder sb = new StringBuilder(s.length());
        while (st.hasMoreTokens()) {
            String tok = st.nextToken();
            sb.append(tok.substring(0, 1).toUpperCase())
                    .append(tok.substring(1).toLowerCase());
        }
        return sb.toString();
    }

    /**
     * 将字符串首字母小写
     *
     * @param s 字符串
     * @return 首字母小写后的新字符串
     */
    public static String lowerFirst(CharSequence s) {
        int len = s.length();
        if (len == 0) return "";
        char c = s.charAt(0);
        if (Character.isLowerCase(c)) return s.toString();
        return String.valueOf(Character.toLowerCase(c)) +
                s.subSequence(1, len);
    }

    /**
     * 将字符串首字母大写
     *
     * @param s 源字符串
     * @return 首字母大写后的字符串
     */
    public static String upperFirst(CharSequence s) {
        int len = s.length();
        if (len == 0) return "";
        char c = s.charAt(0);
        if (Character.isUpperCase(c)) return s.toString();
        return String.valueOf(Character.toUpperCase(c)) +
                s.subSequence(1, len);
    }

    /**
     * 检查两个字符串的忽略大小写后是否相等.
     *
     * @param s1 字符串A
     * @param s2 字符串B
     * @return true 如果两个字符串忽略大小写后相等,且两个字符串均不为null
     */
    public static boolean equalsIgnoreCase(String s1, String s2) {
        return s1 == null ? s2 == null : s1.equalsIgnoreCase(s2);
    }

    /**
     * 检查两个对象的忽略大小写后是否相等.
     *
     * @param obj 字符串对象A
     * @param str 字符串B
     * @return true 如果两个字符串忽略大小写后相等,且两个字符串均不为null
     */
    public static boolean equalsIgnoreCase(Object obj, String str) {
        return obj == null ? str == null : valueOf(obj).equalsIgnoreCase(str);
    }

    /**
     * 检查两个字符串是否相等.
     *
     * @param s1 字符串A
     * @param s2 字符串B
     * @return true 如果两个字符串相等,且两个字符串均不为null
     */
    public static boolean equals(CharSequence s1, CharSequence s2) {
        return Objects.equals(s1, s2);
    }

    /**
     * 判断字符串是否以特殊字符开头
     *
     * @param s 字符串
     * @param c 特殊字符
     * @return 是否以特殊字符开头
     */
    public static boolean startsWithChar(CharSequence s, char c) {
        return null != s && (s.length() != 0 && s.charAt(0) == c);
    }

    /**
     * 判断字符串是否以特殊字符结尾
     *
     * @param s 字符串
     * @param c 特殊字符
     * @return 是否以特殊字符结尾
     */
    public static boolean endsWithChar(CharSequence s, char c) {
        return null != s && (s.length() != 0 && s.charAt(s.length() - 1) == c);
    }

    public static boolean isEmpty(CharSequence... css) {
        for (CharSequence cs : css) {
            if (isEmpty(cs)) return true;
        }
        return false;
    }

    /**
     * @param cs 字符串
     * @return 是不是为空字符串
     */
    public static boolean isEmpty(CharSequence cs) {
        return null == cs || cs.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return null != cs && cs.length() > 0;
    }

    public static boolean isNotEmpty(CharSequence... css) {
        for (CharSequence cs : css) {
            if (isEmpty(cs)) return false;
        }
        return true;
    }

    /**
     * @param cs 字符串
     * @return 是不是为空白字符串
     */
    public static boolean isBlank(CharSequence cs) {
        int L;
        if (cs == null || (L = cs.length()) == 0)
            return true;
        for (int i = 0; i < L; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(CharSequence str) {
        return !isBlank(str);
    }

    /**
     * 去掉字符串前后空白
     *
     * @param cs 字符串
     * @return 新字符串
     */
    public static String trim(CharSequence cs) {
        if (null == cs)
            return null;
        if (cs instanceof String)
            return ((String) cs).trim();
        int length = cs.length();
        if (length == 0)
            return cs.toString();
        int l = 0;
        int last = length - 1;
        int r = last;
        for (; l < length; l++) {
            if (!Character.isWhitespace(cs.charAt(l)))
                break;
        }
        for (; r > l; r--) {
            if (!Character.isWhitespace(cs.charAt(r)))
                break;
        }
        if (l > r)
            return "";
        else if (l == 0 && r == last)
            return cs.toString();
        return cs.subSequence(l, r + 1).toString();
    }

    public static String valueOf(Object o, String defaultVal) {
        return o == null ? defaultVal : o.toString();
    }

    public static String valueOf(Object o) {
        return Strings.valueOf(o, "");
    }

    /**
     * 构建Redis的key 根据字符串
     *
     * @param strs 字符串数组
     * @return 构建出来的redisKey
     */
    public static String buildRedisKey(String... strs) {
        if (strs.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (String str : strs) {
                sb.append(str).append(KEY_SPLIT);
            }
            return sb.substring(0, sb.length() - 1);
        }
        return EMPTY_STR;
    }

    /**
     * 随机 length 长度密码
     *
     * @param length 密码长度
     * @return 生成的密码明文
     */
    public static String randomStrMixNumber(Integer length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            Random random = new Random();
            int nub = random.nextInt(57) + 65;
            if (nub > 90 && nub < 97) {
                sb.append(nub / 10);
                continue;
            }
            char randomChar = (char) nub;
            sb.append(randomChar);
        }
        return sb.toString();
    }

    /**
     * 安全的随机类
     */
    private static final SecureRandom SECURE_RANDOM;

    /*
     * 初始化安全随机类
     */
    static {
        try {
            SECURE_RANDOM = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to initialize SecureRandom", e);
        }
    }

    /**
     * 生成指定长度的随机字符串，线程安全且尽量唯一
     *
     * @param length 长度
     * @return 生成到的随机字符串
     */
    public static String generateUniqueRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length) {
            UUID uuid = new UUID(SECURE_RANDOM.nextLong(), SECURE_RANDOM.nextLong());
            sb.append(uuid.toString().replaceAll("-", ""));
        }
        return sb.substring(0, length);
    }


    /**
     * 填充字符串的颜色
     *
     * @param format 字符串类型
     * @param color  颜色
     * @return 构建出来的带颜色的字符串
     */
    public static String toColor(String format, String color) {
        return color + format + ConsoleColors.RESET;
    }

    /**
     * 根据用户名去生产一个密码
     *
     * @param brandUsername 用户名
     * @return 生产出来的密码
     */
    public static String buildDefPassword(String brandUsername) {
        return brandUsername + DEF_PWD_END;
    }

    /**
     * @param srcPoint 原始的坐标值
     * @return 转换后的坐标值
     */
    public static String covMarsPoint(String srcPoint) {
        return srcPoint.replace(".", "");
    }

    // 可供选择的字符集，包括大小写字母和数字
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    // 生成随机数的安全随机数生成器
    private static final SecureRandom RANDOM = new SecureRandom();

    // 推荐码的长度
    private static final int CODE_LENGTH = 8;

    // 预计算字符集的长度
    private static final int CHARACTERS_LENGTH = CHARACTERS.length();

    /**
     * 生成尽可能随机的推荐吗信息
     *
     * @return 生成的推荐码
     */
    public static String generateRecommendationCode() {
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            // 随机选择字符并添加到 StringBuilder 中
            int index = RANDOM.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }
        return code.toString();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            System.out.println(generateRecommendationCode());
        }
    }

    // 通过缓存字节数组减少对象创建
    private static final ThreadLocal<char[]> CODE_BUFFER = ThreadLocal.withInitial(() -> new char[CODE_LENGTH]);

    public static String generateRecommendationCodeSafe() {
        char[] buffer = CODE_BUFFER.get();
        for (int i = 0; i < CODE_LENGTH; i++) {
            buffer[i] = CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS_LENGTH));
        }
        return new String(buffer);
    }
}
