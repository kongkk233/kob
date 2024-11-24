<template>
    <div class="container">
        <div class="row">
            <div class="col-3">
            <div class="card" style="margin-top: 20px;">
                <div class="card-body">
                    <img :src="$store.state.user.photo" alt="" style="width: 100%;">
                </div>
            </div>
            </div>
            <div class="col-9">
                <div class="card" style="margin-top: 20px;">
                    <div class="card-header">
                        <div style="display: flex; align-items: center; justify-content: space-between;">
                            <span style="font-size: 130%;">我的Bot</span>
                            <button type="button" class="btn btn-outline-primary" style="width: 20%;" data-bs-toggle="modal" data-bs-target="#add-bot-btn">添加Bot</button>
                            <!-- Modal -->
                            <div class="modal fade" id="add-bot-btn" tabindex="-1">
                                <div class="modal-dialog modal-xl modal-dialog-centered modal-dialog-scrollable">
                                    <div class="modal-content">
                                    <div class="modal-header">
                                        <h1 class="modal-title fs-5" id="exampleModalLabel">创建Bot</h1>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="mb-3">
                                            <label for="exampleFormControlInput1" class="form-label">标题</label>
                                            <input v-model="add_bot.title" type="text" class="form-control" id="add-bot-id" placeholder="请输入Bot名称">
                                        </div>
                                        <div class="mb-3">
                                            <label for="exampleFormControlTextarea1" class="form-label">简介</label>
                                            <textarea v-model="add_bot.description" class="form-control" id="add-bot-description" rows="3" placeholder="请输入Bot简介"></textarea>
                                        </div>
                                        <div class="mb-3">
                                            <label for="exampleFormControlTextarea1" class="form-label">代码</label>
                                            <VAceEditor v-model:value="add_bot.content" @init="editorInit" lang="c_cpp"
                                                theme="textmate" style="height: 300px" :options="{
                                                    enableBasicAutocompletion: true, //启用基本自动完成
                                                    enableSnippets: true, // 启用代码段
                                                    enableLiveAutocompletion: true, // 启用实时自动完成
                                                    fontSize: 18, //设置字号
                                                    tabSize: 4, // 标签大小
                                                    showPrintMargin: false, //去除编辑器里的竖线
                                                    highlightActiveLine: true,
                                                }" />
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <div class="error-message" style="color:red;">{{ add_bot.error_message }}</div>
                                        <button @click="addBot" type="button" class="btn btn-primary" style="width: 15%;">创建</button>
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" style="width: 15%;">取消</button>
                                    </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="card-body">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>名称</th>
                                    <th>创建时间</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody >
                                <tr v-for="bot in bots" :key="bot.id" >
                                    <td>{{ bot.title }}</td>
                                    <td>{{ bot.createtime }}</td>
                                    <td>
                                        <button type="button" class="btn btn-success" style="width: 40%; margin-right:20px" data-bs-toggle="modal" :data-bs-target="'#update-bot-modal' + bot.id">修改</button>
                                        <button type="button" class="btn btn-danger" style="width: 40%;" data-bs-toggle="modal" :data-bs-target="'#remove-bot-modal' + bot.id">删除</button>
                                        <!-- Modal -->
                                        <div class="modal fade" :id="'update-bot-modal' + bot.id" tabindex="-1">
                                            <div class="modal-dialog modal-xl modal-dialog-centered modal-dialog-scrollable">
                                                <div class="modal-content">
                                                <div class="modal-header">
                                                    <h1 class="modal-title fs-5" id="exampleModalLabel">创建Bot</h1>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <div class="modal-body">
                                                    <div class="mb-3">
                                                        <label for="exampleFormControlInput1" class="form-label">标题</label>
                                                        <input v-model="bot.title" type="text" class="form-control" id="add-bot-id" placeholder="请输入Bot名称">
                                                    </div>
                                                    <div class="mb-3">
                                                        <label for="exampleFormControlTextarea1" class="form-label">简介</label>
                                                        <textarea v-model="bot.description" class="form-control" id="add-bot-description" rows="3" placeholder="请输入Bot简介"></textarea>
                                                    </div>
                                                    <div class="mb-3">
                                                        <label for="exampleFormControlTextarea1" class="form-label">代码</label>
                                                        <VAceEditor v-model:value="bot.content" @init="editorInit" lang="c_cpp"
                                                            theme="textmate" style="height: 300px" :options="{
                                                                enableBasicAutocompletion: true, //启用基本自动完成
                                                                enableSnippets: true, // 启用代码段
                                                                enableLiveAutocompletion: true, // 启用实时自动完成
                                                                fontSize: 18, //设置字号
                                                                tabSize: 4, // 标签大小
                                                                showPrintMargin: false, //去除编辑器里的竖线
                                                                highlightActiveLine: true,
                                                            }" />
                                                    </div>
                                                </div>
                                                <div class="modal-footer">
                                                    <div class="error-message" style="color:red;">{{ bot.error_message }}</div>
                                                    <button @click="updateBot(bot)" type="button" class="btn btn-primary" style="width: 15%;">保存修改</button>
                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" style="width: 15%;">取消</button>
                                                </div>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- remove Modal -->
                                        <div class="modal fade" :id="'remove-bot-modal' + bot.id" tabindex="-1">
                                            <div class="modal-dialog modal-xl modal-dialog-centered modal-dialog-scrollable">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title" style="color: red;">删除Bot</h5>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <p>确认删除{{ bot.id }}Bot?</p>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <div class="error-message" style="color:red;">{{ bot.error_message }}</div>
                                                        <button @click="remove(bot)" type="button" class="btn btn-danger" style="width: 15%;">确认删除</button>
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" style="width: 15%;">取消</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import {ref, reactive} from 'vue'
import $ from 'jquery'
import { Modal } from 'bootstrap/dist/js/bootstrap.min.js';
import {VAceEditor} from 'vue3-ace-editor'
import ace from 'ace-builds'
import 'ace-builds/src-noconflict/mode-c_cpp';
import 'ace-builds/src-noconflict/mode-json';
import 'ace-builds/src-noconflict/theme-chrome';
import 'ace-builds/src-noconflict/ext-language_tools';
export default {
    components: {
        VAceEditor
    },
    setup() {
        ace.config.set(
            "basePath",
            "https://cdn.jsdelivr.net/npm/ace-builds@" +
            require("ace-builds").version +
            "/src-noconflict/")
        let bots = ref([]);
        let add_bot = reactive({
            title: '',
            description: '',
            content: '',
            error_message:''
        });

        let jwt_token = localStorage.getItem('jwt_token');
        const refresh_list = () => {
            $.ajax({
                url:"http://127.0.0.1:3000/user/bot/getlist/",
                type: "get",
                headers : {
                    Authorization: "Bearer " + jwt_token
                },
                success(resp){
                    bots.value = resp;
                },
                error(resp){
                    console.log(resp);
                }
            });
        }
        refresh_list();

        const addBot = () => {
            $.ajax({
                url:"http://127.0.0.1:3000/user/bot/add/",
                type: "post",
                contentType: "application/json",
                data: JSON.stringify({
                    title: add_bot.title,
                    description: add_bot.description,
                    content: add_bot.content,
                }),
                headers : {
                    Authorization: "Bearer " + jwt_token
                },
                success(resp){
                    if(resp.error_message === 'success'){
                        add_bot.title = '';
                        add_bot.description = '';
                        add_bot.content = '';
                        Modal.getInstance('#add-bot-btn').hide();
                        refresh_list();
                    }else{
                        add_bot.error_message = resp.error_message;
                    }
                },
                error(resp){
                    add_bot.error_message = resp.error_message;
                }
            });
        };
        const updateBot = (bot) => {
            $.ajax({
                url:"http://127.0.0.1:3000/user/bot/update/",
                type: "post",
                contentType: "application/json",
                data: JSON.stringify({
                    bot_id: bot.id,
                    title: bot.title,
                    description: bot.description,
                    content: bot.content,
                }),
                headers : {
                    Authorization: "Bearer " + jwt_token
                },
                success(resp){
                    if(resp.error_message === 'success'){
                        Modal.getInstance('#update-bot-modal' + bot.id).hide();
                        refresh_list();
                    }else{
                        add_bot.error_message = resp.error_message;
                    }
                },
                error(resp){
                    add_bot.error_message = resp.error_message;
                }
            });
        };
        const remove = (bot) => {
            $.ajax({
                url:"http://127.0.0.1:3000/user/bot/remove/",
                type: "post",
                contentType: "application/json",
                data: JSON.stringify({
                    bot_id: bot.id
                }),
                headers : {
                    Authorization: "Bearer " + jwt_token
                },
                success(resp){
                    if(resp.error_message === 'success'){
                        Modal.getInstance('#remove-bot-modal' + bot.id).hide();
                        refresh_list();
                    }
                }
            });
        }
        return {
            bots,
            add_bot,
            addBot,
            remove,
            updateBot
        }
    }
}
</script>

<style>
</style>