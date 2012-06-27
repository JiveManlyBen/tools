package com.benlevy.tools;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class FileTools {
	private static final String DIRECTORY_ERROR = "The supplied directory was not valid";
	
	public static List<File> getFilesInDirectory(String directoryPath) throws IOException {
		return getFilesInDirectory(directoryPath, null);
	}
	public static List<File> getFilesInDirectory(String directoryPath, FilenameFilter filter) throws IOException {
		File dir = new File(directoryPath);
		File[] files;
		if(filter == null)
			files = dir.listFiles();
		else
			files = dir.listFiles(filter);
		ArrayList<File> fileList = new ArrayList<File>();
		for(File f : files){
			if (f.isFile()){
				fileList.add(f);
			}
		}
		return fileList;
	}
	public static List<File> getFoldersInDirectory(String directoryPath) {
		File directory = new File(directoryPath);
		ArrayList<File> files = new ArrayList<File>();
		for(File file : directory.listFiles()) {
			if (file.isDirectory())
				files.add(file);
		}
		return files;
	}
	public static boolean renameFileExtention(String directoryPath, String oldExtension, String newExtension) throws IOException {
		File directory = new File(directoryPath);
		if (directory.isDirectory()){
			String[] ext = {oldExtension};
			List<String> extensions = Arrays.asList(ext);		
			for (File fileInDirectory : directory.listFiles(getImageFileExtensionFilter(extensions))) {
				StringBuilder builder = new StringBuilder(fileInDirectory.getPath());
				String newName = builder.replace(fileInDirectory.getPath().lastIndexOf("."+oldExtension), fileInDirectory.getPath().lastIndexOf("."+oldExtension) + 1, "."+newExtension).toString();
				fileInDirectory.renameTo(new File(newName));
			}
			return true;
		}
		else {
			throw new IOException(DIRECTORY_ERROR);
		}
	}	
	public static FilenameFilter getImageFileExtensionFilter(final List<String> fileExtensions) {
		return new FilenameFilter() {
			public boolean accept(File dir, String name) {
				for (String fileExtension : fileExtensions) {
					if (name.toLowerCase().endsWith(fileExtension.toLowerCase()))
						return true;
				}
				return false;
			}
		};	
	}
	public static void renameFilesToLowerCase(String directoryPath) throws IOException {
		List<File> files = getFilesInDirectory(directoryPath);
		for(File f : files) {
			StringBuilder builder = new StringBuilder(f.getAbsolutePath());
			int position = f.getPath().lastIndexOf(f.getName());
			String newName = builder.replace(position, position + f.getName().length(), f.getName().toLowerCase()).toString();
			f.renameTo(new File(newName));
		}
	}
	public static void writeStringToFile(String contents, String filePath) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(filePath));
		out.write(contents);
		out.close();
	}
	public static Map<String, Integer> getImageDimensions(String filePath) throws IOException {
		BufferedImage img = ImageIO.read(new File(filePath));
		HashMap<String, Integer> sizeMap = new HashMap<String, Integer>();
		sizeMap.put("width", img.getWidth());
		sizeMap.put("height", img.getHeight());
		return sizeMap;
	}
}
