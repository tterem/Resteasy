package org.jboss.resteasy.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import org.jboss.resteasy.plugins.server.netty.NettyJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.jboss.resteasy.util.HttpHeaderNames;
import org.junit.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.net.MalformedURLException;
import java.net.URL;

import static org.jboss.resteasy.test.TestPortProvider.generateURL;

public class RESTEASY1325Test {
   static final int IDLE_TIMEOUT = 10;
   static String BASE_URI = generateURL("");

   @BeforeClass
   public static void setupSuite() throws Exception {
   }

   @AfterClass
   public static void tearDownSuite() throws Exception {
   }

   @Before
   public void setupTest() throws Exception {
   }

   @After
   public void tearDownTest() throws Exception {
   }

   @Test(timeout = IDLE_TIMEOUT * 1000 + 1000)
   public void testIdleCloseConnection() throws Exception {
      NettyJaxrsServer netty = new NettyJaxrsServer();
      ResteasyDeployment deployment = new ResteasyDeployment();
      netty.setDeployment(deployment);
      netty.setPort(TestPortProvider.getPort());
      netty.setRootResourcePath("");
      netty.setSecurityDomain(null);
      netty.setIdleTimeout(IDLE_TIMEOUT);
      netty.start();
      deployment.getRegistry().addSingletonResource(new Resource());
      callAndIdle();
      netty.stop();

   }

   /**
    * Test case
    *
    * @throws InterruptedException
    * @throws MalformedURLException
    */
   private void callAndIdle() throws InterruptedException, MalformedURLException {

      EventLoopGroup group = new NioEventLoopGroup();
      try {
         Bootstrap b = new Bootstrap();
         b.group(group)
                 .channel(NioSocketChannel.class)
                 .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                       ch.pipeline().addLast(new HttpClientCodec());
                       ch.pipeline().addLast(new HttpObjectAggregator(4096));
                       ch.pipeline().addLast(new SimpleChannelInboundHandler<FullHttpResponse>() {
                          @Override
                          protected void channelRead0(ChannelHandlerContext ctx, FullHttpResponse msg) {
//                                System.out.println("HTTP response from resteasy: "+msg);
                             Assert.assertEquals(HttpResponseStatus.OK, msg.status());
                          }
                       });
                    }
                 });

         // first request;
         URL url = new URL(BASE_URI + "/test");
         // Make the connection attempt.
         final Channel ch = b.connect(url.getHost(), url.getPort()).sync().channel();

         // Prepare the HTTP request.
         HttpRequest request = new DefaultFullHttpRequest(
                 HttpVersion.HTTP_1_1, HttpMethod.GET, url.getFile());
         request.headers().set(HttpHeaderNames.HOST, url.getHost());
         request.headers().set(HttpHeaderNames.CONNECTION, "keep-alive");
         // Send the HTTP request.
         ch.writeAndFlush(request);

         // waiting for server close connection after idle.
         ch.closeFuture().await();
      } finally {
         // Shut down executor threads to exit.
         group.shutdownGracefully();
      }
   }

   @Path("/")
   public static class Resource {
      @GET
      @Path("/test")
      @Produces(MediaType.TEXT_PLAIN)
      public String get() {
         return "hello world";
      }
   }
}
