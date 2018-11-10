package xin.sutton.test.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xin.sutton.test.authorization.annotation.Authorization;
import xin.sutton.test.authorization.annotation.CurrentUser;
import xin.sutton.test.bo.FileDetail;
import xin.sutton.test.bo.User;
import xin.sutton.test.service.FileService;
import xin.sutton.test.vo.PageResult;
import xin.sutton.test.vo.ServerResponse;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author codingZhengsz
 * @since 2018-10-23 20:23
 **/
@CrossOrigin(origins = "*", maxAge = 3600) // 跨域问题，允许任何域名请求
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    FileService fileService;

    @GetMapping("/detail/{id}")
    public ResponseEntity<ServerResponse<FileDetail>> getFileDetail(@PathVariable int id) {
        return fileService.getFileDetail(id);
    }

    @Authorization
    @PostMapping("/uploadFile")
    public ServerResponse uploadFile(@RequestParam("fileInput") MultipartFile file, HttpServletRequest request,@CurrentUser User user) throws Exception{
        StringBuffer sb = new StringBuffer();
        sb.append(UUID.randomUUID().toString().replace("-",""));

        String filename = file.getOriginalFilename();
        sb.append(filename.substring(filename.indexOf(".")));

        String url = request.getSession().getServletContext().getRealPath("/upload");
        System.out.println(url);
        String fileUrl = url + "/" + sb.toString();
        file.transferTo(new File(fileUrl));

        FileDetail detail = FileDetail.builder()
                .fileName(filename)
                .fileUrl("http://localhost:8888/upload/"+sb.toString())
                .fileUploader(user.getNickname())
                .uploadTime(new Date().getTime())
                .build();
        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(new Date().getTime()));
        fileService.insertFileDetail(detail);

        return ServerResponse.createByErrorMessage("上传成功");
    }

    @RequestMapping("/picList")
    public ServerResponse getPicList(Integer pageNum,Integer pageSize){
        PageInfo<FileDetail> pageInfo = fileService.getPicList(pageNum, pageSize);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @RequestMapping("/searchList")
    public ServerResponse searchList(Integer pageNum,Integer pageSize,String beginDate,String endDate,String nickname){
        long beginTime = 0,endTime = 0;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try{
            if(StringUtils.isEmpty(beginDate)){
                beginTime = df.parse("2018-01-01").getTime();
            }else{
                beginTime = df.parse(beginDate).getTime();
            }
            if(StringUtils.isEmpty(endDate)){
                endTime = new Date().getTime();
            }else{
                endTime = df.parse(endDate).getTime() + (1000 * 60 * 60 * 24);
            }
        } catch(Exception e) {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        PageInfo<FileDetail> pageInfo = fileService.searchByDateAndNickname(beginTime,endTime,"%"+nickname+"%",pageNum,pageSize);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @GetMapping("/allPic")
    public Map<String,Object> fileDetailPageResult(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Map<String,Object> modelMap = new HashMap<String,Object>();
        List<FileDetail> fileDetails = fileService.getAllFileDetails();
        int rowCount = fileDetails.size();
        if(rowCount%pageSize != 0) {
            rowCount = rowCount / pageSize + 1;
        } else {
            rowCount = rowCount / pageSize;
        }
        List<FileDetail> fileDetails1 = new ArrayList<>();
        int start = (pageNum-1)*pageSize;
        int end = pageNum*pageSize;
        if(end > fileDetails.size()){
            end = fileDetails.size();
        }
        for(int i=start;i<end;i++){
            fileDetails1.add(fileDetails.get(i));
        }

        System.out.println(rowCount);
        modelMap.put("pageCount",rowCount);
        modelMap.put("currentPage",pageNum);
        modelMap.put("list",fileDetails1);
        return modelMap;
    }

}
