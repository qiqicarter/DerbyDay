package com.dt.derbyday.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dt.derbyday.model.Question;
@Repository
public interface QuestionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Question record);

    int insertSelective(Question record);

    Question selectByPrimaryKey(Integer id);
    
    List<Question> selectByGame(String game);

    int updateByPrimaryKeySelective(Question record);

    int updateByPrimaryKey(Question record);
}