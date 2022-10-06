package com.joelforjava.carburant.util

import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate

class ServerUtils {

    static boolean isOnline(String serverUrl) {
        def result = false
        try {
            SSLContext sc = SSLContext.getInstance('SSL')
            sc.init null, createTrustManager(), new SecureRandom()
            HttpsURLConnection.defaultSSLSocketFactory = sc.getSocketFactory()
            HttpsURLConnection.defaultHostnameVerifier = createHostnameVerifier()

            def siteUrl = new URL(serverUrl)
            def connection = siteUrl.openConnection()
            connection.requestMethod = 'GET'
            connection.connect()
            def responseCode = connection.responseCode
            if (responseCode == 200) {
                result = true
            }
        } catch (e) {
            result = false
        }
        result
    }

    private static TrustManager[] createTrustManager() {
        def trustAllCerts = [
                new X509TrustManager() {
                    X509Certificate[] getAcceptedIssuers() {
                        null
                    }
                    void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {}
                    void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {}
                }
        ] as TrustManager[]

        trustAllCerts
    }

    private static HostnameVerifier createHostnameVerifier() {
        def allHostsValid = new HostnameVerifier() {
            boolean verify(String hostname, SSLSession session) {
                true
            }
        }
        allHostsValid
    }
}
