package com.fc.test.common.base;

import com.alibaba.fastjson.JSONObject;
import com.fc.test.common.spring.SpringUtils;
import com.fc.test.mapper.base.MqttMessageMapper;
import com.fc.test.service.MqttMessageService;
import com.fc.test.util.Base64Util;
import com.fc.test.util.DateUtils;
import org.apache.shiro.crypto.hash.Hash;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * 功能描述：MQTT测试
 * 创建人： mao2080@sina.com
 * 创建时间：2017年7月4日 下午5:08:59
 * 修改人： mao2080@sina.com
 * 修改时间：2017年7月4日 下午5:08:59
 */
public class MQTT_tcp implements Runnable{
    private MqttMessageService mqttMessageService;
    /**MQTT服务端ip及端口*/
    private String host = "tcp://localhost:1883";

    /**账号*/
    private String username = "siot";

    /**密码*/
    private String password = "dfrobot";

    /**订阅的主题*/
    private String subTopic = "DIY/TEST01";

    /**clientID*/
    private String clientId = "li2080";

    /**发布的主题*/
    private String pubTopic = "DIY/TEST01";

    /**MQTT-Client*/
    private MqttClient client;

    /**MQTT-Client*/
    private int uuid = 0;

    private Long serverId;

    public MQTT_tcp(String host, String username, String password, String topic){
        this.host = host;
        this.username = username;
        this.password = password;
        this.subTopic = topic;
        this.pubTopic = topic;
    }


    public MQTT_tcp(String host, String username, String password, String topic, Long serverId){
        this.host = host;
        this.username = username;
        this.password = password;
        this.subTopic = topic;
        this.pubTopic = topic;
        this.serverId = serverId;
    }

    
    /**
     * @throws InterruptedException
     * @throws MqttException */
    public void main(String[] args) throws InterruptedException, MqttException {

        // 订阅消息的方法
        //subscribe();
//
        //publish();
    }

    /**
     *
     * 描述：订阅信息
     * @author mao2080@sina.com
     * @created 2017年7月4日 下午4:53:47
     * @since
     * @return
     */
    public  void subscribe() {
        final List<com.fc.test.model.base.MqttMessage> datas = new ArrayList<>();
        try {
            // 创建MqttClient
            getClient().setCallback(new MqttCallback() {

                public void connectionLost(Throwable arg0) {

                }

                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.println("MQTT Rece:" + message.toString());
                    com.fc.test.model.base.MqttMessage data = new com.fc.test.model.base.MqttMessage();
                    //说明是图片base64编码
                    String msg = message.toString();
                    if(msg.length()>500){
                        JSONObject jsonObject = JSONObject.parseObject(msg);
                        String base64 = jsonObject.getString("base64");
                        base64 = base64.substring(2, base64.length()-1);
                        Date date = new Date();
                        String imageName = DateUtils.format(date, "yyyyMMddHHmm")+".jpg";
                        String path = "E:\\workspace\\SpringBoot_v2\\src\\main\\resources\\static\\front\\images\\observation\\"+imageName;
                        boolean b = Base64Util.Base64ToImage(base64.toString(),path);
                        data.setMessage(imageName);
                        data.setType("0");
                    }
                    else {
                        JSONObject jsonObject = JSONObject.parseObject(msg);
                        String type = jsonObject.getString("type");
                        String value = jsonObject.getString("value");
                        data.setMessage(value);
                        data.setType(type);
                    }
                    data.setQueueName(subTopic);
                    data.setServerId(serverId);
                    datas.add(data);
                }

                public void deliveryComplete(IMqttDeliveryToken token) {

                }
            });
            getClient().subscribe(subTopic, 0);
            System.out.println("连接状态:" + client.isConnected());
        } catch (Exception e) {
            e.printStackTrace();
        }
        while(true){
            if(datas.size()>0) {
                for (int i = 0; i < datas.size(); i++) {
                    com.fc.test.model.base.MqttMessage mqttMessage = datas.get(0);
                    if(mqttMessageService==null) {
                        mqttMessageService = SpringUtils.getBean(MqttMessageService.class);
                    }
                    mqttMessageService.doCreate(mqttMessage);
                }
                datas.clear();
            }
        }
    }

    /**
     *
     * 描述：获取MqttClient
     * @author mao2080@sina.com
     * @created 2017年7月6日 上午9:56:37
     * @since
     * @return
     * @throws MqttException
     */
    public  MqttClient getClient() throws MqttException{
        try {
            if(client == null){
                client = new MqttClient(host, clientId);
                MqttConnectOptions conOptions = new MqttConnectOptions();
                conOptions.setUserName(username);
                conOptions.setPassword(password.toCharArray());
                conOptions.setCleanSession(true);
                client.connect(conOptions);
            }
            if(!client.isConnected()){
                client.reconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }

    /**
     *
     * 描述：发布信息
     * @author mao2080@sina.com
     * @throws MqttException
     * @created 2017年7月4日 下午4:53:32
     * @since
     */
    public  void publish(String msg) throws MqttException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            MqttTopic topic = getClient().getTopic(pubTopic);
            MqttMessage message = new MqttMessage(msg.getBytes());
            message.setQos(0);
            topic.publish(message);
            System.out.println("MQTT Send:" + msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
       subscribe();
    }


}
