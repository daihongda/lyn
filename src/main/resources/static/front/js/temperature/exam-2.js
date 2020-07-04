// 基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('main'));
var myChart1 = echarts.init(document.getElementById('main1'));
var myChart2 = echarts.init(document.getElementById('main2'));
var myChart3 = echarts.init(document.getElementById('main3'));
var myChart4 = echarts.init(document.getElementById('main4'));
var myChart5 = echarts.init(document.getElementById('main5'));
var myChart6 = echarts.init(document.getElementById('main6'));
var myChart7 = echarts.init(document.getElementById('main7'));
var myChart8 = echarts.init(document.getElementById('main8'));
var myChart9 = echarts.init(document.getElementById('main9'));
var myChart10 = echarts.init(document.getElementById('main10'));
var myChart11 = echarts.init(document.getElementById('main11'));


function randomData() {
    now = new Date(+now + oneDay);
    value = value + Math.random() * 4 - 2;
    return {
        name: now.toString(),
        value: [
            now,
            Math.round(value)
        ]
    }
}
var data = [];
var now = +new Date();
var oneDay = 2000;
var value = Math.random() * 10+30;

function getData(host,port,pin1,type){
    $.get("http://47.100.12.233:8088/mqtt/getData", {"host":host,"port":port,"pin":pin1}, function (d) {
        now = new Date(+now + oneDay);
        var v = d.data.msg;
        if(type=='tem') {
            v = (v - 32) * 5 / 9;
        }
        else{
            v = v;
        }
        var s = {
            name: now.toString(),
            value: [
                now,
                v.toFixed(2)
            ]
        }
        data.push(s);
        if(data.length<10){
            getData(host,port,pin1);
        }
        else{
            option = {
                title: {
                    text: '温度随时间变化'
                },
                tooltip: {
                    trigger: 'axis',
                    formatter: function(params) {
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
                    data: data,
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
            if(type=='tem') {
                myChart.setOption(option);
            }
            else if(type=='hum'){
                myChart4.setOption(option);
            }
            else{
                myChart7.setOption(option);
            }

            setInterval(function() {
                $.get("http://47.100.12.233:8088/mqtt/getData",{"host":host,"port":port,"pin":pin1},function(d1){
                    now = new Date(+now + oneDay);
                    var v = d1.data.msg;
                    if(type=='tem') {
                        v = (v - 32) * 5 / 9;
                    }
                    else{
                        v = v;
                    }
                    var s = {
                        name: now.toString(),
                        value: [
                            now,
                            v.toFixed(2)
                        ]
                    }
                    data.shift();
                    data.push(s);
                    if(type=='tem') {
                        myChart.setOption({
                            series: [{
                                data: data
                            }]
                        });
                    }
                    else{
                        myChart4.setOption({
                            series: [{
                                data: data
                            }]
                        });
                    }

                })
            }, oneDay);

        }
    })
}



function getData1(host,port,topic,mode,type){
    //day,week,month 对应main1,main2,main3
    $.post("http://47.100.12.233:8088/mqtt/history", {"host":host,"port":port,"topic":topic,"mode":mode,"type":type}, function (d) {
        option = {
            xAxis: {
                type: 'category',
                data: d.data.x
            },
            yAxis: {
                type: 'value'
            },
            series: [{
                type:'line',
                data: d.data.y
            }]
        };
        if(type=='tem') {
// 使用刚指定的配置项和数据显示图表。
            if (mode == 'day') {
                myChart1.setOption(option);
            } else if (mode == 'week') {
                myChart2.setOption(option);
            } else if (mode == 'month') {
                myChart3.setOption(option);
            }
        }
        else if(type=='hum'){
            if (mode == 'day') {
                myChart5.setOption(option);
            } else if (mode == 'week') {
                myChart6.setOption(option);
            } else if (mode == 'month') {
                myChart7.setOption(option);
            }
        }
        else{
            if (mode == 'day') {
                myChart9.setOption(option);
            } else if (mode == 'week') {
                myChart10.setOption(option);
            } else if (mode == 'month') {
                myChart11.setOption(option);
            }
        }
    });
}