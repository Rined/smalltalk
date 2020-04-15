import Vue from 'vue'

const messages = Vue.resource('/message{/id}');

export default {
    add: msg => messages.save({}, msg),
    update: msg => messages.update({id: msg.id}, msg),
    remove: id =>  messages.remove({id}),
    page: page => Vue.http.get('/message',{params: { page }})
}