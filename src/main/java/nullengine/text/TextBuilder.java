package nullengine.text;

import nullengine.text.attribute.TextAttributeManager;

import java.util.ArrayList;

public class TextBuilder {

    private TextAttributeManager textAttributeManager = Text.getTextAttributeManager();

    private ArrayList<Text> texts = new ArrayList<>();

    private Text last;

    public TextBuilder append(String s){
        Text text = Text.as(s);
        last = text;
        texts.add(text);
        return this;
    }

    public TextBuilder attribute(String attributeName, Object... objects){
        last.setAttribute(textAttributeManager.getAttribute(attributeName,objects));
        return this;
    }

    public Texts get(){
        return Texts.as(texts);
    }

    public static TextBuilder builder(){
        return new TextBuilder();
    }


}
