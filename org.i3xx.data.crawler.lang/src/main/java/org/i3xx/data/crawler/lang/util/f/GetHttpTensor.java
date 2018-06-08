package org.i3xx.data.crawler.lang.util.f;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.i3xx.data.crawler.lang.core.Function;
import org.i3xx.data.crawler.lang.core.LeafNode;
import org.i3xx.data.crawler.lang.core.LeafNodeImpl;
import org.i3xx.data.crawler.lang.core.ListNode;
import org.i3xx.data.crawler.lang.core.ListNodeImpl;
import org.i3xx.data.crawler.lang.core.Node;
import org.i3xx.data.crawler.lang.core.Node.Type;
import org.i3xx.data.crawler.lang.util.NodeTools;

public class GetHttpTensor implements Function {
	
	public static final String NAME = "gethttp";
	
	@Override
	public Function getInstance() {
		return new GetHttpTensor();
	}
	
	@Override
	public Node exec(Node node, boolean fix) throws IOException, URISyntaxException {
		
		LeafNode param = (LeafNode)node;
		List<Node> ls = new ArrayList<Node>();
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		URI uri = new URI(param.getValue());
		HttpGet httpget = new HttpGet(uri);
		CloseableHttpResponse response = httpclient.execute(httpget);
		try {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				ls.add( new LeafNodeImpl(Type.NODE, "size", entity.getContentLength()) );
				ls.add( new LeafNodeImpl(Type.NODE, "content", EntityUtils.toString(entity)) );
			}
			ls.add( new LeafNodeImpl(Type.NODE, "status-code", response.getStatusLine().getStatusCode()) );
		}finally {
			response.close();
			httpget.completed();
		}
		ListNode result = new ListNodeImpl(Type.NODE, "result", ls);
		
		ListNode temp = NodeTools.newListNode(Type.NODE, fix?NAME:node.getName(), result);
		return temp;
	}

}
