package com.kd.poc.mule;

import org.mule.api.MuleContext;
import org.mule.api.MuleMessage;
import org.mule.api.annotations.expressions.Lookup;
import org.mule.api.context.MuleContextAware;
import org.mule.api.registry.RegistrationException;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

public class ReadInputMsgTransformer extends AbstractMessageTransformer implements MuleContextAware {

	@Lookup
	private MuleContext muleContext;

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
		if (from != null && ((String) from).trim().length() != 0) {
			message.setOutboundProperty("From", from);
		} else
			message.setOutboundProperty("From", "16504096365");
		try {
			muleContext.getRegistry().registerObject("From", from);
			System.out.println("Data in MuleContext " + muleContext.toString());
		} catch (RegistrationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return message;
	}

	public void setMuleContext(MuleContext muleContext) {
		this.muleContext = muleContext;
	}
}
