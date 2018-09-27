package org.jboss.resteasy.client.jaxrs.engines;

import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SNIHostName;
import javax.net.ssl.SNIServerName;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.jboss.resteasy.client.jaxrs.ClientHttpEngine;
import org.jboss.resteasy.client.jaxrs.ClientHttpEngineBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

public class ClientHttpEngineBuilder43 implements ClientHttpEngineBuilder {

   private ResteasyClientBuilder that;

   @Override
   public ClientHttpEngineBuilder resteasyClientBuilder(ResteasyClientBuilder resteasyClientBuilder)
   {
      that = resteasyClientBuilder;
      return this;
   }

   @Override
   public ClientHttpEngine build()
   {
      HostnameVerifier verifier = null;
      if (that.getHostnameVerifier() != null) {
         verifier = new VerifierWrapper(that.getHostnameVerifier());
      }
      else
      {
         switch (that.getHostnameVerification())
         {
            case ANY:
               verifier = new NoopHostnameVerifier();
               break;
            case WILDCARD:
               verifier = new DefaultHostnameVerifier();
               break;
            case STRICT:
               verifier = new DefaultHostnameVerifier();
               break;
         }
      }
      try
      {
         SSLConnectionSocketFactory sslsf = null;
         SSLContext theContext = that.getSSLContext();
         if (that.isTrustManagerDisabled())
         {
            theContext = SSLContext.getInstance("SSL");
            theContext.init(null, new TrustManager[]{new PassthroughTrustManager()},
               new SecureRandom());
            verifier = new NoopHostnameVerifier();
            sslsf = new SSLConnectionSocketFactory(theContext, verifier);
         }
         else if (theContext != null)
         {
            sslsf = new SSLConnectionSocketFactory(theContext, verifier) {
               @Override
               protected void prepareSocket(SSLSocket socket) throws IOException
               {
                  if(!that.getSniHostNames().isEmpty()) {
                     List<SNIServerName> sniNames = new ArrayList<>(that.getSniHostNames().size());
                     for(String sniHostName : that.getSniHostNames()) {
                        sniNames.add(new SNIHostName(sniHostName));
                     }

                     SSLParameters sslParameters = socket.getSSLParameters();
                     sslParameters.setServerNames(sniNames);
                     socket.setSSLParameters(sslParameters);
                  }
               }
            };
         }
         else if (that.getKeyStore() != null || that.getTrustStore() != null)
         {
            SSLContext ctx = SSLContexts.custom()
               .useProtocol(SSLConnectionSocketFactory.TLS)
               .setSecureRandom(null)
               .loadKeyMaterial(that.getKeyStore(),
                        that.getKeyStorePassword() != null ? that.getKeyStorePassword().toCharArray() : null)
               .loadTrustMaterial(that.getTrustStore(), TrustSelfSignedStrategy.INSTANCE)
               .build();
            sslsf = new SSLConnectionSocketFactory(ctx, verifier) {
               @Override
               protected void prepareSocket(SSLSocket socket) throws IOException
               {
                  List<String> sniHostNames = that.getSniHostNames();
                  if(!sniHostNames.isEmpty()) {
                     List<SNIServerName> sniNames = new ArrayList<>(sniHostNames.size());
                     for (String sniHostName : sniHostNames) {
                        sniNames.add(new SNIHostName(sniHostName));
                     }

                     SSLParameters sslParameters = socket.getSSLParameters();
                     sslParameters.setServerNames(sniNames);
                     socket.setSSLParameters(sslParameters);
                  }
               }
            };
         }
         else
         {
            final SSLContext tlsContext = SSLContext.getInstance(SSLConnectionSocketFactory.TLS);
            tlsContext.init(null, null, null);
            sslsf = new SSLConnectionSocketFactory(tlsContext, verifier);
         }

         final Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
            .register("http", PlainConnectionSocketFactory.getSocketFactory())
            .register("https", sslsf)
            .build();

         HttpClientConnectionManager cm = null;
         if (that.getConnectionPoolSize() > 0)
         {
            PoolingHttpClientConnectionManager tcm = new PoolingHttpClientConnectionManager(
               registry, null, null ,null, that.getConnectionTTL(TimeUnit.MILLISECONDS), TimeUnit.MILLISECONDS);
            tcm.setMaxTotal(that.getConnectionPoolSize());
            if (that.getMaxPooledPerRoute() == 0) {
               that.maxPooledPerRoute(that.getConnectionPoolSize());
            }
            tcm.setDefaultMaxPerRoute(that.getMaxPooledPerRoute());
            cm = tcm;

         }
         else
         {
            cm = new BasicHttpClientConnectionManager(registry);
         }

         RequestConfig.Builder rcBuilder = RequestConfig.custom();
         if (that.getReadTimeout(TimeUnit.MILLISECONDS) > -1)
         {
            rcBuilder.setSocketTimeout((int) that.getReadTimeout(TimeUnit.MILLISECONDS));
         }
         if (that.getConnectionTimeout(TimeUnit.MILLISECONDS) > -1)
         {
            rcBuilder.setConnectTimeout((int)that.getConnectionTimeout(TimeUnit.MILLISECONDS));
         }
         if (that.getConnectionCheckoutTimeout(TimeUnit.MILLISECONDS) > -1)
         {
            rcBuilder.setConnectionRequestTimeout((int)that.getConnectionCheckoutTimeout(TimeUnit.MILLISECONDS));
         }

         return createEngine(cm, rcBuilder, getDefaultProxy(that), that.getResponseBufferSize(), verifier, theContext);
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);
      }
   }
   
   private static HttpHost getDefaultProxy(ResteasyClientBuilder that) {
      String hostName = that.getDefaultProxyHostname();
      return hostName != null ? new HttpHost(hostName, that.getDefaultProxyPort(), that.getDefaultProxyScheme()) : null;
   }

   protected ClientHttpEngine createEngine(final HttpClientConnectionManager cm, final RequestConfig.Builder rcBuilder,
         final HttpHost defaultProxy, final int responseBufferSize, final HostnameVerifier verifier, final SSLContext theContext)
   {
      final HttpClient httpClient;
      if (System.getSecurityManager() == null)
      {
         httpClient = HttpClientBuilder.create()
                 .setConnectionManager(cm)
                 .setDefaultRequestConfig(rcBuilder.build())
                 .setProxy(defaultProxy)
                 .disableContentCompression().build();
      }
      else {
         httpClient = AccessController.doPrivileged(new PrivilegedAction<HttpClient>()
         {
            @Override
            public HttpClient run()
            {
               return HttpClientBuilder.create()
                        .setConnectionManager(cm)
                        .setDefaultRequestConfig(rcBuilder.build())
                        .setProxy(defaultProxy)
                        .disableContentCompression().build();
            }
         });
      }

      ApacheHttpClient43Engine engine = new ApacheHttpClient43Engine(httpClient, true);
      engine.setResponseBufferSize(responseBufferSize);
      engine.setHostnameVerifier(verifier);
      // this may be null.  We can't really support this with Apache Client.
      engine.setSslContext(theContext);
      return engine;
   }
}
