<template>
    <ContentField>
        <div>我的Bot</div>
    </ContentField>
</template>

<script>
import ContentField from '@/components/ContentField.vue'
import $ from 'jquery'
export default {
    components: {
        ContentField
    },
    setup(){
        const jwt_token = localStorage.getItem('jwt_token')
        // $.ajax({
        //     url:"http://127.0.0.1:3000/user/bot/add/",
        //     type: "post",
        //     contentType: "application/json",
        //     data: JSON.stringify({
        //         title:"Bot的标题",
        //         description:"Bot的描述",
        //         content:"Bot的内容",
        //     }),
        //     headers : {
        //         Authorization: "Bearer " + jwt_token
        //     },
        //     success(resp){
        //         console.log(resp);
        //     },
        //     error(resp){
        //         console.log(resp);
        //     }
        // });
        // $.ajax({
        //     url:"http://127.0.0.1:3000/user/bot/remove/",
        //     type: "post",
        //     contentType: "application/json",
        //     data: JSON.stringify({
        //         bot_id:1
        //     }),
        //     headers : {
        //         Authorization: "Bearer " + jwt_token
        //     },
        //     success(resp){
        //         console.log(resp);
        //     },
        //     error(resp){
        //         console.log(resp);
        //     }
        // });
        // $.ajax({
        //     url:"http://127.0.0.1:3000/user/bot/update/",
        //     type: "post",
        //     contentType: "application/json",
        //     data: JSON.stringify({
        //         bot_id:2,
        //         title:"Bot的标题new",
        //         description:"Bot的描述new",
        //         content:"Bot的内容new",
        //     }),
        //     headers : {
        //         Authorization: "Bearer " + jwt_token
        //     },
        //     success(resp){
        //         console.log(resp);
        //     },
        //     error(resp){
        //         console.log(resp);
        //     }
        // });
        $.ajax({
            url:"http://127.0.0.1:3000/user/bot/getlist/",
            type: "get",
            headers : {
                Authorization: "Bearer " + jwt_token
            },
            success(resp){
                console.log(resp);
            },
            error(resp){
                console.log(resp);
            }
        });
    }
}
</script>

<style>
</style>