import Vue from 'vue'
import VueRouter from 'vue-router'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    meta: {
      title: 'Главная страница',
      nonRequiresAuth: true
    },
    component: () => import('../views/Main.vue')
  },
  {
    path: '/about',
    name: 'About',
    meta: {
      title: 'О нас',
      nonRequiresAuth: true
    },
    component: () => import('../views/About.vue')
  },
  {
    path: '/me',
    name: 'Profile',
    meta: {
      title: 'Моя страница'
    },
    component: () => import('../views/profile/Profile.vue')
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
  scrollBehavior() {
    return { x: 0, y: 0 }
  }
})

router.beforeResolve((to, from, next) => {
  if (to.name)
    NProgress.start()
  next()
})

router.afterEach(() => {
  NProgress.done()
})

router.beforeEach((to, from, next) => {
  const { title } = to.meta
  const brand = 'МореВстреч Сочи'

  if (to.matched.some(route => !route.meta.nonRequiresAuth)) {
    if (localStorage.getItem('AuthState')) {
      next()
      return
    } else {
      next('/login')
    }
  } else {
    next()
  }
  
  document.title = `${title ? title + ' | ' : ''}${brand}`
})

export default router