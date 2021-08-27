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
            nonRequiresAuth: true,
            layout: 'main'
        },
        component: () => import('../views/Main.vue')
    },
    {
        path: '/about',
        name: 'About',
        meta: {
            title: 'О нас',
            nonRequiresAuth: true,
            layout: 'main'
        },
        component: () => import('../views/About.vue')
    },
    {
        path: '/terms-conditions',
        name: 'TermsConditions',
        meta: {
            title: 'Условия использования',
            nonRequiresAuth: true,
            layout: 'main'
        },
        component: () => import('../views/TermsConditions.vue')
    },
    {
        path: '/privacy-policy',
        name: 'PrivacyPolicy',
        meta: {
            title: 'Политика конфиденциальности',
            nonRequiresAuth: true,
            layout: 'main'
        },
        component: () => import('../views/PrivacyPolicy.vue')
    },
    {
        path: '/information',
        name: 'Information',
        meta: {
            title: 'Информация',
            nonRequiresAuth: true,
            layout: 'main'
        },
        component: () => import('../views/information/Information.vue')
    },
    {
        path: '/information/presentation',
        name: 'Presentation',
        meta: {
            title: 'Презентация',
            nonRequiresAuth: true,
            layout: 'main'
        },
        component: () => import('../views/information/Presentation.vue')
    },
    {
        path: '/information/for-sponsor',
        name: 'ForSponsor',
        meta: {
            title: 'Для Спонсоров',
            nonRequiresAuth: true,
            layout: 'main'
        },
        component: () => import('../views/information/ForSponsor.vue')
    },
    {
        path: '/information/for-investor',
        name: 'ForInvestor',
        meta: {
            title: 'Для Инвесторов',
            nonRequiresAuth: true,
            layout: 'main'
        },
        component: () => import('../views/information/ForInvestor.vue')
    },
    {
        path: '/information/territory',
        name: 'Territory',
        meta: {
            title: 'Территория Сочи',
            nonRequiresAuth: true,
            layout: 'main'
        },
        component: () => import('../views/information/Territory.vue')
    },
    {
        path: '/information/articles',
        name: 'Articles',
        meta: {
            title: 'Полезные статьи',
            nonRequiresAuth: true,
            layout: 'main'
        },
        component: () => import('../views/information/Articles.vue')
    },
    {
        path: '/information/partners',
        name: 'Partners',
        meta: {
            title: 'Партнёрство',
            nonRequiresAuth: true,
            layout: 'main'
        },
        component: () => import('../views/information/Partners.vue')
    },
    {
        path: '/information/administration',
        name: 'Administration',
        meta: {
            title: 'Администрация',
            nonRequiresAuth: true,
            layout: 'main'
        },
        component: () => import('../views/information/Administration.vue')
    },
    {
        path: '/information/hotel-star',
        name: 'HotelStar',
        meta: {
            title: 'Отель «Звезда»',
            nonRequiresAuth: true,
            layout: 'main'
        },
        component: () => import('../views/information/HotelStar.vue')
    },
    {
        path: '/me',
        name: 'Profile',
        meta: {
            title: 'Моя страница',
            layout: 'user'
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
            next('/?message=not-auth')
        }
    } else {
        next()
    }

    document.title = `${title ? title + ' | ' : ''}${brand}`
})

export default router