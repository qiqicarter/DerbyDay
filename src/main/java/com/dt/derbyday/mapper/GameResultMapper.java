package com.dt.derbyday.mapper;

import org.springframework.stereotype.Repository;
import com.dt.derbyday.model.GameResult;
@Repository
public interface GameResultMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GameResult record);

    int insertSelective(GameResult record);

    GameResult selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GameResult record);

    int updateByPrimaryKey(GameResult record);
}