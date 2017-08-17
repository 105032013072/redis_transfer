package redis_transfer;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.bosssoft.platform.redis.transfer.service.RedisOperation;

public class transferTest {

	@Test
	public void testExport() {
		RedisOperation operation=new RedisOperation("127.0.0.1", 6379);
		String savefile="d:\\test\\data.xml";
		try {
			operation.export(savefile, "gateway:file:");
			//operation.export(savefile, "name");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testImport() {
		RedisOperation operation=new RedisOperation("127.0.0.1", 6378);
		String savefile="d:\\test\\data.xml";
		try {
			operation.importData(savefile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
