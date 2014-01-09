package com.cgi.bpm.esb.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WfCamelRoutes extends RouteBuilder {
	private Logger log = LoggerFactory.getLogger("WfCamelRoutes.class");

	@Override
	public void configure() throws Exception {
		//1. Import Manifest Process - JMS Queue based start
		from("activemq:queue:startwf").to("activiti:Sample_Library_QC?copyVariablesToProperties=true").routeId("Start Sample JMS Route");
		//1. Import Manifest Process - Activiti based start
		from("activiti:Sample_Library_QC:camel_jms_file").routeId("Start Sample Route")
				.pollEnrich("ftp://user@lnx-poc.completegenomics.com/inbox?password=password&binary=true&download=true&passiveMode=true").process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						log.info("We just downloaded: " + exchange.getIn().getHeader("CamelFileName"));
						log.info("Body: " + exchange.getIn().getBody().toString());
						exchange.getOut().setBody(exchange.getIn().getBody());
						exchange.getOut().setHeaders(exchange.getIn().getHeaders());
					}
				})
		//		.routeId("Sample FTP Route").log(org.apache.camel.LoggingLevel.INFO, "Received Info/Message to Start Complete Genomics WF!").to("file://c:/temp/inbox");
		.routeId("Sample FTP Route").log(org.apache.camel.LoggingLevel.INFO, "Received Info/Message to Start Complete Genomics WF!").to("file:///tmp/inbox");

		//2. PGM Process - Send to Analysis
		from("activiti:pgm_sequencing_analysis:pgm_send_to_analysis?copyVariablesToProperties=true").routeId("PGM Route")
				.log(org.apache.camel.LoggingLevel.INFO, "From PGM, Send Message to perform ACTG Analysis.").to("activemq:queue:analysis");

		//2. Proton Process - Send to Analysis
		from("activiti:proton_sequencing_analysis:proton_send_to_analysis?copyVariablesToProperties=true").routeId("Proton Route")
				.log(org.apache.camel.LoggingLevel.INFO, "From Proton, Send Message to perform ACTG Analysis.").to("activemq:queue:analysis");

		//3. Assembly & Analysis
		from("activemq:queue:analysis").routeId("Analysis Route").log(org.apache.camel.LoggingLevel.INFO, "Received Message to perform ACTG Analysis")
				.to("activiti:analysis_process?copyVariablesToProperties=true").to("activiti:analysis_process:wait_analysis?copyVariablesToProperties=true");
	}
}