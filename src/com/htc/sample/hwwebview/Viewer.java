package com.htc.sample.hwwebview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Viewer extends Activity {
	
	private static final String LOG_TAG = "HWWebView Viewer";

	private static final String HTML_LOCATION = "file:///android_asset/index.html";

	private WebView webView;

	private Handler handler = new Handler();

	private boolean firstLoad = true;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		webView = (WebView) findViewById(R.id.webView);

		webView.getSettings().setJavaScriptEnabled(true);

		webView.setWebChromeClient(new WebChromeClient() {

			@Override
			public boolean onConsoleMessage(ConsoleMessage cm) {
				Log.e(LOG_TAG, "onConsoleMessage: "
						+ cm.message() + " -- From line "
		                + cm.lineNumber() + " of "
		                + cm.sourceId() );
		                return true;
			}

			@Override
			public boolean onCreateWindow(WebView view, boolean isDialog,
					boolean isUserGesture, Message resultMsg) {
				
				Log.e(LOG_TAG, "onCreateWindow " + resultMsg);
				return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
			}

			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					JsResult result) {
				
				Log.e(LOG_TAG, "onJsAlert " + message);
				return super.onJsAlert(view, url, message, result);
			}

			@Override
			public boolean onJsConfirm(WebView view, String url,
					String message, JsResult result) {

				Log.e(LOG_TAG, "onJsConfirm " + message);
				return super.onJsConfirm(view, url, message, result);
			}

			@Override
			public boolean onJsPrompt(WebView view, String url, String message,
					String defaultValue, JsPromptResult result) {

				Log.e(LOG_TAG, "onJsPrompt " + message);
				return super.onJsPrompt(view, url, message, defaultValue, result);
			}

			@Override
			public boolean onJsTimeout() {

				Log.e(LOG_TAG, "onJsTimeout");
				return super.onJsTimeout();
			}
			
		});
			
		webView.setWebViewClient(new WebViewClient() {
			
			/*
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				
				if ( firstLoad ) {
					firstLoad = false;
					webView.loadUrl("javascript:onBodyLoaded()");
				}
				
			}
			*/

			@Override
			public void onLoadResource(WebView view, String url) {

				Log.e(LOG_TAG, "onLoadResource " + url);
				super.onLoadResource(view, url);
			}

			@Override
			public void onPageFinished(WebView view, String url) {

				Log.e(LOG_TAG, "onPageFinished " + url);
				super.onPageFinished(view, url);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {

				Log.e(LOG_TAG, "onPageStarted " + url);
				super.onPageStarted(view, url, favicon);
			}

			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				Log.e(LOG_TAG, "Error: " + description);
				Toast.makeText(Viewer.this, "Error: " + description, Toast.LENGTH_LONG).show();
			}
		});

		webView.loadUrl(HTML_LOCATION);
/*
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				webView.loadUrl("javascript:onBodyLoaded()");
			}

		}, 5000);
*/
	}
}