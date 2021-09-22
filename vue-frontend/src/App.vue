// 가장 최상위 컴포먼트


<template>
  <div>
    <header>
      <div>made by 동준</div>
      <router-link to="/" id="home-name">Movie Store</router-link>
        <div id="header-menu">
          <div id="nav" v-for="(key, index) in Menu" :key="index">
            <router-link :to="key.link">{{key.Menu}}</router-link>
          </div>
        </div>
    </header>
    <div id="main">
      <router-view></router-view>
    </div>
  </div>
</template>


<script>
export default {
  data(){
      return{
        Menu : [
          {Menu : 'Movie', link : '/movie'},
          {Menu : 'MyPage', link : '/myPage'},
          {Menu : 'Login', link : '/login'}
        ]
      }
    }
  }
</script>






<style>

header{
  display: flex;
  justify-content: space-between;
}
#home-name{
  font-size: 30px;
}

#header-menu{
  display: flex;
}

#nav {
  display: flex;
  width: 40px;;
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}

#main{
  width: 100vh;
  height: 100vh;
}
</style>
