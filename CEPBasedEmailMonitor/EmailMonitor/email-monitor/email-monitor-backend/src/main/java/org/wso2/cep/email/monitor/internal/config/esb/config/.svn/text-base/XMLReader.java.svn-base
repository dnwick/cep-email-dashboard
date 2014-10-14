package org.wso2.cep.email.monitor.internal.config.esb.config;

import org.apache.log4j.Logger;
import org.wso2.cep.email.monitor.exception.EmailMonitorServiceException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Read content from xml files
 */
public class XMLReader {

    private static final Logger logger = Logger.getLogger(XMLReader.class);


    public String readXML(String path) throws EmailMonitorServiceException {
        InputStream is;
        BufferedReader br = null;
        String line;
        String content = "";
        is = ProxyDeployer.class.getResourceAsStream(path);
        try {
            InputStreamReader inputStreamReader = null;
            try {
                inputStreamReader = new InputStreamReader(is);
                br = new BufferedReader(inputStreamReader);

                    while (null != (line = br.readLine())) {
                        content = content.concat(line);
                    }
            } finally {
                inputStreamReader.close();
                br.close();
            }
        } catch (IOException e){
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException("Error when reading form xml file", e);
        }

        return content;
    }
}
