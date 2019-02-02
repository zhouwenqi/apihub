package com.zhouwenqi.apihub.controller;

import com.zhouwenqi.apihub.core.entity.User;
import com.zhouwenqi.apihub.exception.ApihubNotParameterException;
import com.zhouwenqi.apihub.exception.ApihubParameterErrorException;
import com.zhouwenqi.apihub.service.FileService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Controller - 图片
 * Created by zhouwenqi on 2019/2/1.
 */
@RestController
@RequestMapping("file")
public class ImageController extends BaseController {
    @Autowired
    private FileService fileService;

    /**
     * 获取GridFS图片资源
     * @param id 文件id
     * @return
     * @throws IOException
     */
    @GetMapping(value = "image",produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] image(String id) throws IOException {
        if(StringUtils.isBlank(id)){
            throw new ApihubNotParameterException("文件id不能为空");
        }
        ObjectId fileId = new ObjectId(id);
        GridFsResource gridFsResource = fileService.findByFileId(fileId);
        if(null == gridFsResource){
            throw new ApihubParameterErrorException("文件不存在");
        }
        return IOUtils.toByteArray(gridFsResource.getInputStream());
    }
}
