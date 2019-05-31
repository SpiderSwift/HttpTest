package by.citech.httptest;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.webrtc.DataChannel;
import org.webrtc.IceCandidate;
import org.webrtc.MediaConstraints;
import org.webrtc.MediaStream;
import org.webrtc.PeerConnection;
import org.webrtc.PeerConnectionFactory;
import org.webrtc.RtpReceiver;
import org.webrtc.SdpObserver;
import org.webrtc.SessionDescription;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.lang.reflect.Type;


import okhttp3.WebSocket;
import rx.Scheduler;
import rx.schedulers.Schedulers;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompHeader;
import ua.naiksoftware.stomp.client.StompClient;

import static ua.naiksoftware.stomp.LifecycleEvent.Type.OPENED;
import static ua.naiksoftware.stomp.LifecycleEvent.Type.CLOSED;
import static ua.naiksoftware.stomp.LifecycleEvent.Type.ERROR;


@SuppressWarnings("all")
public class MainActivity extends AppCompatActivity {

    private EditText field_login;
    private EditText field_pass;
    private Button send;
    private Button connect;
    private Button disconnect;
    private TextView login;
    private TextView pass;
    private CookieManager cookieManager;
    private RequestQueue queue;
    private WebView webView;
    private PeerConnection peerConnection;
    private DataChannel dataChannel;
    private List<IceCandidate> candidates = new ArrayList<>();







    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //todo add button field_pass send
        // dataChannel.send(new DataChannel.Buffer(ByteBuffer.wrap("hekki".getBytes()), true));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        field_login = findViewById(R.id.edit_email);
        field_pass = findViewById(R.id.edit_password);
        send = findViewById(R.id.button_send);
        connect = findViewById(R.id.button_connect);
        disconnect = findViewById(R.id.disconnect);
        login = findViewById(R.id.tv_name);
        pass = findViewById(R.id.tv_to);

        //field_pass.setVisibility(View.INVISIBLE);
        pass.setVisibility(View.INVISIBLE);

        //disconnect.setVisibility(View.INVISIBLE);

        queue = Volley.newRequestQueue(this);

        webView = findViewById(R.id.webView);
        /**
         * Для получения сообщений от JS(т.к клиентская часть написана на JS и использует SockJS)
         */
        //webView.setWebChromeClient(new CustomWebChromeClient());
        //webView.getSettings().setJavaScriptEnabled(true);
        //webView.setWebViewClient(new CustomWebClient());
        //webView.loadUrl("http://env-2764881.mycloud.by");


