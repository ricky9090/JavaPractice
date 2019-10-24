package study.functionalstudy;


import java.util.Arrays;
import java.util.List;

/**
 * 字符串列表变换，使用传统循环遍历方式实现
 */
public class VersionA {
    public static void main(String[] args) {
        List<String> orgList = Arrays.asList("neal", "s", "stu", "j", "rich",
                "bob", "aiden", "j", "ethan", "liam",
                "mason", "noah", "lucas", "jacob", "jayden", "jack");

        VersionA a = new VersionA();
        System.out.println(a.cleanNames(orgList));
    }

    public String cleanNames(List<String> names) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).length() > 1) {
                result.append(capitalFirst(names.get(i))).append(",");
            }
        }
        return result.substring(0, result.length() - 1).toString();
    }

    public String capitalFirst(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1, s.length());
    }
}
