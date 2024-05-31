import Anime from '../data/Anime'
import { fetchAnime } from '../services/AnimeService'
// eslint-disable-next-line import/no-named-as-default
import useAnimeStore from '../store/useAnimeStore'
// eslint-disable-next-line import/no-named-as-default
import useUserStore from '../store/useUserStore'

interface UseFetchAnimeProps {
  setSnackbarType: (type: string) => void
  setSnackbarMessage: (message: string) => void
  setSnackbarOpen: (open: boolean) => void
}

const useFetchAnime = ({
  setSnackbarType,
  setSnackbarMessage,
  setSnackbarOpen,
}: UseFetchAnimeProps) => {
  const user = useUserStore((state) => state.currentUser)!
  const page = useAnimeStore((state) => state.page)
  const setAnimeStore = useAnimeStore((state) => state.setAnimeList)
  const setHasMoreStore = useAnimeStore((state) => state.setHasMore)
  const title = useAnimeStore((state) => state.title)
  const sort = useAnimeStore((state) => state.sort)

  const getAnime = () => {
    if (!user) return
    fetchAnime(page, title, user!.token, sort)
      .then((result: { data: Anime[]; status: number }) => {
        if (result.status === 200) {
          setAnimeStore(result.data)
          if (result.data.length < 10) setHasMoreStore(false)
          else setHasMoreStore(true)
          setSnackbarType('success')
          setSnackbarMessage('Successfully fetched anime')
        } else if (result.status === 204) {
          setAnimeStore([])
          setHasMoreStore(false)
          setSnackbarType('info')
          setSnackbarMessage('No anime found')
        }
      })
      .catch((error: { message: string }) => {
        if (error.message === 'Network Error') {
          setSnackbarType('error')
          setSnackbarMessage('Failed to fetch anime, server is down')
        } else {
          setSnackbarType('warning')
          setSnackbarMessage('Unknown error')
        }
      })
      .finally(() => {
        setSnackbarOpen(true)
      })
  }
  return getAnime
}

export default useFetchAnime
