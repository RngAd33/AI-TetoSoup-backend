import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8080/api'
})

export default {
  getRooms() {
    return api.get('/chat/rooms')
  },
  sendMessage(roomId, message) {
    return api.post(`/chat/${roomId}/send`, null, {
      params: { message }
    })
  }
}
