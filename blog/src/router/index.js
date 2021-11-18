import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../layout/Layout.vue'
import Classify from '../layout/Classify.vue'
import Space from '../layout/Space.vue'
import Tag from '../layout/Tag.vue'
import axios from 'axios'

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect: '/blogs',
    children: [
      {
        path: 'blogs',
        name: 'ShowBlogs',
        // meta: {
        //   requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
        // },
        component: () => import("@/views/ShowBlogs")
      },
    ]
  },
  {
    path: '/classify',
    name: 'Classify',
    component: Classify,
    redirect: '/classify/totalClassify',
    children: [
      {
        path: 'totalClassify',
        name: 'TotalClassify',
        component: () => import("@/views/classify/TotalClassify")
      },
      {
        path: 'partClassify/:category',
        name: 'PartClassify',
        component: () => import("@/views/classify/PartClassify"),
        props: true
      },
    ]
  },
  {
    path: '/tag',
    name: 'Tag',
    component: Tag,
    redirect: '/tag/totalTag',
    children: [
      {
        path: 'totalTag',
        name: 'TotalTag',
        component: () => import("@/views/tag/TotalTag")
      },
      {
        path: 'partTag/:tag',
        name: 'PartTag',
        component: () => import("@/views/tag/PartTag"),
        props: true
      },
    ]
  },
  {
    path: '/space',
    name: 'Space',
    component: Space,
    meta: {
      requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
    },
    children: [
      {
        path: 'profile',
        name: 'Profile',
        component: () => import("@/views/space/Profile")
      },
      {
        path: 'privacy',
        name: 'Privacy',
        component: () => import("@/views/space/Privacy")
      },
      {
        path: 'account',
        name: 'Account',
        component: () => import("@/views/space/Account")
      },
      {
        path: 'collection',
        name: 'Collection',
        component: () => import("@/views/space/Collection")
      },
      {
        path: 'content',
        name: 'Content',
        component: () => import("@/views/space/Content")
      },
    ]
  },
  {
    path: '/creation',
    name: 'Creation',
    meta: {
      requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
    },
    component: () => import("@/views/Creation")
  },
  {
    path: '/author',
    name: 'Gleam',
    component: () => import("@/views/Gleam")
  },
  {
    path: '/article/:id',
    name: 'Article',
    component: () => import("@/views/Article"),
    props: true
  },
  {
    path: '/animation',
    name: 'Animation',
    meta: {
      requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
    },
    component: () => import("@/views/Animation"),
    props: true
  },
  {
    path: '/link',
    name: 'Link',
    component: () => import("@/views/Link"),
    props: true
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import("@/views/Login")
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import("@/views/Register")
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach((to, from, next) => {
  if (to.path.startsWith('/login')) {
    window.localStorage.removeItem('access-token')
    window.localStorage.removeItem('access-userid')
    next()
  }
  else if (to.meta.requireAuth) {// 判断该路由是否需要登录权限
    let token = JSON.parse(window.localStorage.getItem("access-token"))
    if (!token) {
      next({ path: '/login' })
    }
    else {
      //校验token合法性
      axios({
        url: "http://" + window.server.ip + ":9090/user/checkToken",
        method: 'get',
        headers: {
          token: token
        }
      }).then(res => {
        if (res.data.code === "-1") {  //校验失败
          next({ path: '/login' }) //最好跳转到错误页面
        }
        next()
      })
    }
  }
  else {
    next()
  }
})

export default router
