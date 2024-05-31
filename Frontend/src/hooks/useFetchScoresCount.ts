import { fetchScoresCount } from '../services/AnimeService'
import useUserStore from '../store/useUserStore'

interface UseFetchScoresCountProps {
  setScoresCount: (scoresCount: number[]) => void
  setSnackbarType: (type: string) => void
  setSnackbarMessage: (message: string) => void
  setSnackbarOpen: (open: boolean) => void
}

const useFetchScoresCount = ({
  setScoresCount,
  setSnackbarType,
  setSnackbarMessage,
  setSnackbarOpen,
}: UseFetchScoresCountProps) => {
  const currentUser = useUserStore((state) => state.currentUser)

  const getScoresCount = () => {
    if (!currentUser) return

    fetchScoresCount(currentUser.token)
      .then((response) => {
        if (response.status === 200) {
          setScoresCount(response.data)
        } else {
          setSnackbarType('error')
          setSnackbarMessage('Failed to fetch scores count')
          setSnackbarOpen(true)
        }
      })
      .catch((error) => {
        setSnackbarType('error')
        setSnackbarMessage(error.response.message)
        setSnackbarOpen(true)
      })
  }

  return getScoresCount
}

export default useFetchScoresCount
