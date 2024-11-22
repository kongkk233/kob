<template>
  <div>
    <NavBar />
    <router-view></router-view>
  </div>
</template>

<script>
// import $ from 'jquery';
// import { ref } from 'vue';
import NavBar from './components/NavBar.vue';
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.min.js";
// import { type } from 'jquery';
// import { data } from 'jquery';

export default {
    components: {
      NavBar
    },
    // setup() {
    //   $.ajax({
    //     url:"http://127.0.0.1:3000/user/account/token/",
    //     type: "post",
    //     contentType: "application/json",
    //     data: JSON.stringify({
    //       username:"kwz",
    //       password:"pkwz"
    //     }),
    //     success(resp){
    //       console.log(resp);
    //     },
    //     error(resp){
    //       console.log(resp);
    //     }
    //   });

    //   $.ajax({
    //     url:"http://127.0.0.1:3000/user/account/info/",
    //     type: "get",
    //     headers:{
    //       Authorization:"Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJkZDA1ZGU1NjQwYzY0NGYyOTU4NTEyMDdiMzk2ZWEyYiIsInN1YiI6IjEiLCJpc3MiOiJzZyIsImlhdCI6MTczMjE5ODIyNSwiZXhwIjoxNzMzNDA3ODI1fQ.p7A2z4hYILiWw-y5YIgYXQrYX84qdv5wPNbZpz1yPS8"
    //     },
    //     success(resp){
    //       console.log(resp);
    //     },
    //     error(resp){
    //       console.log(resp);
    //     }
    //   });

    //   $.ajax({
    //     url:"http://127.0.0.1:3000/user/account/register/",
    //     type: "post",
    //     contentType: "application/json",
    //     data: JSON.stringify({
    //       username:"aaaaaaaa",
    //       password:"paaaaaaaa",
    //       confirmPassword:"paaaaaaaa"
    //     }),
    //     success(resp){
    //       console.log(resp);
    //     },
    //     error(resp){
    //       console.log(resp);
    //     }
    //   })
    // }
  }
</script>

<style>
body {
  background-image: url("@/assets/images/background.png");
  background-size: cover;
}
</style>
