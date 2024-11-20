import { AcGameObject } from "./AcGameObject.js";
import {Wall} from "./Wall.js"
import { Snake } from "./Snake.js";
export class GameMap extends AcGameObject {
    constructor(ctx, parent) {
        super();
        this.ctx = ctx;
        this.parent = parent;
        this.L = 0;

        this.rows = 13;
        this.cols = 14;
        this.inner_walls_count = 40;
        this.walls = [];

        this.snakes = [
            new Snake({id: 0, color: '#ADD8E6', r: this.rows - 2, c: 1}, this),
            new Snake({id: 1, color: '#FFA07A', r: 1, c: this.cols - 2}, this)
        ]
    }

    check_connect(g, sx, sy, tx, ty){
        if(sx == tx && sy == ty) return true;
        g[sx][sy] = true;
        let dx = [-1, 0, 1, 0], dy = [0, 1, 0, -1];
        for(let i = 0; i < 4; i++){
            let x = sx + dx[i], y = sy + dy[i];
            if(!g[x][y] && this.check_connect(g, x, y, tx, ty)) return true;
        }
        return false;
    }

    create_walls(){
        const g = [];
        for(let i = 0; i < this.rows; i++){
            g[i] = [];
            for(let j = 0; j < this.cols; j++){
                g[i][j] = false;
            }
        }
        // 给四周加上障碍物
        for(let i = 0; i < this.rows; i++){
            g[i][0] = g[i][this.cols - 1] = true;
        }

        for(let i = 0; i < this.cols; i++){
            g[0][i] = g[this.rows - 1][i] = true;
        }

        // 添加随机障碍物
        for(let i = 0; i < this.inner_walls_count / 2; i++){
            for(let j = 0; j < 1000; j++){
                let r = parseInt(Math.random() * this.rows);
                let c = parseInt(Math.random() * this.cols);
                if(g[r][c] || g[this.rows - 1 - r][this.cols - 1 - c]) continue;
                if(r == this.rows - 2 && c == 1 || r == 1 && c == this.cols - 2) continue;
                g[r][c] = g[this.rows - 1 - r][this.cols - 1 - c] = true;
                break;
            }
        }
        const copy_g = JSON.parse(JSON.stringify(g));
        if(!this.check_connect(copy_g, this.rows - 2, 1, 1, this.cols - 2)) return false;

        for(let i = 0; i < this.rows; i++){
            for(let j = 0; j < this.cols; j++){
                if(g[i][j]){
                    this.walls.push(new Wall(i, j, this));
                }
            }
        }
        return true;
    }

    check_valid(cell){ //检测目标位置是否合法；没有撞到两条蛇的身体和墙
        for(const wall of this.walls)
            if(wall.r === cell.r && wall.c === cell.c)
                return false;
        for(const snake of this.snakes){
            let k = snake.cells.length;
            if(!snake.check_tail_increasing()) k--;
            for(let i = 0; i < k; i++){
                if(snake.cells[i].r === cell.r && snake.cells[i].c === cell.c){
                    return false;
                }
            }
        }
        return true;
    }

    add_listening_events(){
        // Set focus on the canvas to ensure it can receive keyboard events
        this.ctx.canvas.focus();
        const [snake0, snake1] = this.snakes;
        window.addEventListener('keydown', (e) => {
            const key = e.key;
            switch (key) {
                case 'w':
                    snake0.set_direction(0);
                    snake1.set_direction(2);
                    break;
                case 'd':
                    snake0.set_direction(1);
                    snake1.set_direction(3);
                    break;
                case 's':
                    snake0.set_direction(2);
                    snake1.set_direction(0);
                    break;
                case 'a':
                    snake0.set_direction(3);
                    snake1.set_direction(1);
                    break;
                case 'ArrowUp':
                    snake1.set_direction(0);
                    break;
                case 'ArrowRight':
                    snake1.set_direction(1);
                    break;
                case 'ArrowDown':
                    snake1.set_direction(2);
                    break;
                case 'ArrowLeft':
                    snake1.set_direction(3);
                    break;
            }
        });
    }

    start() {
        for(let i = 0; i < 1000; i++){
            if(this.create_walls()) break;
        }
        this.add_listening_events();
    }

    update_size(){
        this.L = parseInt(Math.min(this.parent.clientWidth / this.cols, this.parent.clientHeight / this.rows));
        this.ctx.canvas.width = this.L * this.cols;
        this.ctx.canvas.height = this.L * this.rows;
    }

    check_ready(){ //判断两条是否都准备好下一个回合
        for(const snake of this.snakes){
            if(snake.status !== "idle"){
                return false;
            }
            if(snake.direction === -1) return false;
            return true;
        }
        
    }

    next_step(){
        for(const snake of this.snakes){
            snake.next_step();
        }
    }

    update() {
        this.update_size();
        if(this.check_ready()){
            this.next_step();
        }
        this.render();
    }

    render(){
        const color_even = '#f0f0f0', color_odd = '#e0e0e0';
        for ( let r = 0; r < this.cols; r++ ) {
            for ( let c = 0; c < this.rows; c++ ) {
                this.ctx.fillStyle = (r + c) % 2 == 0 ? color_even : color_odd;
                this.ctx.fillRect(c * this.L, r * this.L, this.L, this.L)
            }
        }
    }
}