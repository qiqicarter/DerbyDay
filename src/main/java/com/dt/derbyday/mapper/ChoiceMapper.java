package com.dt.derbyday.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dt.derbyday.model.Choice;
@Repository
public interface ChoiceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Choice record);

    int insertSelective(Choice record);

    Choice selectByPrimaryKey(Integer id);
    
    List<Choice> selectByQuestionId(Integer questionId);

    int updateByPrimaryKeySelective(Choice record);

    int updateByPrimaryKey(Choice record);
}