package Entity;

public class StringUtil {

    /**
     * 在字符串中所有反斜杠('\\')前添加百分号('%')
     * 注意：此操作会修改包括转义序列在内的所有反斜杠。
     *
     * @param input 原始字符串
     * @return 处理后的字符串
     */
    public static String addPercentBeforeBackslash(String input, String target, String replacement) {
        // 由于Java中反斜杠需要转义，因此使用"\\\\"
        // return input.replace("\\", "/");
        return input.replace(target, replacement);

    }

    public static String PathChanged(String Path, String sigh) {
        String[] Part = Path.split("/");
        String willReturn = "";
        boolean isOpen = false;
        for (String str : Part) {
            if (str.equals(sigh)) {
                isOpen = true;
            }
            if (isOpen) {
                willReturn += "\\" + str;
            }
        }
        return willReturn;
    }
}