        send.setOnClickListener((view) -> {

            /** Реализация Android Stomp. Рабочая, но подключается по стандартной сессии, а не по SockJS */
            Map<String, String> headerMap = new HashMap<>();
            headerMap.put("Cookie", "JSESSIONID=" + this.cookieManager.getCookieStore().getCookies().get(0).getValue());
            StompClient client = Stomp.over(WebSocket.class, "ws://192.168.0.189/cit-websocket/websocket", headerMap);
            client.connect();
            client.topic("/topic/info/lionheart66666@mail.ru").subscribe(topicMessage -> {
                Log.d("TAG", topicMessage.getPayload());
            });
            client.lifecycle().subscribe(lifecycleEvent -> {
                        switch (lifecycleEvent.getType()) {
                            case OPENED:
                                Log.e("Log", "OPENED");
                                break;
                            case ERROR:
                                Log.e("TAG", "ERROR" + lifecycleEvent.getException());
                                break;
                            case CLOSED:
                                Log.e("TAG", "CLOSED");
                        }
                    });
            //webView.loadUrl("javascript:sendSdp('" + field_pass.getText() + "', '" + field_login.getText() + "')");
            //webView.loadUrl("javascript:sendCandidate('" + field_pass.getText() + "', '" + field_login.getText() + "')");

            //PeerConnectionFactory.InitializationOptions options = PeerConnectionFactory.InitializationOptions.builder(this).createInitializationOptions();
            //PeerConnectionFactory.initialize(options);

            //PeerConnectionFactory factory = PeerConnectionFactory.builder().createPeerConnectionFactory();

            //List<PeerConnection.IceServer> iceServers = new ArrayList<>();

            //PeerConnection.IceServer.Builder iceServerBuilder = PeerConnection.IceServer.builder("turn:turn.bistri.com:80").setUsername("homeo").setPassword("homeo");
            //iceServerBuilder.setTlsCertPolicy(PeerConnection.TlsCertPolicy.TLS_CERT_POLICY_INSECURE_NO_CHECK); //this does the magic.
            //PeerConnection.IceServer server = iceServerBuilder.createIceServer();

            //iceServers.add(server);

//            MediaConstraints constraints = new MediaConstraints();
//            constraints.optional.add(new MediaConstraints.KeyValuePair("DtlsSrtpKeyAgreement", "true"));
//            constraints.optional.add(new MediaConstraints.KeyValuePair("RtpDataChannels", "true"));
//            constraints.mandatory.add(new MediaConstraints.KeyValuePair("OfferToReceiveAudio", "false"));
//            constraints.mandatory.add(new MediaConstraints.KeyValuePair("OfferToReceiveVideo", "false"));
//            peerConnection = factory.createPeerConnection(iceServers, constraints, new PeerConnection.Observer() {
//                @Override
//                public void onSignalingChange(PeerConnection.SignalingState signalingState) {
//                    Log.d("onSignalingChange", signalingState.name());
//                }
//
//                @Override
//                public void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState) {
//                    Log.d("onIceConnectionChange", iceConnectionState.name());
//                }
//
//                @Override
//                public void onIceConnectionReceivingChange(boolean b) {
//                    Log.d("onIceConnectionReceivin", String.valueOf(b));
//                }
//
//                @Override
//                public void onIceGatheringChange(PeerConnection.IceGatheringState iceGatheringState) {
//                    Log.d("onIceGatheringChange", iceGatheringState.name());
//                }
//
//                @Override
//                public void onIceCandidate(IceCandidate iceCandidate) {
//                    Log.d("onIceCandidate", iceCandidate.toString());
//                    ObjectMapper mapper = new ObjectMapper();
//                    String string = "";
//                    try {
//                        string = mapper.writeValueAsString(iceCandidate);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    String finalString = string;
//                    runOnUiThread(() -> webView.loadUrl("javascript:sendCandidate('" + field_pass.getText() + "', '" + finalString + "')"));
//                    //TODO send ice candidate field_pass another pear via signal server?
//
//                }
//
//                @Override
//                public void onIceCandidatesRemoved(IceCandidate[] iceCandidates) {
//
//                }
//
//                @Override
//                public void onAddStream(MediaStream mediaStream) {
//                    Log.d("onAddStream", mediaStream.toString());
//                }
//
//                @Override
//                public void onRemoveStream(MediaStream mediaStream) {
//                    Log.d("onRemoveStream", mediaStream.toString());
//                }
//
//                @Override
//                public void onDataChannel(DataChannel dataChannel) {
//                    Log.d("onDataChannel", dataChannel.toString());
//                }
//
//                @Override
//                public void onRenegotiationNeeded() {
//                    Log.d("onRenegotiationNeeded", "¯\\_(ツ)_/¯");
//                }
//
//                @Override
//                public void onAddTrack(RtpReceiver rtpReceiver, MediaStream[] mediaStreams) {
//
//                }
//            });
            //dataChannel = peerConnection.createDataChannel("dataChannel", new DataChannel.Init());
            //createDataChannel();

//            peerConnection.createOffer(new SdpObserver() {
//                @Override
//                public void onCreateSuccess(SessionDescription sessionDescription) {
//                    Log.d("onCreateSuccess", sessionDescription.description);
//
//                    peerConnection.setLocalDescription(new SimpleSdpObserver(), sessionDescription);
//                    ObjectMapper mapper = new ObjectMapper();
//                    String string = "";
//                    try {
//                        string = mapper.writeValueAsString(sessionDescription);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    String finalString = string;
//                    runOnUiThread(() -> webView.loadUrl("javascript:sendSdp('" + field_pass.getText() + "', '" + finalString + "')") );
//
//                }
//
//                @Override
//                public void onSetSuccess() {
//
//                }
//
//                @Override
//                public void onCreateFailure(String s) {
//
//                }
//
//                @Override
//                public void onSetFailure(String s) {
//
//                }
//            }, constraints);

//            dataChannel.registerObserver(new DataChannel.Observer() {
//                @Override
//                public void onBufferedAmountChange(long l) {
//
//                }
//
//                @Override
//                public void onStateChange() {
//
//                }
//
//                @Override
//                public void onMessage(DataChannel.Buffer buffer) {
//                    Toast.makeText(getApplicationContext(), buffer.toString(), Toast.LENGTH_LONG);
//                }
//            });

            //todo cookie save and adding field_pass webview
            /**
             * Пример добавления куки в запрос и подключения к серверу через webview
             */
            //CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(webView.getContext());
            //android.webkit.CookieManager cookieManager = android.webkit.CookieManager.getInstance();
            //cookieManager.setAcceptCookie(true);
            //cookieManager.removeSessionCookie();
            //cookieManager.setCookie("http://env-2764881.mycloud.by","JSESSIONID=" + this.cookieManager.getCookieStore().getCookies().get(0).getValue()+" ; Domain=.env-2764881.mycloud.by");
            //cookieSyncManager.sync();
            //new Handler().postDelayed(() -> webView.loadUrl("http://env-2764881.mycloud.by/admin/home"), 500);




            /**
             * Пример запроса для регистрации
             */
//            RequestQueue queue = Volley.newRequestQueue(this);
//
//            String url = "http://env-2764881.mycloud.by/registration";
//            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
//                    response -> {},
//                    error -> {}
//            ) {
//
//                @Override
//                public String getBodyContentType() {
//                    return "application/x-www-form-urlencoded; charset=UTF-8";
//                }
//
//                @Override
//                protected Map<String, String> getParams() {
//                    Map<String, String>  params = new HashMap<String, String>();
//
//                    String mail = field_login.getText().toString().trim();
//                    String pass = field_pass.getText().toString().trim();
//
//                    params.put("name", "MyName");
//                    params.put("lastName", "haHAA");
//                    params.put("email", "leon@mail.ru");
//                    params.put("password", "12345");
//                    params.put("nickname", "HiName");
//
//                    return params;
//                }
//
//
//
//                @Override
//                protected Response<String> parseNetworkResponse(NetworkResponse response) {
//
//                    //Log.d("COOKIE", cookieManager.getCookieStore().getCookies().get(0).getValue()); todo сохранить значение куки JSESSIONID
//                    return super.parseNetworkResponse(response);
//                }
//            };
//            queue.add(postRequest);


        });

