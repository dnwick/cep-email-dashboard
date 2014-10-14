package org.wso2.carbon.gmail.mediator.util;

import org.apache.synapse.MessageContext;
import org.apache.synapse.mediators.AbstractMediator;

import org.wso2.carbon.core.util.CryptoException;
import org.wso2.carbon.core.util.CryptoUtil;

import javax.xml.namespace.QName;
import java.nio.charset.Charset;

public class PasswordDecryptor extends AbstractMediator {

    private CryptoUtil cryptoUtil;

    public boolean mediate(MessageContext messageContext) {

        String encryptedPassword= messageContext.getEnvelope().getBody().getFirstElement().
                getFirstChildWithName(new QName(MediatorConstants.TASK_MESSAGE_NAMESPACE, MediatorConstants.PASSWORD)).getText();
        cryptoUtil = CryptoUtil.getDefaultCryptoUtil();
        String decryptedPassword = base64DecodeAndDecrypt(encryptedPassword);

        messageContext.getEnvelope().getBody().getFirstElement().getFirstChildWithName
                (new QName(MediatorConstants.TASK_MESSAGE_NAMESPACE,MediatorConstants.PASSWORD)).setText(decryptedPassword);

        return true;
    }

    public String base64DecodeAndDecrypt(String cipherText) {
        try {
            return new String(cryptoUtil.base64DecodeAndDecrypt(cipherText), Charset.forName("UTF-8"));
        } catch (CryptoException e) {
            String errorMsg = "Base64 decoding and decryption error. " + e.getMessage();
            log.error(errorMsg);
        }
        return null;
    }

}
