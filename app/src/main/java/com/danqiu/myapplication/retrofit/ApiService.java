package com.danqiu.myapplication.retrofit;


import com.danqiu.myapplication.bean.BaseInfo;
import com.danqiu.myapplication.bean.TestBean;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by lzj on 2018/4/28.
 *   所有网络接口
 */

public interface ApiService {
    //基类Url            //"http://www.co360.cn/web/";   // "https://api.douban.com/"; http://www.danqiuedu.com/
    public static final String Base_URL = "http://www.co360.cn/web/";
    public static final String Base_URL2 = "http://img-1253650823.cosgz.myqcloud.com/";


    /**
     * 评价
     *  get  有参  有请求头token
     */
    @GET("api/pri/user/cmts")
    Observable<ResponseBody> getEvaluationList(@Header("Token") String token, @QueryMap Map<String, String> map);
    /**
     * 评价
     *  get  有参  有请求头token
     */
    @GET("api/pri/user/cmts")
    Observable<ResponseBody> getEvaluationLists(@Header("Token") String token,@Query("pageNo") int pageNo,@Query("pageSize") int pageSize);

    /**
     * 上传图片
     * post 有请求头token
     */
    @POST("api/pub/upload")
    @Multipart
    Observable<ResponseBody> upLoadImg(@Header("Token") String token, @Part() MultipartBody multipartBody);

    /**
     * 多文件上传
     * @param token
     * @param maps
     * @param file
     * @return
     */
    @POST("uapi/api/pri/teacher/publish/work")
    @Multipart
    Observable<ResponseBody> publishHomework(@Header("Token") String token, @PartMap Map<String, RequestBody> maps, @Part() MultipartBody.Part [] file);

    /**
     * 下载文件
     *get
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadApp(@Url String url);

    /**
     * 下载文件
     *get
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadApps(@Url String url);


    /**
     * 登录
     * post  有参
     * 未配置解析
     */
    @Headers("Content-type:application/x-www-form-urlencoded")
    @POST("api/pub/user/login")
    @FormUrlEncoded
    Observable<ResponseBody> login_body(@FieldMap Map<String, String> map);
    /**
     * 登录
     * post  有参
     * 配置解析类
     */
    @Headers("Content-type:application/x-www-form-urlencoded")
    @POST("api/pub/user/login")
    @FormUrlEncoded
    Observable<TestBean> login(@FieldMap Map<String, String> map);


    /**
     * 获取movie的数据
     * get 有参
     * @param start
     * @param count
     * @return
     */
    @GET("v2/movie/top250")
    Observable<BaseInfo> getMovies(@Query("start") int start, @Query("count") int count);



}
