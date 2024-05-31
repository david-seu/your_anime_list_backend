import axios from 'axios'
import User from '../data/User'
// eslint-disable-next-line import/no-extraneous-dependencies

// const REST_API_BASE_URL = 'http://localhost:8081/api/auth'

const REST_API_BASE_URL = `${import.meta.env.VITE_REST_API_BASE_URL}/auth`

// const REST_API_BASE_URL =
//   'https://mpp-david-spring-app-20240515142023.azuremicroservices.io/api/auth'

const apiAuth = axios.create({
  baseURL: REST_API_BASE_URL,
})

export const signup = async (user: User) => {
  const role = user.role.toUpperCase()
  const data = {
    username: user.username,
    password: user.password,
    email: user.email,
    enabled: false,
    role: `ROLE_${role}`,
  }
  const result = await apiAuth.post('/signUp', data)
  return result
}

export const signin = async (username: string, password: string) => {
  const data = {
    username,
    password,
  }
  const result = await apiAuth.post('/signIn', data)
  return result
}

export const confirmRegister = async (token: string) => {
  const result = await apiAuth.get(`/signUp/confirm?token=${token}`)
  return result
}

export const confirmLogin = async (code: number) => {
  const result = await apiAuth.get(`/signIn/confirm?code=${code}`)
  return result
}
