// 基于准备好的dom，初始化echarts实例
var myChart1 = echarts.init(document.getElementById('main1'));

function randomData1() {
    now1 = new Date(+now1 + oneDay1);
    value1 = value1 + Math.random() * 4 - 2;
    return {
        name: now1.toString(),
        value: [
            now1,
            Math.round(value1)
        ]
    }
}
var data1 = [];
var now1 = +new Date();
var oneDay1 = 2000;
var value1 = Math.random() * 5+20;
var params = {"host":"127.0.0.1","port":"1883","username":"siot","password":"password","topic":"DIY/TEST01",
"pageNum":1,"pageSize":10};
/*$.get("47.100.12.233:8088/mqtt/page",params,function(d){
    data1.push(d.list);
})*/
/*$.get("http://47.100.12.233:8088/mqtt/page?host=127.0.0.1&port=1883&username=siot&password=dfrobot&topic=DIY/TEST01&pageNum=1&pageSize=10",{},function(d){
    var list = d.list;
    for(var i =0;i<list.length;i++){
        var date = new Date(list[i].value[0]);
        list[i].value[0] = date;
        list[i].name = date.toString();
    }
    data1.push(list);
    var v = d.msg;
    var s = {
        name: now1.toString(),
        value: [
            now1,
            Math.round(v)
        ]
    }
    data1.push(v);
})*/
function getData1(host,port,pin1){
    $.get("http://47.100.12.233:8088/mqtt/getData", {"host":host,"port":port,"pin":pin1}, function (d) {

        now1 = new Date(+now1 + oneDay1);
        var v = d.data.msg;
        v = (v-32)*5/9;
        var s = {
            name: now1.toString(),
            value: [
                now1,
                v.toFixed(2)
            ]
        }
        data1.push(s);
        if(data1.length<10){
            getData1(host,port,pin1);
        }
        else{
            option1 = {
                title: {
                    text: '湿度随时间变化'
                },
                tooltip: {
                    trigger: 'axis',
                    formatter: function (params) {
                        params = params[0];
                        var date = new Date(params.name);
                        return date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear() + ' : ' + params.value[1];
                    },
                    axisPointer: {
                        animation: false
                    }
                },
                xAxis: {
                    type: 'time',
                    splitLine: {
                        show: false
                    }
                },
                yAxis: {
                    type: 'value',
                    boundaryGap: [0, '100%'],
                    splitLine: {
                        show: false
                    }
                },
                series: [{
                    name: '模拟数据',
                    type: 'line',
                    showSymbol: false,
                    hoverAnimation: false,
                    data: data1,
                    markLine: {
                        itemStyle: {
                            normal: {
                                borderWidth: 1,
                                lineStyle: {
                                    type: "dash",
                                    color: 'red',
                                    width: 2
                                },
                                label: {
                                    formatter: 'COD',
                                    textStyle: {
                                        fontSize: 16,
                                        fontWeight: "bolder"
                                    }
                                },
                                show: true,
                                color: '#4c5336'
                            }
                        }
                    },
                }],
            };
// 使用刚指定的配置项和数据显示图表。
            myChart1.setOption(option1);

            setInterval(function() {
                $.get("http://47.100.12.233:8088/mqtt/getData",{"host":host,"port":port,"pin":pin1},function(d1){
                    now1 = new Date(+now1 + oneDay1);
                    var v = d1.data.msg;
                    v = (v-32)*5/9;
                    var s = {
                        name: now1.toString(),
                        value: [
                            now1,
                            v.toFixed(2)
                        ]
                    }
                    data1.shift();
                    data1.push(s);
                    setTemperature1(s.value[1]);
                    myChart1.setOption({
                        series: [{
                            data: data1
                        }]
                    });
                })
            }, oneDay1);

        }
    })
}
//getData1();

/*
$.get("47.100.12.233:9004/lyn/mqtt/page?host=127.0.0.1&port=1883&username=siot&password=dfrobot&topic=DIY/TEST01&pageNum=1&pageSize=10\n",{},function(d){
    data1.push(d.list);
})
*/

/*
setInterval(function() {
    for(var i = 0; i < 1; i++) {
        data1.shift();
        var d1 = randomData1();
        data1.push(d1);
        setTemperature1(d1.value[1]);
    }
    myChart1.setOption({
        series: [{
            data: data1
        }]
    });
}, 1500);
*/
