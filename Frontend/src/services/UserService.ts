import axios from 'axios'
import * as rax from 'retry-axios'
import User from '../data/User'
// eslint-disable-next-line import/no-extraneous-dependencies

const REST_API_BASE_URL = `${import.meta.env.VITE_REST_API_BASE_URL}/user`

rax.attach()

const apiUser = axios.create({
  baseURL: REST_API_BASE_URL,
  raxConfig: {
    retry: 100,
    noResponseRetries: 100,
    httpMethodsToRetry: ['GET', 'POST', 'PATCH', 'DELETE'],
    retryDelay: 10000,
    onRetryAttempt: (err) => {
      const cfg = rax.getConfig(err)
      console.log(`Retry attempt #${cfg?.currentRetryAttempt}`)
    },
  },
})

export const fetchUsers = async (
  page: number,
  username: string,
  token: string,
  sort: string
) => {
  const result = await apiUser.get(`/getAll`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
    params: {
      page,
      username,
      sort,
    },
  })
  return result
}

export const addUser = async (user: User, token: string) => {
  const data = {
    username: user.username,
    email: user.email,
    role: user.role,
    password: user.password,
    enabled: 'true',
  }
  const result = await apiUser.post('/add', data, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  })

  return result
}

export const getUser = async (userId: number, token: string) => {
  const result = await apiUser.get(`/get/${userId}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  })

  return result
}

export const updateUser = async (user: User, token: string) => {
  const data = {
    username: user.username,
    email: user.email,
    password: user.password,
    role: user.role,
  }
  const result = await apiUser.patch(`/update/${user.id}`, data, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  })

  return result
}

export const deleteUser = async (userId: number, token: string) => {
  const result = await apiUser.delete(`/delete/${userId}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  })
  return result
}
