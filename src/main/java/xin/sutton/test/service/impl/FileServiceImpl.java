package xin.sutton.test.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xin.sutton.test.bo.FileDetail;
import xin.sutton.test.dao.FileDetailMapper;
import xin.sutton.test.service.FileService;
import xin.sutton.test.vo.PageResult;
import xin.sutton.test.vo.ServerResponse;

import java.util.List;

/**
 * @author codingZhengsz
 * @since 2018-10-23 20:27
 **/
@Service("fileService")
public class FileServiceImpl implements FileService {

    @Autowired
    FileDetailMapper fileDetailMapper;

    public ResponseEntity<ServerResponse> insertFileDetail(FileDetail fileDetail){
        return fileDetailMapper.insertFile(fileDetail) > 0 ?
                new ResponseEntity<>(ServerResponse.createBySuccessMessage("上传成功"), HttpStatus.OK):
                new ResponseEntity<>(ServerResponse.createByErrorMessage("上传失败"), HttpStatus.OK);
    }

    public ResponseEntity<ServerResponse> batchInsert(List<FileDetail> fileDetails) {
        int result = fileDetailMapper.batchInsert(fileDetails);
        if(fileDetails.size() == result) {
            return new ResponseEntity<>(ServerResponse.createBySuccessMessage("上传成功"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ServerResponse.createByErrorMessage("上传失败"), HttpStatus.OK);
        }
    }

    public ResponseEntity<ServerResponse<FileDetail>> getFileDetail(int id) {
        return new ResponseEntity<>(ServerResponse.createBySuccess(fileDetailMapper.getFileDetail(id)), HttpStatus.OK);
    }

    public ResponseEntity<PageResult<FileDetail>> getFileDetails(int pageNum, int pageSize) {
        List<FileDetail> list = fileDetailMapper.getFileDetails();
        return new ResponseEntity<>(new PageResult<>(list), HttpStatus.OK);
    }

    public List<FileDetail> getAllFileDetails() {
        return fileDetailMapper.getFileDetails();
    }

    public PageInfo<FileDetail> getPicList(Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<FileDetail> list = fileDetailMapper.getFileDetails();
        PageInfo<FileDetail> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public PageInfo<FileDetail> searchByDateAndNickname(long beginTime,long endTime,String nickname,Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<FileDetail> list = fileDetailMapper.selectBetweenDate(beginTime,endTime,nickname);


        PageInfo<FileDetail> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
