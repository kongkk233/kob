<template>
    <ContentField>
        <div class="row justify-content-md-center">
            <div class="col-3">
                <form @submit.prevent="register">
                    <div class="mb-3">
                        <label for="username" class="form-label">用户名</label>
                        <input v-model="username" type="text" class="form-control" id="username" aria-describedby="emailHelp" placeholder="请输入用户名">
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">密码</label>
                        <input v-model="password" type="password" id="password" class="form-control" aria-describedby="passwordHelpBlock" placeholder="请输入密码">
                        <!-- <div id="passwordHelpBlock" class="form-text">
                        Your password must be 8-20 characters long.
                        </div> -->
                    </div>
                    <div class="mb-3">
                        <label for="confirmpassword" class="form-label">确认密码</label>
                        <input v-model="confirmpassword" type="password" id="confirmpassword" class="form-control" aria-describedby="passwordHelpBlock" placeholder="请再次输入密码">
                        <div id="passwordHelpBlock" class="form-text">
                        Your password must be 8-20 characters long.
                        </div>
                    </div>
                    <div class="error-message">{{ error_message }}</div>
                    <button type="submit" class="btn btn-primary">注册</button>
                </form>
            </div>
        </div>
    </ContentField>
</template>

<script>
import ContentField from '@/components/ContentField.vue'
import {ref} from 'vue'
import router from '@/router/index';
import $ from 'jquery'

export default {
    components: {
        ContentField
    },
    setup(){
        let username = ref('')
        let password = ref('')
        let confirmpassword = ref('')
        let error_message = ref('')

        const register = () => {
            $.ajax({
            url:"http://127.0.0.1:3000/user/account/register/",
            type: "post",
            contentType: "application/json",
            data: JSON.stringify({
                username:username.value,
                password:password.value,
                confirmPassword:confirmpassword.value
            }),
            success(resp){
                if(resp.error_message === "success"){
                    router.push({ name: 'user_account_login' })
                }else{
                    error_message.value = resp.error_message
                }
            },
            error(resp){
                error_message.value = resp.error_message
            }
            })
        }

        return {
            register,
            username, 
            password,
            confirmpassword,
            error_message
        }
    }
}
</script>

<style>
button{
    width: 100%;
}
.error-message{
    color: red;
}
</style>