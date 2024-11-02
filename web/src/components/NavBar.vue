<template>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <!-- <a class="navbar-brand" href="#">King of Bots</a> -->
        <router-link class="navbar-brand" :to="{name:'home'}">King of Bots</router-link>
        <div class="collapse navbar-collapse" id="navbarText">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item">
            <router-link class="nav-link" active-class="active" :to="{name:'pk_index'}">对战</router-link>
            </li>
            <li class="nav-item">
            <router-link class="nav-link" active-class="active" :to="{name:'record_index'}">对局列表</router-link>
            </li>
            <li class="nav-item">
            <router-link class="nav-link" active-class="active" :to="{name:'ranklist_index'}">排行榜</router-link>
            </li>
        </ul>
        <span class="navbar-text">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                kongweize
                </a>
                <ul class="dropdown-menu">
                <li><router-link class="dropdown-item" :to="{name:'userbot_index'}">我的Bot</router-link></li>
                <li><hr class="dropdown-divider"></li>
                <li><a class="dropdown-item" href="#">退出</a></li>
                </ul>
            </li>
        </span>
        </div>
    </div>
</nav>
</template>

<script>
</script>

<style>

</style>