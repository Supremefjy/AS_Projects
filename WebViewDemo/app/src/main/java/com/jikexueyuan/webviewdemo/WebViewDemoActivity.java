package com.jikexueyuan.webviewdemo;




import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class WebViewDemoActivity extends Activity {
    private EditText mUrl;
    private Button mConnect;
    private WebView mWebView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = (WebView)findViewById(R.id.webview);
        mUrl = (EditText)findViewById(R.id.url);
        mConnect = (Button)findViewById(R.id.connect);

        /**
         * 设置WebView的属性
         */
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);    //支持Javascript脚本语言
        webSettings.setAllowFileAccess(true);      //允许WebView访问文件数据
        webSettings.setBuiltInZoomControls(true);  //支持内容缩放控制

        mConnect.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                openNetPage();
            }
        });

        mUrl.setOnKeyListener(new OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                if(keyCode == KeyEvent.KEYCODE_ENTER){
                    openNetPage();
                    return true;
                }
                return false;
            }

        });
    }

    private void openNetPage(){
        String url = mUrl.getText().toString();
        if(URLUtil.isNetworkUrl(url)){
            mWebView.loadUrl(url);
        }else{
            mUrl.setText("address error, input again!");
        }
    }

    /**
     * 若多次打开网页，支持网页返回到上一级
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if((keyCode == KeyEvent.KEYCODE_BACK) && (mWebView.canGoBack())){
            mWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
} 