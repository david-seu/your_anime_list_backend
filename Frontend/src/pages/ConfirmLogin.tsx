import { useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import ConfirmLoginForm from '../components/ConfirmLoginForm'
import useUserStore from '../store/useUserStore'
import LinkButton from '../components/LinkButton'

export default function ConfirmLogin() {
  const user = useUserStore((state) => state.currentUser)!
  const navigate = useNavigate()

  useEffect(() => {
    if (!user) {
      navigate('/')
    }
  }, [user, navigate])

  return (
    <div>
      <LinkButton to="/">Back</LinkButton>
      <ConfirmLoginForm />
    </div>
  )
}
