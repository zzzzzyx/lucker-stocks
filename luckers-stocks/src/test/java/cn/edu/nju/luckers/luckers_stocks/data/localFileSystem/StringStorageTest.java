package cn.edu.nju.luckers.luckers_stocks.data.localFileSystem;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.*;

import cn.edu.nju.luckers.luckers_stocks.data.localFileSystem.impl.StringStorageManager;
import cn.edu.nju.luckers.luckers_stocks.data.localFileSystem.service.StringStorageService;

public class StringStorageTest {

	StringStorageService storageService;
	final String testPath = "local/test.sb";
	
	@Before
	public void initial() throws IOException{
		storageService = new StringStorageManager(testPath);
	}
	
	@Test
	public void test() throws IOException{
		ArrayList<String> expectedContent = new ArrayList<>();
		storageService.deleteAllAndWrite(new ArrayList<>());
		
		storageService.addString("haha haha1");
		expectedContent.add("haha haha1");
		assertEquals(expectedContent, storageService.getContent());
		
		storageService.addString("haha hagg6");
		expectedContent.add("haha hagg6");
		assertEquals(expectedContent, storageService.getContent());
		
		storageService.addString("ha44 haha1");
		expectedContent.add("ha44 haha1");
		assertEquals(expectedContent, storageService.getContent());
		
		storageService.deleteString("haha hagg6");
		expectedContent.remove("haha hagg6");
		assertEquals(expectedContent, storageService.getContent());
		
		storageService.addString("hk");
		expectedContent.add("hk");
		assertEquals(expectedContent, storageService.getContent());
		
		storageService.deleteString("haha haha1");
		expectedContent.remove("haha haha1");
		assertEquals(expectedContent, storageService.getContent());
		
		ArrayList<String> a = new ArrayList<String>();
		a.add("好的666");
		storageService.deleteAllAndWrite(a);
		expectedContent = new ArrayList<String>();
		expectedContent.add("好的666");
		assertEquals(expectedContent, storageService.getContent());
		
		storageService.deleteString("好的666");
		expectedContent.remove("好的666");
		assertEquals(expectedContent, storageService.getContent());
		
		storageService.addString("1099099");
		expectedContent.add("1099099");
		assertEquals(expectedContent, storageService.getContent());
		
		storageService.deleteString("1099099");
		expectedContent.remove("1099099");
		assertEquals(expectedContent, storageService.getContent());
	}
}
