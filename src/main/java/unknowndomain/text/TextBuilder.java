package unknowndomain.text;

import unknowndomain.text.attribute.TextAttributeManager;

import java.util.ArrayList;
import java.util.List;

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

    public List<Text> get(){
        return texts;
    }

    public static TextBuilder builder(){
        return new TextBuilder();
    }


}
