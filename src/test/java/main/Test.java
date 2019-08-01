package main;

import nullengine.text.Text;
import nullengine.text.TextBuilder;
import nullengine.text.Texts;
import nullengine.text.attribute.*;
import org.junit.Assert;

import java.awt.*;
import java.util.List;

public class Test {

    @org.junit.Test
    public void Test(){

        TextAttributeManager textAttributeManager = Text.getTextAttributeManager();

        textAttributeManager.putAttribute("color", ColorAttribute.class);

        textAttributeManager.putAttribute("deleteLine", DeleteLineAttribute.class);

        textAttributeManager.putAttribute("test",TestAttribute.class);

        Text text = Text.as("a{b}c");

        text.setAttribute(textAttributeManager.getAttribute("color",1,1,1));

        text.setAttribute(textAttributeManager.getAttribute("deleteLine",new Object[0]));

        text.setAttribute(textAttributeManager.getAttribute("test","alskdhjfg"));


        Assert.assertEquals(text.serialize(),Text.deserialize(text.serialize()).get(0).serialize());

        TextBuilder builder = TextBuilder.builder();

        Texts texts = builder.append("abc")
                .attribute("color",255,123,53)
                .append("bcs")
                .attribute("color", Color.BLUE)
                .attribute("deleteLine")
                .get();

        String s = texts.serialize();

        Texts texts1 = Text.deserialize(s);

        Assert.assertEquals(s,texts1.serialize());
    }

    public static class TestAttribute extends TextAttribute {

        private String text;

        public TestAttribute(String text) {
            this.text = text;
        }


        public static TestAttribute deserialize(String text){
            return new TestAttribute(text.substring(1,text.length()-1));
        }

        @Override
        public String serialize() {
            return "{"+text+"}";
        }
    }

}
