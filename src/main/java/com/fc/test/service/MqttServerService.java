package com.fc.test.service;
import com.fc.test.mapper.base.MqttServerMapper;
import com.fc.test.model.base.MqttServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class MqttServerService {
    @Autowired
    private MqttServerMapper mqttServerMapper;

    
    public void doCreate(MqttServer record) {
        Date date = new Date();
        record.setModifiedBy(0l);
        record.setModifiedAt(date);
        record.setCreatedBy(0l);
        record.setCreatedAt(date);
        mqttServerMapper.insert(record);
    }

    
    public void doUpdate(MqttServer record) {
        record.setModifiedBy(0l);
        record.setModifiedAt(new Date());
        mqttServerMapper.updateByPrimaryKey(record);
    }

    
    public MqttServer getById(Long id) {
        return mqttServerMapper.selectByPrimaryKey(id);
    }

    
    public void doDelete(List<Long> ids) {
        for (Long id : ids) {
            mqttServerMapper.deleteByPrimaryKey(id);
        }
    }

    public List<MqttServer> selectByParams(Map<String, Object> params) {
        return mqttServerMapper.selectByParams(params);
    }

}
