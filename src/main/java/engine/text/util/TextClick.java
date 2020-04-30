package engine.text.util;

import engine.text.util.TextEnum.ClickEventType;

public class TextClick {
	final TextEnum.ClickEventType onClickEventType;
	final String str;

	private TextClick(TextEnum.ClickEventType onClickEventType, String str) {
		this.onClickEventType = onClickEventType;
		this.str = str;
	}

	public static TextClick get(TextEnum.ClickEventType onClickEventType, String str) {
		return new TextClick(onClickEventType, str);
	}
	
	public static TextClick onRunCommand(String str) {
		return new TextClick(ClickEventType.RUN_COMMAND, str);
	}

	public static TextClick onRunJavaScript(String str) {
		return new TextClick(ClickEventType.RUN_JS, str);
	}

	public static TextClick onCopyToClipboard(String str) {
		return new TextClick(ClickEventType.COPY_TO_CLIPBOARD, str);
	}

	public static TextClick onOpenUrl(String str) {
		return new TextClick(ClickEventType.OPEN_URL, str);
	}

	public static TextClick onSuggestText(String str) {
		return new TextClick(ClickEventType.SUGGEST_TEXT, str);
	}

	public TextEnum.ClickEventType getClickEventType() {
		return onClickEventType;
	}

	public String getString() {
		return str;
	}
}
