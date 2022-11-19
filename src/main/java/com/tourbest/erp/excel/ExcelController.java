//package com.tourbest.erp.excel;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.Reader;
//import java.io.UnsupportedEncodingException;
//import java.security.InvalidKeyException;
//import java.security.KeyFactory;
//import java.security.KeyPair;
//import java.security.KeyPairGenerator;
//import java.security.NoSuchAlgorithmException;
//import java.security.PrivateKey;
//import java.security.PublicKey;
//import java.security.SecureRandom;
//import java.security.spec.InvalidKeySpecException;
//import java.security.spec.PKCS8EncodedKeySpec;
//import java.security.spec.X509EncodedKeySpec;
//import java.util.ArrayList;
//import java.util.Base64;
//import java.util.List;
//import java.util.Properties;
//
//import javax.crypto.BadPaddingException;
//import javax.crypto.Cipher;
//import javax.crypto.IllegalBlockSizeException;
//import javax.crypto.NoSuchPaddingException;
//import javax.servlet.http.HttpServletResponse;
//
//import com.lannstark.excel.ExcelFile;
//import com.lannstark.excel.onesheet.OneSheetExcelFile;
//import org.apache.ibatis.io.Resources;
//import org.apache.poi.xssf.usermodel.XSSFCell;
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.junit.Assert;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;
//
//
//@Controller
//@RequestMapping("/excel")
//public class ExcelController {
//
//	@RequestMapping("/main")
//	public String excelTest() {
//
//		return "excel.main";
//	}
//
//	@RequestMapping("/decTest")
//	public void decTest() throws Exception {
//
//		System.out.println("decTest");
//		String resource = "config/test.properties";
//        Properties properties = new Properties();
//        Reader reader = Resources.getResourceAsReader(resource);
//        properties.load(reader);
//
//        System.out.println(properties.toString());
//
//        // RSA 키쌍을 생성
//        KeyPair keyPair = this.genRSAKeyPair();
//
//        PublicKey publicKey = keyPair.getPublic();
//        PrivateKey privateKey = keyPair.getPrivate();
//
//        String plainText = properties.toString();
//
//        // Base64 인코딩된 암호화 문자열
//        String encrypted = this.encryptRSA(plainText, publicKey);
//
//        // 복호화
//        String decrypted = this.decryptRSA(encrypted, privateKey);
//
////		this.RSA_Base64();
////		Workbook workbook = new XSSFWorkbook();
////
////		Sheet sheet = workbook.createSheet();
////
////		Row header = sheet.createRow(0);
////
////		Cell headerCell = header.createCell(0);
////
////
////		headerCell.setCellValue("testCell");
////
////		 try (FileOutputStream out = new FileOutputStream("c:/project/comments.xlsx")) {
////			 workbook.write(out);
////         }
//	}
//
//	@RequestMapping("/excelDown")
//	public void excelDown() throws Exception {
//
//		System.out.println("test");
//		this.RSA_Base64();
////		Workbook workbook = new XSSFWorkbook();
////
////		Sheet sheet = workbook.createSheet();
////
////		Row header = sheet.createRow(0);
////
////		Cell headerCell = header.createCell(0);
////
////
////		headerCell.setCellValue("testCell");
////
////		 try (FileOutputStream out = new FileOutputStream("c:/project/comments.xlsx")) {
////			 workbook.write(out);
////         }
//	}
//
//	@ResponseBody
//	@RequestMapping(value = "/excelUpload", method = RequestMethod.POST)
//	public String excelUpload(MultipartFile excelFile) throws FileNotFoundException, IOException  {
//
////		File file = new File();
////
////		FileInputStream uploadFile = new FileInputStream(file);
//
//		XSSFWorkbook workbook = new XSSFWorkbook(excelFile.getInputStream());
//
//		XSSFSheet sheet = workbook.getSheetAt(0);
//
//		int rows = sheet.getPhysicalNumberOfRows();
//
//        for (int i = 0; i < rows; i++) {
//            XSSFRow row = sheet.getRow(i);
//
//            if(row != null) {
//                int cells = row.getPhysicalNumberOfCells();
//
//                for (int j = 0; j < cells; j++) {
//                    XSSFCell cell = row.getCell(j);
//                    try {
//                        System.out.println(cell.getStringCellValue());
//                    } catch (Exception e) {
//                        System.out.println(cell.getNumericCellValue());
////						e.printStackTrace();
//                    }
//                }
//            }
//        }
//
//        return "";
//	}
//
//	@ResponseBody
//    @RequestMapping(value = "/moduleTest")
//	public void moduleTest(HttpServletResponse response) throws IOException {
//	    List<ExcelVo> list = new ArrayList<>();
//        ExcelVo vo = new ExcelVo();
//        vo.setBroad_no("1");
//        vo.setLoc1("서울");
//        vo.setLoc2("중구");
//        vo.setBroad_no("중구 맛집편");
//        vo.setName("원할머니 갈비");
//
//        list.add(vo);
//
//        response.setContentType("application/vnd.ms-excel");
//
//        ExcelFile excelFile  = new OneSheetExcelFile(list, vo.getClass());
//
//        excelFile.write(response.getOutputStream());
//    }
//
//	public void RSA_Base64()//_암복호화_테스트
//            throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException,
//            BadPaddingException, NoSuchPaddingException, UnsupportedEncodingException,
//            InvalidKeySpecException {
//        // RSA 키쌍을 생성
//        KeyPair keyPair = this.genRSAKeyPair();
//
//        PublicKey publicKey = keyPair.getPublic();
//        PrivateKey privateKey = keyPair.getPrivate();
//
//        String plainText = "RSA Encryption test";
//
//        // Base64 인코딩된 암호화 문자열
//        String encrypted = this.encryptRSA(plainText, publicKey);
//
//        // 복호화
//        String decrypted = this.decryptRSA(encrypted, privateKey);
//
//        Assert.assertEquals(plainText, decrypted);
//
//        // 공개키를 Base64 인코딩한 문자일을 만듦
//        byte[] bytePublicKey = publicKey.getEncoded();
//        String base64PublicKey = Base64.getEncoder().encodeToString(bytePublicKey);
//
//        // 개인키를 Base64 인코딩한 문자열을 만듦
//        byte[] bytePrivateKey = privateKey.getEncoded();
//        String base64PrivateKey = Base64.getEncoder().encodeToString(bytePrivateKey);
//
//        // base64 암호화한 String 에서 Public Key 를 다시생성한후 암호화 테스트를 진행
//        PublicKey rePublicKey = this.getPublicKeyFromBase64Encrypted(base64PublicKey);
//        String encryptedRe = this.encryptRSA(plainText, rePublicKey);
//        String decryptedRe = this.decryptRSA(encryptedRe, privateKey);
//
////        Assert.assertEquals(plainText, decryptedRe);
//
//        // base64 암호화한 String 에서 Private Key 를 다시생성한후 복호화 테스트를 진행
//        PrivateKey privateKeyRe = this.getPrivateKeyFromBase64Encrypted(base64PrivateKey);
//        String decryptedReRe = this.decryptRSA(encryptedRe, privateKeyRe);
//
////        Assert.assertEquals(decrypted, decryptedReRe);
//    }
//
//
//
//
//    /**
//     * 1024비트 RSA 키쌍을 생성
//     */
//    public static KeyPair genRSAKeyPair() throws NoSuchAlgorithmException {
//        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
//        gen.initialize(1024, new SecureRandom());
//        return gen.genKeyPair();
//    }
//
//    /**
//     * Public Key로 RSA 암호화를 수행
//     */
//    public static String encryptRSA(String plainText, PublicKey publicKey)
//            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
//            BadPaddingException, IllegalBlockSizeException {
//        Cipher cipher = Cipher.getInstance("RSA");
//        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
//
//        byte[] bytePlain = cipher.doFinal(plainText.getBytes());
//        return Base64.getEncoder().encodeToString(bytePlain);
//    }
//
//    /**
//     * Private Key로 RSA 복호화를 수행
//     */
//    public static String decryptRSA(String encrypted, PrivateKey privateKey)
//            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
//            BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
//        Cipher cipher = Cipher.getInstance("RSA");
//        byte[] byteEncrypted = Base64.getDecoder().decode(encrypted.getBytes());
//
//        cipher.init(Cipher.DECRYPT_MODE, privateKey);
//        byte[] bytePlain = cipher.doFinal(byteEncrypted);
//        return new String(bytePlain, "utf-8");
//    }
//
//    public static PublicKey getPublicKeyFromBase64Encrypted(String base64PublicKey)
//            throws NoSuchAlgorithmException, InvalidKeySpecException {
//        byte[] decodedBase64PubKey = Base64.getDecoder().decode(base64PublicKey);
//
//        return KeyFactory.getInstance("RSA")
//                .generatePublic(new X509EncodedKeySpec(decodedBase64PubKey));
//    }
//
//    public static PrivateKey getPrivateKeyFromBase64Encrypted(String base64PrivateKey)
//            throws NoSuchAlgorithmException, InvalidKeySpecException {
//        byte[] decodedBase64PrivateKey = Base64.getDecoder().decode(base64PrivateKey);
//
//        return KeyFactory.getInstance("RSA")
//                .generatePrivate(new PKCS8EncodedKeySpec(decodedBase64PrivateKey));
//    }
//}
//
//
