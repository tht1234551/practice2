package com.tourbest.erp.util;

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
//import java.util.HashMap;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tourbest.erp.common.ServerProfiles;

import net.coobird.thumbnailator.Thumbnails;

/**
 *
 *
 *
 */
@Component("FileUtil")
public class FileUtil {

	/** Buffer size */
	public static final int BUFFER_SIZE = 8192;

	public static final String SEPERATOR = File.separator;

	@Autowired
	private ServerProfiles serverProfiles;

	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * 첨부파일에 대한 목록 정보를 취득한다.
	 *
	 * @param files
	 * @return
	 * @throws Exception
	 */
	public List<FileVO> parseFileInf(Map<String, MultipartFile> files, String KeyStr, int fileKeyParam,
			String atchFileId, String storePath) throws Exception {
		int fileKey = fileKeyParam;

		String storePathString = "";
		String atchFileIdString = "";

		if ("".equals(storePath) || storePath == null) {
			storePathString = serverProfiles.getImgUrl();
		} else {
			storePathString = serverProfiles.getImgPath();
		}

		File saveFolder = new File(storePathString);

		if (!saveFolder.exists() || saveFolder.isFile()) {
			saveFolder.mkdirs();
		}

		Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
		MultipartFile file;
		String filePath = "";
		List<FileVO> result = new ArrayList<FileVO>();
		FileVO fvo;

		while (itr.hasNext()) {
			Entry<String, MultipartFile> entry = itr.next();

			file = entry.getValue();
			String orginFileName = file.getOriginalFilename();

			// --------------------------------------
			// 원 파일명이 없는 경우 처리
			// (첨부가 되지 않은 input file type)
			// --------------------------------------
			if ("".equals(orginFileName)) {
				continue;
			}
			//// ------------------------------------

			int index = orginFileName.lastIndexOf(".");
			// String fileName = orginFileName.substring(0, index);
			String fileExt = orginFileName.substring(index + 1);
			String newName = KeyStr + StringUtil.getTimeStamp() + fileKey;
			long _size = file.getSize();

			if (!"".equals(orginFileName)) {
				filePath = storePathString + File.separator + newName;
				file.transferTo(new File(filePath));
			}
			fvo = new FileVO();
			fvo.setFileExtsn(fileExt);
			fvo.setFilePath(storePathString);
			fvo.setFileSize(Long.toString(_size));
			fvo.setFileOriginalNm(orginFileName);
			fvo.setFileNm(newName);
			// fvo.setSeq(atchFileIdString);
			fvo.setFileGrpId(fileKey);

			// writeFile(file, newName, storePathString);
			result.add(fvo);
			fileKey++;
		}

		return result;
	}

