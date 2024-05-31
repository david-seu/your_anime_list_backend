import { useEffect } from 'react'
import 'bootstrap/dist/css/bootstrap.min.css'
import { useNavigate } from 'react-router-dom'
import LinkButton from '../components/LinkButton'
import useUserStore from '../store/useUserStore'
import AddUserForm from '../components/AddUserForm'

export default function AddUser() {
  const user = useUserStore((state) => state.currentUser)!
  const navigate = useNavigate()

  useEffect(() => {
    if (!user) {
      navigate('/')
    }
  }, [user, navigate])

  return (
    <div>
      <LinkButton to="/home">Back</LinkButton>
      <div className="add--container">
        <AddUserForm />
      </div>
    </div>
  )
}
