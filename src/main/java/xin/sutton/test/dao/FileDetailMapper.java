package xin.sutton.test.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import xin.sutton.test.bo.FileDetail;

import java.util.List;

/**
 * TODO...
 *
 * @author codingZhengsz
 * @since 2018-10-23 21:14
 **/
@Repository
public interface FileDetailMapper {

    @Insert("insert into t_filedetail(file_name,file_url,file_uploader) values(#{fileName},#{fileUrl},#{fileUploader})")
    int insertFile(FileDetail fileDetail);

    @Select("select file_name as fileName,file_url as fileUrl,file_uploader as fileUploader from t_filedetail where id=#{id}")
    FileDetail getFileDetail(int id);

    @Select("select id,file_name as fileName,file_url as fileUrl,file_uploader as fileUploader,upload_time as uploadTime from t_filedetail")
    List<FileDetail> getFileDetails();

    @Insert("<script>" +
            "insert into t_filedetail(file_name,file_url,file_uploader) values" +
            "<foreach collection=\"list\" item=\"item1\" index=\"index\" separator=\",\">" +
            "(#{item1.fileName},#{item1.fileUrl},#{item1.fileUploader})" +
            "</foreach>" +
            "</script>")
    int batchInsert(@Param("list") List<FileDetail> fileDetails);
}
