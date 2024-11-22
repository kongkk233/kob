<template>
    <div class="playground">
        <GameMap :key="mapKey" />
        <button type="button" class="btn btn-primary" @click="refreshMap">刷新地图</button>
    </div>
</template>

<script>
import GameMap from './GameMap.vue';
export default {
    components: {
        GameMap
    },
    data() {
        return {
            mapKey: 0
        }
    },
    methods: {
        refreshMap() {
            this.mapKey += 1;
        }
    }
}
</script>

<style scoped>
div.playground {
    margin: 40px auto;
    width: 60vw;
    height: 70vh;
}
button {
    display: block;
    width: 40%;
    margin: 20px auto;
}
</style>