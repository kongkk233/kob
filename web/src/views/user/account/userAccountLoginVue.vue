<template>
    <ContentField v-if="!$store.state.user.pullinginformation">
        <div class="row justify-content-md-center">
            <div class="col-3">
                <form @submit.prevent="login">
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
                    <div class="error-message">{{ error_message }}</div>
                    <button type="submit" class="btn btn-primary">登录</button>
                </form>
            </div>
        </div>
    </ContentField>
</template>

<script>
import ContentField from '@/components/ContentField.vue'
import {useStore} from 'vuex'
import {ref} from 'vue'
import router from '@/router/index';


export default {
    components: {
        ContentField
    },
    setup(){
        const store = useStore()
        let username = ref('')
        let password = ref('')
        let error_message = ref('')

        const jwt_token = localStorage.getItem('jwt_token');
        // console.log(jwt_token)
        if(jwt_token){
            // console.log('jwt_token', jwt_token)
            store.commit('updateToken', jwt_token);
            // console.log('store.state.user.token', store.state.user.token)
            store.dispatch('getInfo', {
                success(){
                    router.push({ name: 'home' })
                    store.commit('updatePullingInformation', false);
                },
                error(){
                    store.commit('updatePullingInformation', false);
                }
            })
        }else{
            store.commit('updatePullingInformation', false);
        }

        const login = () => {
            store.dispatch("login",{
                username: username.value,
                password: password.value,
                success(){
                    store.dispatch("getInfo",{
                        success(){
                            router.push({ name: 'home' })
                        }
                    })
                },
                error(){
                    error_message.value = '用户名或密码错误'
                }
            })
        }

        return {
            login,
            username, 
            password,
            error_message,
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