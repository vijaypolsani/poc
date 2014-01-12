This project is a POC to integrate the common API's available on the Programmable Web.

Business Case: Property Listing & Neighborhood Demographics details over over SMS.

Inputs to SMS: 
	ZIPCODE or Physical Address.
Outputs to SMS from APP: 
	When sent ZipCode, sends back area Demographics (MMS via PDF attachment)
	When sent Address, sends back the property detials (MMS via PDF attachment)
Limitations:
	Since the Twilio Mule integration supports only SMS currently. we will send 160 chars of information only.
Integration: 
	Twilio SMS for Sending and Receiving SMS. Zillow API for Property Data. Mule for ESB orchestration. CloudHub for IPAAS.
	

Overview:

	User driving in a neighborhood gets interested one a property on sale or wish to know more details on a home. User then
sends an SMS to our APP called (Just In Time Home Listings - JitMls) containing the [Physical Address] of the home 
(HouseNumber<space>Street<space>Lane<space>City<space>State) and will get the MMS with the PDF will all the property details. 
	User decides to know Demographics details near the home listing. User then sends an SMS to our APP containing the [ZIPCODE] 
of the city and will get the MMS with the PDF will all the Demographics details of the particular city. 

Implementation: 
Developed using the Mule ESB Studio. Deployed into CloudHub. Integrated Twilio SMS API Mule AnyConnect Adaptor along with REST 
HTTP based Zillow API.
