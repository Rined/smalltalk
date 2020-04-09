<template>
    <v-card class="my-2">
        <v-card-text class="text--primary">
            <div>
                <v-avatar v-if="message.author && message.author.picture" size="36px">
                    <img :src="message.author.picture"
                         :alt="message.author.name">
                </v-avatar>
                <v-avatar v-else color="indigo" size="36px">
                    <v-icon color="white">account_circle</v-icon>
                </v-avatar>
                <span class="pl-3">{{ authorName }}</span>
            </div>
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

    export default {
        props: ['message', 'editMessage'],
        components: {Media, CommentList},
        computed: {
            authorName() {
                return this.message.author ? this.message.author.name : 'unknown'
            }
        },
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