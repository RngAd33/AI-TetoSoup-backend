<template>
  <div class="home-container">
    <a-card class="home-card" :bordered="false">
      <h1 class="title">AI 海龟汤</h1>
      <a-button
        type="primary"
        size="large"
        @click="startNewGame"
        :loading="loading"
      >
        点击开始
      </a-button>
    </a-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import api from '../services/api'

const router = useRouter()
const loading = ref(false)

const startNewGame = async () => {
  loading.value = true
  try {
    // 生成随机房间号
    const roomId = Math.floor(Math.random() * 1000000)
    router.push({ name: 'chat', params: { roomId } })
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.home-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f0f2f5;
}

.home-card {
  width: 400px;
  text-align: center;
  padding: 40px;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.title {
  margin-bottom: 40px;
  color: #1890ff;
  font-size: 32px;
}
</style>
