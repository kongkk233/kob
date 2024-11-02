const AC_GAME_OBJECT = [];

export class AcGameObject {
    constructor() {
        AC_GAME_OBJECT.push(this);
        this.timedelta = 0;
        this.has_called_start = false;
    }

    start() {
        // 只会执行一次
    }
    update() {
        // 每帧执行
    }
    on_destroy() {
        //删除之前执行
    }
    destroy() {
        this.on_destroy();
        for(let i = 0; i < AC_GAME_OBJECT.length; i++) {
            if(AC_GAME_OBJECT[i] == this) {
                AC_GAME_OBJECT.splice(i, 1);
                break;
            }
        }
    }
}

let last_timestramp; // 上一帧的时间戳
const step = (timestramp) => {
    for(let obj of AC_GAME_OBJECT){
        if(!obj.has_called_start){
            obj.start();
            obj.has_called_start = true;
        }else{
            obj.timedelta = timestramp - last_timestramp;
            obj.update();
        }
    }
    last_timestramp = timestramp;
    requestAnimationFrame(step);
}

requestAnimationFrame(step);