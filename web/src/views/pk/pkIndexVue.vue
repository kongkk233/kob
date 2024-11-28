<template>
    <div>
        <PlayGround v-if="$store.state.pk.status === 'playing'" />
        <MatchGround v-if="$store.state.pk.status === 'matching'" />
    </div>
</template>

<script>
import PlayGround from '@/components/PlayGround.vue'
import MatchGround from '@/components/MatchGround.vue'
import { onMounted, onUnmounted } from 'vue';
import {useStore} from 'vuex'

export default {
    components: {
        PlayGround,
        MatchGround
    },
    setup(){
        const store = useStore();
        console.log(store.state.user);
        const socketurl = `http://127.0.0.1:3000/websocket/${store.state.user.token}/`;
        let socket = null;

        onMounted(()=>{
            socket = new WebSocket(socketurl);
            store.commit('updateOpponent',{
                username: '我的对手',
                photo: 'https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png'
            })
            socket.onopen = () => {
                console.log('连接成功');
                store.commit('updateSocket', socket)
            };
            socket.onmessage = (message) => {
                const data = JSON.parse(message.data);
                console.log(data);
                if(data.event === 'matching-success'){
                    console.log(data.username);
                    store.commit('updateOpponent',{
                        username: data.username,
                        photo: data.photo
                    })
                    setTimeout(()=>{
                        store.commit('updateStatus','playing');
                    },1500)
                    console.log(data.gamemap);
                    store.commit('updateGamemap', data.gamemap);
                }
            };
            socket.onclose = () => {
                console.log('连接关闭');
            };
        });

        onUnmounted(()=>{
                socket.close();
                store.commit('updateStatus','matching');
        })
    }
}
</script>

<style>
</style>