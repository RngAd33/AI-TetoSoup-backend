<template>
  <div :class="['message', isUser ? 'user-message' : 'ai-message']">
    <div class="message-avatar">
      <a-avatar :src="isUser ? userAvatar : aiAvatar" />
    </div>
    <div class="message-content">
      <div class="message-bubble">
        {{ message.content }}
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps, computed } from 'vue'
import { UserOutlined, RobotOutlined } from '@ant-design/icons-vue'

const props = defineProps({
  message: {
    type: Object,
    required: true
  },
  isUser: {
    type: Boolean,
    default: false
  }
})

const userAvatar = computed(() => {
  return 'https://joeschmoe.io/api/v1/random'
})

const aiAvatar = computed(() => {
  return 'https://joeschmoe.io/api/v1/random'
})
</script>

<style scoped>
.message {
  display: flex;
  margin-bottom: 16px;
}

.user-message {
  flex-direction: row-reverse;
}

.ai-message {
  flex-direction: row;
}

.message-avatar {
  margin: 0 12px;
}

.message-content {
  max-width: 70%;
}

.message-bubble {
  padding: 12px 16px;
  border-radius: 18px;
  line-height: 1.4;
  word-wrap: break-word;
}

.user-message .message-bubble {
  background-color: #1890ff;
  color: white;
}

.ai-message .message-bubble {
  background-color: #f0f0f0;
  color: rgba(0, 0, 0, 0.85);
}
</style>
