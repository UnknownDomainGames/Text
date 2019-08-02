package nullengine.text.attribute;

import java.awt.*;

public class ColorAttribute extends TextAttribute {

    private int red;
    private int green;
    private int blue;
    private int alpha;

    public ColorAttribute(Color color){
        this(color.getRed(),color.getGreen(),color.getBlue(),color.getAlpha());
    }

    public ColorAttribute(int red, int green, int blue) {
        this(red,green,blue,255);
    }

    public ColorAttribute(int red, int green, int blue,int alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public int getAlpha(){
        return alpha;
    }

    public Color getColor(){
        return new Color(red,green,blue,alpha);
    }

    @Override
    public String serialize() {
        return red+","+green+","+blue+","+alpha;
    }

    public static ColorAttribute deserialize(String serializeText){
        String[] s = serializeText.split(",");
        return new ColorAttribute(Integer.valueOf(s[0]),Integer.valueOf(s[1]),Integer.valueOf(s[2]),Integer.valueOf(s[3]));
    }
}
