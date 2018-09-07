package by.citech.httptest;

import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.io.JsonStringEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.webrtc.IceCandidate;
import org.webrtc.SessionDescription;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class CustomWebChromeClient extends WebChromeClient {


//    private MainActivity activity;
//
//
//    public CustomWebChromeClient(MainActivity activity) {
//        this.activity = activity;
//    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        Log.d("onJSAlert", message);
        return super.onJsAlert(view, url, message, result);
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        Log.d("onJSConfirm", message);
        return super.onJsConfirm(view, url, message, result);
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        Log.d("onJSPrompt", message);
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }


    @Override
    public boolean onConsoleMessage(ConsoleMessage cm) {

        Log.d("YEEEEEE", cm.message());

        String m = cm.message();
//        if (m.startsWith("sdp")) {
//
//            m = m.substring(3);
//            ObjectMapper mapper = new ObjectMapper();
//
//            try {
//                //m = String.valueOf(JsonStringEncoder.getInstance().quoteAsString(m));
//                mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
//                JSONDescription description = mapper.readValue(m, JSONDescription.class);
//                SessionDescription des= new SessionDescription(SessionDescription.Type.fromCanonicalForm(description.getType()), description.getDescription());
//                activity.onDescriptionReceived(des);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else if (m.startsWith("can")) {
//            m = m.substring(3);
//            ObjectMapper mapper = new ObjectMapper();
//
//            try {
//                // todo ????????????
//                //m = String.valueOf(JsonStringEncoder.getInstance().quoteAsString(m));
//                mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
//                JSONCandidate candidate = mapper.readValue(m, JSONCandidate.class);
//                //IceCandidate can = new IceCandidate(candidate.getSdpMid(),candidate.getSdpMLineIndex(),candidate.getSdp());
//                Class<IceCandidate> clazz = IceCandidate.class;
//                Constructor<IceCandidate> c = clazz.getDeclaredConstructor(String.class, int.class, String.class, String.class);
//                c.setAccessible(true);
//                IceCandidate can = c.newInstance(candidate.getSdpMid(), candidate.getSdpMLineIndex(), candidate.getSdp(), candidate.getServerUrl());
//                activity.onCandidateReceived(can);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }



        return true;
    }





}
