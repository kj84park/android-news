package kr.kyungjoon.hansol.newssample.ui.component.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.kyungjoon.hansol.newssample.R;
import kr.kyungjoon.hansol.newssample.network.dto.Articles;

public class DetailedActivity extends AppCompatActivity {

    @BindView(R.id.news_web_view)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Articles article = (Articles) intent.getSerializableExtra("article");

        webView.setWebViewClient(new WebViewClient());
        WebSettings set = webView.getSettings();
        set.setJavaScriptEnabled(true);
        set.setBuiltInZoomControls(true);
        webView.loadUrl(article.getUrl());
    }
}
