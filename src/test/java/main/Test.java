package main;

import unknowndomain.text.Text;
import unknowndomain.text.TextBuilder;
import unknowndomain.text.attribute.*;

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

        System.out.println(text.serialize());

        System.out.println(Text.deserialize(text.serialize())[0].serialize());
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

    @org.junit.Test
    public void builderTest(){
        TextAttributeManager textAttributeManager = Text.getTextAttributeManager();

        textAttributeManager.putAttribute("color", ColorAttribute.class);

        textAttributeManager.putAttribute("deleteLine", DeleteLineAttribute.class);

        textAttributeManager.putAttribute("bold", BoldAttribute.class);

        List<Text> textList = TextBuilder.builder()
                .append("耗子女装")
                .attribute("color",255,255,255)
                .attribute("bold")
                .attribute("deleteLine")
                .append("   女装万岁")
                .attribute("color",111,111,111)
                .attribute("bold")
                .get();

        textList.forEach(text -> System.out.print(text.serialize()));
        System.out.println();
    }

}
