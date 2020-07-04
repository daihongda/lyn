package com.fc.test.controller;

import com.alibaba.fastjson.JSONObject;
import com.fc.test.common.base.*;
import com.fc.test.dto.MessageDTO;
import com.fc.test.model.base.MqttMessage;
import com.fc.test.model.base.MqttServer;
import com.fc.test.service.MqttMessageService;
import com.fc.test.service.MqttServerService;
import com.fc.test.util.DateUtils;
import com.fc.test.util.HttpUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName: CityController
 * @Description: CityController
 * @Author: hongda.dai@youhualin.com
 * @Date: 2019/11/7 13:34
 * @Version: 1.0
 */
@RestController
@RequestMapping(value = "/mqtt")
public class MQTTController extends BaseController {
    @Autowired
    private MqttMessageService mqttMessageService;
    @Autowired
    private MqttServerService mqttServerService;
    private Map<String, String> topicMap = new HashMap<>();

    @RequestMapping(value = "/subsribe", method = RequestMethod.GET)
    public BaseResponse subscribe(@RequestParam(name = "host", required = false) String host,
                                  @RequestParam(name = "port", required = false) String port,
                                  @RequestParam(name = "topic", required = false) String topic,
                                  @RequestParam(name = "username", required = false) String username,
                                  @RequestParam(name = "password", required = false) String password) {
        BaseResponse result = new BaseResponse();
        MqttServer server = new MqttServer();
        server.setIp(host);
        server.setPort(port);
        server.setUsername(username);
        server.setPassword(password);
        //获取温度数据
        host = "tcp://" + host + ":" + port;
        Map<String, Object> params = new HashMap<>();
        params.put("ip", host);
        params.put("port", port);
        List<MqttServer> mqttServers = mqttServerService.selectByParams(params);
        if (!CollectionUtils.isEmpty(mqttServers)) {
            MqttServer old = mqttServers.get(0);
            server.setCreatedAt(old.getCreatedAt());
            server.setCreatedBy(old.getCreatedBy());
            server.setId(old.getId());
            mqttServerService.doUpdate(server);
        } else {
            mqttServerService.doCreate(server);
        }
        String key = host + "-" + port + "-" + topic;
        if ("BUSY".equals(topicMap.get(key))) {

        } else {
            MQTT_tcp con = new MQTT_tcp(host, username, password, topic, server.getId());
            Thread thread = new Thread(con);
            thread.start();
            topicMap.put(key, "BUSY");
        }
        return result;
    }

