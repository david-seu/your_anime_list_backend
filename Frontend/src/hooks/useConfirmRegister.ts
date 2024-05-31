import { useNavigate } from 'react-router-dom'
import { Dispatch, SetStateAction } from 'react'
import { confirmRegister } from '../services/AuthService'

interface UseConfirmRegisterProps {
  token: string
  setSnackbarOpen: Dispatch<SetStateAction<boolean>>
  setSnackbarType: Dispatch<SetStateAction<string>>
  setSnackbarMessage: Dispatch<SetStateAction<string>>
}

const useConfirmRegister = ({
  token,
  setSnackbarOpen,
  setSnackbarType,
  setSnackbarMessage,
}: UseConfirmRegisterProps) => {
  const navigate = useNavigate()

  const handleSubmit = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    e.preventDefault()

    confirmRegister(token)
      .then((result) => {
        if (result.status === 201) {
          setSnackbarType('success')
          setSnackbarMessage('User registered successfully')
          setSnackbarOpen(true)
          navigate('/')
        } else if (result.status === 404) {
          setSnackbarType('error')
          setSnackbarMessage(result.data)
          setSnackbarOpen(true)
        }
      })
      .catch(() => {
        setSnackbarType('error')
        setSnackbarMessage('Error registering user')
        setSnackbarOpen(true)
      })
  }

  return handleSubmit
}

export default useConfirmRegister
