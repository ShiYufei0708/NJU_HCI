<template>
    <a-comment>
        <template slot="actions">
      <span key="comment-basic-like">
        <a-tooltip title="Like">
          <a-icon type="like" :theme="action === 'liked' ? 'filled' : 'outlined'" @click="like" />
        </a-tooltip>
        <span style="padding-left: '8px';cursor: 'auto'">
          {{ likes }}
        </span>
      </span>
            <span key="comment-basic-dislike">
        <a-tooltip title="Dislike">
          <a-icon
                  type="dislike"
                  :theme="action === 'disliked' ? 'filled' : 'outlined'"
                  @click="dislike"
          />
        </a-tooltip>
        <span style="padding-left: '8px';cursor: 'auto'">
          {{ dislikes }}
        </span>
      </span>
            <span key="comment-basic-reply-to">Reply to</span>
        </template>
        <a slot="author">Han Solo</a>
        <a-avatar
                slot="avatar"
                src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"
                alt="Han Solo"
        />
        <p slot="content">
            We supply a series of design principles, practical patterns and high quality design resources
            (Sketch and Axure), to help people create their product prototypes beautifully and
            efficiently.
        </p>
        <a-tooltip slot="datetime" :title="moment().format('YYYY-MM-DD HH:mm:ss')">
            <span>{{ moment().fromNow() }}</span>
        </a-tooltip>
    </a-comment>
</template>
<script>
    import moment from 'moment';
    export default {
        name:"commentModal",
        data() {
            return {
                likes: 0,
                dislikes: 0,
                action: null,
                moment,
            };
        },
        methods: {
            like() {
                this.likes =1 ;
                this.dislikes = 0;
                this.action = 'liked';
            },
            dislike() {
                this.likes = 0;
                this.dislikes = 1;
                this.action = 'disliked';
            },
        },
    };
</script>