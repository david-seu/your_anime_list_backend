import { useEffect } from 'react'
import { useAnimeStore } from '../store/useAnimeStore'
import { getAnime } from '../services/AnimeService'
import useUserStore from '../store/useUserStore'
import User from '../data/User'

interface UseFetchAnimeByIdProps {
  id: string
  setTitle: (title: string) => void
  setScore: (score: number) => void
  setWatched: (watched: boolean) => void
  setNumEpisodes: (numEpisodes: number) => void
  setUser: (user: User) => void
}

const useFetchAnimeById = ({
  id,
  setTitle,
  setScore,
  setWatched,
  setNumEpisodes,
  setUser,
}: UseFetchAnimeByIdProps) => {
  const getAnimeStore = useAnimeStore((state) => state.getAnime)
  const user = useUserStore((state) => state.currentUser)

  useEffect(() => {
    if (!user) return

    if (id) {
      const anime = getAnimeStore(Number(id))

      getAnime(Number(id), user!.token)
        .then(
          (result: {
            data: {
              title: string
              score: number
              watched: boolean
              numEpisodes: number
              user: User
            }
          }) => {
            setTitle(result.data.title)
            setScore(result.data.score)
            setWatched(result.data.watched)
            setNumEpisodes(result.data.numEpisodes)
            setUser(result.data.user)
          }
        )
        .catch(() => {
          if (anime) {
            setTitle(anime.title)
            setScore(anime.score)
            setWatched(anime.watched)
            setNumEpisodes(anime.numEpisodes)
          }
        })
    }
  }, [
    id,
    setTitle,
    setScore,
    setWatched,
    setNumEpisodes,
    getAnimeStore,
    user,
    setUser,
  ])
}

export default useFetchAnimeById
