package ru.tony.sample.routing.repository;


import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.tony.sample.routing.entiry.Country;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Current implementation of country repository
 */
@Service
public class ExternalCountryRepository implements CountryRepository {

    private static final String URL = "https://restcountries.eu/rest/v2/all";
    private static final String KEYPASS = "changeit";

    private List<Country> countryList;

    /**
     * load data from remote service
     */
    @PostConstruct
    public void init() {
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(getHttpClient()));
        ResponseEntity<Country[]> rateResponse =
                restTemplate.getForEntity(URL, Country[].class);
        countryList = Arrays.asList(rateResponse.getBody());
    }

    @Override
    public List<Country> getAll() {
        return countryList;
    }

    @Override
    public int getCount() {
        return countryList.size();
    }

    @Override
    public List<Country> getPage(int page, int pageSize) {
        int start = (page - 1) * pageSize;
        int end = start + pageSize;
        if (page <= 0 || start > getCount()) {
            return new ArrayList<>();
        }
        if (end > getCount()) {
            end = getCount();
        }
        return new ArrayList<>(countryList.subList(start , end ));
    }

    /**
     * Client for work with certificates
     *
     * @return http client
     */
    private CloseableHttpClient getHttpClient() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            try (InputStream instream = this.getClass().getClassLoader().getResourceAsStream("truststore.jks")) {
                trustStore.load(instream, KEYPASS.toCharArray());
            }

            // Trust own CA and all self-signed certs
            SSLContext sslcontext = org.apache.http.conn.ssl.SSLContexts.custom()
                    .loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
                    .build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[]{"TLSv1"},
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

            return HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();
        } catch (IOException | CertificateException | NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            throw new RuntimeException("Something went wrong with creating connection", e);
        }
    }


}
