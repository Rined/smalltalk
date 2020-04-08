import Vue from 'vue'

const comments = Vue.resource('/comment{/id}');

export default {
    add: msg => comments.save({}, msg)
}