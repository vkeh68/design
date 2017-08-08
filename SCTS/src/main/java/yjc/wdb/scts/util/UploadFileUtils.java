package yjc.wdb.scts.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.util.FileCopyUtils;

public class UploadFileUtils {
	
	public static String uploadDrawingFile(String originalName, String uploadPath, byte[] fileData) throws Exception {
		UUID uid = UUID.randomUUID();
		
		int extIdx = originalName.lastIndexOf('.');
		String nameWithoutExtension = originalName.substring(0, extIdx);
		String savedName = nameWithoutExtension + "_" + uid.toString();
		savedName += originalName.substring(extIdx);
		
		File target = new File(uploadPath, savedName);
		System.out.println(target.getAbsolutePath());
		if(target.getParentFile().exists() == false) {
			target.getParentFile().mkdirs();
		}
		
		FileCopyUtils.copy(fileData, target);
		
		return savedName;
	}

	public static String uploadFile(String originalName, String uploadPath, byte[] fileData) throws Exception {
		UUID uid = UUID.randomUUID();
		int extIdx = originalName.lastIndexOf(".");
		String nameWithoutExtension = originalName.substring(0, extIdx);
		String savedName = nameWithoutExtension + "_" + uid.toString();
		savedName += originalName.substring(extIdx);
		
		String savedPath = calculatePath();
		
		File target = new File(uploadPath + savedPath, savedName);
		System.out.println(target.getAbsolutePath());
		if(target.getParentFile().exists() == false) {
			target.getParentFile().mkdirs();
		}
		
		FileCopyUtils.copy(fileData, target);
		
		// 이미지 파일이면 thumbnail 저장
		// 아니면 icon 저장
		String thumbNailName = "";
		String fileExtenstion = originalName.substring(extIdx+1);
		
		if(MediaUtils.getMediaType(fileExtenstion.toLowerCase()) != null ) {
			thumbNailName = makeThumNail(uploadPath, savedPath, savedName);
		}
		else {
			thumbNailName = makeIcon(uploadPath, savedPath, savedName);
		}
			
		
		return thumbNailName;
	}
	
	private static String calculatePath() {
		Calendar cal = Calendar.getInstance();
		
		String yearPath = File.separator + cal.get(Calendar.YEAR);
		
		DecimalFormat df = new DecimalFormat("00");
		String monthPath = File.separator + df.format(cal.get(Calendar.MONTH)+1);

		String datePath = File.separator + df.format(cal.get(Calendar.DATE)+1);
		
		datePath = yearPath + monthPath + datePath;
		
		return datePath;
	}
	
	private static String makeIcon(String uploadPath, String path, String fileName) {
		String iconName = uploadPath + path + File.separator+ fileName;
		
		return iconName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}
	
	private static String makeThumNail(String uploadPath, String path, String fileName) throws Exception {
		BufferedImage sourceImg =
				ImageIO.read(new File(uploadPath+path, fileName));
		
		BufferedImage destImg = 
				Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);
		
		String thumbNailName = uploadPath + path + File.separator + "thumbNail_" + fileName;
		
		File newFile = new File(thumbNailName);
		String fileExtension = 
				fileName.substring(fileName.lastIndexOf(".")+1);
		
		ImageIO.write(destImg, fileExtension.toLowerCase(), newFile);
		thumbNailName = thumbNailName.substring(uploadPath.length());
		thumbNailName = thumbNailName.replace(File.separatorChar, '/');
		
		return thumbNailName;
	}
}

