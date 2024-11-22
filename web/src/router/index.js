import { createRouter, createWebHistory } from 'vue-router'
import NotFound from '@/views/error/NotFound.vue'
import pkIndexVue from '@/views/pk/pkIndexVue.vue'
import ranklistIndexVue from '@/views/ranklist/ranklistIndexVue.vue'
import userbotIndexVue from '@/views/user/bot/userbotIndexVue.vue'
import recordIndexVue from '@/views/record/recordIndexVue.vue'
import userAccountLoginView from '@/views/user/account/userAccountLoginVue.vue'
import userAccountRegisterView from '@/views/user/account/userAccountRegisterVue.vue'
import store from '@/store'
const routes = [
  {
    path: '/',
    name: 'home',
    redirect: '/pk/',
    meta:{
      requireAuth: true
    }
  },
  {
    path: '/pk/',
    name: 'pk_index',
    component: pkIndexVue,
    meta:{
      requireAuth: true
    }
  },
  {
    path: '/ranklist/',
    name: 'ranklist_index',
    component: ranklistIndexVue,
    meta:{
      requireAuth: true
    }
  },
  {
    path: '/user/bot/',
    name: 'userbot_index',
    component: userbotIndexVue,
    meta:{
      requireAuth: true
    }
  },
  {
    path: '/record/',
    name: 'record_index',
    component: recordIndexVue,
    meta:{
      requireAuth: true
    }
  },
  {
    path: '/user/account/login/',
    name: 'user_account_login',
    component: userAccountLoginView,
    meta:{
      requireAuth: false
    }
  },
  {
    path: '/user/account/register/',
    name: 'user_account_register',
    component: userAccountRegisterView,
    meta:{
      requireAuth: false
    }
  },
  {
    path: '/404/',
    name: 'not_found',
    component: NotFound,
    meta:{
      requireAuth: false
    }
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/404/'
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  if(!store.state.user.is_login && to.meta.requireAuth){
    next({name: 'user_account_login'})
  }else{
    next()
  }
})

export default router
