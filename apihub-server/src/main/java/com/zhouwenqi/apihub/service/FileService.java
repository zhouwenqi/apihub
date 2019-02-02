package com.zhouwenqi.apihub.service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mortennobel.imagescaling.AdvancedResizeOp;
import com.mortennobel.imagescaling.ResampleOp;
import com.zhouwenqi.apihub.core.model.enums.FileType;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;

/**
 * Service - 文件
 * Created by zhouwenqi on 2019/1/30.
 */
@Service("fileService")
public class FileService {
    @Autowired
    private GridFsTemplate gridFsTemplate;

    /**
     * 保存图片文件
     * @param inputStream 文件输入流
     * @param fileName 文件名
     * @param fileType 文件类型
     */
    public ObjectId save(InputStream inputStream, String fileName, FileType fileType) {
        DBObject imageDb = new BasicDBObject();
        imageDb.put("createDate",new Date());
        imageDb.put("fileType",fileType);
        Criteria criteria = Criteria.where("filename").is(fileName);
        Query query = new Query(criteria);
        gridFsTemplate.delete(query);
        return gridFsTemplate.store(inputStream,fileName,imageDb);
    }

    /**
     * 获取数据库中文件资源
     * @param fileId 文件id
     * @return
     */
    public GridFsResource findByFileId(ObjectId fileId){
        Criteria criteria = Criteria.where("_id").is(fileId);
        Query query = new Query(criteria);
        GridFSFile gridFSFile =  gridFsTemplate.findOne(query);
        if(null == gridFSFile){
            return null;
        }
        return gridFsTemplate.getResource(gridFSFile);
    }

    /**
     * 上传图片
     * @param inputStream 图片流
     * @param fileName 文件名
     * @param imageFormat 图片格式
     * @param fileType 文件类型
     * @return
     * @throws IOException
     */
    public ObjectId uploadImage(InputStream inputStream,String fileName,String imageFormat, FileType fileType) throws IOException {
        ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream);
        Iterator iterator = ImageIO.getImageReadersByFormatName(imageFormat);
        ImageReader imageReader = (ImageReader)iterator.next();
        imageReader.setInput(imageInputStream);

        /**
         * 上传图片统一裁切为正方形
         * 以原图中心缩略
         */
        int width = imageReader.getWidth(0);
        int height = imageReader.getHeight(0);
        int scale = width < height ? width : height;
        int top = (height - scale)/2;
        int left = (width - scale)/2;

        final int scWidth = 400;
        final int scHeight = 400;

        ImageReadParam imageReadParam = imageReader.getDefaultReadParam();
        Rectangle rectangle = new Rectangle(left,top, scale, scale);
        imageReadParam.setSourceRegion(rectangle);
        BufferedImage bufferedImage = imageReader.read(0,imageReadParam);

        ResampleOp resampleOp = new ResampleOp(scWidth,scHeight);
        resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.Normal);

        BufferedImage resampleImage = resampleOp.filter(bufferedImage,null);
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(bs);
        ImageIO.write(resampleImage,"png",imageOutputStream);
        InputStream stream = new ByteArrayInputStream(bs.toByteArray());
        ObjectId photoId = save(stream,fileName, FileType.PHOTO);
        stream.close();
        return photoId;
    }

}
