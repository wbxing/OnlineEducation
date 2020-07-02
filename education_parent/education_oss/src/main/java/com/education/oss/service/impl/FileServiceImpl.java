package com.education.oss.service.impl;

import com.aliyun.oss.OSSClient;
import com.education.oss.service.FileService;
import com.education.oss.util.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private static final String[] TYPE_STRING = {".png", ".jpg", ".bmp", ".gif", ".jpeg"};
    private final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String upload(MultipartFile file) {
        OSSClient ossClient = null;
        String url = null;
        try {
            // 创建 OSSClient 实例
            ossClient = new OSSClient(ConstantPropertiesUtil.END_POINT,
                    ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            boolean flag = false;

            // 判断文件格式
            for (String type : TYPE_STRING) {
                if (StringUtils.endsWithIgnoreCase(file.getOriginalFilename(), type)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                return "图片格式不正确";
            }
            // 判断文件内容
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image != null) {
                logger.error("image Height: " + image.getHeight());
                logger.error("image Width: " + image.getWidth());
            } else {
                return "文件内容不正确";
            }

            // 获取文件名称
            String filename = file.getOriginalFilename();
            // 文件名字: xxx.xxx.jpg
            // 获取后缀名
            String suffix = filename.substring(filename.lastIndexOf("."));
            String newName = UUID.randomUUID().toString() + suffix;
            String dataPath = new DateTime().toString("yyyy/MM/dd");
            String urlPath = ConstantPropertiesUtil.FILE_HOST + "/" + dataPath + "/" + newName;
            // 上传文件流
            InputStream inputStream = file.getInputStream();
            ossClient.putObject(ConstantPropertiesUtil.BUCKET_NAME, urlPath, inputStream);
            url = "https://" + ConstantPropertiesUtil.BUCKET_NAME + "." + ConstantPropertiesUtil.END_POINT + "/" + urlPath;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭OSSClient。
            assert ossClient != null;
            ossClient.shutdown();
        }

        return url;

    }
}
