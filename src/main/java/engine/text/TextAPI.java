package engine.text;

import engine.text.util.SubText;
import engine.text.util.Texts;

public class TextAPI {

	public static Texts ofTexts(String textr) {
		return Texts.builder().add((SubText) SubText.builder().setText(textr).build()).build();
	}

}
