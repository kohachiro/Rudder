package net.combatserver.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;

/**
 * @author kohachiro
 *
 */
public class SystemInfo {
	public int threads;
	public int cores;
	public String system;
	public String maxMemory;
	public String freeMemory;
	public String totalMemory;
	public String systemTime;
	public String runningTime;
	public String cpuRate;
	private static final long startTime = System.currentTimeMillis();		
    private static final int CPUTIME = 30;
    private static final int PERCENT = 100;
    private static final int FAULTLENGTH = 10;
    private static String linuxVersion = null;
    
    
    public SystemInfo(){
    	setCores();
    	setSystem();
    	setMaxMemory();

	}
    public void refresh(){
    	setFreeMemory();
    	setTotalMemory();
    	setThreads();
    	setSystemTime();
    	setRunnerTime();
    	setCpuRate();
    }
	public void setCores() {
		this.cores = Runtime.getRuntime().availableProcessors();
	}
	public void setSystem() {
		this.system = System.getProperty("os.name");
	}
	public void setMaxMemory() {
		this.maxMemory = Runtime.getRuntime().maxMemory()/1048576+"M";
	}
	public void setFreeMemory() {
		this.freeMemory = Runtime.getRuntime().freeMemory()/1048576+"M";
	}
	public void setTotalMemory() {
		this.totalMemory =  Runtime.getRuntime().totalMemory()/1048576+"M";
	}
	public void setThreads() {
		ThreadGroup parentThread;   
		for (parentThread = Thread.currentThread().getThreadGroup();parentThread.getParent() != null; parentThread = parentThread.getParent());    		
		this.threads =  parentThread.activeCount();
	}
	public void setSystemTime() {
		Date currentTime = new Date(System.currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
		this.systemTime = formatter.format(currentTime);
	}
	public void setRunnerTime() {
		long time=(System.currentTimeMillis()-startTime)/1000;
		long d=time/(3600*24);
		long h=(time%(3600*24))/(3600);
		String sh=String.valueOf(h);
		if (h<10)
			sh="0"+h;
		long m=(time%(3600))/(60);
		String sm=String.valueOf(m);
		if (m<10)
			sm="0"+m;		
		long s=(time%(60));
		String ss=String.valueOf(s);
		if (s<10)
			ss="0"+s;
		
		this.runningTime = d+"days "+sh+":"+sm+":"+ss;
	}
	public void setCpuRate() {
        if (System.getProperty("os.name").toLowerCase().startsWith("windows")) 
        	this.cpuRate = getCpuRatioForWindows()+"%";
        else 
        	this.cpuRate = getCpuRateForLinux()+"%";
	}
	private  double getCpuRateForLinux(){
	    File versionFile = new File("/proc/version");
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader brStat = null;
        StringTokenizer tokenStat = null;
	    try {
			FileInputStream fis = new FileInputStream(versionFile);
			brStat=new BufferedReader(new InputStreamReader(fis));
			linuxVersion=brStat.readLine();

            System.out.println("Get usage rate of CUP , linux version: "+linuxVersion);

            Process process = Runtime.getRuntime().exec("top -b -n 1");
            is = process.getInputStream();                    
            isr = new InputStreamReader(is);
            brStat = new BufferedReader(isr);
            
            if(linuxVersion.equals("2.4")){
                brStat.readLine();
                brStat.readLine();
                brStat.readLine();
                brStat.readLine();
                
                tokenStat = new StringTokenizer(brStat.readLine());
                tokenStat.nextToken();
                tokenStat.nextToken();
                String user = tokenStat.nextToken();
                tokenStat.nextToken();
                String system = tokenStat.nextToken();
                tokenStat.nextToken();
                String nice = tokenStat.nextToken();
                
                System.out.println(user+" , "+system+" , "+nice);
                
                user = user.substring(0,user.indexOf("%"));
                system = system.substring(0,system.indexOf("%"));
                nice = nice.substring(0,nice.indexOf("%"));
                
                float userUsage = new Float(user).floatValue();
                float systemUsage = new Float(system).floatValue();
                float niceUsage = new Float(nice).floatValue();
                
                return (userUsage+systemUsage+niceUsage)/100;
            }else{
                brStat.readLine();
                brStat.readLine();
                    
                tokenStat = new StringTokenizer(brStat.readLine());
                tokenStat.nextToken();
                tokenStat.nextToken();
                tokenStat.nextToken();
                tokenStat.nextToken();
                tokenStat.nextToken();
                tokenStat.nextToken();
                tokenStat.nextToken();
                String cpuUsage = tokenStat.nextToken();
                    
                
                System.out.println("CPU idle : "+cpuUsage);
                Float usage = new Float(cpuUsage.substring(0,cpuUsage.indexOf("%")));
                
                return (1-usage.floatValue()/100);
            }

             
        } catch(Exception ioe){
            System.out.println(ioe.getMessage());
            freeResource(is, isr, brStat);
            return 1;
        } finally{
            freeResource(is, isr, brStat);
        }

    }
    private static void freeResource(InputStream is, InputStreamReader isr, BufferedReader br){
        try{
            if(is!=null)
                is.close();
            if(isr!=null)
                isr.close();
            if(br!=null)
                br.close();
        }catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }
    }