    @RequestMapping(value = "/publish", method = RequestMethod.GET)
    public BaseResponse publish(@RequestParam(name = "host", required = false) String host,
                                  @RequestParam(name = "port", required = false) String port,
                                  @RequestParam(name = "topic", required = false) String topic,
                                  @RequestParam(name = "username", required = false) String username,
                                  @RequestParam(name = "password", required = false) String password,
                                @RequestParam(name = "message", required = false) String message) {
        BaseResponse result = new BaseResponse();
        MqttServer server = new MqttServer();
        server.setIp(host);
        server.setPort(port);
        server.setUsername(username);
        server.setPassword(password);
        //获取温度数据
        host = "tcp://" + host + ":" + port;
        Map<String, Object> params = new HashMap<>();
        params.put("ip", host);
        params.put("port", port);
        List<MqttServer> mqttServers = mqttServerService.selectByParams(params);
        if (!CollectionUtils.isEmpty(mqttServers)) {
            MqttServer old = mqttServers.get(0);
            server.setCreatedAt(old.getCreatedAt());
            server.setCreatedBy(old.getCreatedBy());
            server.setId(old.getId());
            mqttServerService.doUpdate(server);
        } else {
            mqttServerService.doCreate(server);
        }
        try {
            MQTT_tcp con = new MQTT_tcp(host, username, password, topic, server.getId());
            con.publish(message);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }





    @RequestMapping(value = "/getImg", method = RequestMethod.GET)
    public BaseResponse getImg(@RequestParam(name = "host", required = false) String host,
                                @RequestParam(name = "port", required = false) String port,
                                @RequestParam(name = "topic", required = false) String topic) {
        BaseResponse result = new BaseResponse();
        Map<String,Object> params = new HashMap<>();
        params.put("host",host);
        params.put("port",port);
        params.put("queueName",topic);
        MqttMessage mqttMessage = mqttMessageService.selectNewImg(params);
        result.setData(mqttMessage);
        return result;
    }


    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public BaseResponse getData(@RequestParam(name = "host", required = false) String host,
                                  @RequestParam(name = "port", required = false) String port,
                                  @RequestParam(name = "pin", required = false, defaultValue = "A0") String pin) {
        BaseResponse result = new BaseResponse();
        String url = "http://"+host+":"+port+"/?pin="+pin;
//        String url = "http://192.168.0.106:1024/?pin=A0";
        String s = HttpUtil.getData(url);
        Object parse = JSONObject.parse(s);
        result.setData(parse);
        return result;
    }









    @RequestMapping(value = "/history", method = RequestMethod.POST)
    public BaseResponse history(@RequestParam(name = "host", required = false) String host,
                                @RequestParam(name = "port", required = false) String port,
                                @RequestParam(name = "topic", required = false) String topic,
                                @RequestParam(name = "mode", required = false) String mode,
                                @RequestParam(name = "type", required = false,defaultValue = "tem") String type) throws ParseException {
        //mode分为 day,week,month
        BaseResponse result = new BaseResponse();
        //获取温度数据
        Map<String,Object> params = new HashMap<>();
        params.put("ip",host);
        params.put("port",port);
        params.put("queueName",topic);
        //type 数据库中存放的值为0图片,1温度,2湿度,3光照
        Map<String,String> typeMap = new HashMap<>();
        typeMap.put("tem","1");
        typeMap.put("hum","2");
        typeMap.put("sun","3");
        params.put("type",typeMap.get(type));
        List<MessageDTO> messageDTOS = null;
//        Date now = new Date();
        Date now = DateUtils.parseDate("2020-05-05","yyyy-MM-dd");
        String beginTime = DateUtils.parseDateToStr("yyyy-MM-dd",now);
        String endTime = DateUtils.parseDateToStr("yyyy-MM-dd",DateUtils.addDays(now,1));
        switch(mode){
            case "day" :
                params.put("beginTime",beginTime);
                params.put("endTime",endTime);
                messageDTOS = mqttMessageService.selectByParamsGroupByHour(params);
                break;
            case "week" : beginTime = DateUtils.parseDateToStr("yyyy-MM-dd",DateUtils.addDays(now,-6));
                params.put("beginTime",beginTime);
                params.put("endTime",endTime);
                messageDTOS = mqttMessageService.selectByParamsGroupByDay(params);
                break;
            case "month" : beginTime = DateUtils.parseDateToStr("yyyy-MM-dd",DateUtils.addDays(now,-30));
                params.put("beginTime",beginTime);
                params.put("endTime",endTime);
                messageDTOS = mqttMessageService.selectByParamsGroupByDay(params);
                break;
        }
        Map<String,Object> datas = new HashMap<>();
        datas.put("x",messageDTOS.stream().map(d->d.getName()).sorted().collect(Collectors.toList()));
        datas.put("y",messageDTOS.stream().map(d->d.getValue()).sorted().collect(Collectors.toList()));
        /*List<Map<String,Object>> datas =  new ArrayList<>();
        for(MessageDTO d : messageDTOS){
            Map<String,Object> data = new HashMap<>();
            data.put("name",d.getName());
            List<Object> values = new ArrayList<>();
            String pattern = "yyyy-MM-dd";
            if(mode.equals("day")){
                pattern = "yyyy-MM-dd HH";
            }
            try {
                Date date = DateUtils.parseDate(d.getName(), pattern);
                values.add(date);
                String value = d.getValue();
                if(type.equals("tem")){
                    //value = String.format("%.2f",(Double.parseDouble(value)-32)*5/9);
                }
                else if(type.equals("hum")){

                }
                values.add(value);
                data.put("value",values);
                datas.add(data);
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }*/
        result.setData(datas);
        return result;
    }


    @RequestMapping(value = "/getImgList", method = RequestMethod.GET)
    public PageInfo<MessageDTO> getImgList(PageParam pageParam,@RequestParam(name = "host", required = false) String host,
                                @RequestParam(name = "port", required = false) String port,
                                @RequestParam(name = "topic", required = false) String topic) {
        //获取温度数据
        Map<String,Object> params = new HashMap<>();
        params.put("ip",host);
        params.put("port",port);
        params.put("queueName",topic);
        params.put("type","0");
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize(), "created_by asc");
        List<MqttMessage> list = mqttMessageService.selectByParams(params);
        PageInfo<MqttMessage> r = new PageInfo<MqttMessage>(list);
        List<MessageDTO> datas = new ArrayList<>();
        for(MqttMessage d : list){
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setName(DateUtils.parseDateToStr("yyyy-MM-dd HH:mm",d.getCreatedAt()));
            messageDTO.setValue("/static/front/images/observation/"+d.getMessage());//消息里存得是保存路径
            datas.add(messageDTO);
        }
        PageInfo<MessageDTO> result = new PageInfo<MessageDTO>(datas);
        result.setTotal(r.getTotal());
        return result;
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public PageInfo<Map<String, Object>> queryByPage(PageParam pageParam, @RequestParam(name = "host", required = false) String host,
                                                     @RequestParam(name = "port", required = false) String port,
                                                     @RequestParam(name = "topic", required = false) String topic,
                                                     @RequestParam(name = "username", required = false) String username,
                                                     @RequestParam(name = "password", required = false) String password) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ip", host);
        params.put("port", port);
        params.put("username", username);
        params.put("password", password);
        params.put("queueName", topic);
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize(), "created_by asc");
        List<MqttMessage> list = mqttMessageService.selectByParams(params);
        PageInfo<MqttMessage> r = new PageInfo<MqttMessage>(list);
        PageInfo<Map<String, Object>> result = new PageInfo<Map<String, Object>>(assembleMQTTMessageVO(list));
        result.setTotal(r.getTotal());
        return result;
    }

    private List<Map<String, Object>> assembleMQTTMessageVO(List<MqttMessage> list) {
        List<Map<String,Object>> datas =  new ArrayList<>();
        for(MqttMessage d : list){
            Map<String,Object> data = new HashMap<>();
            data.put("name",d.getCreatedAt().toString());
            List<Object> values = new ArrayList<>();
            values.add(d.getCreatedAt());
            values.add(d.getMessage());
            data.put("value",values);
            datas.add(data);
        }
        return datas;
    }


}
