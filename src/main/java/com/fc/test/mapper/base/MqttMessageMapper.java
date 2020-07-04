package com.fc.test.mapper.base;

import com.fc.test.dto.MessageDTO;
import com.fc.test.model.base.MqttMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MqttMessageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MqttMessage record);

    MqttMessage selectByPrimaryKey(Long id);

    int updateByPrimaryKey(MqttMessage record);

    List<MqttMessage> selectByParams(@Param("params") Map<String, Object> params);

    List<MessageDTO> selectByParamsGroupByHour(@Param("params") Map<String, Object> params);

    List<MessageDTO> selectByParamsGroupByDay(@Param("params") Map<String, Object> params);

    MqttMessage selectNewImg(@Param("params") Map<String, Object> params);
}