    /** 
     * 获得CPU使用率.
     * @return 返回cpu使用率
     */
    private double getCpuRatioForWindows() {
        try {
            String procCmd = System.getenv("windir")
                    + "//system32//wbem//wmic.exe process get Caption,CommandLine,"
                    + "KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
            // 取进程信息
            long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));
            Thread.sleep(CPUTIME);
            long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));
            if (c0 != null && c1 != null) {
                long idletime = c1[0] - c0[0];
                long busytime = c1[1] - c0[1];
                return Double.valueOf(
                        PERCENT * (busytime) / (busytime + idletime))
                        .doubleValue();
            } else {
                return 0.0;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0.0;
        }
    }

    /**      
     * 读取CPU信息.
     * @param proc
     * @return
     */
    private long[] readCpu(final Process proc) {
        long[] retn = new long[2];
        try {
            proc.getOutputStream().close();
            InputStreamReader ir = new InputStreamReader(proc.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line = input.readLine();
            if (line == null || line.length() < FAULTLENGTH) {
                return null;
            }
            int capidx = line.indexOf("Caption");
            int cmdidx = line.indexOf("CommandLine");
            int rocidx = line.indexOf("ReadOperationCount");
            int umtidx = line.indexOf("UserModeTime");
            int kmtidx = line.indexOf("KernelModeTime");
            int wocidx = line.indexOf("WriteOperationCount");
            long idletime = 0;
            long kneltime = 0;
            long usertime = 0;
            while ((line = input.readLine()) != null) {
                if (line.length() < wocidx) {
                    continue;
                }
                // 字段出现顺序：Caption,CommandLine,KernelModeTime,ReadOperationCount,
                // ThreadCount,UserModeTime,WriteOperation
                String caption = substring(line, capidx, cmdidx - 1)
                        .trim();
                String cmd = substring(line, cmdidx, kmtidx - 1).trim();
                if (cmd.indexOf("wmic.exe") >= 0) {
                    continue;
                }
                // log.info("line="+line);
                if (caption.equals("System Idle Process")
                        || caption.equals("System")) {
                    idletime += Long.valueOf(
                            substring(line, kmtidx, rocidx - 1).trim())
                            .longValue();
                    idletime += Long.valueOf(
                            substring(line, umtidx, wocidx - 1).trim())
                            .longValue();
                    continue;
                }

                kneltime += Long.valueOf(
                        substring(line, kmtidx, rocidx - 1).trim())
                        .longValue();
                usertime += Long.valueOf(
                        substring(line, umtidx, wocidx - 1).trim())
                        .longValue();
            }
            retn[0] = idletime;
            retn[1] = kneltime + usertime;
            return retn;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                proc.getInputStream().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    /**
     * 由于String.subString对汉字处理存在问题(把一个汉字视为一个字节)，因此在 包含汉字的字符串时存在隐患，现调整如下：
     * 
     * @param src
     *            要截取的字符串
     * @param start_idx
     *            开始坐标(包括该坐标)
     * @param end_idx
     *            截止坐标(包括该坐标)
     * @return
     */
    private static String substring(String src, int start_idx, int end_idx) {
     byte[] b = src.getBytes();
     String tgt = "";
     for (int i = start_idx; i <= end_idx; i++) {
      tgt += (char) b[i];
     }
     return tgt;
    }
}
