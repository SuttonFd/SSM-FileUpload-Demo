package xin.sutton.test.service;

import org.springframework.http.ResponseEntity;
import xin.sutton.test.bo.FileDetail;
import xin.sutton.test.vo.PageResult;
import xin.sutton.test.vo.ServerResponse;

import java.util.List;

/**
 * @author codingZhengsz
 * @since 2018-10-23 20:27
 **/
public interface FileService {

    ResponseEntity<ServerResponse> insertFileDetail(FileDetail fileDetail);

    ResponseEntity<ServerResponse> batchInsert(List<FileDetail> fileDetails);

    ResponseEntity<ServerResponse<FileDetail>> getFileDetail(int id);

    ResponseEntity<PageResult<FileDetail>> getFileDetails(int pageNum, int pageSize);

    List<FileDetail> getAllFileDetails();

}
