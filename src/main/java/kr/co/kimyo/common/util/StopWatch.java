package kr.triniti.common.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import kr.triniti.common.model.vo.AbstractVO;
import kr.triniti.common.util.SystemInfoUtil.CurrentThreadInfo;
import kr.triniti.common.util.SystemInfoUtil.DiskInfo;
import kr.triniti.common.util.SystemInfoUtil.MemoryInfo;

public class StopWatch {
	Calendar c = Calendar.getInstance();
	
	public class Record {
		long   time;
		long   totalDuration;
		long   duration;
		String message;
		
		StackTraceElement codePoistion;
		
		MemoryInfo         memory;
		DiskInfo  []       disks;
		CurrentThreadInfo  thread;
		
		Map<String, String> dump = new HashMap<String, String>();
		
		public Record(long preTime, String message) {
			this.time     = System.currentTimeMillis();
			this.totalDuration = this.time - startTime; 
			this.duration = this.time - preTime;
			this.message  = name + "-" + message;
			
			this.codePoistion = Thread.currentThread().getStackTrace()[3];
			
			if (collectSysInfo) {
				this.memory = SystemInfoUtil.getMeomoryInfo();
				this.disks  = SystemInfoUtil.getDiskInfo();
				this.thread = SystemInfoUtil.getThreadInfo();
			}
			
			if (useDump) {
				this.dump = new HashMap<String, String>();
			}
		}
		
		public Record dump(String name, Object obj) {
			this.dump.put(name, (obj == null ? "null" : obj.toString()));
			
			return this;
		}

		public long getTime() {
			return time;
		}

		public long getDuration() {
			return duration;
		}

		public String getMessage() {
			return message;
		}
		
		public StackTraceElement getCodePoistion() {
			return this.codePoistion;
		}
		
		public String toString() {
			return toString(false, false);
		}
		
		public String toString(boolean callingPosition, boolean datetime) {
			StringBuffer buff = new StringBuffer();
			
			buff.append(String.format(durationFormat, this.duration, this.totalDuration))
				.append(datetime ? StringUtil.getTimestampToDate(this.time,StringUtil.NORMAL_DATETIMEMIL_FORMAT) : "")
				.append(" ").append(message).append("\n")
				.append(
					callingPosition ? String.format("Check Position : %s.%s(%4d)\n"
						, this.codePoistion.getClassName()
						, this.codePoistion.getMethodName()
						, this.codePoistion.getLineNumber()
					) : ""
				)
				;
			
			if (collectSysInfo) {
				buff.append("===== System Information Snapshot ===== \n");
				buff.append("Memory : ").append(this.memory.toJSON()).append("\n")
					.append("Thread : ").append(this.thread.toJSON()).append("\n");
				
				buff.append("Disk   : ");
				
				for (DiskInfo disk : this.disks) {
					buff.append(disk.toJSON()).append("\n");
				}
			}
			
			if (useDump) {
				if (this.dump.size() > 0) {
					buff.append(String.format("== User Dumped Variable [%d] ==\n", this.dump.size()));
					
					for (String key : this.dump.keySet()) {
						buff.append(key).append(":").append(this.dump.get(key)).append("\n");
					}
				}
			}
			
			return buff.toString();
		}
	}
	
	String name;
	ArrayList<Record> records;
	
	long startTime;
	long endTime;
	long totalTime;
	double average;
	
	boolean collectSysInfo = false;
	boolean useDump = false;
	
	int count;
	
	AbstractVO vo;
	
	boolean isStarted = false;
	String durationFormat = "[%04d:%04d]";
	
	public StopWatch(String name, boolean collectSysInfo, boolean useDump) {
		this.records = new ArrayList<Record>();
		 
		this.name = name;
		this.collectSysInfo = collectSysInfo;
		this.useDump = useDump;
	}
	
	public StopWatch(String name) {
		this.records = new ArrayList<Record>();
		 
		this.name = name;
	}
	
	public Record start() {
		this.startTime = System.currentTimeMillis();
		
		this.isStarted = true;
		
		return addRecord(new Record(this.startTime, "START"));
	}
	
	public Record check(long time, String message) {
		return addRecord(new Record(time, message));
	}
	
	public Record check() {
		int count = getLastIndex();
		Record record = this.records.get(count);
		
		return addRecord(new Record(record.getTime(), (count + 1) + ""));
	}
	
	public Record check(String message) {
		Record record = this.records.get(getLastIndex()); 
		
		return addRecord(new Record(record.getTime(), message));
	}
	
	public int getLastIndex() {
		if (this.count == 0) return 0;
		return this.count - 1;
	}
	
	public Record getRecord(String message) {
		for (int i = 0; i < this.records.size(); i++) {
			if (this.records.get(i).getMessage().equals(message)) return this.getRecord(i);
		}
		
		return new Record(0, "");
	}
	
	public Record getRecord(int index) {
		return this.records.get(index);
	}
	
	public Record addRecord(long time, String message) {
		return addRecord(new Record(time, message));
	}
	
	public Record addRecord(Record record) {
		if (!this.isStarted) return null;
		
		this.records.add(record);
		this.count = this.records.size();
		
		return record;
	}
	
	public void reset() {
		this.startTime = -1;
		this.records.clear();
	}
	
	public void stop() {
		this.endTime   = System.currentTimeMillis();
		this.totalTime = this.endTime - this.startTime;
		this.average   = (double) this.totalTime / this.records.size();
		
		addRecord(new Record(this.startTime, "END"));
		
		this.isStarted = false;
	}

	public String getName() {
		return name;
	}

	public long getStartTime() {
		return startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public long getTotalTime() {
		return totalTime;
	}

	public double getAverage() {
		return average;
	}
	
	public int count() {
		return this.count;
	}
	
	public boolean isStarted() {
		return this.isStarted;
	}
	
	public AbstractVO getObject() {
		return this.vo;
	}
	
	public void setObject(AbstractVO vo) {
		this.vo = vo;
	}
	
	public String report() {
		return report(false, false);
	}
	
	public String report(boolean callingPosition, boolean datetime) {
		StringBuffer buff = new StringBuffer();
		
		if (vo != null) buff.append(vo.toJSON()).append("\n");
		
		this.durationFormat = makeDurationFormat();
			
		for (Record r : this.records) {
			buff.append(r.toString(callingPosition, datetime));
		}
			
		buff.append("경과 시간 ").append(this.getTotalTime()).append("mils\n")
			.append("평균 시간 ").append(this.getAverage()).append("mils\n");
		
		return buff.toString();
	}
	
	private String makeDurationFormat() {
		int length = String.format("%d", totalTime).length(); 
		
		return "[%" + length + "d:%" + length + "d] ";
	}
} 