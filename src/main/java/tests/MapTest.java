package tests;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import common.Member;

public class MapTest {

	public static final int memberCount = 2;
	public static final int quorumCount = 2;


	@BeforeTest
	public void init() {
		Member.createAndStartCluster(memberCount, quorumCount);
	}

	@Test
	public void testQuorumMap() {
		Member.map.put("K1", "V1");
		Assert.assertEquals(Member.map.get("K1"), "V1");
	}
	
	@AfterTest
	public void teardown(){
		Member.stopAndDestroyCluster();
	}

	public static void main(String[] args) {
		
		new MapTest().init();
		new MapTest().testQuorumMap();
	}

}
