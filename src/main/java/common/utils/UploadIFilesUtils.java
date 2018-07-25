package common.utils;

import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


/**
 * 功能描述：用于文件上传的工具类
 *
 * @author dhcc
 * @date
 * @see null
 * @修改日志：1.0
 */
public class UploadIFilesUtils extends Object {


    /**
     * 单独文件上传返回路径
     *
     * @param file 文件   ,returnUrl 文件存储位置，path 文件存储路径
     *
     *  存储路径http://localhost:8081/upload/imgs/
     * 文件存储位置"D:\JxcSpace\\jc-jxc-db-center\src\\main\webapp\【s】upload\imgs"
     *  服务器位置：http://10.10.10.144:8080/images/upload/imgs/147.png
     */
    public String uploadPicture(MultipartFile file, String returnUrl, String path, String stiStoreCode) {
        File targetFile = null;
        String msg = "";//返回存储路径
        List imgList = new ArrayList();
        String fileName = file.getOriginalFilename();//获取文件名加后缀
        if (fileName != null && fileName != "") {
            // String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() +"/upload/imgs/";//存储路径
            //  String path = request.getSession().getServletContext().getRealPath("upload/imgs"); //文件存储位置
            String fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length());//文件后缀
            fileName = new Date().getTime() + "_" + new Random().nextInt(1000) + fileF;//新的文件名

            //先判断文件是否存在是
            String fileAdd = DateUtils.getDate("yyyyMMdd");

            File file1 = new File(path + "/" + fileAdd);

            File file2 = new File(path + "/" + fileAdd + "/" + stiStoreCode);

            //如果文件夹不存在则创建
            if (!file1.exists() && !file1.isDirectory()) {
                file1.mkdir();

            }

            if (!file2.exists() && !file2.isDirectory()) {
                file2.mkdir();

            }

            targetFile = new File(file2, fileName);
            try {
                file.transferTo(targetFile);
                msg = fileAdd + "/" + stiStoreCode + "/" + fileName;
                // imgList.add(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return msg;
    }



             /***
             * @Description:
             * @param  baseFile（ String base64字符串）
             * @Return:
             * @Author: Mr.Water.by
             * @Date: 2018/7/22 16:40
             */
//            public  String upload64Img(String baseFile,String returnUrl, String path, String stiStoreCode)throws IllegalStateException, IOException {
//
//
//
//              //  String path = request.getSession().getServletContext().getRealPath("uploadImg");
//                // Base64 base64=new Base64();
//              // Base64 base64 =  new Base64();
//                //file 为前台隐藏域里面的字符串
//                if(baseFile!= null && !"".equals(baseFile)){
//                    int index = 0;
//                        //base64 解码
//                        byte[] byteArray = Base64.getDecoder().decode(baseFile);
//                    // 调整异常数据
//                        for (byte b : byteArray) {
//                        if(b<0)
//                            b+=256;
//                    }
//                        String imageName = "imageName";
//                        try {
//                            OutputStream out = new FileOutputStream(path+File.separator+imageName);
//                            out.write(byteArray);
//                            out.close();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            System.out.println(imageNames[0]);
//                        }
//                        imageNames[index] = path+File.separator+imageName;
//                        index ++ ;
//
//                }
//                System.out.println(imageNames[0]);
//
//                return  "";
//            }




    /**
     * @Description: 将base64编码字符串转换为图片
     * @Author:
     * @CreateTime:
     * @param imgStr base64编码字符串
     * @param path 图片路径-具体到文件
     * @return returnPath
     */
    public static  String generateImage(String imgStr, String returnUrl, String path, String stiStoreCode) {

        //data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAVAAA
        //加密解密测试已通过，解密头部未处理；加密未加头部
        String returnPath="";
        if (imgStr == null)
       return returnPath;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
        // 解密
        byte[] b = decoder.decodeBuffer(imgStr);
       // 处理数据
        for (int i = 0; i < b.length; ++i) {
        if (b[i] < 0) {
        b[i] += 256;
        }
        }
        OutputStream out = new FileOutputStream(path);
        out.write(b);
        out.flush();
        out.close();
        return "";
        } catch (Exception e) {
        return "";
        }
    }

    /**
     * @Description: 根据图片地址转换为base64编码字符串
     * @Author:
     * @CreateTime:
     * @return
     */
    public static String getImageStr(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }
    /**
     * 示例
     */
    public static void main(String[] args) {
        String strImg = getImageStr("C:/Users/Water.by/Desktop/20180722145058.png");
        System.out.println(strImg);
      //String asdf=  generateImage(strImg, "C:/Users/Water.by/Desktop/3306.png");
       // System.out.print(asdf);
    }


}
