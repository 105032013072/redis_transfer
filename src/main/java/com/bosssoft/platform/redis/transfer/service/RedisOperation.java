package com.bosssoft.platform.redis.transfer.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import static redis.clients.jedis.ScanParams.SCAN_POINTER_START;

import com.bosssoft.platform.common.redis.client.JedisConnection;
import com.bosssoft.platform.redis.transfer.uil.Constant;
import com.bosssoft.platform.redis.transfer.uil.DataFile;
import com.bosssoft.platform.redis.transfer.uil.RedisUtil;

import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

public class RedisOperation {
	private String hostName;
	
	 private int port;
	
	 private RedisUtil redisUtil;
	 
	 public RedisOperation(String hostName,int port){
		 redisUtil=new RedisUtil(hostName, port);
	 }
	 
	 public void export(String savefile,String containerKey) throws IOException{
		 File f=new File(savefile);
		 if(f.exists()) f.delete();
		 
		 //若containerKey不是key，获取其下的所有key
		 if(containerKey.endsWith(":")){
		     Set<String> allKeys=getContainerAllKeys(containerKey);
		     
		     Iterator<String> it=allKeys.iterator();
		     while(it.hasNext()){
		    	 exportOneKey(savefile,it.next());
		     }
		
		 }else{
			 exportOneKey(savefile, containerKey);
		 }
	 }
	 
	 
	 public void importData(String file) throws Exception{
		 int maxid = Integer.valueOf(DataFile.readMaxId(file, Constant.MAXID));
			for(int i = 0 ; i < maxid; i++) {
				String key = DataFile.read(file, Constant.KEY + i);
				String value = DataFile.read(file, Constant.VALUE + i);
				int ttl=0;
				redisUtil.restore(key, ttl, value.getBytes(Constant.CODEC));
			}
	 }
	 
	 
	 private void exportOneKey(String savefile, String key) throws IOException {
		 byte[] value=redisUtil.dump(key);
		 //获取key的序号
		 String id = DataFile.readMaxId(savefile, Constant.MAXID);
		 
		 DataFile.write(savefile, Constant.KEY+id, key);
		 DataFile.write(savefile, Constant.VALUE+id, new String(value,Constant.CODEC));
			
		 //记录key的序号
		  int maxid = Integer.parseInt(id) + 1;
		  DataFile.write(savefile, Constant.MAXID, String.valueOf(maxid));
		
	}

	private Set<String> getContainerAllKeys(String containerKey) {
		Set<String> nodekeys=new HashSet<String>();
		
		ScanParams params=new ScanParams();
		params.match(containerKey+"*");
		ScanResult<String> result;
		String cursor=SCAN_POINTER_START;
		do{
			result=redisUtil.sacn(cursor, params);
			nodekeys.addAll(result.getResult());
			cursor=result.getStringCursor();
		}while(!result.getStringCursor().equals(SCAN_POINTER_START));
		
	    return nodekeys;
	 }

	 
	 
	 
}
