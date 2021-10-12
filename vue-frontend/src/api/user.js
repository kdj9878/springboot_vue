import axios from 'axios';

const URL = 'http://localhost:8888/'

export function login(params){
    return axios({
        url: URL + 'api/user/login',
        method : 'post',
        data : params
    });
}

export function logout(params){
    return axios({
        url : '/logout',
        method : 'post',
        data : params
    })
}

export function getInfo(){
    
}