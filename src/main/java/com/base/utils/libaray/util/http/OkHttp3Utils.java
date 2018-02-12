package com.base.utils.libaray.util.http;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * 
 * @author smz
 *
 */
public class OkHttp3Utils {

	public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");

	private volatile static OkHttp3Utils mInstance;

	private OkHttpClient mOkHttpClient;

	private OkHttp3Utils() {
		super();
		Builder clientBuilder = new OkHttpClient().newBuilder();
		clientBuilder.readTimeout(30, TimeUnit.MINUTES);
		clientBuilder.connectTimeout(10, TimeUnit.MINUTES);
		clientBuilder.writeTimeout(60, TimeUnit.MINUTES);
		clientBuilder.connectionPool(new ConnectionPool(5, 10, TimeUnit.MINUTES));
		mOkHttpClient = clientBuilder.build();
	}

	public static OkHttp3Utils getInstance() {
		OkHttp3Utils temp = mInstance;
		if (temp == null) {
			synchronized (OkHttp3Utils.class) {
				temp = mInstance;
				if (temp == null) {
					temp = new OkHttp3Utils();
					mInstance = temp;
				}
			}
		}
		return temp;
	}

	public OkHttpClient getOkHttpClient() {
		return mOkHttpClient;
	}

	/**
	 * 设置请求头
	 * 
	 * @param headersParams
	 * @return
	 */
	private Headers SetHeaders(Map<String, Object> headersParams) {
		Headers headers = null;
		Headers.Builder headersbuilder = new Headers.Builder();
		if (headersParams != null) {
			Iterator<String> iterator = headersParams.keySet().iterator();
			String key = "";
			while (iterator.hasNext()) {
				key = iterator.next().toString();
				headersbuilder.add(key, String.valueOf(headersParams.get(key)));
			}
		}
		headers = headersbuilder.build();
		return headers;
	}

	/**
	 * post请求参数
	 * 
	 * @param BodyParams
	 * @return
	 */
	private RequestBody SetPostRequestBody(Map<String, Object> BodyParams) {
		RequestBody body = null;
		FormBody.Builder formEncodingBuilder = new FormBody.Builder();
		if (BodyParams != null) {
			Iterator<String> iterator = BodyParams.keySet().iterator();
			String key = "";
			while (iterator.hasNext()) {
				key = iterator.next().toString();
				formEncodingBuilder.add(key, String.valueOf(BodyParams.get(key)));
			}
		}
		body = formEncodingBuilder.build();
		return body;
	}

	/**
	 * get方法连接拼加参数
	 * 
	 * @param mapParams
	 * @return
	 */
	public static String setGetUrlParams(Map<String, Object> mapParams) {
		String strParams = "";
		if (mapParams != null) {
			Iterator<String> iterator = mapParams.keySet().iterator();
			String key = "";
			while (iterator.hasNext()) {
				key = iterator.next().toString();
				strParams += "&" + key + "=" + String.valueOf(mapParams.get(key));
			}
		}
		return strParams;
	}

