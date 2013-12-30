package com.pixaura.network;

import android.util.Log;

import com.pixaura.R;
import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;
import com.squareup.okhttp.OkHttpClient;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class JamSnapService extends RetrofitGsonSpiceService {

    private final static String BASE_URL = "https://dev.jamsnap.com";

    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(JamSnap.class);
    }

    @Override
    protected RestAdapter.Builder createRestAdapterBuilder() {

        RestAdapter.Builder restAdapter = super.createRestAdapterBuilder();

        OkHttpClient client = new OkHttpClient();

        restAdapter
                .setClient(new OkClient(client))
                .build();

        return restAdapter;
    }

    @Override
    protected String getServerUrl() {
        return BASE_URL;
    }

    /**
     * Not used.
     * Was needed earlier to pass a custom clent configured with a trust store
     * to avoid retrofit.RetrofitError: javax.net.ssl.SSLHandshakeException
     * "Could not validate certificate signature."
     * https://github.com/square/retrofit/issues/265#issuecomment-20777566
     *
     * May not be needed
     */
    private void configureClient(OkHttpClient client) {
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream caInput = getResources().openRawResource(R.raw.jamsnap);
            Certificate ca = null;
            try {
                ca = cf.generateCertificate(caInput);
                System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            finally {
                caInput.close();
            }

            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, tmf.getTrustManagers(), null);

            client.setSslSocketFactory(context.getSocketFactory());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
