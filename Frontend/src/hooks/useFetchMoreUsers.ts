import User from '../data/User'
import { fetchUsers } from '../services/UserService'
import useUserStore from '../store/useUserStore'

interface UseFetchMoreUsersProps {
  setSnackbarType: (type: string) => void
  setSnackbarMessage: (message: string) => void
  setSnackbarOpen: (open: boolean) => void
}

const useFetchMoreUsers = ({
  setSnackbarType,
  setSnackbarMessage,
  setSnackbarOpen,
}: UseFetchMoreUsersProps) => {
  const currentUser = useUserStore((state) => state.currentUser)
  const page = useUserStore((state) => state.page)
  const setHasMoreStore = useUserStore((state) => state.setHasMore)
  const nextPage = useUserStore((state) => state.nextPage)
  const appendUserStore = useUserStore((state) => state.appendUserList)
  const username = useUserStore((state) => state.username)
  const sort = useUserStore((state) => state.sort)

  const fetchMoreUsers = () => {
    if (!currentUser) return
    nextPage()
    fetchUsers(page, username, currentUser!.token, sort)
      .then((result: { data: User[]; status: number }) => {
        if (result.status === 200) {
          appendUserStore(result.data)
          setSnackbarType('success')
          setSnackbarMessage('Successfully fetched users')
        } else if (result.status === 204) {
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

  return fetchMoreUsers
}

export default useFetchMoreUsers
