package kr.triniti.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.GZIPOutputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.commons.io.IOUtils;

import kr.triniti.common.exception.StackTraceLog;
import kr.triniti.common.util.filter.RegxFilter;

public class FileUtil {
    public static File getFile(String fileName) {
        return new File(fileName);
    }

    public static byte[] read(String fileName) {
        return read(new File(fileName));
    }
    
    public static byte[] read(File f) {
        try {
			return read(new FileInputStream(f));
		} catch (FileNotFoundException e) {
			StackTraceLog.error(e);
		}
        
        return (new byte[0]);
    }

    public static byte [] read(InputStream is) {
        try {
            return IOUtils.toByteArray(is);
        } catch (IOException e) {
        	e.printStackTrace();
			// StackTraceLog.error(e);
        }
        return null;
    }
    
    public static String readText(String fileName) {
    	return readText(new File(fileName));
    }
    
    public static String readText(File f) {
    	try {
			return read(new FileInputStream(f), "UTF-8").toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			//StackTraceLog.error(e);
		}
    	
    	return null;
    }
    
    public static StringBuffer readTextBuffer(File f) {
    	try {
			return read(new FileInputStream(f), "UTF-8");
		} catch (FileNotFoundException e) {
			StackTraceLog.error(e);
		}
    	
    	return null;
    }

