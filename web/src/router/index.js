import { createRouter, createWebHistory } from 'vue-router'
import NotFound from '@/views/error/NotFound.vue'
import pkIndexVue from '@/views/pk/pkIndexVue.vue'
import ranklistIndexVue from '@/views/ranklist/ranklistIndexVue.vue'
import userbotIndexVue from '@/views/user/bot/userbotIndexVue.vue'
import recordIndexVue from '@/views/record/recordIndexVue.vue'
const routes = [
  {
    path: '/',
    name: 'home',
    redirect: '/pk/'
  },
  {
    path: '/pk/',
    name: 'pk_index',
    component: pkIndexVue
  },
  {
    path: '/ranklist/',
    name: 'ranklist_index',
    component: ranklistIndexVue
  },
  {
    path: '/user/bot/',
    name: 'userbot_index',
    component: userbotIndexVue
  },
  {
    path: '/record/',
    name: 'record_index',
    component: recordIndexVue
  },
  {
    path: '/404/',
    name: 'not_found',
    component: NotFound
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

export default router
