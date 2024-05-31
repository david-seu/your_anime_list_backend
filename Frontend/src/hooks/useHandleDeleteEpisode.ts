import { deleteEpisode } from '../services/EpisodeService'
// eslint-disable-next-line import/no-named-as-default
import useEpisodeStore from '../store/useEpisodeStore'
import useUserStore from '../store/useUserStore'

interface UseHandleDeleteEpisodeProps {
  setSnackbarType: (type: string) => void
  setSnackbarMessage: (message: string) => void
  setSnackbarOpen: (open: boolean) => void
}

const useHandleDeleteEpisode = ({
  setSnackbarType,
  setSnackbarMessage,
  setSnackbarOpen,
}: UseHandleDeleteEpisodeProps) => {
  const deleteEpisodeStore = useEpisodeStore((state) => state.deleteEpisode)
  const episodeList = useEpisodeStore((state) => state.getAllEpisodes)()
  const user = useUserStore((state) => state.currentUser)!

  const handleDeleteEpisode = () => {
    if (!user) return

    episodeList.forEach(async (episode) => {
      if (episode.checked) {
        deleteEpisodeStore(episode.id)

        deleteEpisode(episode.id, user!.token)
          .then((result) => {
            if (result.status === 204) {
              setSnackbarType('success')
              setSnackbarMessage('Successfully deleted episode')
            } else {
              setSnackbarType('error')
              setSnackbarMessage('Failed to delete episode')
            }
          })
          .catch(() => {
            setSnackbarType('warning')
            setSnackbarMessage('Server is down, but episode deleted locally')
          })
          .finally(() => {
            setSnackbarOpen(true)
          })
      }
    })
  }
  return handleDeleteEpisode
}

export default useHandleDeleteEpisode
