/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kd.service.provider.c3g.adm._2012._06;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.Processor;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;

import com.tsys.xmlmessaging.ad.IAPappRespInfoResponseDataType;
import com.tsys.xmlmessaging.ad.IAPappRespInfoResponseDataType.TS2ConsumerInfo;
import com.tsys.xmlmessaging.ad.InqAppInfoDocument;
import com.tsys.xmlmessaging.ad.InqAppInfoDocument.InqAppInfo;
import com.tsys.xmlmessaging.ad.InqAppInfoRequestType;
import com.tsys.xmlmessaging.ad.InqAppInfoResponseDocument;
import com.tsys.xmlmessaging.ad.InqAppInfoResponseDocument.InqAppInfoResponse;
import com.tsys.xmlmessaging.ad.InqAppInfoResponseType;
import com.tsys.xmlmessaging.ad.TSYSResponseMsgType;
import com.tsys.xmlmessaging.ad.TSYSXMLMessagingInquiryStub;
import com.tsys.xmlmessaging.ad.TSYSprofileInqDocument;
import com.wellsfargo.dto.adm._2012._06.InquireAppInfoDTO;
import com.wellsfargo.persistence.adm.NewAppDAO;
import com.wellsfargo.persistence.adm.NewAppService;
import com.wellsfargo.persistence.adm.impl.NewAppServiceImpl;
import com.wellsfargo.persistence.adm.vo.AdmTs2;
import com.wellsfargo.processor.ProfileProcessor;
import com.wellsfargo.service.provider.c3g.inquiry._2012._06.InquireProfileType;
import com.wellsfargo.service.provider.c3g.inquiry._2012._06.InquireRequestType;
import com.wellsfargo.tsysclient.adm._2012._06.InquireAppInfoClient;
import com.wellsfargo.utility.TsysWebServiceHelper;

/**
 * @version 
 */
public class AdmRouteWithMocksTest extends CamelTestSupport {

	static NewAppDAO newAppDAOMock;
	static NewAppService newAppService;
	static List<AdmTs2> decisionInfoList = new ArrayList<AdmTs2>();
	static AdmTs2 admTs2;
	static InqAppInfoDocument inqAppInfoDocument;
	static TSYSXMLMessagingInquiryStub adInquiryStubMock;
	static InquireAppInfoClient inquireAppInfoClient;
	static TSYSprofileInqDocument tsysprofileInqDocument;
	static InqAppInfoResponseDocument inqAppInfoResponseDocument;
	static InqAppInfoResponseType inqAppInfoResponseType;
	static DataFieldMaxValueIncrementer msgIncrementerMock;
	static ProfileProcessor profileProcessor;

