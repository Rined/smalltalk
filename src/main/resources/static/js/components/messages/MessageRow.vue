<template>
    <v-card class="my-2">
        <v-card-text class="text--primary">
            <user-link :user="message.author" size="36"></user-link>
            <div class="pt-3">
                {{ message.text }}
            </div>
        </v-card-text>
        <media v-if="message.link" :message="message"></media>
        <v-card-actions>
            <v-btn @click="edit" small rounded>
                Edit
            </v-btn>
            <v-btn @click="del" icon small>
                <v-icon>delete</v-icon>
            </v-btn>
        </v-card-actions>
        <comment-list
                :comments="message.comments"
                :message-id="message.id"></comment-list>
    </v-card>
</template>

<script>
    import {mapActions} from 'vuex'
    import Media from 'components/media/Media.vue'
    import CommentList from 'components/comment/CommentList.vue'
    import UserLink from "components/UserLink.vue";

    export default {
        props: ['message', 'editMessage'],
        components: {UserLink, Media, CommentList},
        methods: {
            ...mapActions(['removeMessageAction']),
            edit() {
                this.editMessage(this.message);
            },
            del() {
                this.removeMessageAction(this.message);
            }
        }
    }
</script>

<style>

</style>