package com;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;


@Controller
public class UploadController {
    @Autowired
    JdbcTemplate jdbcTemplate;
    //跳转到上传文件的页面
    @RequestMapping(value = "/gouploadimg", method = RequestMethod.GET)
    public String goUploadImg() {
    //跳转到 templates 目录下的 uploadimg.html
        return "file";
    }

    //处理文件上传
    @ResponseBody //返回json数据
    @RequestMapping(value = "/oneUploadimg", method = RequestMethod.POST)
    //@RequestParam把参数绑定在你的控制器上，MultipartFile是spring类型，代表HTML中form data方式上传的文件，包含二进制数据+文件名称
    public String uploadImg(@RequestParam MultipartFile file, HttpServletRequest request) throws IOException {
        //获取文件的类型
        String contentType = file.getContentType();
        //获取文件原始的名字
        String fileName = file.getOriginalFilename();
        //设置原始存放的地址
        Random random = new Random();
        fileName = random.nextInt(100) + fileName;
        String filePath = "E:/implements/src/main/resources/static/img";
        long fileSize = file.getSize();
        if (file.isEmpty()) {
            return "文件为空！";
        }
        try {
            //尝试上传文件，参数是文件的二进制数据，文件存放的路径，文件的名字
            uploadFile(file.getBytes(), filePath, fileName);
            jdbcTemplate.update("INSERT INTO ccs_image (bin_data,filemane,filetype,filesize) VALUES ('"+ filePath + "/" + fileName +"','" + fileName + "','"+ contentType +"','"+ fileSize +"')");
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
            //返回json
        return "上传成功";
    }

    @ResponseBody
    @RequestMapping(value="/MultiUploadimg", method = RequestMethod.POST)
    //上传的文件会转换成MultiUploadimg对象，file名字对应html中上传控件的name
    //这里用到throws Exception的用法是忽略这个方法下面的异常处理
    //public String MultuploadImg(@RequestParam MultipartFile[] files)throws Exception{
    //只要在方法的参数内输入@requestParam("multifiles")就会自动对应网页内部的"multifiles"文件名
    public String MultuploadImg(@RequestParam("multifiles") MultipartFile[] Multifiles){
        if(Multifiles.length == 0){
            return "请选择要上传的文件";
        }
        for (MultipartFile multipartFile : Multifiles) {
            if(multipartFile.isEmpty()){
                return "文件上传失败";
            }
            try{
                byte[] fileBytes = multipartFile.getBytes();
                String filePath = "D:/img";
                String originalFilename = multipartFile.getOriginalFilename();
                uploadFile(fileBytes, filePath, originalFilename);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "文件上传完毕";
    }

    //这里的Exception是明确程序有哪些异常，所以一下的程序需要用在try..catch当中处理
    public static void uploadFile(byte[] file, String filePath, String fileName){
        //用File这个类创建一个新的文件路径
        File targetFile = new File(filePath);
        //如果文件不存在则创建一个文件路径
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try{
            //FileOutputStream流是指文件字节输出流，专用于输出原始字节流如图像数据等，其继承OutputStream类，拥有输出流的基本特性
            FileOutputStream out = new FileOutputStream(filePath +"/"+ fileName);
            //用FileOutputStream输出到二进制数组file中
            out.write(file);
            //终止的指令
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/Checkimg",method = RequestMethod.GET)
    public String Checking(Model model){
        List<Map<String, Object>> mobile = jdbcTemplate.queryForList("select * from ccs_image");
        System.out.println(mobile.get(9).get("bin_data"));
        String img = (String) mobile.get(9).get("bin_data");
        String[] img0 = img.split("img");
        img = "img" + img0[1];
        System.out.println(img);
        model.addAttribute("img",img);
        model.addAttribute("Check",mobile);
        return "Checkimg";
    }
}
