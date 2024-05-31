import { SetStateAction, Dispatch } from 'react'
import Episode from '../data/Episode'
import { useEpisodeStore } from '../store/useEpisodeStore'
import { addEpisode } from '../services/EpisodeService'
import useUserStore from '../store/useUserStore'

interface UseAddEpisodeProps {
  title: string
  number: number
  season: number
  score: number
  watched: boolean
  animeTitle: string
  setSnackbarOpen: Dispatch<SetStateAction<boolean>>
  setSnackbarType: Dispatch<SetStateAction<string>>
  setSnackbarMessage: Dispatch<SetStateAction<string>>
}

const useAddEpisode = ({
  title,
  number,
  season,
  score,
  watched,
  animeTitle,
  setSnackbarOpen,
  setSnackbarType,
  setSnackbarMessage,
}: UseAddEpisodeProps) => {
  const addEpisodeStore = useEpisodeStore((state) => state.addEpisode)
  const deleteEpisodeStore = useEpisodeStore((state) => state.deleteEpisode)
  const user = useUserStore((state) => state.currentUser)!

  const handleSubmit = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    e.preventDefault()
    if (!user) return

    const id = -1

    const newEpisode: Episode = {
      id,
      title,
      number,
      season,
      score,
      watched,
      checked: false,
      animeTitle,
    }
    addEpisodeStore(newEpisode)

    addEpisode(newEpisode, user!.token)
      .then((result) => {
        if (result.status !== 201) {
          addEpisodeStore(newEpisode)
          setSnackbarType('error')
          setSnackbarMessage('Failed to add episode')
        } else {
          addEpisodeStore(newEpisode)
          setSnackbarType('success')
          setSnackbarMessage('Successfully added episode')
        }
      })
      .catch((error) => {
        if (error.message === 'Network Error') {
          setSnackbarType('warning')
          setSnackbarMessage('Server is down, but episode added locally')
        } else if (error.response.status === 404) {
          setSnackbarType('error')
          deleteEpisodeStore(id)
          setSnackbarMessage('Failed to add episode, invalid anime title')
        } else {
          addEpisodeStore(newEpisode)
          setSnackbarType('error')
          setSnackbarMessage('Failed to add episode, unknown error')
        }
      })
      .finally(() => {
        setSnackbarOpen(true)
      })
  }

  return handleSubmit
}

export default useAddEpisode
