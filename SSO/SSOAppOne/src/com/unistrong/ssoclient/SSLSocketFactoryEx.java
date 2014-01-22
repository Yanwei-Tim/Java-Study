
package com.unistrong.ssoclient;

import org.apache.http.conn.ssl.SSLSocketFactory;

import android.util.Log;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

class SSLSocketFactoryEx extends SSLSocketFactory {

    SSLContext sslContext = SSLContext.getInstance("TLS");

    public SSLSocketFactoryEx(KeyStore truststore)
            throws NoSuchAlgorithmException, KeyManagementException,
            KeyStoreException, UnrecoverableKeyException {
        super(truststore);

        TrustManager tm = new X509TrustManager() {

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] chain, String authType)
                    throws java.security.cert.CertificateException {
            	Log.v("checkClientTrusted", "checkClientTrusted---------------------------");
            	Log.v("Client cer-authType", authType);
            	if (chain != null){
            		for ( java.security.cert.X509Certificate cer : chain) {
            			Log.v("Client cer-cer", cer.toString());
					}
            	}
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] chain, String authType)
                    throws java.security.cert.CertificateException {
            	Log.v("checkServerTrusted", "checkServerTrusted---------------------------");
            	Log.v("Server cer-authType", authType);
            	if (chain != null){
            		for ( java.security.cert.X509Certificate cer : chain) {
            			Log.v("Server cer-cer", cer.toString());
					}
            	}
            }
        };

        sslContext.init(null, new TrustManager[] {
            tm
        }, null);
    }

    @Override
    public Socket createSocket(Socket socket, String host, int port,
            boolean autoClose) throws IOException, UnknownHostException {
        return sslContext.getSocketFactory().createSocket(socket, host, port,
                autoClose);
    }

    @Override
    public Socket createSocket() throws IOException {
        return sslContext.getSocketFactory().createSocket();
    }
}
