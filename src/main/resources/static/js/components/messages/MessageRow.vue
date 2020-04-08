<template>
    <v-card class="my-2">
        <v-card-text class="text--primary">
            <i>({{ message.id }})</i>
            {{ message.text }}
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

    export default {
        props: ['message', 'editMessage'],
        components: {Media, CommentList},
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