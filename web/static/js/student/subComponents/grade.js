var Grade = Vue.extend({
    props: ['noticeList','gradeWeightList','experimentNames','experimentScores','attendanceList','rankList','classId', 'courseId', 'practiceList','studentNumber'],
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
                ],
            expScore:0.0,
            practiceScore:0.0,
            attendanceScore:0.0,
            totalScore:0.0
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
                        data: this.rankList
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
        axios({
            url: '/SoftwareEngineering/studentServlet?action=getTotalGrade',
            method: "Post",
            data: {
                courseID: this.courseId,
                classID: this.classId,
                studentNumber:this.studentNumber
            },
        }).then(resp => {
            this.expScore = resp.data.expScore
            this.attendanceScore = resp.data.attendScore
            this.practiceScore = resp.data.practiceScore
            this.totalScore = resp.data.totalScore
        });
    },
    template: `
        <div id="app" style="height: 550px;overflow: auto;padding: 30px 0 70px 0">
                <div style="width: 90%;padding-left: 50px"><el-divider content-position="right"><h3>模块占比</h3></el-divider></div>
                <div id="myChart1" :style="{width: '83%', height: '350px', margin: '0 0 0 70px',padding: '30px 0 0 0'}"></div>
                <div style="width: 90%;padding-left: 50px"><el-divider content-position="right"><h3>实验项目总成绩：{{expScore}}</h3></el-divider></div>
               <div id="myChart" :style="{width: '83%', height: '350px', margin: '0 0 0 70px',padding: '30px 0 0 0'}"></div>
               <div style="width: 90%;padding:50px 0 0 50px"><el-divider content-position="right"><h3>考勤总成绩：{{attendanceScore}}</h3></el-divider></div>
               <el-row>
                    <el-col :span="20" :offset="2">
                        <el-table
                          :data="attendanceList"
                          style="width: 100%">
                          <el-table-column
                            prop="endTime"
                            label="日期"
                            width="700">
                          </el-table-column>
                          <el-table-column
                            prop="status"
                            label="考勤情况">
                            <template slot-scope="scope">
                            <el-tag type="success">{{scope.row.status}}</el-tag>
                            </template>
                          </el-table-column>
                        </el-table>
                    </el-col>
                </el-row>
                <div style="width: 90%;padding:50px 0 0 50px"><el-divider content-position="right"><h3>对抗练习总成绩：{{practiceScore}}</h3></el-divider></div>
                <el-row>
                    <el-col :span="4" :offset="16">
                        <div><h2 style="display: inline-block">您的总成绩为:</h2></div>
                    </el-col>
                    <el-col :span="2" :offset="20">
                        <div><h1  style="color: green;display: inline-block">{{totalScore}}</h1></div>
                    </el-col>
                </el-row>
        </div>
        `
});