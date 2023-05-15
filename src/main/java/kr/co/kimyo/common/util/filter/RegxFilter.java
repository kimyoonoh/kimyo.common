package kr.triniti.common.util.filter;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class RegxFilter implements FilenameFilter {
	
	boolean isCaselgnore = false; 
	ArrayList<String> regxs = new ArrayList<String>();

	public RegxFilter(String fileRegx) { 
		setRegxPattern(fileRegx, false);
	}
	
	public RegxFilter(String fileRegx, boolean isCaseIgnore) {
		setRegxPattern(fileRegx, isCaseIgnore);
	}

	private void setRegxPattern(String fileRegx, boolean isCaseIgnore) {
		this.isCaselgnore = isCaseIgnore;
		
		for (String regx : fileRegx.split(";|,|\\s|\\|")) {
			if (isCaseIgnore) regx = regx.toLowerCase();
			
			regxs.add(regx
				.replaceAll("\\?", "\\\\w{0,1}")
				.replaceAll("\\.", "\\\\.")
				.replaceAll("\\*", ".*")
			);
		}
	}
	
	public boolean accept(File f, String name) {
//		if ((new File(name)).isDirectory()) return false;
		for (String regx : regxs) {
			if (this.isCaselgnore) name = name.toLowerCase();
			
			if (name.matches(regx)) return true;
		}
		return false;
	}
}