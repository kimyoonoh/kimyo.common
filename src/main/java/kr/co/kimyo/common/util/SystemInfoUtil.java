package kr.triniti.common.util;

import java.io.File;
import java.lang.management.ManagementFactory;

import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

import javax.swing.filechooser.FileSystemView;

import kr.triniti.common.model.vo.AbstractVO;

public class SystemInfoUtil {

    @SuppressWarnings("serial")
	public static class DiskInfo extends AbstractVO {
    	private String diskName;
        private long diskTotalSpace;
        private long diskFreeSpace;
        private long diskUsableSpace;
        
        public String getDiskName() {
			return diskName;
		}
		public void setDiskName(String diskName) {
			this.diskName = diskName;
		}
		public long getDiskTotalSpace() {
            return diskTotalSpace;
        }
        public void setDiskTotalSpace(long diskTotalSpace) {
            this.diskTotalSpace = diskTotalSpace;
        }
        public long getDiskFreeSpace() {
            return diskFreeSpace;
        }
        public void setDiskFreeSpace(long diskFreeSpace) {
            this.diskFreeSpace = diskFreeSpace;
        }
        public long getDiskUsableSpace() {
            return diskUsableSpace;
        }
        public void setDiskUsableSpace(long diskUsableSpace) {
            this.diskUsableSpace = diskUsableSpace;
        }
    }
    
    @SuppressWarnings("serial")
	public static class MemoryInfo extends AbstractVO {
        MemoryUsage heap;
        MemoryUsage noHeap;

        public MemoryUsage getHeap() {
            return heap;
        }
        public void setHeap(MemoryUsage heap) {
            this.heap = heap;
        }
        public MemoryUsage getNoHeap() {
            return noHeap;
        }
        public void setNoHeap(MemoryUsage noHeap) {
            this.noHeap = noHeap;
        }
    }
    
    @SuppressWarnings("serial")
	public static class CurrentThreadInfo extends AbstractVO {
    	long   id;
    	String name;
    	String status;
    	
        long cpuTime;
        long userTime;
        
        int count;
        int peakCount;
        long StartedCount;
        
        boolean isNative;
        boolean isSuspened;
        
        long blockedCount;
        long blockedTime;
        
        long waitCount;
        long waitTime;
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public long getCpuTime() {
			return cpuTime;
		}
		public void setCpuTime(long cpuTime) {
			this.cpuTime = cpuTime;
		}
		public long getUserTime() {
			return userTime;
		}
		public void setUserTime(long userTime) {
			this.userTime = userTime;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
		public int getPeakCount() {
			return peakCount;
		}
		public void setPeakCount(int peakCount) {
			this.peakCount = peakCount;
		}
		public long getStartedCount() {
			return StartedCount;
		}
		public void setStartedCount(long l) {
			StartedCount = l;
		}
		public boolean isNative() {
			return isNative;
		}
		public void setNative(boolean isNative) {
			this.isNative = isNative;
		}
		public boolean isSuspened() {
			return isSuspened;
		}
		public void setSuspened(boolean isSuspened) {
			this.isSuspened = isSuspened;
		}
		public long getBlockedCount() {
			return blockedCount;
		}
		public void setBlockedCount(long l) {
			this.blockedCount = l;
		}
		public long getBlockedTime() {
			return blockedTime;
		}
		public void setBlockedTime(long blockedTime) {
			this.blockedTime = blockedTime;
		}
		public long getWaitCount() {
			return waitCount;
		}
		public void setWaitCount(long l) {
			this.waitCount = l;
		}
		public long getWaitTime() {
			return waitTime;
		}
		public void setWaitTime(long waitTime) {
			this.waitTime = waitTime;
		}
    }
    
    public static DiskInfo [] getDiskInfo() {
    	FileSystemView fsv = FileSystemView.getFileSystemView();
    	
    	File [] rootVolumns = fsv.getRoots();
    	
        DiskInfo [] disks = new DiskInfo[rootVolumns.length];
        
        for (int i = 0; i < rootVolumns.length; i++) {
        	disks[i] = new DiskInfo();
        	
        	disks[i].setDiskName(rootVolumns[i].toPath().toString());
	        disks[i].setDiskFreeSpace(rootVolumns[i].getFreeSpace());
	        disks[i].setDiskTotalSpace(rootVolumns[i].getTotalSpace());
	        disks[i].setDiskUsableSpace(rootVolumns[i].getUsableSpace());
        }
        
        return disks;
    }
    
    public static MemoryInfo getMeomoryInfo() {
        MemoryInfo memory = new MemoryInfo();
        
        MemoryMXBean membean = (MemoryMXBean) ManagementFactory.getMemoryMXBean();
        
        memory.setHeap(membean.getHeapMemoryUsage());
        memory.setNoHeap(membean.getNonHeapMemoryUsage());
        
        return memory;
    }
    
    public static CurrentThreadInfo getThreadInfo() {
    	CurrentThreadInfo ct = new CurrentThreadInfo(); 
    	
    	ThreadMXBean tbean = (ThreadMXBean) ManagementFactory.getThreadMXBean();
    	
        long id = Thread.currentThread().getId();
        
        ThreadInfo t = tbean.getThreadInfo(id);
        
        ct.setId(id);
        ct.setName(t.getThreadName());
        ct.setStatus(t.getThreadState().name());
        ct.setBlockedCount(t.getBlockedCount());
        ct.setBlockedTime(t.getBlockedTime());
        ct.setCount(tbean.getThreadCount());
        ct.setCpuTime(tbean.getCurrentThreadCpuTime());
        ct.setUserTime(tbean.getCurrentThreadCpuTime());
        ct.setPeakCount(tbean.getPeakThreadCount());
        ct.setStartedCount(tbean.getTotalStartedThreadCount());
        ct.setWaitCount(t.getWaitedCount());
        ct.setWaitTime(t.getWaitedTime());
        ct.setNative(t.isInNative());
        ct.setSuspened(t.isSuspended());
        
        return ct;
    }
}
