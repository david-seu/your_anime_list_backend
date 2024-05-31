import Episode from '../data/Episode'
import { fetchEpisode } from '../services/EpisodeService'
import useEpisodeStore from '../store/useEpisodeStore'
import useUserStore from '../store/useUserStore'

interface UseFetchEpisodesProps {
  setSnackbarType: (type: string) => void
  setSnackbarMessage: (message: string) => void
  setSnackbarOpen: (open: boolean) => void
}

const useFetchEpisodes = ({
  setSnackbarType,
  setSnackbarMessage,
  setSnackbarOpen,
}: UseFetchEpisodesProps) => {
  const page = useEpisodeStore((state) => state.page)
  const setEpisodeStore = useEpisodeStore((state) => state.setEpisodeList)
  const user = useUserStore((state) => state.currentUser)!
  const title = useEpisodeStore((state) => state.title)
  const setHasMore = useEpisodeStore((state) => state.setHasMore)
  const sort = useEpisodeStore((state) => state.sort)

  const getEpisodes = () => {
    if (!user) return
    fetchEpisode(page, title, user!.token, sort)
      .then((result: { data: Episode[]; status: number }) => {
        if (result.status === 200) {
          setEpisodeStore(result.data)
          if (result.data.length < 10) setHasMore(false)
          else setHasMore(true)
          setSnackbarType('success')
          setSnackbarMessage('Successfully fetched episodes')
        } else if (result.status === 204) {
          setEpisodeStore([])
          setHasMore(false)
          setSnackbarType('info')
          setSnackbarMessage('No episodes found')
        }
      })
      .catch((error) => {
        if (error.message === 'Network Error') {
          setSnackbarType('error')
          setSnackbarMessage('Failed to fetch episode, server is down')
        } else {
          setSnackbarType('warning')
          setSnackbarMessage('Unknown error')
        }
      })
      .finally(() => {
        setSnackbarOpen(true)
      })
  }
  return getEpisodes
}

export default useFetchEpisodes
