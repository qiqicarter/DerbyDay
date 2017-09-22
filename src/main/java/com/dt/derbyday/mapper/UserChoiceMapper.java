package com.dt.derbyday.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dt.derbyday.dto.UserChoiceDisplay;
import com.dt.derbyday.model.UserChoice;

@Repository
public interface UserChoiceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserChoice record);

    int insertSelective(UserChoice record);

    UserChoice selectByPrimaryKey(Integer id);
    
    List<UserChoice> selectByGameAndQuestionAndChoice(UserChoice uc);
    
    List<UserChoice> selectByGameAndQuestion(UserChoiceDisplay uc);
    
    List<UserChoice> selectByGameAndNoQuestion(UserChoiceDisplay uc);
    
    List<UserChoiceDisplay> selectHistory(UserChoice record);

    int updateByPrimaryKeySelective(UserChoice record);

    int updateByPrimaryKey(UserChoice record);
    
    int selectQuestionCount(Integer question);
    
    int selectChoiceCount(Integer choice);
}