	/**
	 * 파일을 Upload 처리한다.
	 *
	 * @param request
	 * @param where
	 * @param maxFileSize
	 * @return
	 * @throws Exception
	 */
	public static List<FileVO> uploadFiles(HttpServletRequest request, String where, long maxFileSize) throws Exception {
		List<FileVO> list = new ArrayList<FileVO>();

		MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;
		Iterator<?> fileIter = mptRequest.getFileNames();
		
		//List<String> validExtensions = Arrays.asList(new String[]{"jpg", "jpeg", "gif", "png", ""});
		List<String> validExtensions = Arrays.asList(new String[]{
				"ADE", "ADP", "APK", "BAT", "CHM", "CMD", "COM", "CPL", "DLL", "DMG",
				"EXE", "HTA", "INS", "ISP", "JAR", "JS", "JSE", "LIB", "LNK", "MDE", 
				"MSC", "MSI", "MSP", "MST", "NSH", "PIF", "SCR", "SCT", "SHB", "SYS", 
				"VB", "VBE", "VBS", "VXD", "WSC", "WSF", "WSH", "CAB"
		});  // 등록할 수 없는 파일 확장자  :: 구굴 메일에서 제한하는 파일확장자 목록입니다.

		while (fileIter.hasNext()) {
			MultipartFile mFile = mptRequest.getFile((String) fileIter.next());
			
			if(!mFile.isEmpty()) {
				FileVO vo = new FileVO();

				String tmp = mFile.getOriginalFilename();
				String fileExtsn = tmp.substring(tmp.lastIndexOf(".") + 1 );
				
				if(StringUtil.isEmpty(fileExtsn)) {
					throw new Exception("파일 확장자가 존재하지 않습니다.");
				}
				
				if(validExtensions.contains(fileExtsn.toLowerCase())) {
					throw new Exception("등록할 수 없는 파일이 포함되어 있습니다.");
				}
				
				if(mFile.getSize() > maxFileSize) {
					throw new Exception(String.format("파일은 최대 %dKB까지 업로드할 수 있습니다.", maxFileSize / 1024));
				}
				
				if (tmp.lastIndexOf("\\") >= 0) {
					tmp = tmp.substring(tmp.lastIndexOf("\\") + 1);
				}

				vo.setFileName(tmp);
				vo.setContentType(mFile.getContentType());
				//vo.setServerSubPath(getTodayString());  하위 디렉터리 만드리 않음
				vo.setServerSubPath("attach");  // 하위 디렉터리 고정
				vo.setPhysicalName(getPhysicalFileName());
				vo.setFileExtsn(fileExtsn);
				vo.setSize(mFile.getSize());
				
				vo.setFilePath(where + SEPERATOR + vo.getServerSubPath() + SEPERATOR);
				//vo.setFilePath(where + SEPERATOR);

				if (tmp.lastIndexOf(".") >= 0) {
					vo.setPhysicalName(vo.getPhysicalName()); // 2012.11 KISA 보안조치
				}
				
				if (mFile.getSize() > 0) {
					InputStream is = null;

					try {
						is = mFile.getInputStream();
						
						saveFile(is, new File(WebUtil.filePathBlackList(vo.getFilePath() + vo.getPhysicalName() + "." + vo.getFileExtsn())));
					} finally {
						if (is != null) {
							is.close();
						}
					}
					list.add(vo);
				}
			}
		}

		return list;
	}
	
	/**
	 * 단일 파일 업로드
	 *
	 * @param request
	 * @param where
	 * @param maxFileSize
	 * @return
	 * @throws Exception
	 */
	public static FileVO uploadFile(HttpServletRequest request, String name, String storedFileName, String where, long maxFileSize) throws Exception {
		
		FileVO vo = new FileVO();
		MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;
		
		List<String> validExtensions = Arrays.asList(new String[]{"jpg", "jpeg", "gif", "png"});
		
		MultipartFile mFile = mptRequest.getFile(name);
		
		if(!mFile.isEmpty()) {
			String tmp = mFile.getOriginalFilename();
			String fileExtsn = tmp.substring(tmp.lastIndexOf(".") + 1 );
			
			if(StringUtil.isEmpty(fileExtsn)) {
				throw new Exception("파일 확장자가 존재하지 않습니다.");
			}
			
			if(!validExtensions.contains(fileExtsn.toLowerCase())) {
				throw new Exception("이미지 파일(jpg,jpeg,gif,png)만 업로드 할 수 있습니다.");
			}
			
			if(mFile.getSize() > maxFileSize) {
				throw new Exception(String.format("파일은 최대 %dKB까지 업로드할 수 있습니다.", maxFileSize / 1024));
			}
			
			if (tmp.lastIndexOf("\\") >= 0) {
				tmp = tmp.substring(tmp.lastIndexOf("\\") + 1);
			}
			
			vo.setFileName(tmp);  // 오리지날 파일명
			vo.setContentType(mFile.getContentType());
			vo.setPhysicalName(storedFileName);  // 실제파일명
			vo.setFileExtsn(fileExtsn);  // 확장자
			vo.setSize(mFile.getSize());  // 사이즈
			vo.setFilePath(where);  
			
			if (mFile.getSize() > 0) {
				InputStream is = null;
				
				try {
					is = mFile.getInputStream();
					
					saveFile(is, new File(WebUtil.filePathBlackList(vo.getFilePath() + vo.getPhysicalName() + "." + vo.getFileExtsn())));
				} finally {
					if (is != null) {
						is.close();
					}
				}
			}	
		}
		return vo;
	};

