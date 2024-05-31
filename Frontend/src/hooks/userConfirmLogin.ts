import { useNavigate } from 'react-router-dom'
import { Dispatch, SetStateAction } from 'react'
import { confirmLogin } from '../services/AuthService'
import useUserStore from '../store/useUserStore'
import User from '../data/User'

interface UseConfirmLoginProps {
  code: number
  setSnackbarOpen: Dispatch<SetStateAction<boolean>>
  setSnackbarType: Dispatch<SetStateAction<string>>
  setSnackbarMessage: Dispatch<SetStateAction<string>>
}

const useConfirmLogin = ({
  code,
  setSnackbarOpen,
  setSnackbarType,
  setSnackbarMessage,
}: UseConfirmLoginProps) => {
  const navigate = useNavigate()

  const signIn = useUserStore((state) => state.signIn)

  const handleSubmit = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    e.preventDefault()

    confirmLogin(code)
      .then((result) => {
        if (result.status === 200) {
          const user: User = {
            id: result.data.id,
            username: result.data.username,
            email: result.data.email,
            role: result.data.role,
            enabled: true,
            password: '',
            token: result.data.token,
          }
          signIn(user)
          setSnackbarType('success')
          setSnackbarMessage('User logged successfully')
          setSnackbarOpen(true)
          navigate('/home')
        } else if (result.status === 404) {
          setSnackbarType('error')
          setSnackbarMessage(result.data)
          setSnackbarOpen(true)
        }
      })
      .catch((error) => {
        setSnackbarType('error')
        setSnackbarMessage(error.response.data)
        setSnackbarOpen(true)
      })
  }

  return handleSubmit
}

export default useConfirmLogin
