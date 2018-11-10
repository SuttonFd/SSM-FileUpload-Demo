package xin.sutton.test.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import xin.sutton.test.bo.FileDetail;
import xin.sutton.test.config.RootConfig;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = RootConfig.class)
public class FileDetailMapperTest {

    @Autowired
    FileDetailMapper fileDetailMapper;

    @Test
    public void insertFile() {
        FileDetail fileDetail = FileDetail.builder()
                .fileName("dog.jpg")
                .fileUrl("http://120.77.180.199:8090/img/dog.jpg")
                .fileUploader("勺子")
                .build();

        Assert.assertEquals(1,fileDetailMapper.insertFile(fileDetail));
    }

    @Test
    public void getFileDetail() {
        FileDetail fileDetail = fileDetailMapper.getFileDetail(1);
        Assert.assertEquals("dog.jpg",fileDetail.getFileName());
    }

    @Test
    public void getFileDetails() {
        List<FileDetail> fileDetails = fileDetailMapper.getFileDetails();
        System.out.println(fileDetails.get(1).getUploadTime());
    }


    @Test
    public void selectBetweenDateTest(){
        try{
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            List<FileDetail> fileDetails = fileDetailMapper.selectBetweenDate(df.parse("2018-11-07").getTime(),df.parse("2018-11-10").getTime(),null);
            System.out.println(fileDetails.get(0).getId());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}