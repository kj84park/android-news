package kr.kyungjoon.hansol.newssample.ui.component.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.kyungjoon.hansol.newssample.R;
import kr.kyungjoon.hansol.newssample.listener.RecyclerItemListener;
import kr.kyungjoon.hansol.newssample.network.api.RetroClient;
import kr.kyungjoon.hansol.newssample.network.api.newsApiCallback;
import kr.kyungjoon.hansol.newssample.network.dto.Articles;
import kr.kyungjoon.hansol.newssample.network.dto.GetResponse;
import kr.kyungjoon.hansol.newssample.ui.component.Base;
import kr.kyungjoon.hansol.newssample.ui.component.details.DetailedActivity;
import retrofit2.Retrofit;

public class MainActivity extends Base implements MainView {

    public final String TAG = MainActivity.class.getName();

    @Inject
    Retrofit retrofit;
    private MainPresenter mainPresenter;
    RetroClient retro;

    @BindView(R.id.news_list)
    RecyclerView recyclerView;

    @BindView(R.id.pb_loading)
    ProgressBar progressBar;

    @BindView(R.id.spinner_country)
    Spinner spinnerCountry;

    @BindView(R.id.spinner_category)
    Spinner spinnerCategory;


    List<Articles> articles;

    private String country;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getMainComponent().inject(this);
        mainPresenter = new MainPresenter(this);
        Log.d("aa", "aaaaa");

        country = "kr";
        category = null;

        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                country = (String)parent.getItemAtPosition(position);
                getNews();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                country = "kr";
            }
        });

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = (String)parent.getItemAtPosition(position);
                if("Headline".equals(category)){
                    category = null;
                }
                getNews();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                category = null;
            }
        });

        retro = RetroClient.getInstance(this).createBaseApi();

        getNews();
    }

    void getNews() {
        retro.getResponse(country, category, "04871c167cfb4a3c9c18b9d170d8ba7f", new newsApiCallback() {
            @Override
            public void onError(Throwable t) {
                Log.d(TAG, "###### onError : ");
            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                GetResponse response = (GetResponse) receivedData;
                initView(response.getArticles());
                Log.d(TAG, "###### onSuccess : " + code);
                progressBar.setVisibility(8);
            }

            @Override
            public void onFailure(int code) {
                Log.d(TAG, "###### onFailure : " + code);
            }
        });
    }

    private final RecyclerItemListener recyclerItemListener = position -> {
        Articles article =  articles.get(position);
        Intent intent = new Intent(getApplicationContext(), DetailedActivity.class);
        intent.putExtra("article", article);

        startActivity(intent);
    };

    void initView(List<Articles> articles) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new MainViewAdapter(articles, recyclerItemListener));
        this.articles = articles;
    }


//    public void loadPosts() {
//
//        retrofit.create(EventApi.class).getFeeds().subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<List<Event>>() {
//                    @Override
//                    public void onCompleted() {
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(List<Event> data) {
//
//                    }
//                });
//    }
}

