import { deleteAnime } from '../services/AnimeService'
import useEpisodeStore from '../store/useEpisodeStore'
import useAnimeStore from '../store/useAnimeStore'
import useUserStore from '../store/useUserStore'

interface UseHandleDeleteAnimeProps {
  setSnackbarType: (type: string) => void
  setSnackbarMessage: (message: string) => void
  setSnackbarOpen: (open: boolean) => void
}

const useHandleDeleteAnime = ({
  setSnackbarType,
  setSnackbarMessage,
  setSnackbarOpen,
}: UseHandleDeleteAnimeProps) => {
  const animeList = useAnimeStore((state) => state.getAllAnime)()
  const episodeList = useEpisodeStore((state) => state.getAllEpisodes)()
  const deleteAnimeStore = useAnimeStore((state) => state.deleteAnime)
  const deleteEpisodeStore = useEpisodeStore((state) => state.deleteEpisode)
  const user = useUserStore((state) => state.currentUser)!

  const handleDeleteAnime = () => {
    if (!user) return

    animeList.forEach(async (anime) => {
      if (anime.checked) {
        deleteAnimeStore(anime.id)
        episodeList.forEach((episode) => {
          if (episode.anime?.id === anime.id) {
            deleteEpisodeStore(episode.id)
          }
        })
        deleteAnime(anime.id, user!.token)
          .then((result) => {
            if (result.status === 204) {
              setSnackbarType('success')
              setSnackbarMessage('Successfully deleted anime')
            } else {
              setSnackbarType('error')
              setSnackbarMessage('Failed to delete anime')
            }
          })
          .catch(() => {
            setSnackbarType('warning')
            setSnackbarMessage('Server is down, but anime deleted locally')
          })
          .finally(() => {
            setSnackbarOpen(true)
          })
      }
    })
  }
  return handleDeleteAnime
}

export default useHandleDeleteAnime