        connect.setOnClickListener((v -> {
//            webView.loadUrl("javascript:connectUnique('" + field_login.getText() + "')");
//
//            field_login.setVisibility(View.INVISIBLE);
//            login.setVisibility(View.INVISIBLE);
//
//            field_pass.setVisibility(View.VISIBLE);
//            pass.setVisibility(View.VISIBLE);
//
//            connect.setVisibility(View.INVISIBLE);
//            disconnect.setVisibility(View.VISIBLE);



            //todo volley login request and cookie adding
            cookieManager = new CookieManager();
            CookieHandler.setDefault(cookieManager);

            RequestQueue queue = Volley.newRequestQueue(this);




            String url = "http://env-2764881.mycloud.by/login";
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    response -> {},
                    error -> {}
            ) {

                @Override
                public String getBodyContentType() {
                    return "application/x-www-form-urlencoded; charset=UTF-8";
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String>  params = new HashMap<String, String>();


                    String mail = field_login.getText().toString().trim();
                    String pass = field_pass.getText().toString().trim();

                    params.put("email", mail);
                    params.put("password", pass);
                    params.put("Submit", "Login");

                    return params;
                }



                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    //Log.d("COOKIE", cookieManager.getCookieStore().getCookies().get(0).getValue()); todo сохранить значение куки JSESSIONID
                    return super.parseNetworkResponse(response);
                }
            };
            queue.add(postRequest);
        }));

        disconnect.setOnClickListener(v -> {
            //webView.loadUrl("javascript:connectUnique('" + field_login.getText() + "')");


//            webView.loadUrl("javascript:disconnect()");
//
//            field_login.setVisibility(View.VISIBLE);
//            login.setVisibility(View.VISIBLE);
//
//            field_pass.setVisibility(View.INVISIBLE);
//            pass.setVisibility(View.INVISIBLE);
//
//
//            connect.setVisibility(View.VISIBLE);
//            disconnect.setVisibility(View.INVISIBLE);

//            dataChannel = peerConnection.createDataChannel("dataChannel", new DataChannel.Init());
//
//            dataChannel.registerObserver(new DataChannel.Observer() {
//                @Override
//                public void onBufferedAmountChange(long l) {
//
//                }
//
//                @Override
//                public void onStateChange() {
//
//                }
//
//                @Override
//                public void onMessage(DataChannel.Buffer buffer) {
//                    Toast.makeText(getApplicationContext(), buffer.toString(), Toast.LENGTH_LONG);
//                }
//            });



            //createDataChannel();

            //dataChannel.send(new DataChannel.Buffer(ByteBuffer.wrap("abc".getBytes()), true));



            RequestQueue queue = Volley.newRequestQueue(this);




            String url = "http://env-2764881.mycloud.by/verify?hash=" + field_pass.getText().toString();
            StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                    response -> {},
                    error -> {}
            ) {

                @Override
                public String getBodyContentType() {
                    return "application/x-www-form-urlencoded; charset=UTF-8";
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String>  params = new HashMap<String, String>();

                    String mail = field_login.getText().toString().trim();
                    String pass = field_pass.getText().toString().trim();

                    params.put("email", mail);
                    params.put("password", pass);
                    params.put("Submit", "Login");

                    return params;
                }



                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    //Log.d("COOKIE", cookieManager.getCookieStore().getCookies().get(0).getValue()); todo сохранить значение куки JSESSIONID
                    return super.parseNetworkResponse(response);
                }
            };
            queue.add(postRequest);

        });

    }


    public void onDescriptionReceived(SessionDescription description) {
        if (description.type.equals(SessionDescription.Type.OFFER)) {
            PeerConnectionFactory.InitializationOptions options = PeerConnectionFactory.InitializationOptions.builder(this).createInitializationOptions();
            PeerConnectionFactory.initialize(options);

            PeerConnectionFactory factory = PeerConnectionFactory.builder().createPeerConnectionFactory();

            List<PeerConnection.IceServer> iceServers = new ArrayList<>();

            PeerConnection.IceServer.Builder iceServerBuilder = PeerConnection.IceServer.builder("stun:stun.l.google.com:19302");
            iceServerBuilder.setTlsCertPolicy(PeerConnection.TlsCertPolicy.TLS_CERT_POLICY_INSECURE_NO_CHECK); //this does the magic.

            PeerConnection.IceServer server = iceServerBuilder.createIceServer();

            iceServers.add(server);

            MediaConstraints constraints = new MediaConstraints();
            constraints.optional.add(new MediaConstraints.KeyValuePair("DtlsSrtpKeyAgreement", "true"));
            constraints.optional.add(new MediaConstraints.KeyValuePair("RtpDataChannels", "true"));
            constraints.mandatory.add(new MediaConstraints.KeyValuePair("OfferToReceiveAudio", "false"));
            constraints.mandatory.add(new MediaConstraints.KeyValuePair("OfferToReceiveVideo", "false"));
            peerConnection = factory.createPeerConnection(iceServers, constraints, new PeerConnection.Observer() {
                @Override
                public void onSignalingChange(PeerConnection.SignalingState signalingState) {
                    Log.d("onSignalingChange", signalingState.name());
                }

                @Override
                public void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState) {
                    Log.d("onIceConnectionChange", iceConnectionState.name());
                }

                @Override
                public void onIceConnectionReceivingChange(boolean b) {
                    Log.d("onIceConnectionReceivin", String.valueOf(b));
                }

                @Override
                public void onIceGatheringChange(PeerConnection.IceGatheringState iceGatheringState) {
                    Log.d("onIceGatheringChange", iceGatheringState.name());
                }

                @Override
                public void onIceCandidate(IceCandidate iceCandidate) {
                    Log.d("onIceCandidate", iceCandidate.toString());


                    //TODO send ice candidate field_pass another pear via signal server?

                    ObjectMapper mapper = new ObjectMapper();
                    String string = "";
                    try {
                        string = mapper.writeValueAsString(iceCandidate);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    String finalString = string;
                    runOnUiThread(() -> webView.loadUrl("javascript:sendCandidate('" + field_pass.getText() + "', '" + finalString + "')") );
                }

                @Override
                public void onIceCandidatesRemoved(IceCandidate[] iceCandidates) {

                }

                @Override
                public void onAddStream(MediaStream mediaStream) {
                    Log.d("onAddStream", mediaStream.toString());
                }

                @Override
                public void onRemoveStream(MediaStream mediaStream) {
                    Log.d("onRemoveStream", mediaStream.toString());
                }

                @Override
                public void onDataChannel(DataChannel dataChannel) {
                    Log.d("onDataChannel", dataChannel.toString());
                }

                @Override
                public void onRenegotiationNeeded() {
                    Log.d("onRenegotiationNeeded", "¯\\_(ツ)_/¯");
                }

                @Override
                public void onAddTrack(RtpReceiver rtpReceiver, MediaStream[] mediaStreams) {

                }
            });
            for (IceCandidate candidate : candidates) {
                peerConnection.addIceCandidate(candidate);
            }

            createDataChannel();




            peerConnection.setRemoteDescription(new SimpleSdpObserver(), description);

            peerConnection.createAnswer(new SdpObserver() {
                @Override
                public void onCreateSuccess(SessionDescription sessionDescription) {
                    Log.d("onCreateSuccess", sessionDescription.description);

                    peerConnection.setLocalDescription(new SimpleSdpObserver(), sessionDescription);
                    //peerConnection.setRemoteDescription(new SimpleSdpObserver(), description);
                    ObjectMapper mapper = new ObjectMapper();
                    String string = "";
                    try {
                        string = mapper.writeValueAsString(sessionDescription);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    String finalString = string;
                    runOnUiThread(() -> webView.loadUrl("javascript:sendSdp('" + field_pass.getText() + "', '" + finalString + "')") );
                }

                @Override
                public void onSetSuccess() {

                }

                @Override
                public void onCreateFailure(String s) {
                    Log.d("onCreateFailure: ", s);
                }

                @Override
                public void onSetFailure(String s) {

                }
            }, constraints);




        } else {
            Log.d("MainActivity", "got answer description : " + description);
            peerConnection.setRemoteDescription(new SimpleSdpObserver(), description);
        }


    }

    public void onCandidateReceived(IceCandidate candidate) {
        candidates.add(candidate);
        if (peerConnection != null) {
            peerConnection.addIceCandidate(candidate);
        }
    }






    public void createDataChannel() {

        dataChannel = peerConnection.createDataChannel("dataChannel", new DataChannel.Init());
//        dataChannel.registerObserver(new DataChannel.Observer() {
//            @Override
//            public void onBufferedAmountChange(long l) {
//
//            }
//
//            @Override
//            public void onStateChange() {
//
//            }
//
//            @Override
//            public void onMessage(DataChannel.Buffer buffer) {
//                Toast.makeText(getApplicationContext(), buffer.data.toString(), Toast.LENGTH_LONG);
//            }
//        });
    }
}






