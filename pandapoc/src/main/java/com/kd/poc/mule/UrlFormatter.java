package com.kd.poc.mule;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

public class UrlFormatter extends AbstractMessageTransformer {

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		String body = message.getInboundProperty("Body");
		// It is expected as : 2114 Bigelow Ave Seattle WA
		//Convert it as: 2114+Bigelow+Ave&citystatezip=Seattle%2C+WA
		String[] data = body.split(" ");
		if (data.length != 0 && data.length > 1) {
			StringBuilder sbr = new StringBuilder();
			String citystatezip = "&citystatezip";
			citystatezip = citystatezip + data[(data.length - 2)] + data[(data.length - 1)];
			for (int i = 0; i < data.length - 2; i++) {
				sbr.append(i);
			}
			sbr.append(citystatezip);
			message.setOutboundProperty("Body", sbr.toString());
		}
		System.out.println("Body Message: " + message);
		return message;
	}

	public static boolean isNumeric(String str) {
		return str.matches("[+-]?\\d*(\\.\\d+)?");
	}
}
