// eslint-disable-next-line import/no-named-as-default
import Episode from '../data/Episode'
import { fetchEpisode } from '../services/EpisodeService'
import useAnimeStore from '../store/useAnimeStore'
import useEpisodeStore from '../store/useEpisodeStore'
// eslint-disable-next-line import/no-named-as-default
import useUserStore from '../store/useUserStore'

interface UseFetchMoreEpisodesProps {
  setSnackbarType: (type: string) => void
  setSnackbarMessage: (message: string) => void
  setSnackbarOpen: (open: boolean) => void
}

const useFetchMoreEpisodes = ({
  setSnackbarType,
  setSnackbarMessage,
  setSnackbarOpen,
}: UseFetchMoreEpisodesProps) => {
  const user = useUserStore((state) => state.currentUser)
  const page = useAnimeStore((state) => state.page)
  const setHasMoreStore = useEpisodeStore((state) => state.setHasMore)
  const nextPage = useEpisodeStore((state) => state.nextPage)
  const appendEpisodeList = useEpisodeStore((state) => state.appendEpisodeList)
  const title = useEpisodeStore((state) => state.title)
  const sort = useEpisodeStore((state) => state.sort)

  const fetchMoreEpisodes = () => {
    if (!user) return
    nextPage()
    fetchEpisode(page, title, user!.token, sort)
      .then((result: { data: Episode[]; status: number }) => {
        if (result.status === 200) {
          appendEpisodeList(result.data)
          setSnackbarType('success')
          setSnackbarMessage('Successfully fetched episodes')
        } else if (result.status === 204) {
          setHasMoreStore(false)
          setSnackbarType('info')
          setSnackbarMessage('No episodes found')
        }
      })
      .catch((error: { message: string }) => {
        if (error.message === 'Network Error') {
          setSnackbarType('error')
          setSnackbarMessage('Failed to fetch episodes, server is down')
        } else {
          setSnackbarType('warning')
          setSnackbarMessage('Unknown error')
        }
      })
      .finally(() => {
        setSnackbarOpen(true)
      })
  }

  return fetchMoreEpisodes
}

export default useFetchMoreEpisodes
