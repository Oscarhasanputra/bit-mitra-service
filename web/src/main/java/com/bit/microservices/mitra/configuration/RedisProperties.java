package com.bit.microservices.mitra.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "spring.redis")
@RefreshScope
public class RedisProperties {
    
    private String host;
    private String password;
    private int port;
    private long defaultExpire;
    private Map<String, Long> cacheExpirations = new HashMap<>();
    
    private Pool pool;
    private final Cluster cluster= new Cluster();
    
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public long getDefaultExpire() {
		return defaultExpire;
	}
	public void setDefaultExpire(long defaultExpire) {
		this.defaultExpire = defaultExpire;
	}
	public Map<String, Long> getCacheExpirations() {
		return cacheExpirations;
	}
	public void setCacheExpirations(Map<String, Long> cacheExpirations) {
		this.cacheExpirations = cacheExpirations;
	}
    
	public Pool getPool() {
		return pool;
	}

	public void setPool(Pool pool) {
		this.pool = pool;
	}
	
	public Cluster getCluster() {
		return cluster;
	}
	
    public static class Cluster {
    	
    	private List<String> nodes = new ArrayList<>();
    	
    	public List<String> getNodes() {
    	    return nodes;
    	}

    	public void setNodes(List<String> nodes) {
    	    this.nodes = nodes;
    	}
    }
    
    public static class Pool {
    	
    	private int maxIdle;
    	private int minIdle;
    	private int maxActive;
    	private int minActive;
    	
    	public int getMaxIdle() {
			return maxIdle;
		}
		public void setMaxIdle(int maxIdle) {
			this.maxIdle = maxIdle;
		}
		public int getMinIdle() {
			return minIdle;
		}
		public void setMinIdle(int minIdle) {
			this.minIdle = minIdle;
		}
		public int getMaxActive() {
			return maxActive;
		}
		public void setMaxActive(int maxActive) {
			this.maxActive = maxActive;
		}
		public int getMinActive() {
			return minActive;
		}
		public void setMinActive(int minActive) {
			this.minActive = minActive;
		}
		
    }
    
}
