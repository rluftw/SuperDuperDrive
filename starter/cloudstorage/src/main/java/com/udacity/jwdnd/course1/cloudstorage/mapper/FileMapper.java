package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.Model.internal.File;
import org.apache.ibatis.annotations.*;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    File[] getAllFiles(Integer userId);

    @Select("SELECT * FROM FILES WHERE filename = #{fileName}")
    File getFile(String filename);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File getFileFromId(Integer fileId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer addFile(File file);

    @Delete("DELETE FROM FILES where fileId = #{fileId}")
    Integer deleteFile(Integer fileId);
}