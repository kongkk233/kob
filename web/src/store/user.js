import $ from 'jquery'

export default {
    state: {
        id:"",
        username:"",
        photo:"",
        token:"",
        is_login:"",
        pullinginformation:true //是否正在拉取信息
    },
    getters: {
    },
    mutations: {
        updateUser(state, user){
            state.id = user.id
            state.username = user.username
            state.photo = user.photo
            state.is_login = user.is_login
        },
        updateToken(state, token){
            state.token = token
        },
        logout(state){
            state.id = ""
            state.username = ""
            state.token = ""
            state.photo = ""
            state.is_login = false
        },
        updatePullingInformation(state, pullinginformation){
            state.pullinginformation = pullinginformation
        }
    },
    actions: {
        login(context, data){
            $.ajax({
                url:"http://127.0.0.1:3000/user/account/token/",
                type: "post",
                contentType: "application/json",
                data: JSON.stringify({
                  username:data.username,
                  password:data.password
                }),
                success(resp){
                  if(resp.error_message === "success"){                
                    localStorage.setItem("jwt_token", resp.token);
                    context.commit("updateToken", resp.token);
                    data.success(resp);
                  }else{
                    //登录失败
                    data.error(resp)
                  }
                },
                error(resp){
                    data.error(resp)
                }
              });
        },
        getInfo(context, data){
            $.ajax({
            url:"http://127.0.0.1:3000/user/account/info/",
            type: "get",
            headers:{
            Authorization:"Bearer " + context.state.token
            },
            success(resp){
                context.commit("updateUser", {
                    ...resp,
                    is_login:true
                });
                data.success(resp);
            },
            error(resp){
                data.error(resp);
            }
            });
        },
        logout(context){
            context.commit("logout");
            localStorage.removeItem("jwt_token");
        }
    },
    modules: {
    }
}