import axios from 'axios';

/**
 * 
 * @param {*} params 
 * 유저 정보를 전달 후 토큰을 받음
 */
export function getToken(params){
    return axios({
        url : '/token/getToken',
        method : 'post',
        data : params
    })
}

export function setToken(){
   
}

export function removeToken(){

}