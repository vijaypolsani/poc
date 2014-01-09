This is a POC project that is implemented with the below technology.
The sample POC is used to automate the Genome Sequencing work flow. The business implementation requirements is to create
new workflows based on the customer sample profile. Once the worlfow is created the PM can track of the progress and update
customers on the delivery date and provide further feedback on the current status of the flow.

There are multiple integrations that can be achived based on the use cases. This POC when deployed asks for the user login
default: admin/admin and other users are configured in the activiti BPM database backend. Once the user logins in the user
can verify all the data sent by the customer via FTP into out DMZ FTP server (DropBox future integration). There is a
constant scheduled poller configured in Camel to lookout for the files in Excel format and process data according to the
business requirements.
	Once the data is received, then the activiti bpm engine is responsible to push the next task in the model. The business
components are built using Spring Core beans and the backedn integration is run using an ORM/JDBCTemplate/Spring Data.

The current deployment should work on Tomcat. Integration of the asyn communication is done via ActiveMQ.
 
1) Activiti as the WF engine
2) Camel as the ESB
3) Spring Beans for the business logic. Spring Templates for JDBC/JMS for connectivity.
4) Postgres as the backend database. 
5) Running on Tomcat & Planning for JBoss
6) ActiveMQ as the messaging Backbone.
7) Vaadin GUI for the forms implementation. Planning NodeJS front end