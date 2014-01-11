package com.kd.poc.mule;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

public class ReadInputMsgTransformer extends AbstractMessageTransformer {

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {

		Object body = message.getInboundProperty("Body");
		Object from = message.getInboundProperty("From");
		System.out.println("Body Message: " + body);
		System.out.println("From SMS: " + from);
		if (body != null && ((String) body).trim().length() != 0)
			message.setOutboundProperty("Body", body);
		else
			message.setOutboundProperty("Body", "Got empty SMS Body.");
		if (from != null)
			message.setOutboundProperty("From", from);
		return message;
	}

}
