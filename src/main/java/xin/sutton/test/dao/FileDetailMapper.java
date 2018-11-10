package xin.sutton.test.dao;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.*;
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

    @Insert("insert into t_filedetail(file_name,file_url,file_uploader,upload_time) values(#{fileName},#{fileUrl},#{fileUploader},#{uploadTime})")
    int insertFile(FileDetail fileDetail);

    @Select("select file_name as fileName,file_url as fileUrl,file_uploader as fileUploader from t_filedetail where id=#{id}")
    FileDetail getFileDetail(int id);

    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "fileName", column = "file_name"),
            @Result(property = "fileUrl", column = "file_url"),
            @Result(property = "fileUploader", column = "file_uploader"),
            @Result(property = "uploadTime", column = "upload_time")
    })
    @Select("select id,file_name,file_url,file_uploader,upload_time from t_filedetail")
    List<FileDetail> getFileDetails();

    @Insert("<script>" +
            "insert into t_filedetail(file_name,file_url,file_uploader) values" +
            "<foreach collection=\"list\" item=\"item1\" index=\"index\" separator=\",\">" +
            "(#{item1.fileName},#{item1.fileUrl},#{item1.fileUploader})" +
            "</foreach>" +
            "</script>")
    int batchInsert(@Param("list") List<FileDetail> fileDetails);

    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "fileName", column = "file_name"),
            @Result(property = "fileUrl", column = "file_url"),
            @Result(property = "fileUploader", column = "file_uploader"),
            @Result(property = "uploadTime", column = "upload_time")
    })
    @Select({"<script>"+
            "select id,file_name,file_url,file_uploader,upload_time from t_filedetail " +
            "where upload_time >= #{beginTime} " +
            "and upload_time &lt;= #{endTime} " +
            "<if test='nickname != null'>" +
            "and file_uploader like #{nickname}" +
            "</if>" +
            "</script>"}
            )
    List<FileDetail> selectBetweenDate(@Param("beginTime") long beginTime,@Param("endTime") long endTime,@Param("nickname") String nickname);
}
