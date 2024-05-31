import { SetStateAction, Dispatch } from 'react'
import Anime from '../data/Anime'
import { useAnimeStore } from '../store/useAnimeStore'
import { addAnime } from '../services/AnimeService'
import useUserStore from '../store/useUserStore'

interface UseAddAnimeProps {
  title: string
  score: number
  watched: boolean
  setSnackbarOpen: Dispatch<SetStateAction<boolean>>
  setSnackbarType: Dispatch<SetStateAction<string>>
  setSnackbarMessage: Dispatch<SetStateAction<string>>
}

const useAddAnime = ({
  title,
  score,
  watched,
  setSnackbarOpen,
  setSnackbarType,
  setSnackbarMessage,
}: UseAddAnimeProps) => {
  const addAnimeStore = useAnimeStore((state) => state.addAnime)
  const user = useUserStore((state) => state.currentUser)!

  const handleSubmit = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    e.preventDefault()

    if (!user) return

    const id = -1

    const newAnime: Anime = {
      id,
      title,
      watched,
      score,
      checked: false,
      numEpisodes: 0,
      user,
    }
    addAnime(newAnime, user!.token)
      .then((result) => {
        if (result.status !== 201) {
          setSnackbarType('error')
          setSnackbarMessage('Failed to add anime')
        } else {
          addAnimeStore(newAnime)
          setSnackbarType('success')
          setSnackbarMessage('Successfully added anime')
        }
      })
      .catch((error) => {
        if (error.response.status === 400) {
          setSnackbarType('error')
          setSnackbarMessage(error.response.data)
        } else {
          addAnimeStore(newAnime)
          setSnackbarType('warning')
          setSnackbarMessage('Server is down, but anime added locally')
        }
      })
      .finally(() => {
        setSnackbarOpen(true)
      })
  }

  return handleSubmit
}

export default useAddAnime
