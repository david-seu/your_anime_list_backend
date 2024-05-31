import axios from 'axios'
import * as rax from 'retry-axios'
import Episode from '../data/Episode'
// eslint-disable-next-line import/no-extraneous-dependencies

const REST_API_BASE_URL = `${import.meta.env.VITE_REST_API_BASE_URL}/episode`

// const REST_API_BASE_URL =
//   'https://mpp-david-spring-app-20240515142023.azuremicroservices.io/api/episode'
rax.attach()

export const fetchEpisode = async (
  page: number,
  title: string,
  token: string,
  sort: string
) => {
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

export const addEpisode = async (episode: Episode, token: string) => {
  const data = {
    title: episode.title,
    number: episode.number,
    season: episode.season,
    score: episode.score,
    watched: episode.watched,
    animeTitle: episode.animeTitle,
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

export const getEpisode = async (episodeId: number, token: string) => {
  const result = await axios(`${REST_API_BASE_URL}/get/${episodeId}`, {
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

export const updateEpisode = async (episode: Episode, token: string) => {
  const data = {
    title: episode.title,
    number: episode.number,
    season: episode.season,
    score: episode.score,
    watched: episode.watched,
    animeTitle: episode.animeTitle,
  }
  const result = await axios(`${REST_API_BASE_URL}/update/${episode.id}`, {
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

export const deleteEpisode = async (episodeId: number, token: string) => {
  const result = await axios(`${REST_API_BASE_URL}/delete/${episodeId}`, {
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
