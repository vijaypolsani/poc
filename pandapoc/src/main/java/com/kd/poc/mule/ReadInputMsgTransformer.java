package com.kd.poc.mule;

import java.util.HashMap;

import org.mule.api.MuleContext;
import org.mule.api.MuleMessage;
import org.mule.api.registry.RegistrationException;
import org.mule.api.transformer.TransformerException;
import org.mule.api.transport.PropertyScope;
import org.mule.transformer.AbstractMessageTransformer;

public class ReadInputMsgTransformer extends AbstractMessageTransformer {

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		MuleContext muleContext = message.getMuleContext();
		System.out.println("Data in Message " + message);
		Object body = message.getInboundProperty("Body");
		Object from = message.getInboundProperty("From");
		if (body != null)
			message.setOutboundProperty("Body", body);
		else {
			body = "N/A";
			message.setOutboundProperty("Body", body);
			@SuppressWarnings("rawtypes")
			HashMap map = (HashMap) message.getPayload();
			map.put("Body", body);
			message.setPayload(map);
		}

		if (from != null) {
			from.toString().replaceAll("\\s+", "");
			message.setOutboundProperty("From", from);
		} else {
			from = "16504096365";
			message.setOutboundProperty("From", from);
			message.setProperty("From", "16504096365", PropertyScope.INBOUND);
		}
		try {
			muleContext.getRegistry().registerObject("From", from);
		} catch (RegistrationException e) {
			e.printStackTrace();
		}
		System.out.println("Data in Message " + message);
		return message;
	}

}
