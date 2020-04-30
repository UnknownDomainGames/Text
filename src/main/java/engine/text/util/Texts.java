package engine.text.util;

import java.util.ArrayList;
import java.util.List;

public class Texts implements Cloneable {
	public static final Texts Default = new Texts();

	private final List<SubText> textList;

	private Texts() {
		this(new ArrayList<SubText>());
	}

	private Texts(List<SubText> textList) {
		this.textList = textList;
	}

	@Override
	public Texts clone() {
		return new Texts(getTexts());
	}

	public static Texts.Builder builder() {
		return new Texts.Builder();
	}

	public List<SubText> getTexts() {
		return new ArrayList<SubText>(textList);
	}

	public static class Builder {

		List<SubText> list = new ArrayList<SubText>();

		private Builder() {

		}

		public Builder add(SubText text) {
			list.add(text);
			return this;
		}

		public Texts build() {
			return new Texts(list);
		}
	}
}
