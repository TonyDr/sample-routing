package ru.tony.sample.routing.rest;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@Component
public class SSLRestTemplate {

    private static final String KEYPASS = "changeit";

    private RestTemplate template;

    public SSLRestTemplate() {
        template = new RestTemplate(new HttpComponentsClientHttpRequestFactory(getHttpClient()));
    }

    public RestTemplate getTemplate() {
        return template;
    }

    /**
     * Http client for work with certificates
     *
     * @return http client
     */
    private CloseableHttpClient getHttpClient() {
        try {
            return HttpClients.custom()
                    .setSSLSocketFactory(
                            new SSLConnectionSocketFactory(createSslContext(loadLocalKeyStore())))
                    .build();
        } catch (IOException | CertificateException | NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            throw new RuntimeException("Something went wrong with creating connection", e);
        }
    }

    private KeyStore loadLocalKeyStore() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        try (InputStream instream = this.getClass().getClassLoader().getResourceAsStream("truststore.jks")) {
            trustStore.load(instream, KEYPASS.toCharArray());
        }
        return trustStore;
    }

    private SSLContext createSslContext(KeyStore trustStore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException {
        // Trust own CA and all self-signed certs
        return SSLContexts.custom()
                .loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
                .build();
    }

}

