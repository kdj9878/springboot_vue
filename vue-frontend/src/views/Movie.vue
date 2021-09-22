
<template>
    <div>
        <span v-for="(item, index) in data.boardList" :key="index">
            {{item.l_no}}
            {{item.l_name}}
        </span>

    </div>
</template>



<script>
import {onMounted, reactive} from 'vue'

export default {
    name : 'APP',
    components : {
        
    },
    setup(){
        const data = reactive({
            boardList : []
        });
    
        const getList = () => {
            fetch('http://localhost:8081/guest/movie')
            .then(response => response.json())
            .then(response => {
                data.boardList = response
            })
        }

        onMounted(() => {
            getList();
        })
        return {
            data : data,
            getList : getList
            
        }
    }
}
</script>


<style>

</style>