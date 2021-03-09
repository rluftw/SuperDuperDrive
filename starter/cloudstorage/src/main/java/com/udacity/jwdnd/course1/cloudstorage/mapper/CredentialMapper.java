package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.Model.internal.Credential;

import org.apache.ibatis.annotations.*;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    Credential[] getAllCredentials(Integer userId);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES(#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer addCredential(Credential credential);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, key = #{key}, password = #{password}, userid = #{userId} WHERE credentialid = #{id}")
    Integer updateCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{id}")
    Integer deleteCredential(Integer credentialId);
}
