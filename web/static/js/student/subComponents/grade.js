var Grade = Vue.extend({
    props: ['noticeList','gradeWeightList','experimentNames','experimentScores'],
    data() {
        return {
            scores:[],
            names:[],
            attendance: [
                {
                    date:'2021-11-11-12:21',
                    situation: '出勤'
                },
                {
                    date:'2021-11-21-12:21',
                    situation: '出勤'
                },
                {
                    date:'2021-12-11-12:21',
                    situation: '出勤'
                },
                {
                    date:'2021-12-23-12:21',
                    situation: '出勤'
                },
                ]
        };
    },
    methods: {
        drawPie() {
            let myChartPie = echarts.init(document.getElementById('myChart1'))
            myChartPie.setOption({
                title: {
                    text: '各模块百分比',
                    subtext: '祝你取得好成绩！',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left'
                },
                series: [
                    {
                        name: '成绩组成',
                        type: 'pie',
                        radius: '50%',
                        data: this.$props.gradeWeightList,
                        emphasis: {
                            itemStyle: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            })
        },
        drawLine() {
            // 基于准备好的dom，初始化echarts实例
            let myChart = echarts.init(document.getElementById('myChart'))
            // 绘制图表
            myChart.setOption({
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'cross',
                        label: {
                            backgroundColor: '#00BDBE'
                        }
                    }
                },
                legend: {
                    data: ['分数', '班级排名'],
                },
                grid: {
                    top: 40,
                    bottom: 20,
                    right: 35
                },
                color: ['#00BDBE', '#2B97FB', '#FF9500', '#FFCC00', '#FF3B30'],
                xAxis: {
                    axisLine: {
                        lineStyle: {
                            color: '#E8E8E8'
                        },
                    },
                    axisLabel: {
                        color: '#494949'
                    },
                    // 坐标轴刻度
                    axisTick: {
                        show: false,
                    },
                    data: this.names
                },
                yAxis: [
                    {
                        name: '分数',
                        type: 'value',
                        nameTextStyle: {
                            color: '#323232'
                        },
                        axisLine: {
                            show: true,
                            lineStyle: {
                                color: '#E8E8E8'
                            }
                        },
                        axisLabel: {
                            color: '#494949'
                        },
                        // x轴对应的竖线
                        splitLine: {
                            show: true,
                            lineStyle: {
                                color: '#E8E8E8'
                            }
                        },
                        // 坐标轴刻度
                        axisTick: {
                            show: false,
                        },
                    },
                    {
                        name: '排名',
                        nameLocation: 'start',
                        nameTextStyle: {
                            color: '#323232'
                        },
                        type: 'value',
                        inverse: true, //是否反向
                        axisLine: {
                            show: true,
                            lineStyle: {
                                color: '#E8E8E8'
                            }
                        },
                        axisLabel: {
                            color: '#494949'
                        },
                        // x轴对应的竖线
                        splitLine: {
                            show: true,
                            lineStyle: {
                                color: '#E8E8E8'
                            }
                        },
                        // 坐标轴刻度
                        axisTick: {
                            show: false,
                        },
                        min: 1,
                    }
                ],
                series: [
                    {
                        name: '分数',
                        type: 'line',
                        yAxisIndex: 0,
                        //smooth: true,
                        data: this.scores
                    },
                    {
                        name: '班级排名',
                        type: 'line',
                        yAxisIndex: 1,
                        //smooth: true,
                        data: [200, 80, 5]
                    },
                ]
            });
        }
    },
    mounted() {
        this.scores=this.$props.experimentScores
        this.names=this.$props.experimentNames
        this.drawLine()
        this.drawPie()
    },
    template: `
        <div id="app" style="height: 550px;overflow: auto;padding: 30px 0 70px 0">
                <div style="width: 90%;padding-left: 50px"><el-divider content-position="right"><h3>模块占比</h3></el-divider></div>
                <div id="myChart1" :style="{width: '83%', height: '350px', margin: '0 0 0 70px',padding: '30px 0 0 0'}"></div>
                <div style="width: 90%;padding-left: 50px"><el-divider content-position="right"><h3>实验项目</h3></el-divider></div>
               <div id="myChart" :style="{width: '83%', height: '350px', margin: '0 0 0 70px',padding: '30px 0 0 0'}"></div>
               <div style="width: 90%;padding:50px 0 0 50px"><el-divider content-position="right"><h3>考勤</h3></el-divider></div>
               <el-row>
                    <el-col :span="20" :offset="2">
                        <el-table
                          :data="attendance"
                          style="width: 100%">
                          <el-table-column
                            prop="date"
                            label="日期"
                            width="700">
                          </el-table-column>
                          <el-table-column
                            prop="situation"
                            label="考勤情况">
                            <template slot-scope="scope">
                            <el-tag type="success">{{scope.row.situation}}</el-tag>
                            </template>
                          </el-table-column>
                        </el-table>
                    </el-col>
                </el-row>
                <div style="width: 90%;padding:50px 0 0 50px"><el-divider content-position="right"><h3>对抗练习</h3></el-divider></div>
                <el-row>
                    <el-col :span="4" :offset="16">
                        <div><h2 style="display: inline-block">您的总成绩为:</h2></div>
                    </el-col>
                    <el-col :span="2" :offset="20">
                        <div><h1  style="color: green;display: inline-block">40</h1></div>
                    </el-col>
                </el-row>
        </div>
        `
});