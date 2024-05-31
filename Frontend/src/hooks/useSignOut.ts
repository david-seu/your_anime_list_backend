import { useNavigate } from 'react-router-dom'
// eslint-disable-next-line import/no-named-as-default
import useUserStore from '../store/useUserStore'

const useSignOut = () => {
  const signOutStore = useUserStore((state) => state.signOut)
  const navigate = useNavigate()

  const signOut = () => {
    signOutStore()
    navigate('/')
  }

  return signOut
}

export default useSignOut
