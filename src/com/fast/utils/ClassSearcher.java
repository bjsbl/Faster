package com.fast.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fast.log.Logger;

public class ClassSearcher {

	protected static final Logger LOG = Logger.getLogger(ClassSearcher.class);
	private String baseDirName;
	private static ClassSearcher instance;

	private ClassSearcher(String baseDirName) {
		this.baseDirName = baseDirName;
	}

	public static ClassSearcher getInstance(String path) {
		if (instance == null) {
			instance = new ClassSearcher(path);
		}
		return instance;
	}

	public List<String> run() {
		return findFiles(baseDirName);
	}

	private List<String> findFiles(String baseDirName) {
		List<String> classFiles = new ArrayList<String>();
		File baseDir = new File(baseDirName);
		if (!baseDir.exists() || !baseDir.isDirectory()) {
			LOG.error("Error：" + baseDirName + " not Directory！");
		} else {
			String[] filelist = baseDir.list();
			for (int i = 0; i < filelist.length; i++) {
				File readfile = new File(baseDirName + File.separator + filelist[i]);
				if (readfile.isDirectory()) {
					classFiles.addAll(findFiles(baseDirName + File.separator + filelist[i]));
				} else {
					String tem = readfile.getAbsoluteFile().toString().replaceAll("\\\\", "/");
					String classname = tem.substring(tem.indexOf("/classes") + "/classes".length() + 1, tem.indexOf(".class"));
					classFiles.add(classname.replaceAll("/", "."));
				}
			}
		}
		return classFiles;
	}
}
