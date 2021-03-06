package net.morimekta.providence.thrift.client;

import net.morimekta.providence.PApplicationException;
import net.morimekta.providence.serializer.BinarySerializer;
import net.morimekta.providence.serializer.Serializer;
import net.morimekta.test.providence.thrift.service.Failure;
import net.morimekta.test.providence.thrift.service.MyService;
import net.morimekta.test.providence.thrift.service.MyService2;
import net.morimekta.test.providence.thrift.service.Request;
import net.morimekta.test.providence.thrift.service.Response;
import net.morimekta.test.thrift.thrift.service.MyService.Iface;
import net.morimekta.test.thrift.thrift.service.MyService.Processor;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.awaitility.Awaitility;
import org.awaitility.Duration;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static net.morimekta.providence.PApplicationExceptionType.UNKNOWN_METHOD;
import static net.morimekta.providence.testing.ProvidenceMatchers.equalToMessage;
import static net.morimekta.providence.thrift.util.TestUtil.findFreePort;
import static org.awaitility.Awaitility.waitAtMost;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Test that we can connect to a thrift servlet and get reasonable input and output.
 */
public class SocketClientHandlerTest {
    private static ExecutorService   executor;
    private static int               port;
    private static Iface             impl;
    private static TSimpleServer     server;
    private static BinarySerializer  serializer;
    private static InetSocketAddress address;

    @BeforeClass
    public static void setUpServer() throws Exception {
        Awaitility.setDefaultPollDelay(2, TimeUnit.MILLISECONDS);

        port = findFreePort();
        impl = Mockito.mock(Iface.class);

        TServerSocket transport = new TServerSocket(port);
        server = new TSimpleServer(
                new TServer.Args(transport)
                        .protocolFactory(new TBinaryProtocol.Factory())
                        .processor(new Processor<>(impl)));
        executor = Executors.newSingleThreadExecutor();
        executor.submit(server::serve);
        serializer = new BinarySerializer();
        address = new InetSocketAddress("localhost", port);
    }

    @Before
    public void setUp() {
        reset(impl);
    }

    @AfterClass
    public static void tearDownServer() {
        try {
            server.stop();
            executor.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSimpleRequest() throws IOException, TException, Failure {
        AtomicBoolean called = new AtomicBoolean();
        when(impl.test(new net.morimekta.test.thrift.thrift.service.Request("test")))
                .thenAnswer(i -> {
                    called.set(true);
                    return new net.morimekta.test.thrift.thrift.service.Response("response");
                });

        MyService.Iface client = new MyService.Client(new SocketClientHandler(serializer, address));

        Response response = client.test(new Request("test"));

        waitAtMost(Duration.ONE_HUNDRED_MILLISECONDS).untilTrue(called);
        verify(impl).test(any(net.morimekta.test.thrift.thrift.service.Request.class));

        assertThat(response, is(equalToMessage(new Response("response"))));
    }

    @Test
    public void testOnewayRequest() throws IOException, TException {
        MyService.Iface client = new MyService.Client(new SocketClientHandler(serializer, address));

        AtomicBoolean called = new AtomicBoolean();
        doAnswer(i -> {
            called.set(true);
            return null;
        }).when(impl).ping();

        client.ping();

        waitAtMost(Duration.ONE_HUNDRED_MILLISECONDS).untilTrue(called);

        verify(impl).ping();
    }

    @Test
    public void testSimpleRequest_exception() throws IOException, TException {
        when(impl.test(any(net.morimekta.test.thrift.thrift.service.Request.class)))
                .thenThrow(new net.morimekta.test.thrift.thrift.service.Failure("failure"));

        MyService.Iface client = new MyService.Client(new SocketClientHandler(serializer, address));

        try {
            client.test(new Request(null));
            fail("no exception");
        } catch (Failure f) {
            assertThat(f, is(equalToMessage(new Failure("failure"))));
        }
    }

    @Test
    public void testSimpleRequest_wrongMethod()
            throws IOException, TException, Failure, InterruptedException {
        when(impl.test(any(net.morimekta.test.thrift.thrift.service.Request.class)))
                .thenThrow(new net.morimekta.test.thrift.thrift.service.Failure("failure"));

        MyService2.Iface client = new MyService2.Client(new SocketClientHandler(serializer, address));

        try {
            client.testing(new Request(null));
            fail("no exception");
        } catch (PApplicationException e) {
            assertThat(e.getId(), is(UNKNOWN_METHOD));
            assertThat(e.getMessage(), is(equalTo("Invalid method name: 'testing'")));
        }

        Thread.sleep(10L);

        verifyZeroInteractions(impl);
    }

    @Test
    public void testSimpleRequest_cannotConnect() throws IOException, Failure, InterruptedException {
        Serializer serializer = new BinarySerializer();
        InetSocketAddress address = new InetSocketAddress("localhost", port - 10);
        MyService.Iface client = new MyService.Client(new SocketClientHandler(serializer, address));

        try {
            client.test(new Request(null));
            fail("no exception");
        } catch (ConnectException e) {
            assertThat(e.getMessage(), containsString("Connection refused"));
        }

        Thread.sleep(10L);

        verifyZeroInteractions(impl);
    }
}
