import {createStore} from 'vuex'
import userStore from './user/userStore'

export const store = createStore({
    modules :{
        userStore : userStore
    }
})