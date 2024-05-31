import axios from 'axios'
import * as rax from 'retry-axios'
import Anime from '../data/Anime'
// eslint-disable-next-line import/no-extraneous-dependencies

const REST_API_BASE_URL = `${import.meta.env.VITE_REST_API_BASE_URL}/anime`
//   'https://mpp-david-spring-app-20240515142023.azuremicroservices.io/api/anime'

rax.attach()

export const fetchAnime = async (
  page: number,
  title: string,
  token: string,
  sort: string
) => {
  console.log(`${REST_API_BASE_URL}/getAll?title=${title}`)
  const result = await axios(`${REST_API_BASE_URL}/getAll`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
    params: {
      page,
      title,
      sort,
    },
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
  return result
}

export const addAnime = async (anime: Anime, token: string) => {
  const data = {
    title: anime.title,
    watched: anime.watched,
    score: anime.score,
  }
  const result = await axios(`${REST_API_BASE_URL}/add`, {
    method: 'POST',
    headers: {
      Authorization: `Bearer ${token}`,
    },
    data,
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

  return result
}

export const getAnime = async (animeId: number, token: string) => {
  const result = await axios(`${REST_API_BASE_URL}/get/${animeId}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
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
  return result
}

export const updateAnime = async (anime: Anime, token: string) => {
  const data = {
    title: anime.title,
    watched: anime.watched,
    score: anime.score,
    user: anime.user,
  }
  const result = await axios(`${REST_API_BASE_URL}/update/${anime.id}`, {
    method: 'PATCH',
    headers: {
      Authorization: `Bearer ${token}`,
    },
    data,
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

  return result
}

export const deleteAnime = async (animeId: number, token: string) => {
  const result = await axios(`${REST_API_BASE_URL}/delete/${animeId}`, {
    method: 'DELETE',
    headers: {
      Authorization: `Bearer ${token}`,
    },
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

  return result
}

export const fetchScoresCount = async (token: string) => {
  const result = await axios(`${REST_API_BASE_URL}/scoresCount`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
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

  return result
}
