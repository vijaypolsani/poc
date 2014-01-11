This project is a POC to integrate the common API's available on the Programmable Web.

Business Case: 

User driving in a neighborhood is interested in the property details of the home on sale or listed then send an SMS containing the
physical ADDRESS of the home (HouseNumber<space>address<space>zipcode) and would like to know the details of home. 
	The user then sends an SMS with (HouseNumber<space>address<space>zipcode)(ex: 825 Maria Ln 98453)to the current developed 
APP(JITHomeSaleListings) and get's the list of the available properties on sale. The user gets the functionality of the listed 
sale properties on the go.
	The user if sends an SMS with only (zipcode)(ex: 98453)then the APP(JITHomeSaleListings) sends the areas DEMOGRAPHICS back. 

Implementation: 
Create an interface for returning the listed home properties for sale currently available for a given Zip-code sent via SMS.

Integrate the Twilio SMS api for fetching the COMPLETE ADDRESS (or) ZIPCode via a users phone. Call Zillow API for the property details 
and then convert them into PDF and send back the results as SMS to the caller. Also upload the same results into the company DropBox 
account for future Analytics.

