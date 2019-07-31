package nullengine.text.attribute;

public class ColorAttribute extends TextAttribute {

    private int red;
    private int green;
    private int blue;

    public ColorAttribute(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
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

    @Override
    public String serialize() {
        return red+","+green+","+blue;
    }

    public static ColorAttribute deserialize(String serializeText){
        String[] s = serializeText.split(",");
        return new ColorAttribute(Integer.valueOf(s[0]),Integer.valueOf(s[1]),Integer.valueOf(s[2]));
    }
}
