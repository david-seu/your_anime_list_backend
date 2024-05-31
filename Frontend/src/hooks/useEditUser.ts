import { SetStateAction, Dispatch } from 'react'
import useUserStore from '../store/useUserStore'
import User from '../data/User'
import { updateUser } from '../services/UserService'

interface UseEditUserProps {
  id: string
  username: string
  email: string
  password: string
  role: string
  setSnackbarOpen: Dispatch<SetStateAction<boolean>>
  setSnackbarType: Dispatch<SetStateAction<string>>
  setSnackbarMessage: Dispatch<SetStateAction<string>>
}

const useEditUser = ({
  id,
  username,
  email,
  password,
  role,
  setSnackbarOpen,
  setSnackbarType,
  setSnackbarMessage,
}: UseEditUserProps) => {
  const updateUserStore = useUserStore((state) => state.updateUser)
  const currentUser = useUserStore((state) => state.currentUser)!

  const handleSubmit = (e: React.FormEvent<HTMLButtonElement>): void => {
    e.preventDefault()
    if (!currentUser) return

    const updatedUser: User = {
      id: Number(id),
      username,
      email,
      password,
      role,
      enabled: true,
      token: '',
      checked: false,
    }
    updateUserStore(updatedUser)

    updateUser(updatedUser, currentUser!.token)
      .then((result) => {
        if (result.status === 200) {
          setSnackbarType('success')
          setSnackbarMessage('User updated successfully')
        } else {
          setSnackbarType('error')
          setSnackbarMessage('Error updating user')
        }
      })
      .catch(() => {
        setSnackbarType('warning')
        setSnackbarMessage('Server is down, but user updated locally')
      })
      .finally(() => {
        setSnackbarOpen(true)
      })
  }

  return handleSubmit
}

export default useEditUser
