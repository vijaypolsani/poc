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

/**
 * @version 
 */
public class AdmRouteTest extends CamelTestSupport {

	private static final Logger log = LoggerFactory
			.getLogger(AdmRouteTest.class);

	
	protected AbstractXmlApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("resources/spring/adm-camel-config.xml");
	}

	@BeforeClass
	public static void classSetUp() {
		//NOTE: Set this configuration to the location of the configuration files.
		//System.setProperty("jboss.server.config.dir","C:/opt/jboss-as-7.1.1.Final/standalone/configuration");
		System.setProperty("jboss.server.config.dir",
				"C:/_eclipsehome/adm_2012_01/src/build/build/classes/resources/properties");
		ConsoleAppender console = new ConsoleAppender(); //create appender
		//configure the appender
		String PATTERN = "%d [%p|%c|%C{1}] %m%n";
		console.setLayout(new PatternLayout(PATTERN));
		console.setThreshold(Level.INFO);
		console.activateOptions();
		//add appender to any Logger (here is root)
		org.apache.log4j.Logger.getRootLogger().addAppender(console);
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

}