import { useEffect } from 'react'
import useUserStore from '../store/useUserStore'
import { getUser } from '../services/UserService'

interface UseFetchUserByIdProps {
  id: string
  setUsername: (username: string) => void
  setEmail: (email: string) => void
  setPassword: (password: string) => void
  setRole: (role: string) => void
}

const useFetchUserById = ({
  id,
  setUsername,
  setEmail,
  setPassword,
  setRole,
}: UseFetchUserByIdProps) => {
  const getUserStore = useUserStore((state) => state.getUser)
  const currentUser = useUserStore((state) => state.currentUser)

  useEffect(() => {
    if (!currentUser) return

    if (id) {
      const user = getUserStore(Number(id))

      getUser(Number(id), currentUser!.token)
        .then(
          (result: {
            data: {
              username: string
              email: string
              password: string
              role: string
            }
          }) => {
            setUsername(result.data.username)
            setEmail(result.data.email)
            setPassword(result.data.password)
            setRole(result.data.role)
          }
        )
        .catch(() => {
          if (user) {
            setUsername(user.username)
            setEmail(user.email)
            setPassword(user.password)
            setRole(user.role)
          }
        })
    }
  }, [
    id,
    currentUser,
    getUserStore,
    setUsername,
    setEmail,
    setPassword,
    setRole,
  ])
}

export default useFetchUserById
