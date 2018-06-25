package org.i3xx.lemonade.lang.util.l;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;

public class URLTool {

	/**
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static final String readText(URL url) throws IOException {
		
		InputStreamReader in = new InputStreamReader(url.openStream());
		StringWriter out = new StringWriter();
		
		char[] cbuf = new char[256];
		int c = 0;
		
		try {
			while((c=in.read(cbuf))>-1) {
				out.write(cbuf, 0, c);
			}
		}finally {
			in.close();
			out.close();
		}
		
		return out.toString();
	}
}
