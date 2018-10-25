package com.danqiu.myapplication.utils;

import com.danqiu.myapplication.retrofit.ApiService;
import com.danqiu.myapplication.retrofit.DownloadProgressInterceptor;
import com.danqiu.myapplication.retrofit.JsDownloadListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/28.
 *
 */
@SuppressWarnings("unchecked")
public class RetDownload_Utils {
    /**
     * 服务器 Url
     */
    public static  String baseUrl = ApiService.Base_URL;
    public OkHttpClient.Builder  httpClient;
    public ApiService apiService;
    private static final String TAG = "test-down";
    private static final int DEFAULT_TIMEOUT = 5;
    private Retrofit retrofit;
    private JsDownloadListener mListener;//下载


    public RetDownload_Utils(JsDownloadListener listener) {
           this.mListener = listener;

           httpClient = new OkHttpClient.Builder();
           httpClient.addInterceptor(new DownloadProgressInterceptor(mListener));//自定义下载拦截器
           httpClient.retryOnConnectionFailure(true);
           httpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
           httpClient.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
           httpClient.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

           retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
           apiService = retrofit.create(ApiService.class);
    }

    //下载
    public void downApkFile( String url,final String pathDown, final String saveName,Subscriber subscriber){
        Observable observable = apiService.downloadApp(url)
                .unsubscribeOn(Schedulers.io())
                .map(new Func1<ResponseBody, InputStream>() {
                    @Override
                    public InputStream call(ResponseBody responseBody) {
                        return responseBody.byteStream();
                    }
                })
                .observeOn(Schedulers.computation())    //耗时处理调度
                .doOnNext(new Action1<InputStream>() {
                    @Override
                    public void call(InputStream inputStream) {
                        try {
                            File_Utils.writeFile(inputStream ,File_Utils.mkFile(pathDown,saveName));
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                            try {
                                throw new Exception(e.getMessage(), e);
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });
        observable.subscribeOn(Schedulers.io())  // 被观察者的实现线程
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())  // 观察者的实现线程
                .subscribe(subscriber);
    }

}
