import useAnimeStore from '../store/useAnimeStore'
import useUserStore from '../store/useUserStore'
import { deleteUser } from '../services/UserService'

interface UseHandleDeleteUserProps {
  setSnackbarType: (type: string) => void
  setSnackbarMessage: (message: string) => void
  setSnackbarOpen: (open: boolean) => void
}

const useHandleDeleteUser = ({
  setSnackbarType,
  setSnackbarMessage,
  setSnackbarOpen,
}: UseHandleDeleteUserProps) => {
  const deleteAnimeStore = useAnimeStore((state) => state.deleteAnime)
  const deleteUserStore = useUserStore((state) => state.deleteUser)
  const animeList = useAnimeStore((state) => state.getAllAnime)()
  const usersList = useUserStore((state) => state.getUsers)()
  const currentUser = useUserStore((state) => state.currentUser)!

  const handleDeleteUser = () => {
    if (!currentUser) return

    usersList.forEach(async (user) => {
      if (user.checked) {
        deleteUserStore(user.id)
        animeList.forEach((anime) => {
          if (anime.user?.id === user.id) {
            deleteAnimeStore(anime.id)
          }
        })
        deleteUser(user.id, user!.token)
          .then((result) => {
            if (result.status === 204) {
              setSnackbarType('success')
              setSnackbarMessage('Successfully deleted user')
            } else {
              setSnackbarType('error')
              setSnackbarMessage('Failed to delete user')
            }
          })
          .catch(() => {
            setSnackbarType('warning')
            setSnackbarMessage('Server is down, but user deleted locally')
          })
          .finally(() => {
            setSnackbarOpen(true)
          })
      }
    })
  }
  return handleDeleteUser
}

export default useHandleDeleteUser
