import { useEffect } from 'react'
import io from 'socket.io-client'
import Anime from '../data/Anime'

interface UseSocketAnimeProps {
  addAnimeStore: (anime: Anime) => void
  animeList: Anime[]
}

const useSocketAnime = ({ addAnimeStore, animeList }: UseSocketAnimeProps) => {
  useEffect(() => {
    const socket = io('http://localhost:8081')

    socket.on('/topic/anime', (newAnime) => {
      addAnimeStore(newAnime)
    })

    return () => {
      socket.disconnect()
    }
  }, [addAnimeStore, animeList])
}

export default useSocketAnime