	/**
	 * 오늘 날짜 문자열 취득. ex) 20090101
	 * 
	 * @return
	 */
	public static String getTodayString() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());

		return format.format(new Date());
	};

	/**
	 * 물리적 파일명 생성.
	 * 
	 * @return
	 */
	public static String getPhysicalFileName() {
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	};

	/**
	 * 파일명 변환.
	 * 
	 * @param filename
	 *            String
	 * @param browser
	 *            String
	 * @return
	 * @throws Exception
	 */
	protected static String convert(String filename, String browser) throws Exception {
		String encodedFilename = null;
		if (browser.equals("MSIE")) {
			encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
		} else if (browser.equals("Firefox")) {
			encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
		} else if (browser.equals("Opera")) {
			encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
		} else if (browser.equals("Chrome")) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < filename.length(); i++) {
				char c = filename.charAt(i);
				if (c > '~') {
					sb.append(URLEncoder.encode("" + c, "UTF-8"));
				} else {
					sb.append(c);
				}
			}
			encodedFilename = sb.toString();
		} else {
			throw new RuntimeException("Not supported browser");
		}
		return encodedFilename;
	}

	protected static String getBrowser(HttpServletRequest request) {
		String header = request.getHeader("User-Agent");
		if (header.indexOf("MSIE") > -1) {
			return "MSIE";
		} else if (header.indexOf("Chrome") > -1) {
			return "Chrome";
		} else if (header.indexOf("Opera") > -1) {
			return "Opera";
		} else if (header.indexOf("Trident/7.0") > -1) {
			return "MSIE";
		}
		return "Firefox";
	};

	/**
	 * Stream으로부터 파일을 저장함.
	 * 
	 * @param is
	 *            InputStream
	 * @param file
	 *            File
	 * @throws IOException
	 */
	public static long saveFile(InputStream is, File file) throws IOException {
		// 디렉토리 생성
		if (!file.getParentFile().exists()) {
			// 2017.02.08 이정은 시큐어코딩(ES)-부적절한 예외 처리[CWE-253, CWE-440, CWE-754]
			if (file.getParentFile().mkdirs()) {
				logger.debug("[file.mkdirs] file : Directory Creation Success");
			} else {
				logger.error("[file.mkdirs] file : Directory Creation Fail");
			}
		}

		OutputStream os = null;
		long size = 0L;

		try {
			os = new FileOutputStream(file);

			int bytesRead = 0;
			byte[] buffer = new byte[BUFFER_SIZE];

			while ((bytesRead = is.read(buffer, 0, BUFFER_SIZE)) != -1) {
				size += bytesRead;
				os.write(buffer, 0, bytesRead);
			}
		} finally {
			ResourceCloseHelper.close(os);
		}

		return size;
	};

	/**
	 * 파일을 Download 처리한다.
	 *
	 * @param response
	 * @param where
	 * @param serverSubPath
	 * @param physicalName
	 * @param original
	 * @throws Exception
	 */
	public static void downloadFile(HttpServletRequest request, HttpServletResponse response, String where,
			String original) throws Exception {
		String downFileName = where;

		File file = new File(WebUtil.filePathBlackList(downFileName));

		if (!file.exists()) {
			throw new FileNotFoundException(downFileName);
		}

		if (!file.isFile()) {
			throw new FileNotFoundException(downFileName);
		}

		byte[] b = new byte[BUFFER_SIZE];

		original = original.replaceAll("\r", "").replaceAll("\n", "");
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition",
				"attachment; filename=\"" + convert(original, getBrowser(request)) + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");

		BufferedInputStream fin = null;
		BufferedOutputStream outs = null;

		try {
			fin = new BufferedInputStream(new FileInputStream(file));
			outs = new BufferedOutputStream(response.getOutputStream());

			int read = 0;

			while ((read = fin.read(b)) != -1) {
				outs.write(b, 0, read);
			}
		} finally {
			ResourceCloseHelper.close(outs, fin);
		}
	};
	
	/**
	 * 파일을 Download 처리한다.
	 *
	 * @param response
	 * @param where
	 * @param serverSubPath
	 * @param physicalName
	 * @param original
	 * @throws Exception
	 */
	public static void inputStreamToFile(HttpServletRequest request, HttpServletResponse response, String original, InputStream is) throws Exception {
		
		byte[] b = new byte[BUFFER_SIZE];
		
		original = original.replaceAll("\r", "").replaceAll("\n", "");
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition",
				"attachment; filename=\"" + convert(original, getBrowser(request)) + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");
		
		BufferedInputStream fin = null;
		BufferedOutputStream outs = null;
		
		try {
			fin = new BufferedInputStream(is);
			outs = new BufferedOutputStream(response.getOutputStream());
			
			int read = 0;
			
			while ((read = fin.read(b)) != -1) {
				outs.write(b, 0, read);
			}
		} finally {
			ResourceCloseHelper.close(outs, fin, is);
		}
	};

	/**
	 * 이미지에 대한 미리보기 기능을 제공한다.
	 *
	 * mimeType의 경우는 JSP 상에서 다음과 같이 얻을 수 있다.
	 * getServletConfig().getServletContext().getMimeType(name);
	 *
	 * @param response
	 * @param where
	 * @param serverSubPath
	 * @param physicalName
	 * @param mimeType
	 * @throws Exception
	 */
	public static void viewFile(HttpServletResponse response, String where, String serverSubPath, String physicalName,
			String mimeTypeParam) throws Exception {
		String mimeType = mimeTypeParam;
		String downFileName = where + SEPERATOR + serverSubPath + SEPERATOR + physicalName;

		File file = new File(WebUtil.filePathBlackList(downFileName));

		if (!file.exists()) {
			throw new FileNotFoundException(downFileName);
		}

		if (!file.isFile()) {
			throw new FileNotFoundException(downFileName);
		}

		byte[] b = new byte[BUFFER_SIZE];

		if (mimeType == null) {
			mimeType = "application/octet-stream;";
		}

		response.setContentType(WebUtil.removeCRLF(mimeType));
		response.setHeader("Content-Disposition", "filename=image;");

		BufferedInputStream fin = null;
		BufferedOutputStream outs = null;

		try {
			fin = new BufferedInputStream(new FileInputStream(file));
			outs = new BufferedOutputStream(response.getOutputStream());

			int read = 0;

			while ((read = fin.read(b)) != -1) {
				outs.write(b, 0, read);
			}
		} finally {
			ResourceCloseHelper.close(outs, fin);
		}
	};
	
	/**
	 * 배열 순서대로 하위 폴더 생성함.
	 * @param dirNames :: 하위폴더 목록
	 * @param defaultPath :: 기본 패스
	 * @throws IOException
	 */
	public String reateDirs(String[] dirNames, String defaultPath) throws IOException {
		String path = (defaultPath instanceof String )? defaultPath : serverProfiles.getComDataFilePath() ;
		for(String dir : dirNames) {
			path = createDir(path + File.separator + dir);
		}
		return path;
	}
	public String reateDirs(int comId, String[] dirNames, String defaultPath) throws IOException {
		String path = (defaultPath instanceof String )? defaultPath : serverProfiles.getComDataFilePath() ;
		path = createDir(path + File.separator + createComDirectoryNm(comId));
		for(String dir : dirNames) {
			path = createDir(path + File.separator + dir);
		}
		return path;
	};
	/**
	 * HTTP URL 반환
	 * @param dirNames
	 * @param defaultUrl
	 * @return
	 * @throws IOException
	 */
	public String getHttpUrl(int comId, String[] dirNames, String defaultUrl) throws IOException {
		String url = (defaultUrl instanceof String )? defaultUrl : serverProfiles.getComDataFileUrl() ;
		url += "/" + createComDirectoryNm(comId);
		for(String dir : dirNames) {
			url += "/" + dir;
		}
		return url;
	}
	
	/**
	 * 폴더 만들기  :: 폴더 검사 후 없으면 생성
	 * @param filePath
	 */
	public String createDir(String filePath) throws IOException {
		File dir = new File(filePath);
		if(!dir.isDirectory()) { // 폴더 존재여부 확인
			dir.mkdirs();  // 폴더 생성
		}
		return filePath;
	};
	/**
	 * 회사 폴더명 생성
	 * @param comId
	 * @return
	 */
	public static String createComDirectoryNm(int comId) {
		String directoryNm = "C" + StringManagerUtil.addZero(comId, 7);
		return directoryNm;
	};
	public static String createUserDirectoryNm(long userId) {
		String directoryNm = "U" + StringManagerUtil.addZero(userId, 7);
		return directoryNm;
	};
	public static String createConDirectoryNm(long conId) {
		String directoryNm = "F" + StringManagerUtil.addZero(conId, 9);
		return directoryNm;
	};
	/**
	 * 폴더 생성 후 path 반환
	 * @param comId
	 * @return
	 * @throws IOException 
	 */
	public String getComDefaultDirectoryPath(int comId) throws IOException {
		String comDataPath = serverProfiles.getComDataFilePath();
		return createDir(comDataPath + File.separator + createComDirectoryNm(comId));
	};

	
	/**
	 * 이미지 단일 파일 업로드  :: 이미지만 업로드 할때 사용함   ::  2019-01-09 신문섭 새벽....  잠안자고 뭐하니  ㅠㅠㅠ.ㅠㅠㅠ
	 * @param request
	 * @param name  ::  post 될 필드 이름
	 * @param storedFileName  :: 실제로 저장될 파일명
	 * @param where  :: 저장 위치
	 * @param maxFileSize  :: 최대 파일용량  KB
	 * @param maxWidth  ::  가로 최대 사이즈, 넘는 경우 강제 썸네일로 조정함
	 * @return
	 * @throws Exception
	 */
	public static FileVO imageUploadFile(HttpServletRequest request, String name, String storedFileName, String where, long maxFileSize, int maxImageWidth) throws Exception {
		FileVO vo = new FileVO();
		MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;
		List<String> validExtensions = Arrays.asList(new String[]{"jpg", "jpeg", "gif", "png"});
		MultipartFile mFile = mptRequest.getFile(name);
		
		if(!mFile.isEmpty()){
			String tmp = mFile.getOriginalFilename();
			String fileExtsn = tmp.substring(tmp.lastIndexOf(".") + 1 );
			
			if(StringUtil.isEmpty(fileExtsn)) {  throw new Exception("파일 확장자가 존재하지 않습니다.");  }
			if(!validExtensions.contains(fileExtsn.toLowerCase())) {   throw new Exception("이미지 파일(jpg,jpeg,gif,png)만 업로드 할 수 있습니다.");  }
			if(mFile.getSize() > maxFileSize) {  throw new Exception(String.format("파일은 최대 %dKB까지 업로드할 수 있습니다.", maxFileSize / 1024));  }
			if (tmp.lastIndexOf("\\") >= 0) {  tmp = tmp.substring(tmp.lastIndexOf("\\") + 1);  }
			
			vo.setFileName(tmp);  					// 오리지날 파일명
			vo.setContentType(mFile.getContentType());
			vo.setPhysicalName(storedFileName);  	// 실제파일명
			vo.setFileExtsn(fileExtsn);  			// 확장자
			vo.setSize(mFile.getSize());  			// 사이즈
			vo.setFilePath(where);    				// 파일경로
			
			if(mFile.getSize() > 0){
				InputStream is = null;
				try {
					is = mFile.getInputStream();
					saveFile(is, new File(WebUtil.filePathBlackList(vo.getFilePath() + File.separator + vo.getPhysicalName() + "." + vo.getFileExtsn())));
				} finally {  if (is != null) { is.close(); } }
			}
			
			// 이미지 파일의 경우 가로세로 사이즈 식별함.
			Image img = new ImageIcon(vo.getFilePath() + File.separator + vo.getPhysicalName() + "." + vo.getFileExtsn()).getImage();
			vo.setImgWidth(img.getWidth(null));
			vo.setImgHeight(img.getHeight(null));
			if(maxImageWidth > 0 && maxImageWidth < vo.getImgWidth()) {   // 이미지 썸네일
				File image = new File(vo.getFilePath() + File.separator + vo.getPhysicalName() + "." + vo.getFileExtsn());
				if(image.exists()) {  // resizing
					Thumbnails.of(image).width(maxImageWidth).outputQuality(1.0d).toFile(image);  // 같은 비율로 이미지 줄임.
				}
			}
		}
		return vo;
	};
	
	/**
	 * 
	 * @param selectFile  :: 복사할 디렉토리
	 * @param copyFile :: 복사될 디렉토리
	 */
	public static void copys(File selectFile, File copyFile) { //복사할 디렉토리, 복사될 디렉토리
		File[] ff = selectFile.listFiles();  //복사할 디렉토리안의 폴더와 파일들을 불러옵니다.
		for (File file : ff) {
			File temp = new File(copyFile.getAbsolutePath() +"\\"+ file.getName()); 
			//	temp - 본격적으로 디렉토리 내에서 복사할 폴더,파일들을 순차적으로 선택해 진행합니다. 
 
			if (file.isDirectory()){ //만약 파일이 아니고 디렉토리(폴더)라면
				temp.mkdirs();          //복사될 위치에 똑같이 폴더를 생성하고,
				copys(file, temp);      //폴더의 내부를 다시 살펴봅니다.
			}else{                   //만약 파일이면 복사작업을 진행합니다.
				FileInputStream fis = null;
				FileOutputStream fos = null;
  
				try {
					fis = new FileInputStream(file);
					fos = new FileOutputStream(temp);
					byte[] b = new byte[4096];   //4kbyte단위로 복사를 진행합니다.
					int cnt = 0;
   
					while ((cnt = fis.read(b)) != -1) {  //복사할 파일에서 데이터를 읽고,
						fos.write(b, 0, cnt);               //복사될 위치의 파일에 데이터를 씁니다.
					}
				}catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						fis.close();
						fos.close();
					} catch (IOException e) {
						// 	TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
   };
	

   
   // base64 인코딩된 파일 저장
   public boolean writeImageToDisk(FileItem item, File imageFile) {
	    // clear error message
	    //errorMessage = null;
	    FileOutputStream out = null;
	    boolean ret = false;
	    try {
	        // write thumbnail to output folder
	        out = createOutputStream(imageFile);

	        // Copy input stream to output stream
	        byte[] headerBytes = new byte[22];
	        InputStream imageStream = item.getInputStream();
	        imageStream.read(headerBytes);

	        String header = new String(headerBytes);
	        // System.out.println(header);

	        byte[] b = new byte[4 * 1024];
	        byte[] decoded;
	        int read = 0;
	        while ((read = imageStream.read(b)) != -1) {
	            // System.out.println();
	            //if (Base64.isArrayByteBase64(b)) {
	                decoded = Base64.decodeBase64(b);
	                out.write(decoded);
	            //}
	        }

	        ret = true;
	    } catch (IOException e) {
	        StringWriter sw = new StringWriter();
	        e.printStackTrace(new PrintWriter(sw));
	        //errorMessage = "error: " + sw;

	    } finally {
	        if (out != null) {
	            try {
	                out.close();
	            } catch (Exception e) {
	                StringWriter sw = new StringWriter();
	                e.printStackTrace(new PrintWriter(sw));
	                System.out.println("Cannot close outputStream after writing file to disk!" + sw.toString());
	            }
	        }

	    }

	    return ret;
	}

	/**
	 * Helper method for the creation of a file output stream.
	 * 
	 * @param imageFolder
	 *            : folder where images are to be saved.
	 * @param id
	 *            : id of spcefic image file.
	 * @return FileOutputStream object prepared to store images.
	 * @throws FileNotFoundException
	 */
	protected FileOutputStream createOutputStream(File imageFile) throws FileNotFoundException {

	    imageFile.getParentFile().mkdirs();

	    return new FileOutputStream(imageFile);
	}
	
	/* String base64 데이터  이미지 저장
	 * public class Base64ToImgDecoder {

		public static boolean decoder(String base64, String target){
			
			String data = base64.split(",")[1];
			
			byte[] imageBytes = DatatypeConverter.parseBase64Binary(data);
			
			try {
				
				BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(imageBytes));
				
				ImageIO.write(bufImg, "jpg", new File(target));
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				return false;
			}
			
			return true;
			
		}
		
	}*/
	
	/**
	 * 디렉토리 삭제 :: 하위 폴더가 잇는 경우 하위폴더 및 파일 모두 삭제
	 * @param path
	 */
	public static void deleteFile(String path) {
		File deleteFolder = new File(path);

		if(deleteFolder.exists()){
			File[] deleteFolderList = deleteFolder.listFiles();
			
			for (int i = 0; i < deleteFolderList.length; i++) {
				if(deleteFolderList[i].isFile()) {
					deleteFolderList[i].delete();
				}else {
					deleteFile(deleteFolderList[i].getPath());
				}
				deleteFolderList[i].delete(); 
			}
			deleteFolder.delete();
		}
	};
};
