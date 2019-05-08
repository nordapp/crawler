package org.i3xx.lemonade.lang.ext.util.f;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.i3xx.lemonade.lang.core.Function;
import org.i3xx.lemonade.lang.core.LeafNode;
import org.i3xx.lemonade.lang.core.LeafNodeImpl;
import org.i3xx.lemonade.lang.core.ListNode;
import org.i3xx.lemonade.lang.core.ListNodeImpl;
import org.i3xx.lemonade.lang.core.Node;
import org.i3xx.lemonade.lang.core.Node.Type;
import org.i3xx.lemonade.lang.util.NodeTools;

/**
 * Simple to use http GET
 * 
 * @author green
 *
 */
public class GetHttpTensor implements Function {
	
	public static final String NAME = "gethttp";
	
	@Override
	public Function getInstance() {
		return new GetHttpTensor();
	}
	
	@Override
	public Node exec(Node node) throws Exception {
		
		LeafNode param = (LeafNode)node;
		List<Node> ls = new ArrayList<Node>();
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
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
		}finally {
			httpclient.close();
		}
		ListNode result = new ListNodeImpl(Type.NODE, "result", ls);
		
		ListNode temp = NodeTools.newListNode(Type.NODE, Node.UNKNOWN, result);
		return temp;
	}

}
