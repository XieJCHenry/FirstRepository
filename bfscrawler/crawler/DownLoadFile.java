package crawler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EntityUtils;

public class DownLoadFile {
	public String getFileNameByUrl(String url, String contentType) {
		url = url.substring(7);
		if (contentType.indexOf("html") != -1) {
			url = url.replace("[\\?/:*|<>\"]", "_");
			return url;
		} else {
			return url.replaceAll("[\\?/:*|<>\"]", "_") + "."
					+ contentType.substring(contentType.lastIndexOf("/") + 1);
		}
	}

	private void saveToLocal(byte[] data, String filePath) {
		try {
//			DataOutputStream out=new DataOutputStream(new FileOutputStream(new File(filePath)));
			FileOutputStream fos = new FileOutputStream(new File(filePath));
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			for (int i = 0; i < data.length; i++)
				bos.write(data[i]);
			bos.flush();
			bos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String downloadPage(String url, String path) {
		String filePath = null;

		RequestConfig config = RequestConfig.custom().setConnectTimeout(5 * 1000)
				.setSocketTimeout(5 * 1000).build();
		HttpClientBuilder builder = HttpClientBuilder.create()
				.setRetryHandler(new DefaultHttpRequestRetryHandler(10, true))
				.setDefaultRequestConfig(config);
		CloseableHttpClient client = builder.build();
		HttpGet httpget = new HttpGet(url);

		try {
			HttpResponse response = client.execute(httpget);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed:" + statusCode);
			}

			HttpEntity entity = response.getEntity();
			BufferedInputStream bis = new BufferedInputStream(entity.getContent());
			// ByteArrayBuffer bas = new ByteArrayBuffer(20);

			filePath = path + "\\temp2\\"
					+ getFileNameByUrl(url, response.getLastHeader("Content-Type").getValue())+".html";
//			System.out.println(response.getLastHeader("Content-Type").getValue());
			File file = new File(filePath);
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			int current = 0;
			byte[] data = new byte[1024];
			while ((current = bis.read(data)) != -1) {
				bos.write(data, 0, current);
			}
			bos.flush();
			bos.close();
			bis.close();
			if (entity != null)
				EntityUtils.consume(entity);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filePath;
	}
}
