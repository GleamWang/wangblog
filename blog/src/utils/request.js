import axios from 'axios'
import router from '@/router'


const request = axios.create({
    timeout: 50000
})

// request 拦截器
// 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';

    return config
}, error => {
    return Promise.reject(error)
});

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
    response => {
        let res = response.data;
        // 如果是返回的文件
        if (response.config.responseType === 'blob') {
            return res
        }
        // 兼容服务端返回的字符串数据
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        return res;
    },
    error => {
        // console.log('err' + error) // for debug
        // return Promise.reject(error)
        if (error && error.response) {
            switch (error.response.status) {
                case 404:
                    router.push({
                        name: '404',
                    });
                    // error.message = '请求出错(404)'
                    break;

                case 500:
                    router.push({
                        name: '500',
                    });
                    //  error.message = '服务器错误(500)';
                    break;

                default: error.message = `连接出错(${error.response.status})!`;
            }
        }
        return Promise.reject(error);
    }
)


export default request