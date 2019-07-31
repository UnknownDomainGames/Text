package nullengine.text;

import nullengine.text.attribute.TextAttribute;
import nullengine.text.attribute.TextAttributeManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Text {

    private static TextAttributeManager textAttributeManager = new TextAttributeManager();

    private String text;

    private HashMap<Class<? extends TextAttribute>, TextAttribute> attributeHashMap = new HashMap<>();

    public Text(String text) {
        this.text = text;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAttribute(TextAttribute textAttribute) {
        this.attributeHashMap.put(textAttribute.getClass(), textAttribute);
    }

    public boolean hasAttribute(Class<? extends TextAttribute> textAttributeClass) {
        return attributeHashMap.containsKey(textAttributeClass);
    }

    public TextAttribute getAttribute(Class<? extends TextAttribute> textAttributeClass) {
        return attributeHashMap.get(textAttributeClass);
    }

    public TextAttribute removeAttribute(Class<? extends TextAttribute> textAttributeClass) {
        return attributeHashMap.remove(textAttributeClass);
    }

    public TextAttribute[] getAttributes() {
        return attributeHashMap.values().toArray(new TextAttribute[0]);
    }

    public String serialize() {
        return insertSlash(text) + "{" + serializeAttributes() + "}";
    }

    private String insertSlash(String text) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char ch : text.toCharArray()) {
            switch (ch) {
                case '{':
                case '}':
                    stringBuilder.append('\\');
                default:
                    stringBuilder.append(ch);
            }
        }
        return stringBuilder.toString();
    }

    private String serializeAttributes() {

        TextAttribute[] attributes = getAttributes();

        StringBuilder stringBuilder = new StringBuilder();

        for (TextAttribute attribute : attributes) {

            stringBuilder.append(textAttributeManager.getName(attribute.getClass()));
            stringBuilder.append("{");
            stringBuilder.append(insertSlash(attribute.serialize()));
            stringBuilder.append("}");

        }
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return "Text{" +
                "text='" + text + '\'' +
                ", attributes=" + attributeHashMap +
                '}';
    }

    public static TextAttributeManager getTextAttributeManager() {
        return textAttributeManager;
    }

    public static Text as(String string) {
        return new Text(string);
    }

    public static Text[] deserialize(String serializeText) {

        List<Text> texts = new ArrayList<>();

        int left = 0;

        var string = serializeText;

        while ((left = indexOfLeft(string)) != -1) {

            Text text = Text.as(deleteSlash(string.substring(0, left)));

            var lastString = string.substring(left + 1);

            int right = getRight(lastString);

            deserializeAttribute(lastString.substring(0, right))
                    .stream()
                    .forEach(textAttribute -> text.setAttribute(textAttribute));

            string = string.substring(right + 1);

            texts.add(text);
        }

        return texts.toArray(new Text[0]);
    }

    private static String deleteSlash(String text) {
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];

            switch (ch) {
                case '{':
                case '}':
                    stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
                default:
                    stringBuilder.append(ch);
            }
        }
        return stringBuilder.toString();
    }

    private static int indexOfLeft(String text) {
        char[] chars = text.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '{' && (i == 0 || chars[i - 1] != '\\')) {
                return i;
            }
        }
        return -1;
    }

    private static int getRight(String text) {
        char[] chars = text.toCharArray();

        int leftNum = 0;

        for (int i = 0; i < chars.length; i++) {

            char ch = chars[i];

            if (ch == '{' && chars[i - 1] != '\\')
                leftNum++;

            if (ch == '}' && (i == 0 || chars[i - 1] != '\\'))
                if (leftNum != 0)
                    leftNum--;
                else
                    return i;
        }
        return -1;
    }

    private static List<TextAttribute> deserializeAttribute(String text) {

        List<TextAttribute> list = new ArrayList<>();

        int left = 0;

//        System.out.println("1   " + text);

        while ((left = indexOfLeft(text)) != -1) {

            var name = text.substring(0, left);

//            System.out.println("2   " + name);
//            System.out.println("3   " + text.substring(left + 1));
            var right = getRight(text.substring(left + 1));

            var value = text.substring(left + 1, left + 1 + right);
//            System.out.println("4   " + value);

            list.add(textAttributeManager.deserialize(name, deleteSlash(value)));

            text = text.substring(left + right + 2);

//            System.out.println("5   " + text);

        }

        return list;
    }

}
