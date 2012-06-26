package com.benlevy.tools;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

public class FileTools {
	public static List<File> getFilesInDirectory(String filePath) throws IOException {
		return getFilesInDirectory(filePath, null);
	}
	public static List<File> getFilesInDirectory(String filePath, FilenameFilter filter) throws IOException {
		File dir = new File(filePath);
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
			throw new IOException("The supplied directory was not valid");
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
	public static void writeStringToFile(String contents, String filePath) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(filePath));
		out.write(contents);
		out.close();
	}
	public static String[] getImageDimensions(String filePath) throws IOException {
		BufferedImage img = ImageIO.read(new File(filePath));
		String[] dimensions = {Integer.toString(img.getWidth()), Integer.toString(img.getHeight())};
		return dimensions;
	}
}
