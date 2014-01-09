package com.kd.processor.adm;

import java.util.Date;

import javax.xml.ws.Holder;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.cxf.message.MessageContentsList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NewAppRequestProcessor implements Processor {

	private static final Logger log = LoggerFactory
			.getLogger(NewAppRequestProcessor.class);

	public void process(Exchange exchange) throws Exception {
		MessageContentsList messageContentsList = null;
		Holder<WFContextType> wfContextType = null;
		AdmTs2 admTs2 = null;
		CardType cardType = null;
		Date currentDate = null;
		try {
			log.debug("ADM Request HEADERS in NewAppRequestProcessor: Exchange IN Headers List: "
					+ exchange.getIn().getHeaders());
			log.debug("ADM Request BODY in NewAppRequestProcessor: Exchange IN Body List: "
					+ exchange.getIn().getBody());
			messageContentsList = new MessageContentsList();
			wfContextType = new Holder<WFContextType>();
			cardType = new CardType();
			currentDate = new Date(System.currentTimeMillis());
			String routeId = (String) exchange.getIn().getHeader("ROUTE_ID");
			admTs2 = (AdmTs2) exchange.getIn().getBody();
			wfContextType.value = new WFContextType();
			wfContextType.value
					.setApplicationId("C3P_APP_ID");
			wfContextType.value.setCreationTimestamp(currentDate);
			wfContextType.value.setMessageId(Long.toString(admTs2
					.getCCDMS_ADM_REQ_SEQ_ID()));
			cardType.setCardNumber("DUMMY_CARD");

			if (routeId
					.equalsIgnoreCase("MAINT_ADM_ROUTE")) {
				MaintProfileType maintProfileType = new MaintProfileType();
				maintProfileType
						.setClientId("TS2_CLIENT_ID");
				maintProfileType
						.setUserId("TS2_CLIENT_ID");
				maintProfileType.setVendorId("VENDOR_ID");

				UpdateNewAppRequest updateNewAppRequest = new UpdateNewAppRequest();
				updateNewAppRequest.setAdmTs2(admTs2);
				//Populate Base Profile
				updateNewAppRequest.setTsysType("TS2");
				updateNewAppRequest.setMessageId(Long.toString(admTs2
						.getCCDMS_ADM_REQ_SEQ_ID()));
				updateNewAppRequest.setCard(cardType);
				updateNewAppRequest.setMaintProfile(maintProfileType);
				messageContentsList.add(updateNewAppRequest);
				messageContentsList.add(wfContextType);
			} else if (routeId
					.equalsIgnoreCase("INQUIRY_ADM_ROUTE")) {
				InquireProfileType inqProfileType = new InquireProfileType();
				inqProfileType
						.setClientId("TS2_CLIENT_ID");
				inqProfileType.setUserId("TS2_CLIENT_ID);
				inqProfileType.setVendorId("VENDOR_ID);

				InquireAppInfoRequest inquireAppInfoRequest = new InquireAppInfoRequest();
				inquireAppInfoRequest.setAdmTs2(admTs2);
				//Populate Base Profile
				inquireAppInfoRequest.setTsysType("TS2");
				inquireAppInfoRequest.setMessageId(Long.toString(admTs2
						.getCCDMS_ADM_REQ_SEQ_ID()));
				inquireAppInfoRequest.setCard(cardType);
				inquireAppInfoRequest.setInquireProfile(inqProfileType);
				messageContentsList.add(inquireAppInfoRequest);
				messageContentsList.add(wfContextType);
			} else if (routeId
					.equalsIgnoreCase("ADDAUTHOPTSAMAINT_ADM_ROUTE)) {

				log.info("Start Of ADDAUTHOPTSAMAINT_ADM_ROUTE.");
				MaintProfileType maintProfileType = new MaintProfileType();
				maintProfileType
						.setClientId("TS2_CLIENT_ID);

				maintProfileType.setVendorId("VENDOR_ID);

				AddAuthOptsRequest addAuthOptsRequest = new AddAuthOptsRequest();
				addAuthOptsRequest.setAdmTs2(admTs2);
				// Populate Base Profile
				addAuthOptsRequest.setTsysType("TS2");
				addAuthOptsRequest.setMessageId(Long.toString(admTs2
						.getCCDMS_ADM_REQ_SEQ_ID()));

				//addAuthOptsRequest.setMessageId("ADMMANT");
				addAuthOptsRequest.setCard(cardType);
				addAuthOptsRequest.setMaintProfile(maintProfileType);
				messageContentsList.add(addAuthOptsRequest);
				messageContentsList.add(wfContextType);
				log.info("End Of ADDAUTHOPTSAMAINT_ADM_ROUTE.");
			} else {
				log.info("Incoming message not a defined Camel Route. Please wait until ADM record is inserted into DB.");
			}
			log.debug("In the NewAppRequestProcessor. Sequence Number:"
					+ admTs2.getCCDMS_ADM_REQ_SEQ_ID());
			exchange.getOut().setHeader("SEQUENCE_NUMBER,
					admTs2.getCCDMS_ADM_REQ_SEQ_ID());
			exchange.getOut().setHeaders(exchange.getIn().getHeaders());
			exchange.getOut().setBody(messageContentsList);
			log.debug("Completed NewAppRequestProcessor: Exchange OUT Headers List: "
					+ exchange.getOut().getHeaders());
			log.debug("Completed NewAppRequestProcessor: Exchange OUT Body List: "
					+ exchange.getOut().getBody());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in NewAppRequestProcessor: " + e.getMessage());
			throw new AdmProcessingException("", "", e);
		}
	}
}
