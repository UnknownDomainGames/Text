package engine.text.util;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import engine.text.util.TextEnum.Style;

public class HoverText {

	final String text;
	final Color color;
	final String font_name;
	final Integer font_size;
	final Set<TextEnum.Style> styles = new HashSet<TextEnum.Style>();

	protected HoverText(String text) {
		this(text, null, null, null, new HashSet<Style>());
	}

	protected HoverText(String text, Color color, String font_name, Integer font_size, Set<Style> styles) {
		this.text = text;
		this.color = color;
		this.font_name = font_name;
		this.font_size = font_size;
		this.styles.addAll(styles);
	}

	public String getText() {
		return text;
	}

	public Color getColor() {
		return color;
	}

	public String getFontName() {
		return font_name;
	}

	public Integer getFontSize() {
		return font_size;
	}

	public Set<String> getStyles() {
		Set<String> styles_name = new HashSet<String>();
		for (Style style : styles) {
			styles_name.add(style.name());
		}
		return styles_name;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		String text = null;
		Color color = null;
		String font_name = null;
		Integer font_size = null;
		Set<Style> styles = new HashSet<Style>();

		public Builder setText(String text) {
			this.text = text;
			return this;
		}

		public Builder setColor(Color color) {
			this.color = color;
			return this;
		}

		public Builder setFontName(String font_name) {
			this.font_name = font_name;
			return this;
		}

		public Builder setFontSize(Integer font_size) {
			this.font_size = font_size;
			return this;
		}

		public Builder setStyles(Style... styles) {
			this.styles.clear();
			for (Style style : styles) {
				this.styles.add(style);
			}
			return this;
		}

		public Builder addStyles(Style style) {
			this.styles.add(style);
			return this;
		}
		
		public HoverText build() {
			return new HoverText(text, color, font_name, font_size, styles);
		}

	}

}
