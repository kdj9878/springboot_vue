import axios from 'axios';

export function login(params){
    return axios({
        url: '/Login/do',
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