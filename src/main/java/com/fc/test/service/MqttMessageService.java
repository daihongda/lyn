package com.fc.test.service;
import com.fc.test.dto.MessageDTO;
import com.fc.test.mapper.base.MqttMessageMapper;
import com.fc.test.model.base.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class MqttMessageService {
    @Autowired
    private MqttMessageMapper mqttMessageMapper;

    
    public void doCreate(MqttMessage record) {
        Date date = new Date();
        record.setModifiedBy(0l);
        record.setModifiedAt(date);
        record.setCreatedBy(0l);
        record.setCreatedAt(date);
        mqttMessageMapper.insert(record);
    }

    
    public void doUpdate(MqttMessage record) {
        record.setModifiedBy(0l);
        record.setModifiedAt(new Date());
        mqttMessageMapper.updateByPrimaryKey(record);
    }

    
    public MqttMessage getById(Long id) {
        return mqttMessageMapper.selectByPrimaryKey(id);
    }

    
    public void doDelete(List<Long> ids) {
        for (Long id : ids) {
            mqttMessageMapper.deleteByPrimaryKey(id);
        }
    }

    
    public List<MqttMessage> selectByParams(Map<String, Object> params) {
        return mqttMessageMapper.selectByParams(params);
    }


    public List<MessageDTO> selectByParamsGroupByHour(Map<String, Object> params) {
        return mqttMessageMapper.selectByParamsGroupByHour(params);
    }


    public List<MessageDTO> selectByParamsGroupByDay(Map<String, Object> params) {
        return mqttMessageMapper.selectByParamsGroupByDay(params);
    }


    public MqttMessage selectNewImg(Map<String,Object> params){
        return mqttMessageMapper.selectNewImg(params);
    }
}
