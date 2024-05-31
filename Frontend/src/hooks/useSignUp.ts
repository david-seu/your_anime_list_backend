import { SetStateAction, Dispatch } from 'react'
import { useNavigate } from 'react-router-dom'
import { signup } from '../services/AuthService'
import User from '../data/User'

interface UseSignUpProps {
  username: string
  password: string
  email: string
  role: string
  setSnackbarOpen: Dispatch<SetStateAction<boolean>>
  setSnackbarType: Dispatch<SetStateAction<string>>
  setSnackbarMessage: Dispatch<SetStateAction<string>>
}

const useSignUp = ({
  username,
  password,
  email,
  role,
  setSnackbarOpen,
  setSnackbarType,
  setSnackbarMessage,
}: UseSignUpProps) => {
  const navigate = useNavigate()

  const handleSubmit = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    e.preventDefault()

    const id = -1

    const newUser: User = {
      id,
      username,
      password,
      email,
      token: '',
      role,
      enabled: false,
    }

    signup(newUser)
      .then((result) => {
        if (result.status === 404) {
          setSnackbarType('error')
          setSnackbarMessage('Failed to add user')
        } else {
          setSnackbarType('success')
          setSnackbarMessage('Successfully added user')
          setSnackbarOpen(true)
          navigate('/register/confirm')
        }
      })
      .catch((error) => {
        if (error.response.status === 400) {
          setSnackbarType('error')
          setSnackbarMessage(error.response.data)
        } else {
          setSnackbarType('warning')
          setSnackbarMessage('Server is down, please try again later')
        }
      })
      .finally(() => {
        setSnackbarOpen(true)
      })
  }

  return handleSubmit
}

export default useSignUp
