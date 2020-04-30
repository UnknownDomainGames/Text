package engine.text.util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import engine.text.util.TextEnum.Style;

public class SubText extends HoverText {

	final List<HoverText> list = new ArrayList<HoverText>();
	final TextClick click;

	protected SubText(String text) {
		this(text, null, null, null, new HashSet<Style>(), Arrays.asList(), null);
	}

	protected SubText(String text, Color color, String font_name, Integer font_size, Set<Style> styles, List<HoverText> list, TextClick click) {
		super(text, color, font_name, font_size, styles);
		this.list.addAll(list);
		this.click = click;
	}

	public List<HoverText> getHoverTexts() {
		return new ArrayList<HoverText>(list);
	}

	public TextClick onClick() {
		return click;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder extends HoverText.Builder {

		List<HoverText> list = new ArrayList<HoverText>();
		TextClick click = null;

		public Builder setHoverTexts(List<HoverText> list) {
			this.list.clear();
			this.list.addAll(list);
			return this;
		}

		public Builder setHoverText(int i, HoverText hText) {
			this.list.set(i, hText);
			return this;
		}

		public Builder addHoverText(HoverText hText) {
			this.list.add(hText);
			return this;
		}

		public Builder addHoverText(int i, HoverText hText) {
			this.list.add(i, hText);
			return this;
		}

		public Builder setClick(TextClick click) {
			this.click = click;
			return this;
		}

		@Override
		public SubText build() {
			return new SubText(text, color, font_name, font_size, styles, list, click);
		}

	}

}
