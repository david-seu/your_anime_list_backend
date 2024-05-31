import useUserStore from '../store/useUserStore'
import { fetchUsers } from '../services/UserService'
import User from '../data/User'

interface UseFetchUserProps {
  setSnackbarType: (type: string) => void
  setSnackbarMessage: (message: string) => void
  setSnackbarOpen: (open: boolean) => void
}

const useFetchUsers = ({
  setSnackbarType,
  setSnackbarMessage,
  setSnackbarOpen,
}: UseFetchUserProps) => {
  const user = useUserStore((state) => state.currentUser)!
  const page = useUserStore((state) => state.page)
  const setUserStore = useUserStore((state) => state.setUsers)
  const setHasMoreStore = useUserStore((state) => state.setHasMore)
  const username = useUserStore((state) => state.username)
  const sort = useUserStore((state) => state.sort)

  const getUsers = () => {
    if (!user) return
    fetchUsers(page, username, user!.token, sort)
      .then((result: { data: User[]; status: number }) => {
        if (result.status === 200) {
          setUserStore(result.data)
          if (result.data.length < 10) setHasMoreStore(false)
          else setHasMoreStore(true)
          setSnackbarType('success')
          setSnackbarMessage('Successfully fetched users')
        } else if (result.status === 204) {
          setUserStore([])
          setHasMoreStore(false)
          setSnackbarType('info')
          setSnackbarMessage('No users found')
        }
      })
      .catch((error: { message: string }) => {
        if (error.message === 'Network Error') {
          setSnackbarType('error')
          setSnackbarMessage('Failed to fetch users, server is down')
        } else {
          setSnackbarType('warning')
          setSnackbarMessage('Unknown error')
        }
      })
      .finally(() => {
        setSnackbarOpen(true)
      })
  }
  return getUsers
}

export default useFetchUsers
