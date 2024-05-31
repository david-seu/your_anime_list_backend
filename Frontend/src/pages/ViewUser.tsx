/* eslint-disable import/no-named-as-default */
import { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import LinkButton from '../components/LinkButton'
import useUserStore from '../store/useUserStore'
import useFetchUserById from '../hooks/useFetchUserById'

export default function ViewUser(): JSX.Element {
  const [username, setUsername] = useState<string>('')
  const [email, setEmail] = useState<string>('')
  const [password, setPassword] = useState<string>('')
  const [role, setRole] = useState<string>('')

  const { id } = useParams<{ id: string }>() as unknown as { id: string }

  const currentUser = useUserStore((state) => state.currentUser)!
  const navigate = useNavigate()

  useEffect(() => {
    if (!currentUser) {
      navigate('/')
    }
  }, [currentUser, navigate])

  useFetchUserById({
    id,
    setUsername,
    setEmail,
    setPassword,
    setRole,
  })

  return (
    <div>
      <LinkButton to="/home">Back</LinkButton>
      <LinkButton to={`/editUser/${id}`}>Edit</LinkButton>
      <div className="view--container">
        <h1>{username}</h1>
        <p>Email: {email}</p>
        <p>Password: {password}</p>
        <p>Role: {role}</p>
      </div>
    </div>
  )
}