	private static final Logger log = LoggerFactory
			.getLogger(AdmRouteWithMocksTest.class);

	
	protected AbstractXmlApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext(
				"resources/spring/InterceptSendToMockEndpointStrategyTest.xml");
	}

	@BeforeClass
	public static void classSetUp() {
		//NOTE: Set this configuration to the location of the configuration files.
		System.setProperty("jboss.server.config.dir",
				"C:/_eclipsehome/c3g/build/build/classes/resources/properties");
		ConsoleAppender console = new ConsoleAppender();
		//configure the appender
		String PATTERN = "%d [%p|%c|%C{1}] %m%n";
		console.setLayout(new PatternLayout(PATTERN));
		console.setThreshold(Level.INFO);
		console.activateOptions();
		org.apache.log4j.Logger.getRootLogger().addAppender(console);
	}

	@BeforeClass
	public static void testData() {
		//NewAppService
		admTs2 = new AdmTs2();
		admTs2.setCCDMS_ADM_REQ_SEQ_ID(1L);
		admTs2.setAPP_ID_INFO_APPID("000000187");
		admTs2.setCCDMS_REQUEST_STATUS_CD("WC");
		decisionInfoList.add(admTs2);

		//Request adInquiryStub
		inqAppInfoDocument = InqAppInfoDocument.Factory.newInstance();
		InqAppInfo inqAppInfo = InqAppInfoDocument.InqAppInfo.Factory
				.newInstance();
		InqAppInfoRequestType inqAppInfoRequestType = InqAppInfoRequestType.Factory
				.newInstance();
		inqAppInfoRequestType.setKey("000000187");
		inqAppInfoRequestType
				.setKeyType(com.tsys.xmlmessaging.ad.TSYSAcctInquiryRequestType.KeyType.Enum
						.forString("ADMAppNbr"));
		inqAppInfoRequestType.setVersion(InqAppInfoRequestType.Version.Enum
				.forString("2.1.0"));
		inqAppInfo.setInqAppInfoRequest(inqAppInfoRequestType);
		inqAppInfoDocument.setInqAppInfo(inqAppInfo);

		try {
			//Profile
			InquireRequestType inquireRequestType = new InquireRequestType();
			inquireRequestType.setMessageId("ADMINFO");
			InquireProfileType inquireProfileType = new InquireProfileType();
			inquireProfileType.setVendorId("00000000");
			inquireProfileType.setClientId("8485");
			inquireProfileType.setUserId("8485");
			inquireRequestType.setInquireProfile(inquireProfileType);
			tsysprofileInqDocument = TsysWebServiceHelper
					.getADTsysProfileInquireDocument(inquireRequestType);

			//Response adInquiryStub
			inqAppInfoResponseDocument = InqAppInfoResponseDocument.Factory
					.newInstance();
			InqAppInfoResponse inqAppInfoResponse = InqAppInfoResponse.Factory
					.newInstance();
			inqAppInfoResponseType = InqAppInfoResponseType.Factory
					.newInstance();

			IAPappRespInfoResponseDataType iAPappRespInfoResponseDataType = IAPappRespInfoResponseDataType.Factory
					.newInstance();
			TS2ConsumerInfo ts2ConsumerInfo = TS2ConsumerInfo.Factory
					.newInstance();
			ts2ConsumerInfo.setAcctNbr("5563520698707275");
			iAPappRespInfoResponseDataType.setTS2ConsumerInfo(ts2ConsumerInfo);
			inqAppInfoResponseType
					.setAppRespInfo(iAPappRespInfoResponseDataType);
			inqAppInfoResponseType.setStatus("000");
			inqAppInfoResponseType
					.setStatusMsg(TSYSResponseMsgType.StatusMsg.Enum
							.forString("passed"));
			inqAppInfoResponse.setInqAppInfoResult(inqAppInfoResponseType);
			inqAppInfoResponseDocument
					.setInqAppInfoResponse(inqAppInfoResponse);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("**Exception in BeforeClass: " + e.getMessage());
		}
	}

	@BeforeClass
	public static void init() throws RemoteException {
		newAppDAOMock = mock(NewAppDAO.class);
		newAppService = new NewAppServiceImpl(newAppDAOMock);
		when(newAppDAOMock.getNewApp("UP")).thenReturn(decisionInfoList);

		AdmTs2 admTs2 = new AdmTs2();
		admTs2.setCCDMS_ADM_REQ_SEQ_ID(1L);
		admTs2.setCCDMS_REQUEST_STATUS_CD("SC");
		admTs2.setCCDMS_CARD_NBR("5563520808933738");

		org.mockito.Mockito.doNothing().when(newAppDAOMock)
				.saveOrUpdateCardNbr(admTs2);

		adInquiryStubMock = mock(TSYSXMLMessagingInquiryStub.class);
		inquireAppInfoClient = new InquireAppInfoClient();
		inquireAppInfoClient.setADInquiryStub(adInquiryStubMock);
		when(
				adInquiryStubMock.inqAppInfo(inqAppInfoDocument,
						tsysprofileInqDocument)).thenReturn(
				inqAppInfoResponseDocument);

		msgIncrementerMock = mock(DataFieldMaxValueIncrementer.class);
		profileProcessor = new ProfileProcessor();
		profileProcessor.setMsgIncrementer(msgIncrementerMock);
		when(msgIncrementerMock.nextLongValue()).thenReturn(1L);
	}

	/*	@Test
		public void testAdvisedMockEndpoints() throws Exception {
			RouteDefinition route = context
					.getRouteDefinition("StartInquireAppInfo");
			//context.setTracing(true);
			context.setLazyLoadTypeConverters(false);
			route.adviceWith(context, new RouteBuilder() {
				Predicate isNewAppServiceUP = header("ROUTE_ID").isEqualTo(
						"StartInquireAppInfo");
				Predicate inquireAppInfoClient = header("TSYS").isEqualTo(
						"inquireAppInfoClient");
				Predicate newAppService = header("END").isEqualTo("NewAppService");

				public void configure() throws Exception {
					// intercept sending to http and detour to our processor instead

					intercept().when(isNewAppServiceUP).process(
							new NewAppServiceMock());
					intercept().when(inquireAppInfoClient).process(
							new InquireAppInfoClientMock());
					intercept().when(newAppService)
							.process(new NewAppServiceMock());
				}
			});
			Thread.sleep(600000L);
		}*/

	@Test
	public void testMockEndpoints() throws Exception {

		//context.setTracing(true);
		context.setLazyLoadTypeConverters(false);

		MockEndpoint mockNewAppServicePS = getMockEndpoint("mock:newAppServicePS");
		mockNewAppServicePS.whenAnyExchangeReceived(new NewAppServiceMock());

		MockEndpoint mockProfile = getMockEndpoint("mock:profileProcessor");
		mockProfile.whenAnyExchangeReceived(new ProfileProcessorMock());

		MockEndpoint mockInquireAppInfoClient = getMockEndpoint("mock:inquireAppInfoClient");
		mockInquireAppInfoClient
				.whenAnyExchangeReceived(new InquireAppInfoClientMock());

		MockEndpoint mockNewAppServiceApp = getMockEndpoint("mock:newAppServiceApp");
		mockNewAppServiceApp
				.whenAnyExchangeReceived(new NewAppServiceAppMock());

		Thread.sleep(600000L);
	}

	@Test
	public void testOriginalRouteEndpoints() throws Exception {
		RouteDefinition route = context
				.getRouteDefinition("StartInquireAppInfo");
		//context.setTracing(true);
		context.setLazyLoadTypeConverters(false);
		log.info("Route: " + route.toString());
		Thread.sleep(600000L);
	}

	private class NewAppServiceMock implements Processor {
		@Override
		public String toString() {
			return "*** NewAppServiceMock Created." + "]";
		}

		public void process(Exchange exchange) throws Exception {
			log.info("Headers:" + exchange.getIn().getHeaders());
			log.info("Body:" + exchange.getIn().getBody());
			exchange.getOut().setHeaders(exchange.getIn().getHeaders());
			exchange.getIn().setBody(newAppService.getNewAppNewStatus().get(0));
			exchange.getOut()
					.setBody(newAppService.getNewAppNewStatus().get(0));
			log.info("***Inside the NewAppServiceMock Mock Process: "
					+ exchange);

		}
	}

	private class NewAppServiceAppMock implements Processor {
		@Override
		public String toString() {
			return "*** NewAppServiceAppMock Created." + "]";
		}

		public void process(Exchange exchange) throws Exception {
			log.info("Headers:" + exchange.getIn().getHeaders());
			log.info("Body:" + exchange.getIn().getBody());
			exchange.getOut().setHeaders(exchange.getIn().getHeaders());
			AdmTs2 admTs2 = (AdmTs2) exchange.getIn().getBody();
			newAppService.saveOrUpdateCardNbr(admTs2);
			log.info("***Completed the NewAppServiceAppMock Mock Process: "
					+ exchange);

		}
	}

	private class ProfileProcessorMock implements Processor {
		@Override
		public String toString() {
			return "*** ProfileProcessorMock Created." + "]";
		}

		public void process(Exchange exchange) throws Exception {
			log.info("Headers:" + exchange.getIn().getHeaders());
			log.info("Body:" + exchange.getIn().getBody());
			exchange.getOut().setHeaders(exchange.getIn().getHeaders());
			profileProcessor.process(exchange);
			log.info("***Completed the ProfileProcessorMock Mock Process: "
					+ exchange);

		}
	}

	private class InquireAppInfoClientMock implements Processor {
		@Override
		public String toString() {
			return "*** InquireAppInfoClientMock Created." + "]";
		}

		public void process(Exchange exchange) throws Exception {
			log.info("Headers:" + exchange.getIn().getHeaders());
			log.info("Body:" + exchange.getIn().getBody());
			InquireAppInfoDTO inquireAppInfoDTO = (InquireAppInfoDTO) exchange
					.getIn().getBody();
			exchange.getOut().setHeaders(exchange.getIn().getHeaders());
			//Send the stubbed In request.
			InquireAppInfoDTO inquireAppInfoDTOResponse = inquireAppInfoClient
					.callInquireAppInfoAD(inquireAppInfoDTO, exchange);
			//Now return the Stubbed Out Response.
			// inqAppInfoResponseType will always be NULL as we are MOCKING.
			exchange.getOut().setBody(inqAppInfoResponseType);
			log.info("***Completed the InquireAppInfoClientMock Mock Process: "
					+ exchange);

		}
	}

}