    public static StringBuffer read(FileInputStream fis, String encoding) {
        StringBuffer fileText = new StringBuffer("");
        
        long fileLimit = 1024 * 1024 * 500; // 최대 500mB까지
        
        BufferedReader in = null;
        
        try {
            in = new BufferedReader(new InputStreamReader(fis, encoding));
            
            String s = in.readLine();
            
            while (s != null) {
            	fileText.append(s);
            	
            	if (fileText.length() > fileLimit) break;
            	
            	s = in.readLine();
            	
            	if (s != null) fileText.append("\n");
            }
        } catch (IOException e) {
        	e.printStackTrace();
			//StackTraceLog.error(e);
        } finally {
        	if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					StackTraceLog.error(e);
				}
        	}
        }
        
        return fileText;
    }
    
    public static String [] readLines(String fileName) {
    	return readLines(new File(fileName));
    }
    
    public static String [] readLines(File in) {
    	try {
			return readLines(new FileInputStream(in), "utf-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			//ExceptionUtil.debug(e);
		}
    	
    	return (new String[0]);
    }
    
    public static String [] readLines(FileInputStream fis) {
    	return readLines(fis, "utf-8");
    }
    
    public static String [] readLines(FileInputStream fis, String encoding) {
    	List<String> lines = null;
		try {
			lines = IOUtils.readLines(fis, encoding);
		} catch (IOException e) {
			StackTraceLog.error(e);
		}
    			
    	return lines.toArray(new String [lines.size()]);
    }
    
    public static List<String> readLinesList(String fileName) {
    	return readLinesList(new File(fileName));
    }
    
    public static List<String> readLinesList(File in) {
    	try {
			return readLinesList(new FileInputStream(in), "utf-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			//ExceptionUtil.debug(e);
		}
    	
    	return new ArrayList<String>();
    }
    
    public static List<String> readLinesList(FileInputStream fis) {
    	return readLinesList(fis, "utf-8");
    }
    
    public static List<String> readLinesList(FileInputStream fis, String encoding) {
    	List<String> lines = null;
		try {
			lines = IOUtils.readLines(fis, encoding);
		} catch (IOException e) {
			StackTraceLog.error(e);
		}
    			
    	return lines;
    }
    
    public static boolean safeMakeDir(String path) {
        File f = new File(path);
        
        if (f.exists()) return false;
        
        return f.mkdir();
    }
    
    public static void safeMakeDeepDir(String path) {
    	String sep = File.separator.equals("\\") ? "\\\\" : File.separator;
    	
    	String [] dirs = path.split(sep); 
    	
    	StringBuffer buff = new StringBuffer();
    	for (int i = 0; i < dirs.length; i++) {
    		buff.append(dirs[i]).append(File.separator);
    		safeMakeDir(buff.toString());
    	}
    }
    
    public static boolean isExist(String file) {
    	File f = new File(file);
    	
    	return f.exists();
    }
    
    public static boolean isEmpty(String dirPath) {
    	return isEmpty(dirPath, false);
    }
    
    public static boolean isEmpty(String dirPath, boolean onlyFile) {
    	File f = new File(dirPath);
    	
    	if (!f.isDirectory()) return true;
    	
    	for (File fl : f.listFiles()) {
    		if (onlyFile) {
    			if (fl.isFile()) return false;
    		} else {
    			return false;
    		}
    	}
    	
    	return true;
    }

    public static byte[] toEuckr(String utf8) {
        byte[] result = new byte[0];
        try {
            result = utf8.getBytes("euc-kr");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean save(String filePath, String content) {
        return save(new File(filePath), content);
    }

    public static boolean save(File f, String content) {
        try {
            return save(new FileOutputStream(f), content);
        } catch (FileNotFoundException e) {
        }
        return false;
    }

    public static boolean save(FileOutputStream fos, String content) {
    	if (content == null) return false;
    	
        boolean result = true;
        try {
            OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF-8");

            writer.write(content);

            writer.close();
        } catch (IOException e) {
            result = false;
        }
        
        return result;
    }

    public static boolean save(String filePath, byte[] content) {
        return save(new File(filePath), content);
    }

    public static boolean save(File f, byte[] content) {
        try {
            return save(new FileOutputStream(f), content);
        } catch (FileNotFoundException e) {
			StackTraceLog.error(e);
        }
        return false;
    }

    public static boolean save(FileOutputStream fos, byte[] content) {
        boolean result = true;
        try {
            try {
                fos.write(content);
                
                fos.close();
            } catch (FileNotFoundException e) {
                result = false;
            }
            
            return result;
        } catch (IOException e) {
            result = false;
        }
        
        return result;
    }
    
    public static boolean saveCompress(String filePath, String content) {
    	try {
    		FileOutputStream fos = new FileOutputStream(filePath);
    		
    		GZIPOutputStream zos = new GZIPOutputStream(fos);
    		
    		InputStream is = new ByteArrayInputStream(content.getBytes());
    		
    		byte [] buff = new byte[1024];
    		int len;
    		
    		while((len = is.read(buff)) != -1) {
    			zos.write(buff, 0, len);
    		}
    		
    		zos.close();
    		fos.close();
    		is.close();
    	} catch (Exception e) {
			StackTraceLog.error(e);
    	}
    	
    	return false;
    }
    
    public static boolean compressTextFile(String filePath) {
    	return compressTextFile(filePath, String.format("%s.gz", filePath));
    }
    
    public static boolean compressTextFile(String filePath, String zipFileName) {
    	return saveCompress(zipFileName, readText(filePath));
    }
    
    public static void append(String srcFileName, String content) {
    	append(new File(srcFileName), content);
    }
    
    public static void append(File srcFileName, String content) {
    	FileWriter fw = null;
    	
    	try {
			fw = new FileWriter(srcFileName, true);
			
			fw.write(content);
		} catch (IOException e) {
			StackTraceLog.error(e);
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) { }
			}
		}
    }
    
    public static void appendFile(File srcFile, File tarFile) {
    	append(srcFile, readText(tarFile));
    }
    
    public static void appendFile(String srcFileName, String tarFileName) {
    	appendFile(new File(srcFileName), new File(tarFileName));
    }

    public static String getExt(String fileName) {
        int extPosition = fileName.lastIndexOf(".");
        
        if (extPosition == -1) {
            return "";
        }
        
        return fileName.substring(extPosition + 1);
    }

    public static boolean delete(String filePath) {
        return delete(new File(filePath));
    }

    public static boolean delete(File f) {
        return f.delete();
    }
    
    public static void move(String fileName, String targetPath) {
    	move(new File(fileName), new File(targetPath));
    }
    
    public static void move(File f, File targetDir) {
    	if (!f.exists()) return;
    	
    	if (!targetDir.exists()) return;
    	
    	f.renameTo(new File(targetDir.getPath() + File.separator + f.getName()));
    }
    
    public static String getTempFileName(String format, int length) {
    	return String.format(format, RandomUtil.getRandomStr(length));
    }
    
    public static String getTempFileName(int length) {
    	return getTempFileName("%s", length);
    }
    
    public static String [] getFileNames(String path, String pattern, boolean isCaseIgnore) {
    	RegxFilter filter = new RegxFilter(pattern, isCaseIgnore); 
    	
    	return (new File(path)).list(filter);
    }
    
    public static List<Path> getFullFileList(String dir, final RegxFilter filter) {
    	final List<Path> files = new ArrayList<Path>();
    	
    	try {
	    	Files.walkFileTree(Paths.get(dir), new SimpleFileVisitor<Path>() {
	    		@Override
	    		public FileVisitResult postVisitDirectory(Path file, IOException exp) {
	    			return FileVisitResult.CONTINUE;
	    		}
	    		
	    		@Override
	    		public FileVisitResult preVisitDirectory(Path file, BasicFileAttributes attrs) {
	    			return FileVisitResult.CONTINUE;
	    		}
	    		
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
					if (filter.accept(file.toFile(), file.toFile().getName())) {
						files.add(file);
					}
					
					return FileVisitResult.CONTINUE;
				}
				
				@Override
				public FileVisitResult visitFileFailed(Path file, IOException exp) {
					return FileVisitResult.CONTINUE;
				}
			});
    	} catch (IOException e) {
			StackTraceLog.error(e);
    	}
    	
    	return files;
    }
    
    public static String getAppRootPath() throws IOException {
    	return new File(".").getCanonicalPath();
    }
    
    public static BasicFileAttributes getFileInfo(String fileName) {
    	try {
			return Files.readAttributes(Paths.get(fileName), BasicFileAttributes.class);
		} catch (IOException e) {
			StackTraceLog.error(e);
		} 
    	
    	return null;
    }
    
    private final static String PAT_CSV_SPLIT = "(?:^|,)(?=[^\"]|(\")?)\"?((?(1)[^\"]*|[^,\"]*))\"?(?=,|$)";
    
    public static ArrayList<String []> readCSV(String fileName, boolean existHeader) {
    	String [] contents = readLines(fileName);
    	ArrayList<String []> rows = new ArrayList<String []>();
    	
    	int startIndex = existHeader ? 1 : 0;
    	
    	for (int i = startIndex; i < contents.length; i++) {
    		//rows.add(contents[i].split(PAT_CSV_SPLIT));
    		rows.add(contents[i].split(","));
    	}
    	
    	return rows;
    }
    
    public static ArrayList<HashMap<String, String>> readCSVMap(String fileName) {
    	String [] contents = readLines(fileName);
    	ArrayList<HashMap<String, String>> rows = new ArrayList<HashMap<String, String>>();
    	
    	String [] keys = contents[0].split(",");
    	
    	for (int i = 1; i < contents.length; i++) {
    		HashMap<String, String> rowMap = new HashMap<String, String>(); 
    		String [] row = contents[i].split(PAT_CSV_SPLIT);
    		
    		for (int j = 0; j < keys.length; j++) {
    			rowMap.put(keys[j], row[j]);
    		}
    		
    		rows.add(rowMap);
    	}
    	
    	return rows;
    }
    
    public static String appendPath(String root, String sub) {
		boolean hasRootDel =(StringUtil.lastChar(root) == File.separatorChar);
		boolean hasSubDel = (StringUtil.firstChar(sub) == File.separatorChar);
		
		return (hasRootDel || hasSubDel) ? String.format("%s%s", root, sub) : String.format("%s%s%s", root, File.separatorChar, sub);
    }
    
    public static void main(String [] args) throws IOException {
    	String path = "D:\\temp";
    	String [] fileNames = getFileNames(path, "*.txt *.htm?", false);
    	
    	for (int i = 0; i < fileNames.length; i++) {
    		System.out.println(fileNames[i]);
    		
    		append("D:\\appendTest.txt", readText(path + "/" + fileNames[i]));
    	}
    	
    	System.out.println(readText("D:\\appendTest.txt"));
    	
    	//getFullFileList("D:\\workspaces", new RegxFilter("([^\\.]*\\.)+doc|(*\\.)+docx|(*\\.)+xls|(*\\.)+xlsx|(*\\.)+ppt|(*\\.)+pptx|(*\\.)+hwp"));
    	
    	//getFileInfo("");
    	
    	/*
    	String [] aaa = getFileNames("D:\\workspace\\triniti.trident.apps.crawler\\src\\main\\resource\\sqlmap\\", "sql-?map-?config-?\\w*.xml", false);
    	
    	for (String s : aaa) {
    		System.out.println(s);
    	} */
    	
    	//System.out.println(getAppRootPath());
    	
    	//safeMakeDeepDir("D:\\test\\temp1\\temp2\\temp3\\temp4\\temp5\\temp6\\temp7");
    	
    	//String content = readText("D:\\test\\aaaa-result.xlsx.txt");
    	
    	//saveCompress("D:\\test\\aaaa-result.xlsx.txt.gz", content);
    	
    	// String content = readText("D:\\temp\\komoran\\www.khan.co.kr-[경향신문]경제-20200513-7793563.txt");
    	
//    	String content = readText("D:\\temp\\komoran\\verb.txt");
//    	
//    	System.out.print(content);
//    	
//    	File f = new File("/data/textfilter/input");
//    	
//    	System.out.println(f.getAbsolutePath());
//    	
//    	System.out.println(getTempFileName("aaa-aa[%10s].xx", 20));
    	
   		System.out.println(FileUtil.isEmpty("D:\\temp\\test02", false));
    }
}
