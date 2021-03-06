package com.obulex.jpos.service;

import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;
import org.jpos.util.Log;
import org.jpos.util.NameRegistrar;

public class Server implements ISORequestListener {

	@Override
    public boolean process(ISOSource source, ISOMsg message) {
	try {
	    ISOMsg isoMessageResponse = (ISOMsg) message.clone();
	    isoMessageResponse.setResponseMTI(); // default response

	    MessageProcessor serverprocessor = (MessageProcessor) NameRegistrar
		    .getIfExists("serverprocessor");
	    if (serverprocessor != null) {
	    	isoMessageResponse = serverprocessor.process(message);
	    }
	    else
	    {
	    	Log log = new Log();
	    	log.info("Server.process : serverprocessor is null");
	    }
	    source.send(isoMessageResponse);
	    return true;
	} catch (Exception e) {
	    return false;
	}
    }

}
