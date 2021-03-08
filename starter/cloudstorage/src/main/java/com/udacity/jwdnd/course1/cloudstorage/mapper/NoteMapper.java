package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import org.apache.ibatis.annotations.*;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    Note[] getAllNotes(Integer userId);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{title}, #{description}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer addNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{id}")
    Integer deleteNote(Integer noteId);

    @Update("UPDATE NOTES SET notetitle = #{title}, notedescription = #{description} WHERE noteid = #{id}")
    Integer updateNote(Note note);
}
