import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../layout/Layout.vue'
import Classify from '../layout/Classify.vue'
import Space from '../layout/Space.vue'
import Tag from '../layout/Tag.vue'
import Error from '../layout/Error.vue'
import Manage from '../layout/Manage.vue'
import axios from 'axios'
import { ElMessage } from 'element-plus'

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
        meta: {
          title: 'wangblog-首页',
        },
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
        meta: {
          title: 'wangblog-分类',
        },
        component: () => import("@/views/classify/TotalClassify")
      },
      {
        path: 'partClassify/:category',
        name: 'PartClassify',
        meta: {
          title: 'wangblog-详细分类',
        },
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
        meta: {
          title: 'wangblog-标签',
        },
        component: () => import("@/views/tag/TotalTag")
      },
      {
        path: 'partTag/:tag',
        name: 'PartTag',
        meta: {
          title: 'wangblog-详细标签',
        },
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
        meta: {
          title: 'wangblog-个人资料',
        },
        component: () => import("@/views/space/Profile")
      },
      {
        path: 'privacy',
        name: 'Privacy',
        meta: {
          title: 'wangblog-隐私设置',
        },
        component: () => import("@/views/space/Privacy")
      },
      {
        path: 'account',
        name: 'Account',
        meta: {
          title: 'wangblog-账号设置',
        },
        component: () => import("@/views/space/Account")
      },
      {
        path: 'collection',
        name: 'Collection',
        meta: {
          title: 'wangblog-我的收藏',
        },
        component: () => import("@/views/space/Collection")
      },
      {
        path: 'content',
        name: 'Content',
        meta: {
          title: 'wangblog-内容管理',
        },
        component: () => import("@/views/space/Content")
      },
    ]
  },
  {
    path: '/error',
    name: 'Error',
    component: Error,
    children: [
      {
        path: '404',
        name: '404',
        meta: {
          title: '404',
        },
        component: () => import("@/views/error/404")
      },
      {
        path: '500',
        name: '500',
        meta: {
          title: '500',
        },
        component: () => import("@/views/error/404")
      },
      {
        path: 'null',
        name: 'Null',
        meta: {
          title: 'null',
        },
        component: () => import("@/views/error/null")
      },
    ]
  },
  {
    path: '/manage',
    name: 'Manage',
    redirect: '/manage/ofArticle',
    meta: {
      requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
      requirePerms: true,  // 添加该字段，表示进入这个路由是需要权限的
    },
    component: Manage,
    children: [
      {
        path: 'ofArticle',
        name: 'OfArticle',
        meta: {
          title: 'wangblog-文章管理',
        },
        component: () => import("@/views/manage/OfArticle")
      },
      {
        path: 'ofUser',
        name: 'OfUser',
        meta: {
          title: 'wangblog-用户管理',
        },
        component: () => import("@/views/manage/OfUser")
      },
      {
        path: 'ofUserInfo',
        name: 'OfUserInfo',
        meta: {
          title: 'wangblog-用户信息管理',
        },
        component: () => import("@/views/manage/OfUserInfo")
      },
      {
        path: 'ofAnimation',
        name: 'OfAnimation',
        meta: {
          title: 'wangblog-追番管理',
        },
        component: () => import("@/views/manage/OfAnimation")
      },
    ]
  },
  {
    path: '/creation',
    name: 'Creation',
    meta: {
      requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
      requirePerms: true,  // 添加该字段，表示进入这个路由是需要权限的
      title: 'wangblog-写博客',
    },
    component: () => import("@/views/Creation")
  },
  {
    path: '/author',
    name: 'Gleam',
    meta: {
      title: 'wangblog-关于作者',
    },
    component: () => import("@/views/Gleam")
  },
  {
    path: '/article/:id',
    name: 'Article',
    meta: {
      title: 'wangblog-博客',
    },
    component: () => import("@/views/Article"),
    props: true
  },
  {
    path: '/modify/:id',
    name: 'Modify',
    meta: {
      title: 'wangblog-修改博客',
    },
    component: () => import("@/views/Modify"),
    props: true
  },
  {
    path: '/result',
    name: 'Result',
    meta: {
      title: 'wangblog-搜索',
    },
    component: () => import("@/views/Result"),
    props: true
  },
  {
    path: '/animation',
    name: 'Animation',
    meta: {
      requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
      requirePerms: true,  // 添加该字段，表示进入这个路由是需要权限的
      title: 'wangblog-追番',
    },
    component: () => import("@/views/Animation"),
    props: true
  },
  {
    path: '/link',
    name: 'Link',
    meta: {
      title: 'wangblog-友链',
    },
    component: () => import("@/views/Link"),
    props: true
  },
  {
    path: '/login',
    name: 'Login',
    meta: {
      title: 'wangblog-登录',
    },
    component: () => import("@/views/Login")
  },
  {
    path: '/register',
    name: 'Register',
    meta: {
      title: 'wangblog-注册',
    },
    component: () => import("@/views/Register")
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'none',
    redirect: '/error/null',
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
    window.localStorage.removeItem('access-username')
    next()
  }
  else if (to.meta.requireAuth) {// 判断该路由是否需要登录权限
    let token = JSON.parse(window.localStorage.getItem("access-token"))
    if (!token) {
      ElMessage.error("此功能需要登录后才能使用！")
      // next({ path: '/login' })
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
          ElMessage.error(res.data.msg)
          next({ path: '/login' }) //最好跳转到错误页面
        }
        else{
          if(to.meta.requirePerms){
            //校验perms权限
            axios({
              url: "http://" + window.server.ip + ":9090/user/checkPerms",
              method: 'get',
              headers: {
                token: token
              }
            }).then(res => {
              if (res.data.code === "-1") {  //没有manage权限
                ElMessage.error(res.data.msg)
                next({ path: '/' }) //最好跳转到错误页面
              }
              else
                next()
            })
          }
          else{
            next()
          }
        }
      })
    }
  }
  else {
    next()
  }
})

export default router
