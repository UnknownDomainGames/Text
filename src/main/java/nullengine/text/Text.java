package nullengine.text;

import nullengine.text.attribute.TextAttribute;
import nullengine.text.attribute.TextAttributeManager;
import nullengine.text.escape.Escaper;
import nullengine.text.format.symbol.SymbolManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Text {

    private static TextAttributeManager textAttributeManager = new TextAttributeManager();

    private static Escaper ESCAPER = Escaper
            .builder()
            .addEscape('{')
            .addEscape('}')
            .build();

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
        return ESCAPER.escape(text) + "{" + serializeAttributes() + "}";
    }

    private String serializeAttributes() {

        TextAttribute[] attributes = getAttributes();

        StringBuilder stringBuilder = new StringBuilder();

        for (TextAttribute attribute : attributes) {

            stringBuilder.append(textAttributeManager.getName(attribute.getClass()));
            stringBuilder.append("{");
            stringBuilder.append(ESCAPER.escape(attribute.serialize()));
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

    public static Texts deserialize(String serializeText) {

        List<Text> texts = new ArrayList<>();

        int left = 0;

        var string = serializeText;

        while ((left = ESCAPER.indexOf(string,'{')) != -1) {
            Text text = Text.as(ESCAPER.unescape(string.substring(0, left)));

            var lastString = string.substring(left + 1);

            int right = getRight(lastString);

            deserializeAttribute(lastString.substring(0, right))
                    .stream()
                    .forEach(textAttribute -> text.setAttribute(textAttribute));

            string = string.substring(left + right + 2);

            texts.add(text);
        }

        return Texts.as(texts);
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

        while ((left = ESCAPER.indexOf(text,'{')) != -1) {

            var name = text.substring(0, left);

            var right = getRight(text.substring(left + 1));

            var value = text.substring(left + 1, left + 1 + right);

            list.add(textAttributeManager.deserialize(name, ESCAPER.unescape(value)));

            text = text.substring(left + right + 2);

        }

        return list;
    }

}
