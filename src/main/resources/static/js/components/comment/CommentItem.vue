<template>
    <v-list-item>
        <user-link :user = "comment.author"></user-link>
        <v-list-item-content>
            <v-list-item-title>{{comment.text}}</v-list-item-title>
        </v-list-item-content>
    </v-list-item>
</template>

<script>
    import UserLink from "components/UserLink.vue";
    export default {
        name: "CommentItem",
        components: {UserLink},
        props: ['comment']
    }
</script>

<style scoped>

</style>