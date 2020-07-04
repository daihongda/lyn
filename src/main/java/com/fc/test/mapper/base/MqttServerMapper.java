package com.fc.test.mapper.base;

import com.fc.test.model.base.MqttServer;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MqttServerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MqttServer record);

    MqttServer selectByPrimaryKey(Long id);

    int updateByPrimaryKey(MqttServer record);

    List<MqttServer> selectByParams(@Param("params") Map<String,Object> params);
}