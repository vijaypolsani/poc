/**
 * This file was automatically generated by the Mule Development Kit
 */
package org.mule.module.googlemaps;

import java.io.IOException;

import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Connect;
import org.mule.api.annotations.ValidateConnection;
import org.mule.api.annotations.ConnectionIdentifier;
import org.mule.api.annotations.Disconnect;
import org.mule.api.annotations.param.ConnectionKey;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;
import org.mule.api.annotations.rest.RestCall;
import org.mule.api.annotations.rest.RestQueryParam;
import org.mule.api.annotations.rest.RestUriParam;
import org.mule.api.annotations.rest.RestExceptionOn;
import org.mule.api.ConnectionException;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Processor;

/**
 * Cloud Connector
 *
 * @author MuleSoft, Inc.
 */
@Connector(name="googlemaps", schemaVersion="1.0-SNAPSHOT", friendlyName="googlemaps", minMuleVersion="3.4")
public abstract class GoogleMapsConnector
{
    /**
     * Configurable
     */
    @Configurable
    @Optional
    @Default("KM")
    private String myProperty;

    public String getMyProperty() {
		return myProperty;
	}

	/**
     * Set property
     *
     * @param myProperty My property
     */
    public void setMyProperty(String myProperty)
    {
        this.myProperty = myProperty ;
    }

    /**
     * Connect
     *
     * @param username A username
     * @param password A password
     * @throws ConnectionException
     */
    @Connect
    public void connect(@ConnectionKey String username, String password)
        throws ConnectionException {
        /*
         * CODE FOR ESTABLISHING A CONNECTION GOES IN HERE
         */
    }

    /**
     * Disconnect
     */
    @Disconnect
    public void disconnect() {
        /*
         * CODE FOR CLOSING A CONNECTION GOES IN HERE
         */
    }

    /**
     * Are we connected
     */
    @ValidateConnection
    public boolean isConnected() {
        return true;
    }

    /**
     * Are we connected
     */
    @ConnectionIdentifier
    public String connectionId() {
        return "001";
    }

    /**
     * Custom processor
     *
     * {@sample.xml ../../../doc/GoogleMaps-connector.xml.sample googlemaps:get-distance}
     *
     * @param origins Content to be processed
     * @param destinations Content to be processed
     * @param sensor A false sensor says whether we are using GMAPS sensors
     * @return Some string
     * @throws java.io.IOException throws the exception
     */
    @Processor
    @RestCall(uri= ("http://maps.googleapis.com/maps/api/distancematrix/json"),
    		method =org.mule.api.annotations.rest.HttpMethod.GET,
    		contentType ="application/json",
    		exceptions={@RestExceptionOn(expression="#[message.inboundProperties['http.status'] != 200]")})
    public abstract String getDistance(
    		@RestQueryParam ("origins") String origins, 
    		@RestQueryParam("destinations") String destinations,
    		@RestQueryParam("sensor") String sensor
    		)throws IOException;
}
