package com.cvc.cvcms.controller;

import com.cvc.cvcms.common.ErrorEnum;
import com.cvc.cvcms.common.FtpUtil;
import com.cvc.cvcms.common.JsonStandard;
import com.cvc.cvcms.pojo.Advertisement;
import com.cvc.cvcms.service.AdvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: XiuGitHung
 * @Date: 2021/4/1 22:34
 * @Description:
 */
@RestController
@MultipartConfig
public class AdvController {

    @Autowired
    AdvService advService;

    @Autowired
    FtpUtil ftpUtil;

    @PostMapping("/advertisement/release")
    //    @PreAuthorize("hasAuthority('')")
    public JsonStandard release(MultipartFile upload, HttpServletRequest req, String detail, @DateTimeFormat(pattern="yyyy-MM-dd") Date startTime,@DateTimeFormat(pattern="yyyy-MM-dd") Date endTime) throws IOException {
        if(upload.isEmpty()){
            return JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,"请先选择图片");
        }

        String filename = UUID.randomUUID().toString().replace("-","_")
                + upload.getOriginalFilename().substring(upload.getOriginalFilename().lastIndexOf('.'));
        FileInputStream in = (FileInputStream) upload.getInputStream();
        if(ftpUtil.uploadToFtp(filename,in)){
            boolean flag = advService.release(filename,detail,startTime,endTime);
            if(flag) {
                return JsonStandard.success("发布成功");
            }
        }
          return JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,"发布异常");
    }

    @PostMapping("/advertisement/delete")
    //    @PreAuthorize("hasAuthority('')")
    public JsonStandard delete(Integer advertisementId){
        Advertisement advertisement = advService.findAdvertisementById(advertisementId);
        if(advertisement == null) {
            return JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,"no this advertisement");
        }else {
            advService.deleteAdvertisement(advertisementId);
            return JsonStandard.success("删除成功");
        }

    }

    @PostMapping("/advertisement/update")
    //    @PreAuthorize("hasAuthority('')")
    public JsonStandard update(@RequestParam(required = false)MultipartFile upload,
                               Integer advertisementId,
                               @RequestParam(required = false) String detail,
                               @RequestParam(required = false)@DateTimeFormat(pattern="yyyy-MM-dd") Date startTime,
                               @RequestParam(required = false)@DateTimeFormat(pattern="yyyy-MM-dd") Date endTime,
                               @RequestParam(required = false) Integer status) throws  IOException{
        Advertisement advertisement = advService.findAdvertisementById(advertisementId);
        if(advertisement == null) {
            return JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,"no this advertisement");
        }
        if(upload != null) {
            String filename = UUID.randomUUID().toString().replace("-","_");
            FileInputStream in = (FileInputStream) upload.getInputStream();
            if(ftpUtil.uploadToFtp(filename,in)){
                advertisement.setImg(filename);
            }
        }
        if(detail != null) {
            advertisement.setDetail(detail);
        }
        if(startTime != null) {
            advertisement.setStartTime(startTime);
        }
        if(endTime != null) {
            advertisement.setEndTime(endTime);
        }
        if(status != null) {
            advertisement.setStatus(status);
        }
        advService.updateAdvertisement(advertisement);
        return JsonStandard.success("更改成功");
    }

    @PostMapping("/advertisement/get")
    //    @PreAuthorize("hasAuthority('')")
    public JsonStandard getAdvertisementById(Integer advertisementId) {
        Advertisement advertisement = advService.findAdvertisementById(advertisementId);
        if(advertisement==null){
            return JsonStandard.success("no this advertisement");
        }
        Map<String, Object> result = new HashMap<>(1);
        result.put("advertisement", advertisement);
        return JsonStandard.success(result);
    }

    @GetMapping("/advertisements")
    //    @PreAuthorize("hasAuthority('')")
    public JsonStandard getAllAdvertisement() {
        List<Advertisement> advertisementList = advService.findALLAdvertisement();
        Map<String, Object> result = new HashMap<>(1);
        result.put("advertisements",advertisementList);
        return JsonStandard.success(result);
    }
    @PostMapping("/advertisement/undercarriage")
    //    @PreAuthorize("hasAuthority('')")
    public JsonStandard undercarriage(Integer advertisementId ) throws  IOException{
        Advertisement advertisement = advService.findAdvertisementById(advertisementId);
        if(advertisement == null) {
            return JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,"no this advertisement");
        }

        advertisement.setStatus(0);


        advService.updateAdvertisement(advertisement);
        return JsonStandard.success("更改成功");
    }

    @PostMapping("/advertisement/publish")
    //    @PreAuthorize("hasAuthority('')")
    public JsonStandard publish(Integer advertisementId ) throws  IOException{
        Advertisement advertisement = advService.findAdvertisementById(advertisementId);
        if(advertisement == null) {
            return JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,"no this advertisement");
        }

        advertisement.setStatus(1);


        advService.updateAdvertisement(advertisement);
        return JsonStandard.success("更改成功");
    }

    @GetMapping("/advertisements/get/published_advertisements")
    //    @PreAuthorize("hasAuthority('')")
    public JsonStandard getAllPublishedAdvertisement() {
        List<Advertisement> advertisementList = advService.findALLPublishedAdvertisement();
        Map<String, Object> result = new HashMap<>(1);
        result.put("advertisements",advertisementList);
        return JsonStandard.success(result);
    }



}
