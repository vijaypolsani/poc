App to provide OnDemand Property Listing & Neighborhood Demographics details over SMS.
The project is a POC to integrate the common API's available on the Programmable Web to create a mashup of different API's. The solution caters for OnDemand information access for home buyers.
User sends an SMS to our APP called (Just In Time Home Listings - JitMls) containing the [Physical Address] of the home (HouseNumber<space>Street<space>City<space>State) and will get the MMS with all the property details. User decides to know Demographics details near the home listing, and sends an SMS to our APP containing the [ZIPCODE] of the city.
Inputs: SMS the ZIPCODE or Physical Address. 
Outputs: SMS response from APP with Demographics (MMS via PDF attachment) for a ZIPCODE input. When input is an Address the app responds with home property details (MMS via PDF attachment) 
Limitations: Since the Twilio Mule integration supports only SMS currently. we will send 160 chars of information out of the complete content and MMS support is extended soon. 
Stack: Twilio SMS for Sending and Receiving SMS. Zillow API for Property Data queries. Mule for ESB orchestration. CloudHub for IPAAS runtime. Git for source control.
