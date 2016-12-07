package tests;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import common.Member;

public class QueueTest {

	public static final int memberCount = 2;
	public static final int quorumCount = 2;


//	@BeforeMethod
//	public void setup() {
//		Member.createAndStartCluster(memberCount, quorumCount);
//	}
	
	@Test
	public void testAddAll() {
		Member.createAndStartCluster(2, 2);

		ArrayList<String> elements = new ArrayList<String>();
		for(int i=1;i<=5;i++){
			elements.add("K"+i);
		}
		Assert.assertEquals(Member.queue.addAll(elements), true);
		Member.stopAndDestroyCluster();

	}

	@Test
	public void testRemoveAll() {
		Member.createAndStartCluster(2, 2);

		ArrayList<String> elements = new ArrayList<String>();
		for(int i=1;i<=5;i++){
			elements.add("K"+i);
		}
		Assert.assertEquals(Member.queue.removeAll(elements), true);
		Member.stopAndDestroyCluster();

	}
	
	@Test
	public void testRetainAll() {
		Member.createAndStartCluster(2, 2);

		ArrayList<String> elements = new ArrayList<String>();
		for(int i=1;i<=5;i++){
			elements.add("K"+i);
		}
		Member.queue.add("K6");
		Assert.assertEquals(Member.queue.retainAll(elements), true);
		Member.stopAndDestroyCluster();

	}
	
	@Test
	public void testPeek() {
		Member.createAndStartCluster(2, 2);

		Member.queue.add("K1");
		Assert.assertEquals(Member.queue.peek(), "K1");
		Member.stopAndDestroyCluster();

	}
	
	@Test
	public void testRemove() {
		Member.createAndStartCluster(2, 2);

		Member.queue.add("K1");
		Assert.assertEquals(Member.queue.remove(), "K1");
		Member.stopAndDestroyCluster();

	}
	
	@Test
	public void testPoll() {
		Member.createAndStartCluster(2, 2);
		Member.queue.add("K1");
		Assert.assertEquals(Member.queue.poll(), "K1");
		Member.stopAndDestroyCluster();
	}
	
//	@BeforeMethod
//	public void teardown(){
//		Member.stopAndDestroyCluster();
//	}

	public static void main(String[] args) {
		
//		new QueueTest().setup();
		//new QueueTest().testPeek();
	}

}
