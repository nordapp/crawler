package org.i3xx.lemonade.lang.ext.util.f;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.i3xx.lemonade.lang.core.Function;
import org.i3xx.lemonade.lang.core.FunctionVars;
import org.i3xx.lemonade.lang.core.LeafNode;
import org.i3xx.lemonade.lang.core.LeafNodeImpl;
import org.i3xx.lemonade.lang.core.Node;
import org.i3xx.lemonade.lang.core.Node.Type;
import org.i3xx.lemonade.lang.util.NodeTools;

/**
 * Reads one property file from git and insert the properties to the variables map.
 * 
 * @author green
 *
 */
public class GitHubProperties extends FunctionVars {
	
	public static final String NAME = "gitprops";
	
	@Override
	public Function getInstance() {
		return new GitHubProperties();
	}
	
	@Override
	public Node exec(Node node,  Map<String, Node> variables) throws IOException, URISyntaxException {
		
		LeafNode param = (LeafNode)node;
		List<Node> ls = new ArrayList<Node>();
		
		String properties = null;
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		URI uri = new URI(param.getValue());
		HttpGet httpget = new HttpGet(uri);
		CloseableHttpResponse response = httpclient.execute(httpget);
		try {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				
				properties = EntityUtils.toString(entity);
				
				ls.add( new LeafNodeImpl(Type.NODE, "size", entity.getContentLength()) );
				ls.add( new LeafNodeImpl(Type.NODE, "content", properties) );
			}
			ls.add( new LeafNodeImpl(Type.NODE, "status-code", response.getStatusLine().getStatusCode()) );
		}finally {
			response.close();
			httpget.completed();
		}
		
		if(properties!=null) {
			Properties props = new Properties();
			props.load(new StringReader(properties));
			
			for(String k : props.stringPropertyNames()) {
				variables.put(k, new LeafNodeImpl(Type.NODE, k, props.getProperty(k)));
			}//for
		}//fi
		
		//The variables are set above in the code. Do not set any node twice.
		return NodeTools.createUnknownLeaf(properties==null?"":properties);
	}

}
