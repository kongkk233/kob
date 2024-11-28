<template>
    <div class="matchground">
        <div class="row">
            <div class="col-6">
                <div class="user-photo">
                    <img :src="$store.state.user.photo" alt="">
                </div>
                <div class="user-name">{{ $store.state.user.username }}</div>
            </div>
            <div class="col-6">
                <div class="user-photo">
                    <img :src="$store.state.pk.opponent_photo" alt="">
                </div>
                <div class="user-name">{{ $store.state.pk.opponent_username }}</div>
            </div>
        </div>
        <div class="row justify-content-md-center">
            <div class="col-12" style="width:15%; margin-top:15vh;">
                <button @click="click_match_btn" class="btn btn-success btn-lg">{{match_btn}}</button>
            </div>
        </div>
    </div>
</template>

<script>
import {ref} from 'vue';
import {useStore} from 'vuex';
export default{
    setup() {
        let match_btn = ref("开始匹配");
        const store = useStore();
        const click_match_btn = () => {
            if(match_btn.value === "开始匹配"){
                match_btn.value = "取消匹配";
                store.state.pk.socket.send(JSON.stringify({
                    event: 'start-matching'
                }));
            }
            else{
                match_btn.value = "开始匹配";
                store.state.pk.socket.send(JSON.stringify({
                    event: 'stop-matching'
                }));
            }
        }


        return {
            match_btn,
            click_match_btn
        }
    }
}
</script>

<style scoped>
div.matchground {
    margin: 40px auto;
    width: 60vw;
    height: 70vh;
    background: rgba(50, 50, 50, 0.3);
}
div.user-photo {
    text-align: center;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-top: 20%;
}
div.user-photo > img {
    width: 200px;
    height: 200px;
    border-radius: 50%;
}
div.user-name {
    text-align: center;
    font-size: 24px;
    font-weight: bold;
    margin-top: 3%;
    color: white;
}
</style>