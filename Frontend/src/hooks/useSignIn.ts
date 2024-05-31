import { SetStateAction, Dispatch } from 'react'
import { useNavigate } from 'react-router-dom'
import { signin } from '../services/AuthService'
import useUserStore from '../store/useUserStore'

interface UseLoginProps {
  username: string
  password: string
  setSnackbarOpen: Dispatch<SetStateAction<boolean>>
  setSnackbarType: Dispatch<SetStateAction<string>>
  setSnackbarMessage: Dispatch<SetStateAction<string>>
}

const useSignIn = ({
  username,
  password,
  setSnackbarOpen,
  setSnackbarType,
  setSnackbarMessage,
}: UseLoginProps) => {
  const navigate = useNavigate()
  const signIn = useUserStore((state) => state.signIn)

  const handleSubmit = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    e.preventDefault()

    signin(username, password)
      .then((result) => {
        if (result.status === 400) {
          setSnackbarType('error')
          setSnackbarMessage('Failed to login')
        } else if (result.status === 401) {
          setSnackbarType('error')
          setSnackbarMessage('Invalid password')
        } else if (result.status === 404) {
          setSnackbarType('error')
          setSnackbarMessage('User not found')
        } else {
          signIn(result.data)
          setSnackbarType('warning')
          setSnackbarMessage('2 Factor Authentication required to log')
          navigate('/confirm')
        }
      })
      .catch((error) => {
        if (error.response === undefined) {
          setSnackbarType('error')
          setSnackbarMessage('Server is down, please try again later')
        } else if (
          error.response.status === 404 ||
          error.response.status === 401
        ) {
          setSnackbarType('error')
          setSnackbarMessage(error.response.data)
        }
      })
      .finally(() => {
        setSnackbarOpen(true)
      })
  }

  return handleSubmit
}

export default useSignIn