	public static void main(String[] args) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("awb", "123");
		params.put("order", "25");
		params.put("username", "kkli");
		params.put("password", "fwe");
		System.out.println(setGetUrlParams(params));
	}

	/**
	 * 实现post请求 同步
	 * 
	 * @param reqUrl
	 * @param headersParams
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public String doPost_Execute(String reqUrl, Map<String, Object> headersParams, Map<String, Object> params)
			throws IOException {
		Request.Builder RequestBuilder = new Request.Builder();
		RequestBuilder.url(reqUrl);// 添加URL地址
		RequestBuilder.post(SetPostRequestBody(params));
		RequestBuilder.headers(SetHeaders(headersParams));
		Request request = RequestBuilder.build();
		Call call = mOkHttpClient.newCall(request);
		Response response = call.execute();
		byte[] bytes = response.body().bytes();
		String result = new String(bytes, Charset.forName("UTF-8"));
		return result;
	}

	/**
	 * 实现post请求 异步
	 * 
	 * @param reqUrl
	 * @param headersParams
	 * @param params
	 * @param callback
	 */
	public void doPost_Enqueue(String reqUrl, Map<String, Object> headersParams, Map<String, Object> params,
			final NetCallback callback) {
		Request.Builder RequestBuilder = new Request.Builder();
		RequestBuilder.url(reqUrl);// 添加URL地址
		RequestBuilder.post(SetPostRequestBody(params));
		RequestBuilder.headers(SetHeaders(headersParams));// 添加请求头
		Request request = RequestBuilder.build();

		mOkHttpClient.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				callback.onSuccess(0, response.body().toString());
				call.cancel();
			}

			@Override
			public void onFailure(Call call, IOException e) {
				callback.onFailure(-1, e.getMessage());
				call.cancel();
			}
		});
	}

	public String doGet_Execute(String reqUrl, Map<String, Object> headersParams, Map<String, Object> params) {
		Request.Builder RequestBuilder = new Request.Builder();
		RequestBuilder.url(reqUrl + setGetUrlParams(params));// 添加URL地址 自行加 ?
		RequestBuilder.headers(SetHeaders(headersParams));// 添加请求头
		Request request = RequestBuilder.build();
		String result = "";
		try {
			Response response = mOkHttpClient.newCall(request).execute();
			if (response.isSuccessful()) {
				byte[] bytes = response.body().bytes();
				result = new String(bytes, Charset.forName("UTF-8"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 实现get请求
	 * 
	 * @param reqUrl
	 * @param headersParams
	 * @param params
	 * @param callback
	 */
	public void doGet_Enqueue(String reqUrl, Map<String, Object> headersParams, Map<String, Object> params,
			final NetCallback callback) {
		Request.Builder RequestBuilder = new Request.Builder();
		RequestBuilder.url(reqUrl + setGetUrlParams(params));// 添加URL地址 自行加 ?
		RequestBuilder.headers(SetHeaders(headersParams));// 添加请求头
		Request request = RequestBuilder.build();
		mOkHttpClient.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				callback.onSuccess(0, response.body().toString());
				call.cancel();
			}

			@Override
			public void onFailure(Call call, IOException e) {
				callback.onFailure(-1, e.getMessage());
				call.cancel();
			}
		});
	}

	/**
	 * 参数为 json格式
	 * 
	 * @param BodyParams
	 * @return
	 */
	private RequestBody SetPostRequestBodyByJson(String BodyParams) {
		MediaType JSON = MediaType.parse("application/json; charset=utf-8");
		return RequestBody.create(JSON, BodyParams);
	}

	/**
	 * post 提交String
	 * 
	 * @param postBody
	 * @return
	 */
	private RequestBody SetPostRequestBodyByString(String postBody) {
		return RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody);
	}

	private RequestBody SetPostRequestBodyByStream() {
		return new RequestBody() {
			@Override
			public MediaType contentType() {
				return MEDIA_TYPE_MARKDOWN;
			}

			@Override
			public void writeTo(BufferedSink sink) throws IOException {
				sink.writeUtf8("Numbers\n");
				sink.writeUtf8("-------\n");
				for (int i = 2; i <= 997; i++) {
					sink.writeUtf8(String.format(" * %s = %s\n", i, factor(i)));
				}
			}

			private String factor(int n) {
				for (int i = 2; i < n; i++) {
					int x = n / i;
					if (x * i == n)
						return factor(x) + " × " + i;
				}
				return Integer.toString(n);
			}

		};
	}

	/**
	 * 实现post请求 同步
	 * 
	 * @param reqUrl
	 * @param headersParams
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public String doPost_Execute(String reqUrl, Map<String, Object> headersParams, String params) throws IOException {
		Request.Builder RequestBuilder = new Request.Builder();
		RequestBuilder.url(reqUrl);// 添加URL地址
		RequestBuilder.post(SetPostRequestBodyByJson(params));

		RequestBuilder.headers(SetHeaders(headersParams));// 添加请求头
		Request request = RequestBuilder.build();
		Call call = mOkHttpClient.newCall(request);
		Response response = call.execute();
		byte[] bytes = response.body().bytes();
		String result = new String(bytes, Charset.forName("UTF-8"));
		return result;
	}

	/**
	 * 实现post请求 异步
	 * 
	 * @param reqUrl
	 * @param headersParams
	 * @param params
	 * @param callback
	 */
	public void doPost_Enqueue(String reqUrl, Map<String, Object> headersParams, String params,
			final NetCallback callback) {
		Request.Builder RequestBuilder = new Request.Builder();
		RequestBuilder.url(reqUrl);// 添加URL地址
		RequestBuilder.post(SetPostRequestBodyByJson(params));
		RequestBuilder.headers(SetHeaders(headersParams));// 添加请求头
		Request request = RequestBuilder.build();

		mOkHttpClient.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				callback.onSuccess(0, response.body().toString());
				call.cancel();
			}

			@Override
			public void onFailure(Call call, IOException e) {
				callback.onFailure(-1, e.getMessage());
				call.cancel();
			}
		});
	}

	public abstract class NetCallback {
		public abstract void onFailure(int code, String msg);

		public abstract void onSuccess(int code, String content);

	}

	public String doPost_ExecuteByString(String reqUrl, Map<String, Object> headersParams, String postBody)
			throws Exception {

		Request.Builder RequestBuilder = new Request.Builder();
		RequestBuilder.url(reqUrl);// 添加URL地址
		RequestBuilder.post(SetPostRequestBodyByString(postBody));

		RequestBuilder.headers(SetHeaders(headersParams));// 添加请求头
		Request request = RequestBuilder.build();
		Call call = mOkHttpClient.newCall(request);
		Response response = call.execute();
		byte[] bytes = response.body().bytes();
		String result = new String(bytes, Charset.forName("UTF-8"));
		return result;
	}
}