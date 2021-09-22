<template>
    <section id="login-section">
            <div id="id-div">
                <input id="usernameInput" type="text" placeholder="아이디" v-model="userInfo.username">
            </div>
            <div id="pw-div">
                <input id="passwordInput" type="password" placeholder="비밀번호" v-model="userInfo.password">
            </div>
            <div id="login-button">
                <button @click="doLogin">로그인</button>
            </div>
    </section>
</template>

<script>
import { reactive } from '@vue/reactivity';
import userStore from 'vuex'

export default {
    setup(){
        const userInfo = reactive({
            username : '',
            password : ''
        })

        const store = userStore();
        const doLogin = () =>{
            store.dispatch('login', userInfo);

        };
        return {
            userInfo,
            doLogin
        }

    }

}


</script>

<style>
#login-section{
    position: relative;
    top: 20%;
    left: 70%;
    width: 400px;
    height: auto;
    background-color: bisque;
    display: flex;
    flex-direction: column;
}

#id-div{
    display: flex;
    justify-content: center;
}
#id-div input{
    width: 80%;
    height: 40px;
}
#pw-div{
    display: flex;
    justify-content: center;
}
#pw-div input{
    width: 80%;
    height: 40px;
}

#login-button{
    display: flex;
    justify-content: center;
}
#login-button button{
    margin-top: 10px;
    width: 80%;
    height: 35px;
}

</style>