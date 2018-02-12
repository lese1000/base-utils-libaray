package com.base.utils.libaray.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

/**
 * 下载文件 创建人：FH 创建时间：2014年12月23日
 * 
 * @version
 */
public class FileDownload {

	/**
	 * @param response
	 * @param filePath
	 *            //文件完整路径(包括文件名和扩展名)
	 * @param fileName
	 *            //下载后看到的文件名
	 * @return 文件名
	 */
	public static void fileDownload(final HttpServletResponse response, String filePath, String fileName)
			throws Exception {

		byte[] data = FileUtil.toByteArray2(filePath);
		fileName = URLEncoder.encode(fileName, "UTF-8");
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream;charset=UTF-8");
		OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
		outputStream.write(data);
		outputStream.flush();
		outputStream.close();
		response.flushBuffer();

	}

	/**
	 * 
	 * @param response
	 * @param urlstr
	 * @throws Exception
	 */
	public static void downloadByUrl(HttpServletResponse response, String urlstr) throws Exception {
		String[] sarr = urlstr.split("/");
		String fileName = sarr[sarr.length - 1] + ".pdf";
		byte[] data = FileUtil.strtoByteArray2(urlstr);
		fileName = URLEncoder.encode(fileName, "UTF-8");
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream;charset=UTF-8");
		OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
		outputStream.write(data);
		outputStream.flush();
		outputStream.close();
		response.flushBuffer();

	}

	/**
	 * 文件下载
	 * 
	 * @param response
	 * @param url
	 *            HTTP URL
	 * @throws Exception
	 */
	public static void downloadByUrl2(HttpServletResponse response, String urlstr, String fileName) throws Exception {

		InputStream ism = null;
		try {
			URL url = new URL(urlstr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			ism = connection.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (ism == null) {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("网络异常，稍后再试!");
			out.flush();
			out.close();
		} else {
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			if (BaseUtil.isEmpty(fileName)) {
				fileName = DateUtil.fomatDate(new Date(), "yyyyMMddHHmmss");
			}
			// response.setContentType(conType+";charset=utf-8");
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".pdf");
			// response.setHeader("Content-Length", String.valueOf(fileLength));
			try {
				bis = new BufferedInputStream(ism);
				bos = new BufferedOutputStream(response.getOutputStream());
				byte[] buff = new byte[2048];
				int bytesRead;
				while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
					bos.write(buff, 0, bytesRead);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (bis != null)
					bis.close();
				if (bos != null)
					bos.close();
			}
		}

	}
}
