package com.anton.project.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TitleExtractor {
	private final static Pattern TITLE = Pattern.compile("\\<title>(.*)\\</title>");
	
	public static String getPageTitle(String targetUrl) throws IOException {
		URL url = new URL(targetUrl);
		URLConnection conn = url.openConnection();
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");
		

		ContentType type = ContentType.getContentType(conn);
		if (!type.contentType.equals("text/html")) {
			return "";
		}
		else {
			Charset charset = getCharset(type);
			if (charset == null) {
				charset = Charset.defaultCharset();
			}
			
			InputStream in = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
			
			int n = 0;
			char[] buf = new char[1024];
			StringBuilder content = new StringBuilder();
			
			while ((n = reader.read(buf, 0, buf.length)) != -1) {
				content.append(buf, 0, n);
				
				if (content.toString().contains("</head>")) {
					Matcher matcher = TITLE.matcher(content);
					
					if (matcher.find()) {
						return matcher.group(1).replaceAll("[\\s\\<>]+", " ").trim();
					}
					
					else {
						break;
					}
				}
			}
			
			reader.close();
		}
		
		return "";
	}
	
	private static Charset getCharset(ContentType contentType) {
        if (contentType != null && contentType.charset != null && !contentType.charset.isEmpty() && Charset.isSupported(contentType.charset))
            return Charset.forName(contentType.charset);
        else
            return null;
    }	
	
	private static final class ContentType {
		private String contentType;
		private String charset;
		
		private ContentType(String contentType, String charset) {
			this.contentType = contentType;
			this.charset = charset;
		}
		
		private static ContentType getContentType(URLConnection conn) {
			String contentType = "";
			String charset = "";
			
			String content = conn.getHeaderField("Content-Type");
			
			String[] data = content.split(";");
			if (data.length == 2) {
				contentType = data[0].trim();
				charset = data[1].trim();
			}
			else {
				contentType = data[0].trim();
			}
			
			return new ContentType(contentType, charset);
		}
		
		
	}
}
