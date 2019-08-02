package nullengine.text.escape;

import java.util.HashSet;

public abstract class Escaper {

    public abstract String escape(String text);


    /**
     * only escape text can use
     *
     * @param text
     * @return
     */
    public abstract String unescape(String text);

    /**
     * index unescape char
     * @param text
     * @param ch
     * @return
     */
    public abstract int indexOf(String text, char ch);

    /**
     * only escape char can be index
     *
     * @param text
     * @param ch
     * @return
     */
    public abstract int indexOfEscapedChar(String text, char ch);

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {

        private SimpleEscaper escaper = new SimpleEscaper();

        public Builder addEscape(char ch) {
            escaper.addChar(ch);
            return this;
        }

        public Escaper build() {
            return escaper;
        }

    }

    private static class SimpleEscaper extends Escaper {

        private HashSet<Character> needEscapedChar = new HashSet<>();

        public SimpleEscaper() {
            addChar('\\');
        }

        public void addChar(char ch) {
            needEscapedChar.add(ch);
        }

        @Override
        public String escape(String text) {
            StringBuilder stringBuilder = new StringBuilder();

            char[] chars = text.toCharArray();

            for (char ch : chars) {
                if (needEscapedChar.contains(ch))
                    stringBuilder.append('\\');
                stringBuilder.append(ch);
            }

            return stringBuilder.toString();
        }

        @Override
        public String unescape(String text) {
            StringBuilder stringBuilder = new StringBuilder();

            boolean escape = false;

            char[] chars = text.toCharArray();

            for (int i = 0; i < chars.length; i++) {
                char ch = chars[i];
                if (needEscapedChar.contains(ch))
                    if (!escape)
                        escape = true;
                    else {
                        stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
                        escape = false;
                    }
                stringBuilder.append(ch);
            }

            return stringBuilder.toString();
        }

        @Override
        public int indexOf(String text, char ch) {
            boolean escape = false;

            char[] chars = text.toCharArray();

            for(int i = 0;i<chars.length;i++){
                char c = chars[i];

                if(escape){
                    escape = false;
                    continue;
                }

                if(c == '\\'){
                    escape = true;
                    continue;
                }

                if(c == ch)
                    return i;
            }
            return -1;
        }

        @Override
        public int indexOfEscapedChar(String text, char ch) {
            boolean escape = false;

            char[] chars = text.toCharArray();

            for(int i = 0;i<chars.length;i++){
                char c = chars[i];

                if(escape){
                    if(c == ch)
                        return i;
                    escape = false;
                    continue;
                }

                if(c == '\\'){
                    escape = true;
                    continue;
                }
            }
            return -1;
        }
    }

}
