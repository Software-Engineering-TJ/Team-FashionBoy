var FileDownLoad = Vue.extend({
    props: ['fileList', 'materialList', 'courseId', 'classId', 'instructorNumber'],
    data() {
        return {
        };
    },
    methods: {
        uploadSuccess() {
            this.$message({
                type: 'success',
                message: '文件上传成功！'
            });
        },
    },
    template: `
         <div style="padding: 20px;position:relative;height: 600px;overflow: auto">
             <el-row :gutter="0" style="margin-bottom: 0px">
                    <el-col :span="1">
                        <div class="grid-content bg-purple" style="border-radius: 0;background-color: white"></div>
                    </el-col>
                    <el-col :span="4">
                        <div class="grid-content bg-purple" style="border-radius: 0;background-color: white;line-height: 36px">文件名</div>
                    </el-col>
                    <el-col :span="7">
                        <div class="grid-content bg-purple" style="border-radius: 0;background-color: white"></div>
                    </el-col>
                    <el-col :span="2">
                        <div class="grid-content bg-purple" style="border-radius: 0;line-height: 36px;background-color: white">文件大小</div>
                    </el-col>
                    <el-col :span="1">
                        <div class="grid-content bg-purple" style="border-radius: 0;line-height: 36px;background-color: white"></div>
                    </el-col>
                    <el-col :span="2">
                        <div class="grid-content bg-purple" style="border-radius: 0;line-height: 36px;background-color: white">上传时间</div>
                    </el-col>
                    <el-col :span="1">
                        <div class="grid-content bg-purple" style="border-radius: 0;line-height: 36px;background-color: white"></div>
                    </el-col>
                    <el-col :span="2">
                        <div class="grid-content bg-purple" style="border-radius: 0;line-height: 36px;background-color: white">上传人</div>
                    </el-col>
                    <el-col :span="4">
                        <div class="grid-content bg-purple" style="border-radius: 0;line-height: 36px;background-color: white">操作</div>
                    </el-col>   
                </el-row>
             <hr style="margin-bottom: 5px;color: #C0C4CC">
            <template v-for="item in fileList">
                <el-row :gutter="0" style="margin-bottom: 5px">
                    <el-col :span="1">
                        <div class="grid-content bg-purple" style="border-radius:4px 0 0 4px">
                            <i class="el-icon-document" style="font-size: 30px;padding-top: 10px"></i>
                        </div>
                    </el-col>
                    <el-col :span="4">
                        <div class="grid-content bg-purple" style="border-radius: 0;line-height: 48px">
                            <el-link :underline="false" :href="item.fileURL" download="">{{item.fileName}}</el-link>
                        </div>
                    </el-col>
                    <el-col :span="7">
                        <div class="grid-content bg-purple" style="border-radius: 0"></div>
                    </el-col>
                    <el-col :span="2">
                        <div class="grid-content bg-purple" style="border-radius: 0;line-height: 50px">{{item.fileSize}}</div>
                    </el-col>
                    <el-col :span="1">
                        <div class="grid-content bg-purple" style="border-radius: 0;line-height: 50px"></div>
                    </el-col>
                    <el-col :span="2">
                        <div class="grid-content bg-purple" style="border-radius: 0;line-height: 50px">{{item.upLoadDate}}</div>
                    </el-col>
                    <el-col :span="1">
                        <div class="grid-content bg-purple" style="border-radius: 0;line-height: 50px"></div>
                    </el-col>
                    <el-col :span="2">
                        <div class="grid-content bg-purple" style="border-radius: 0;line-height: 50px">{{item.upLoadUser}}</div>
                    </el-col>
                    <el-col :span="4">
                        <div class="grid-content bg-purple" style="border-radius: 0 4px 4px 0;line-height: 50px">
                            <el-button type="text">撤回</el-button>
                        </div>
                    </el-col>
                </el-row>
            </template>
            <div style="display: inline-block;position: absolute;bottom: 10px;right: 24px">
                <el-upload
                        class="upload-demo"
                        ref="upload"
                        :auto-upload="true"
                        action="/SoftwareEngineering/fileServlet?action=uploadFile"
                        :data="{
                                courseID:this.$props.courseId,
                                classID:this.$props.classId,
                                userNumber:this.$props.instructorNumber,
                                expname:'other'
                            }"
                        :on-success="uploadSuccess"
                        :file-list="materialList">
                        <el-button type="primary">上传参考资料</el-button>
                </el-upload>
            </div>
        </div>
        `
});