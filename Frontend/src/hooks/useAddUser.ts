import { SetStateAction, Dispatch } from 'react'
import useUserStore from '../store/useUserStore'
import User from '../data/User'
import { addUser } from '../services/UserService'

interface UseAddAnimeProps {
  username: string
  email: string
  password: string
  role: string
  setSnackbarOpen: Dispatch<SetStateAction<boolean>>
  setSnackbarType: Dispatch<SetStateAction<string>>
  setSnackbarMessage: Dispatch<SetStateAction<string>>
}

const useAddUser = ({
  username,
  email,
  password,
  role,
  setSnackbarOpen,
  setSnackbarType,
  setSnackbarMessage,
}: UseAddAnimeProps) => {
  const addUserStore = useUserStore((state) => state.addUser)
  const user = useUserStore((state) => state.currentUser)!

  const handleSubmit = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    e.preventDefault()

    if (!user) return

    const newUser: User = {
      id: -1,
      username,
      email,
      password,
      role: `ROLE_${role.toUpperCase}`,
      checked: false,
      token: '',
      enabled: false,
    }
    addUser(newUser, user!.token)
      .then((result) => {
        if (result.status !== 201) {
          setSnackbarType('error')
          setSnackbarMessage('Failed to add user')
        } else {
          addUserStore(newUser)
          setSnackbarType('success')
          setSnackbarMessage('Successfully added user')
        }
      })
      .catch((error) => {
        if (error.response.status === 400) {
          setSnackbarType('error')
          setSnackbarMessage(error.response.data)
        } else {
          addUserStore(newUser)
          setSnackbarType('warning')
          setSnackbarMessage('Server is down, but user added locally')
        }
      })
      .finally(() => {
        setSnackbarOpen(true)
      })
  }

  return handleSubmit
}

export default useAddUser
