package com.benlevy.tools;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class ImageTools {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	public static Calendar getJpegTakenDate(String filePath) throws IOException, ImageProcessingException {
		File jpegFile = new File(filePath);
		Metadata metadata = ImageMetadataReader.readMetadata(jpegFile);
		try {
			ExifSubIFDDirectory dir = metadata.getDirectory(ExifSubIFDDirectory.class);
			Calendar cal = Calendar.getInstance();
			cal.setTime(dir.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL));
			return cal;
		} catch (NullPointerException ex) {
			return null;
		}
	}
}
