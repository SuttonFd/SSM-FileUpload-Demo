package xin.sutton.test.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author codingZhengsz
 * @since 2018-10-23 20:29
 **/
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDetail {

    private int id;
    private String fileName;
    private String fileUrl;
    private String fileUploader;
    private Date uploadTime;

}
