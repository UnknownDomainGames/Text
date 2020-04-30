package engine.text.util;

public interface TextEnum {

	public enum Style {
		BOLD, ITALIC, STRIKETHROUGH, UNDERLINE
	}

	public enum ClickEventType {
		RUN_COMMAND, RUN_JS, OPEN_URL, SUGGEST_TEXT, COPY_TO_CLIPBOARD
	}

}
