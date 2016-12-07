package common;

import java.util.ArrayList;

import com.hazelcast.cluster.ClusterState;
import com.hazelcast.config.Config;
import com.hazelcast.config.InMemoryFormat;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.QueueConfig;
import com.hazelcast.config.QuorumConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.IQueue;
import com.hazelcast.memory.MemorySize;
import com.hazelcast.memory.MemoryUnit;
import com.hazelcast.quorum.QuorumType;

public class Member{
	
	public static final ArrayList<HazelcastInstance> clusterInstanceList = new ArrayList<HazelcastInstance>();
	public static int quorumCount = 0;;
	public static IMap<String, String> map = null;
	public static IQueue<String> queue = null;
	
	private static HazelcastInstance startInstance(){
		Config config = new Config();
        config.getGroupConfig().setName("hz-cluster");
        config.getGroupConfig().setPassword("password");
        config.setLicenseKey("ENTERPRISE_HD#32Nodes#AK7ValIbHF6OUNyr10JSkETmfjw5u1900100102912700160319101919110");
        config.getManagementCenterConfig().setEnabled(true).setUrl("http://localhost:8080/mancenter");
        config.getNativeMemoryConfig().setEnabled(true).setSize(new MemorySize(10, MemoryUnit.MEGABYTES));
        config.setProperty("hazelcast.jmx", "true");

        QuorumConfig quorumConfig = new QuorumConfig();
        quorumConfig.setName("my-quorum");
        quorumConfig.setEnabled(true);
        quorumConfig.setSize(quorumCount);
        quorumConfig.setType(QuorumType.READ_WRITE);
        config.addQuorumConfig(quorumConfig);
        
        MapConfig hdmapConfig = new MapConfig();
        hdmapConfig.setName("myMap").setBackupCount(1).setInMemoryFormat(InMemoryFormat.OBJECT);
        hdmapConfig.setQuorumName("my-quorum");
        
        QueueConfig queueConfig = new QueueConfig();
        queueConfig.setName("myQueue").setBackupCount(1).setMaxSize(0).setQuorumName("my-quorum");

        
        config.addMapConfig(hdmapConfig);
        config.addQueueConfig(queueConfig);
        
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);
        
        map = instance.getMap("myMap");
        queue = instance.getQueue("myQueue");
        return instance;
	}
	
	public static void createAndStartCluster(int memberCount, int quorumCount){
		System.out.println("************** Starting Cluster with Member Count : " + memberCount + ", Quorum Count : " + quorumCount + " **************");
		Member.quorumCount = quorumCount;
		clusterInstanceList.clear();
		
		for (int i = 0; i < memberCount; i++) {
			clusterInstanceList.add(startInstance());
		}
		
	//	while(!(clusterInstanceList.get(memberCount-1).getCluster().getClusterState().compareTo(ClusterState.ACTIVE) == 0)){
//			System.out.println("Waiting to get cluster ACTIVE");
//			try {
//				Thread.sleep(5000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}	
		//}

		
		System.out.println("************** Cluster Started **************");
	}
	
	public static void stopAndDestroyCluster(){
		for (HazelcastInstance instance : clusterInstanceList) {
			System.out.println("Stopping Instance : " + instance.getName());
			instance.shutdown();
			System.out.println("************** Cluster STOPPED **************");

		}
	}
	
    public static void main(String[] args) throws InterruptedException {
    	Member.createAndStartCluster(2, 2);
    }
}
