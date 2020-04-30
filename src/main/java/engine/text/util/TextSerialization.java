package engine.text.util;

import java.awt.Color;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import engine.text.TextAPI;
import engine.text.util.TextEnum.ClickEventType;

public class TextSerialization {

	public static String serialize(Texts texts) {
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("Texts");
		for (SubText sText : texts.getTexts()) {
			Element e = root.addElement("SubText");
			e.addAttribute("Text", sText.getText());
			if (sText.getColor() != null)
				e.addAttribute("Color", Integer.toHexString(sText.getColor().getRGB()).substring(2));
			if (sText.getFontName() != null)
				e.addAttribute("FontName", sText.getFontName());
			if (sText.getFontSize() != null)
				e.addAttribute("FontSize", sText.getFontSize().toString());
			if (!sText.getStyles().isEmpty()) {
				String str = sText.getStyles().toString();
				e.addAttribute("Styles", str.substring(1, str.length() - 1));
			}
			if (sText.onClick() != null) {
				TextClick tClick = sText.onClick();
				e.addAttribute("ClickType", tClick.getClickEventType().name());
				e.addAttribute("ClickDate", tClick.getString());
			}
			if (!sText.getHoverTexts().isEmpty()) {
				e.addAttribute("isHoverTexts", "true");
				for (HoverText hText : sText.getHoverTexts()) {
					Element eh = e.addElement("HoverTexts");
					eh.addAttribute("Text", hText.getText());
					if (hText.getColor() != null)
						eh.addAttribute("Color", "0x" + Integer.toHexString(hText.getColor().getRGB()).substring(2));
					if (hText.getFontName() != null)
						eh.addAttribute("FontName", hText.getFontName());
					if (hText.getFontSize() != null)
						eh.addAttribute("FontSize", hText.getFontSize().toString());
					if (!hText.getStyles().isEmpty()) {
						String str = hText.getStyles().toString();
						eh.addAttribute("FontSize", str.substring(1, str.length() - 1));
					}
				}
			}
		}
		return root.asXML();
	}

	public static Texts deserialize(String asXML) {
		try {
			Texts.Builder tBuilder = Texts.builder();
			Element root = DocumentHelper.parseText(asXML).getRootElement();
			for (Element e : root.elements()) {
				SubText.Builder sBuilder = SubText.builder();
				setAttribute(e, sBuilder);
				String cType = e.attributeValue("ClickType");
				String cDate = e.attributeValue("ClickDate");
				Boolean isHTexts = Boolean.getBoolean(e.attributeValue("isHoverTexts"));
				if (cType != null && cDate != null) {
					sBuilder.setClick(TextClick.get(ClickEventType.valueOf(cType), cDate));
				}
				if (isHTexts) {
					for (Element e1 : e.elements()) {
						HoverText.Builder hBuilder = HoverText.builder();
						setAttribute(e1, hBuilder);
						sBuilder.addHoverText(hBuilder.build());
					}
				}
				tBuilder.add(sBuilder.build());
			}
			return tBuilder.build();
		} catch (DocumentException e) {
			return TextAPI.ofTexts("");
		}
	}

	private static void setAttribute(Element e, HoverText.Builder sBuilder) {
		for (Attribute a : e.attributes()) {
			String name = a.getName();
			String date = a.getData().toString();
			switch (name) {
			case "Text":
				sBuilder.setText(date);
				continue;
			case "Color":
				Integer i = Integer.parseInt(date, 16);
				sBuilder.setColor(new Color(i));
				continue;
			case "FontName":
				sBuilder.setFontName(date);
				continue;
			case "FontSize":
				sBuilder.setFontSize(Integer.parseInt(date));
				continue;
			case "Styles":
				String[] sty = date.split(", ");
				for (String str : sty) {
					sBuilder.addStyles(TextEnum.Style.valueOf(str));
				}
				continue;
			}
		}
	}
}
